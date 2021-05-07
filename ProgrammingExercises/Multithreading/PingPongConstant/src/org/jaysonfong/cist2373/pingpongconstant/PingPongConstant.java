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
package org.jaysonfong.cist2373.pingpongconstant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jaysonfong.cist2373.pingpongconstant.service.PingPong;
import org.jaysonfong.cist2373.pingpongconstant.service.Word;

/**
 *
 * @author Jayson Fong <contact@jaysonfong.org>
 */
public class PingPongConstant {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Introduction Message
        System.out.println("System Ping Checkup Service: Starting...\n");
        
        // Creates a thread pool
        ExecutorService pingPongService = Executors.newCachedThreadPool();
        // Runs the program
        pingPongService.execute(new PingPong(Word.PING));
        // Gets rid of the executor service
        pingPongService.shutdown();
    }
    
}
