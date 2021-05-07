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
package org.jaysonfong.cist2373.pingpongconstant.service;

/**
 *
 * @author Jayson Fong <contact@jaysonfong.org>
 */
public class PingPong implements Runnable {
    
    // Default to Sleep for 10 Seconds
    private static final int SLEEP_INTERVAL = 0b10011100010000;
    
    private final Word word;
    private int executionTimes;
    
    public PingPong(Word word) {
        // Execute 10 Times by Default
        this(word, 0b1010);
    }
    
    public PingPong(Word word, int executionTimes) {
        this.word = word;
        this.executionTimes = executionTimes;
    }
    
    @Override
    public void run() {
        // Gets the text for the word.
        String wordText = this.word.getText();
        // Runs executionTimes many times.
        for (int executions = 0b0; executions < executionTimes; executions++) {
            System.out.println(wordText);
            // Runs sleep process.
            this.doSleep();
        }
    }
    
    /* Sleeps the thread for SLEEP_INTERVAL time.
     * Executed after each word print.
     */
    private void doSleep() {
        try {
            Thread.sleep(PingPong.SLEEP_INTERVAL);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted", e);
        }
    }
    
}
