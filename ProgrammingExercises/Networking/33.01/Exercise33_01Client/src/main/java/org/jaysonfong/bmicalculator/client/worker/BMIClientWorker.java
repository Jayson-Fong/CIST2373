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
package org.jaysonfong.bmicalculator.client.worker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Base64;
import org.jaysonfong.bmicalculator.client.App;

/**
 * Process send & reciept
 * @author Jayson Fong <contact@jaysonfong.org>
 */
public class BMIClientWorker implements Runnable {
    
    private final String data;
    
    // Input should be base64!
    public BMIClientWorker(String sendData) {
        this.data = sendData;
    } 
    
    @Override
    public void run() {
        App app = App.getApp();
        
        try {
            // Create socket
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(App.SERVER_HOST, App.getServerPort()), 3000);
            
            // Send to BMI server
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            output.write(this.data.getBytes());
            output.flush();

            // Retrieve result
            DataInputStream input = new DataInputStream(socket.getInputStream());
        
            String result = new String(
                Base64.getDecoder().decode(
                    input.readAllBytes()
                )
            );
            app.addDataLine(result);
            
            output.close();
            input.close();
            socket.close();
        } catch (UnknownHostException uhe) {
            app.addDataLine("Unable to Connect to Host: " + uhe.getLocalizedMessage());
        } catch (IOException ioe) {
            app.addDataLine("Failed to Connect to Server");
        } catch (NumberFormatException nfe) {
            app.addDataLine("Invalid Port Format: " + nfe.getLocalizedMessage());
        }
    }
    
}
