/*
 * The MIT License
 *
 * Copyright 2021 Jayson Fong <contact@jaysonfong.org>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jaysonfong.bmicalculator.server.worker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Base64;
import java.util.Date;
import org.jaysonfong.bmicalculator.server.App;
import org.json.JSONObject;

/**
 * Processes connections
 * @author Jayson Fong <contact@jaysonfong.org>
 */
public class BMIWorker implements Runnable {
    
    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    
    public BMIWorker(Socket socket) throws IOException {
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
    }
    
    @Override
    public void run() {
        App app = App.getApp();
        
        app.addDataLine("Connected to a client at " + new Date());
        try {  
            JSONObject inputData = new JSONObject(
                new String(
                    Base64.getDecoder().decode(
                        this.input.readNBytes(this.input.available())
                    )
                )
            );

            // Check if the data is good
            if (!inputData.has("weight") || !inputData.has("height")) {
                String message = "Malformed Data Error";
                app.addDataLine(message);
                this.output.write(
                    Base64.getEncoder().encode(
                        message.getBytes()
                    )
                );
                this.output.flush();
            } else {            
                double weight = this.getDouble(inputData, "weight");
                double height = this.getDouble(inputData, "height");
                double bmi = this.calculateBMI(weight, height);
            
                String message = String.format(
                    "Weight: %.2f\nHeight: %.2f\nBMI is %.2f. %s",
                    weight, height, bmi, this.getInterpretation(bmi)
                );
                // Adds result to UI
                app.addDataLine(message);

                // Writes result to stream
                this.output.write(
                    Base64.getEncoder().encode(
                        message.getBytes()
                    )
                );
                this.output.flush();
                
            }
            
            this.output.close();
            this.input.close();
            this.socket.close();
        } catch (IOException e) {
            app.addDataLine("Failed to Process BMI information");
        }
    }
    
    private double getDouble(JSONObject json, String key) {
        return json.getDouble(key);
    }
    
    // Multiply the weight (lb) divided by the height (in) squared by 703 and round to the nearest hundreadth
    private double calculateBMI(double weight, double height) {
        return ((int) (70300 * (weight / Math.pow(height, 2)))) / 100.0;
    }
    
    private String getInterpretation(double bmi) {
        if (bmi < 18.5) return "Underweight";
        else if (18.5 <= bmi && bmi < 25.0) return "Normal";
        else if (25.0 <= bmi && bmi < 30.0) return "Overweight";
        else return "Obese";
    }
    
}
