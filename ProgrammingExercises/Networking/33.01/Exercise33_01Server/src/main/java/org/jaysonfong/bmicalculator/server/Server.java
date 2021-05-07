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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jaysonfong.bmicalculator.server.worker.BMIWorker;

/**
 * Accepts connections and creates worker instances
 * @author Jayson Fong <contact@jaysonfong.org>
 */
public class Server implements Runnable {
    
    private ExecutorService threadPool;
    
    private boolean active = true;
    
    @Override
    public void run() {
        // Makes a new thread pool
        this.threadPool = Executors.newFixedThreadPool(App.THREADS);
        try {
            ServerSocket serverSocket = new ServerSocket(App.APP_PORT);

            App.getApp().addDataLine("Server started at " + new Date());
            Socket socket;        
            // Create a new worker on connection
            while(this.active) {
                socket = serverSocket.accept();
                this.threadPool.submit(
                    new BMIWorker(socket)
                );
            }
        } catch (IOException e) {
            App.getApp().addDataLine("Failed to Run on Port " + App.APP_PORT);
        }
        this.threadPool.shutdown();
    }
    
    public void stop() {
        this.active = false;
    }
    
}
