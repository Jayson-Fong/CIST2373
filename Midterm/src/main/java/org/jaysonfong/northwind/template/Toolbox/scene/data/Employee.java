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
public class Employee {
    
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty birthDate;
    
    public Employee() {}
    
    public Employee(String firstName, String lastName, String birthYear) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.birthDate = new SimpleStringProperty(birthYear);
    }
    
    public StringProperty firstNameProperty() {
        return this.firstName;
    }
    
    public StringProperty lastNameProperty() {
        return this.lastName;
    }
    
    public StringProperty birthDateProperty() {
        return this.birthDate;
    }
    
    public String getFirstName() {
        return this.firstName.get();
    }
    
    public String getLastName() {
        return this.lastName.get();
    }
    
    public String getBirthDate() {
        return this.birthDate.get();
    }
    
    public void setFirstName(String fName) {
        this.firstName.set(fName);
    }
    
    public void setLastName(String lName) {
        this.lastName.set(lName);
    }
    
    public void setBirthDate(String bYear) {
        this.birthDate.set(bYear);
    }
    
}
