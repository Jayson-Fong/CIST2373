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

import java.io.IOException;
import java.io.RandomAccessFile;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Address Book File
 * @author Jayson Fong <contact@jaysonfong.org>
 */
public class AddressBookFile extends RandomAccessFile {
    
    public final static String DATA_FILE_PATH = "address.dat";
    
    public final static int NAME_LENGTH = 32; // 32 Chars, 2 Bytes Each
    public final static int NAME_SIZE = 64; // 32 Chars, 2 Bytes Each
    public final static int STREET_LENGTH = 32;
    public final static int STREET_SIZE = 64; // 32 Chars, 2 Bytes Each
    public final static int CITY_LENGTH = 20;
    public final static int CITY_SIZE = 40; // 20 Chars, 2 Bytes Each
    public final static int STATE_LENGTH = 2;
    public final static int STATE_SIZE = 4;  // 2 Chars, 2 Bytes Each
    public final static int ZIP_SIZE = 4; // 4 Bytes 
    public final static int RECORD_SIZE = 176; // 176 Bytes
    
    private final BooleanProperty empty;
    
    public AddressBookFile() throws IOException {
        // Opens Address File
        super(AddressBookFile.DATA_FILE_PATH, "rw");
        this.empty = new SimpleBooleanProperty(this.length() == 0b0);
    }
    
    /* Get Empty Property
     * Returns a Boolean property on if the file is empty.
     */
    public BooleanProperty getEmptyProperty() {
        return this.empty;
    }
    
    /* Read Address
     * Reads and returns an address at a position.
     */
    public Address readAddress(long position) throws IOException {
        Address address = new Address();
        
        seek(position);
        address.getNameProperty().setValue(this.readFixedLengthString(AddressBookFile.NAME_LENGTH));
        address.getStreetProperty().setValue(this.readFixedLengthString(AddressBookFile.STREET_LENGTH));
        address.getCityProperty().setValue(this.readFixedLengthString(AddressBookFile.CITY_LENGTH));
        address.getStateProperty().setValue(this.readFixedLengthString(AddressBookFile.STATE_LENGTH));
        address.getZipProperty().setValue(this.readInt());
        
        return address;
    }
    
    /* Write Address
     * Writes an address to the file.
     */
    public void writeAddress(long position, Address address) {
        try {
            seek(position);
            this.writeFixedLengthString(address.getNameProperty().getValue(), AddressBookFile.NAME_LENGTH);
            this.writeFixedLengthString(address.getStreetProperty().getValue(), AddressBookFile.STREET_LENGTH);
            this.writeFixedLengthString(address.getCityProperty().getValue(), AddressBookFile.CITY_LENGTH);
            this.writeFixedLengthString(address.getStateProperty().getValue(), AddressBookFile.STATE_LENGTH);
            this.writeInt(address.getZipProperty().getValue());
            this.empty.setValue(false);
        } catch (IOException e) {
            throw new RuntimeException("Writing Error Occured", e);
        }
    }
    
    /* Add Address
     * Appends a new address to the file.
     */
    public void addAddress(Address address) {
        try {
            // Writes to File
            writeAddress(this.length(), address);
            this.empty.setValue(false);
        } catch (IOException e) {
            throw new RuntimeException("Error Determining File Size", e);
        }
    }
    
    /* First Address
     * Returns the first address if the file is not blank.
     */
    public Address firstAddress() {
        Address address = null;
        try {
            if (this.length() > 0b0) {
                address = readAddress(0b0);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error Determining File Size", e);
        }
        return address;
    }
    
    /* Last Address
     * Returns the last address if the file is not blank.
     */
    public Address lastAddress() {
        Address address = null;
        try {
            if (this.length() > 0b0) {
                address = this.readAddress(this.length() - AddressBookFile.RECORD_SIZE);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error Determining File Size", e);
        }
        return address;
    }
    
    /* Has Next Address
     * Returns true if there is another address to be read.
     */
    public boolean hasNextAddress() throws IOException {
        long currentPosition = this.getFilePointer();
        return currentPosition < this.length();
    }
    
    /* Next Address
     * Returns the next address in the file if it exists.
     */
    public Address nextAddress() {
        Address address = null;
        try {
            long currentPosition = this.getFilePointer();
            if (currentPosition < this.length()) {
                address = this.readAddress(currentPosition);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error Determining File Size", e);
        }
        return address;
    }
    
    /* Previous Address
     * Returns the previous address if the file is not blank and not already at the first.
     */
    public Address previousAddress() {
        Address address = null;
        try {
            long currentPosition = this.getFilePointer();
            if (currentPosition - AddressBookFile.RECORD_SIZE > 0b0) {
                address = this.readAddress(currentPosition - 2 * AddressBookFile.RECORD_SIZE);
            } else {
                address = this.readAddress(0b0);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error Fetching Previous Address", e);
        }
        return address;
    }
    
    /* Update Address
     * Updates the current record.
     */
    public void updateAddress(Address address) {
        try {
            this.writeAddress(getFilePointer() - AddressBookFile.RECORD_SIZE, address);
            this.empty.setValue(false);
        } catch (IOException e) {
            throw new RuntimeException("Update Failure", e);
        }
    }
    
    /* Fixed Length String Utility
     * Reads a Fixed Length String
     */
    private String readFixedLengthString(int length) throws IOException {
        // Character Buffer
        char[] chars = new char[length];
        // Reads One Character at a Time
        for (int charIndex = 0b0; charIndex < length; charIndex++) {
            chars[charIndex] = this.readChar();
        }
        return new String(chars);
    }
    
    /* Fixed Length String Utility
     * Writes a Fixed Length String
     */
    private void writeFixedLengthString(String str, int length) throws IOException {
        // Character Buffer
        char[] chars = new char[length];
        // Move String Characters into Character Buffer
        str.getChars(0, Math.min(str.length(), length),  chars, 0);
        // Fill Empty Indexes With Whitespace
        for (int charIndex = Math.min(str.length(), length); charIndex < chars.length; charIndex++) {
            chars[charIndex] = ' ';
        }
        // Write Characters to File
        this.writeChars(new String(chars));
    }
    
}
