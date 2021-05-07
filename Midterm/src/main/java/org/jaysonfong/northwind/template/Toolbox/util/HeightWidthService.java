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

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;

/**
 *
 * @author Jayson Fong <contact@jaysonfong.org>
 */
public class HeightWidthService {
    
    private static final SimpleDoubleProperty height = new SimpleDoubleProperty(500);
    private static final SimpleDoubleProperty width = new SimpleDoubleProperty(650);
    
    private HeightWidthService() {}
    
    public static final double getHeight() {
        return HeightWidthService.height.getValue();
    }
    
    public static final double getWidth() {
        return HeightWidthService.width.getValue();
    }
    
    public static final void update(double height, double width) {
        HeightWidthService.height.setValue(height);
        HeightWidthService.width.setValue(width);
    }
    
    public static final void setResizeListener(Scene scene) {
        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            HeightWidthService.height.setValue(newValue);
        });
        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            HeightWidthService.width.setValue(newValue);
        });
    }
    
}
