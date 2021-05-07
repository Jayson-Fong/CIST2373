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
package org.jaysonfong.northwind.template.Toolbox.scene.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jayson Fong <contact@jaysonfong.org>
 */
public class Order {
    
    //OrderDate, Freight, ProductName, UnitPrice, Quantity, Discount
    
    private StringProperty orderDate;
    private StringProperty freight;
    private StringProperty productName;
    private StringProperty unitPrice;
    private StringProperty quantity;
    private StringProperty discount;
    
    public Order(String orderDate, String freight, String productName, String unitPrice, String quantity, String discount) {
        this.orderDate = new SimpleStringProperty(orderDate);
        this.freight = new SimpleStringProperty(freight);
        this.productName = new SimpleStringProperty(productName);
        this.unitPrice = new SimpleStringProperty(unitPrice);
        this.quantity = new SimpleStringProperty(quantity);
        this.discount = new SimpleStringProperty(discount);
    }
    
    public StringProperty orderDateProperty() {
        return this.orderDate;
    }
                
    public StringProperty freightProperty() {
        return this.freight;
    }
                
    public StringProperty productNameProperty() {
        return this.productName;
    }
                
    public StringProperty unitPriceProperty() {
        return this.unitPrice;
    }
                
    public StringProperty quantityProperty() {
        return this.quantity;
    }
                
    public StringProperty discountProperty() {
        return this.discount;
    }
    
}
