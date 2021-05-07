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
package org.jaysonfong.cist2373.pingpongmultiple;

import org.jaysonfong.cist2373.pingpongmultiple.service.PingPong;
import org.jaysonfong.cist2373.pingpongmultiple.service.Word;

/**
 *
 * @author Jayson Fong <contact@jaysonfong.org>
 */
public class PingPongMultiple {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Introduction
        System.out.println("Ping Pong System Checkup Service\n");
        
        // Create the thread outputting "Ping"
        Thread pingThread = new Thread(
            new PingPong(Word.PING)
        );
        // Create the thread outputting "Pong"
        Thread pongThread = new Thread(
            new PingPong(Word.PONG)
        );
        
        // Run the threads
        pingThread.start();
        pongThread.start();
        
        // Wait until the threads finish
        try {
            pingThread.join();
            pongThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted", e);
        }
        System.out.println("\nProcess Complete: Your computer properly pings and pongs.");
        
    }
    
}
