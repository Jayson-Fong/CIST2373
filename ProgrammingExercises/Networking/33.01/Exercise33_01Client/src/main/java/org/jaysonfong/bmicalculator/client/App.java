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
package org.jaysonfong.bmicalculator.client;

import java.util.Base64;
import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.jaysonfong.bmicalculator.client.worker.BMIClientWorker;
import org.json.JSONObject;

/**
 *
 * @author Jayson Fong <contact@jaysonfong.org>
 */
public class App extends Application {
    
    public static final String SERVER_HOST = "localhost";
    private static StringProperty SERVER_PORT;

    private static App APP;
        
    private TextField weight;
    private TextField height;
    private TextField port;
    private TextArea dataText;
    
    @Override
    public void start(Stage stage) {
        App.APP = this;
        
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        
        // Upper Section
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(0, 0, 10, 0));
        
        // Weight Field
        this.weight = new TextField();
        this.weight.promptTextProperty().set("Weight (lb)");
        grid.add(new Label("Weight in Pounds"), 0, 0);
        grid.add(this.weight, 1, 0);
        
        // Port Field
        this.port = new TextField();
        this.port.promptTextProperty().set("Port");
        App.SERVER_PORT = this.port.textProperty();
        this.port.setText("8000");
        grid.add(new Label("Port"), 2, 0);
        grid.add(this.port, 3, 0);
        
        // Height Field
        this.height = new TextField();
        this.height.promptTextProperty().set("Height (in)");
        grid.add(new Label("Height in Inches"), 0, 1);
        grid.add(this.height, 1, 1);
        
        // Submit button
        grid.add(this.getSubmitButton(), 2, 1);
        
        root.setTop(grid);
        
        // Message Section
        this.dataText = new TextArea();
        root.setCenter(this.dataText);
        this.dataText.setEditable(false);
        
        // Footer
        root.setBottom(new Label("Created by Jayson Fong 2021"));
        
        // Show scene
        var scene = new Scene(root, 640, 480);
        stage.setTitle("BMI Calculation Client");
        stage.setScene(scene);
        stage.show();
    }
    
    public Button getSubmitButton() {
        Button button = new Button("Submit");
        
        button.setOnMouseClicked((MouseEvent e) -> {
            try {
                double weightVal = Double.parseDouble(this.weight.getText());
                double heightVal = Double.parseDouble(this.height.getText());
                
                // Create JSON Data
                JSONObject json = new JSONObject();
                json.put("weight", weightVal);
                json.put("height", heightVal);
                
                String base = Base64.getEncoder().encodeToString(
                    json.toString().getBytes()
                );
                
                new Thread(new BMIClientWorker(base)).start();
            } catch (NumberFormatException nfe) {
                this.addDataLine("Malformed Input: Try Again");
            }
        });
        
        return button;
    }
    
    // Adds a line to the textarea
    public void addDataLine(String line) {
        this.dataText.textProperty().set(
            String.format(
                "%s\n%s",
                this.dataText.getText(),
                line
            )
        );
    }
    
    // Returns...well...this.
    public static App getApp() {
        if (App.APP == null) {
            return new App();
        }
        return App.APP;
    }
    
    public static int getServerPort() {
        return Integer.valueOf(App.SERVER_PORT.get());
    }

    public static void main(String[] args) {
        launch();
    }

}