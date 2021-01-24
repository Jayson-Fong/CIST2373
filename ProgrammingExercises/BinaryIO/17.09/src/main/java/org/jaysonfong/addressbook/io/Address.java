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
package org.jaysonfong.addressbook.io;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Address
 * @author Jayson Fong <contact@jaysonfong.org>
 */
public class Address {
    
    private final StringProperty name;
    private final StringProperty street;
    private final StringProperty city;
    private final StringProperty state;
    private final IntegerProperty zip;
    
    public Address() {
        this.name = new SimpleStringProperty();
        this.street = new SimpleStringProperty();
        this.city = new SimpleStringProperty();
        this.state = new SimpleStringProperty();
        this.zip = new SimpleIntegerProperty();
    }
    
    public Address(String name, String street, String city, String state, int zip) {
        this.name = new SimpleStringProperty(name);
        this.street = new SimpleStringProperty(street);
        this.city = new SimpleStringProperty(city);
        this.state = new SimpleStringProperty(state);
        this.zip = new SimpleIntegerProperty(zip);
    }
    
    public StringProperty getNameProperty() {
        return this.name;
    }
    
    public StringProperty getStreetProperty() {
        return this.street;
    }
        
    public StringProperty getCityProperty() {
        return this.city;
    }
    
    public StringProperty getStateProperty() {
        return this.state;
    }
    
    public IntegerProperty getZipProperty() {
        return this.zip;
    }
    
    @Override
    public String toString() {
        return String.format(
            "[Name: \"%s\", Street: \"%s\", City: \"%s\", State: \"%s\", Zip: \"%s\"]",
            this.getNameProperty().toString(),
            this.getStreetProperty().toString(),
            this.getCityProperty().toString(),
            this.getStateProperty().toString(),
            this.getZipProperty().toString()
        );
    }
    
}
