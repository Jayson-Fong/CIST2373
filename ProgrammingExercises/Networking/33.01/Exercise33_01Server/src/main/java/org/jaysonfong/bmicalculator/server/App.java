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
package org.jaysonfong.bmicalculator.server;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Jayson Fong <contact@jaysonfong.org>
 */
public class App extends Application {
    
    private static App APP;

    public static final int APP_PORT = 8000;
    public static final int THREADS = 3;
        
    private TextArea data;
    
    private Server server;
    
    @Override
    public void start(Stage stage) {
        // Creates the...lame...user interface.
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        this.data = new TextArea();
        data.setEditable(false);
        root.setCenter(this.data);
        
        // Used for adding messages to the UI by threads.
        App.APP = this;
        
        // Descriptor
        root.setTop(new Label(this.getDescriptor()));
        
        // Copyright
        root.setBottom(new Label("Created by Jayson Fong 2021"));
        
        // Display the scene
        var scene = new Scene(root, 640, 480);
        stage.setTitle("BMI Calculation Server");
        stage.setScene(scene);
        stage.show();
        
        // Start the server
        this.server = new Server();
        new Thread(this.server).start();
    }
    
    // Adds a line to the textarea
    public void addDataLine(String line) {
        this.data.textProperty().set(
            String.format(
                "%s\n%s",
                this.data.textProperty().get(),
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
    
    private String getDescriptor() {
        return String.format(
            "BMI Calculation Server Running on Port %s with %s Threads",
            App.APP_PORT,
            App.THREADS
        );
    }
    
    @Override
    public void stop() {
        this.server.stop();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }

}