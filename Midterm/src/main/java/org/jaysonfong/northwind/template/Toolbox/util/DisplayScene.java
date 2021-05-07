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
package org.jaysonfong.northwind.template.Toolbox.util;

import org.jaysonfong.northwind.template.Toolbox.scene.CustomersByStateScene;
import org.jaysonfong.northwind.template.Toolbox.scene.DisplayOrderDetailsScene;
import org.jaysonfong.northwind.template.Toolbox.scene.EmployeesByBirthYearScene;
import org.jaysonfong.northwind.template.Toolbox.scene.ErrorScene;
import org.jaysonfong.northwind.template.Toolbox.scene.IScene;
import org.jaysonfong.northwind.template.Toolbox.scene.MainScene;
import org.jaysonfong.northwind.template.Toolbox.scene.OrderDetailsScene;

/**
 *
 * @author Jayson Fong <contact@jaysonfong.org>
 */
public enum DisplayScene {
    
    MAIN,
    ERROR,
    DISPLAY_ORDER_SUMMARY,
    DISPLAY_ORDER_DETAILS,
    DISPLAY_CUSTOMERS_BY_STATE,
    DISPLAY_EMPLOYEES_BY_BIRTHYEAR;
    
    IScene getScene() {
        switch(this) {
            case MAIN:
                return new MainScene();
            case ERROR:
                return new ErrorScene();
            case DISPLAY_ORDER_SUMMARY:
                return new DisplayOrderDetailsScene();
            case DISPLAY_ORDER_DETAILS:
                return new OrderDetailsScene();
            case DISPLAY_CUSTOMERS_BY_STATE:
                return new CustomersByStateScene();
            case DISPLAY_EMPLOYEES_BY_BIRTHYEAR:
                return new EmployeesByBirthYearScene();
            default:
                throw new RuntimeException("Unknown Scene");
        }
    }
    
}
