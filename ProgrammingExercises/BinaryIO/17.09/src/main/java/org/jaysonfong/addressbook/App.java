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
package org.jaysonfong.addressbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jaysonfong.addressbook.io.Address;
import org.jaysonfong.addressbook.io.AddressBookFile;


/**
 * Application
 * @author Jayson Fong <contact@jaysonfong.org>
 */
public class App extends Application {

    private final AddressBookFile addressBookFile;
    
    private final TextField nameField = new TextField();
    private final TextField streetField = new TextField();
    private final TextField cityField = new TextField();
    private final TextField stateField = new TextField();
    private final TextField zipField = new TextField();
    
    private BooleanProperty disableUpdate = new SimpleBooleanProperty(true);
    
    private StringProperty title;
    
    public App() {
        // Setup File
        try {
            this.addressBookFile = new AddressBookFile();
        } catch (IOException e) {
            throw new RuntimeException("Failed to Create AddressBookFile", e);
        }
    }
    
    @Override
    public void start(Stage stage) {
        // Used to Change the Title Later
        this.title = stage.titleProperty();
        
        // Configure Root
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        
        // Configure Fieldset Styling
        GridPane fields = new GridPane();
        fields.setAlignment(Pos.CENTER);
        fields.setHgap(0b1111);
        fields.setVgap(0b1111);
        
        // Add Fields w/ Labels
        
        // Name
        fields.add(new Label("Name"), 0, 0, 1, 1);
        fields.add(this.nameField, 1, 0, 6, 1);
        
        // Street
        fields.add(new Label("Street"), 0, 1, 1, 1);
        fields.add(this.streetField, 1, 1, 6, 1);
        
        // City
        fields.add(new Label("City"), 0, 2, 1, 1);
        fields.add(this.cityField, 1, 2, 2, 1);
        
        // State
        fields.add(new Label("State"), 3, 2, 1, 1);
        fields.add(this.stateField, 4, 2, 1, 1);
        
        // Zip
        fields.add(new Label("Zip"), 5, 2, 1, 1);
        fields.add(this.zipField, 6, 2, 1, 1);
        
        // Toolset
        HBox toolset = new HBox(15);
        toolset.getChildren().addAll(this.getToolset());
        toolset.setAlignment(Pos.CENTER);
        
        // If addresses exist, load the first.
        if (!this.addressBookFile.getEmptyProperty().getValue()) {
            this.doFirst();
        }
        
        // Tooltips
        this.nameField.tooltipProperty().set(new Tooltip("Enter Up to 32 Characters"));
        this.streetField.tooltipProperty().set(new Tooltip("Enter Up to 32 Characters"));
        this.cityField.tooltipProperty().set(new Tooltip("Enter Up to 20 Characters"));
        this.stateField.tooltipProperty().set(new Tooltip("Enter 2 Characters"));
        this.zipField.tooltipProperty().set(new Tooltip("Enter Up to 5 Digits"));
        
        // Add Items to root
        root.getChildren().addAll(fields, toolset);
        
        // Set Initial Title
        this.setTitle("Home");
        
        var scene = new Scene(root, 750, 300);
        stage.setScene(scene);
        stage.show();
    }
    
    /* Gets Toolset
     * Returns a list of buttons which are set to execute their respective functions.
     */
    private List<Button> getToolset() {
        List<Button> toolset = new ArrayList();
        
        Button button;
        
        // Add
        button = new Button("Clear/New");
        button.setOnMouseClicked(e -> this.doNew());
        toolset.add(button);
        
        // Add
        button = new Button("Add");
        button.setOnMouseClicked(e -> this.doAdd());
        toolset.add(button);
        
        // First
        button = new Button("First");
        button.setOnMouseClicked(e -> this.doFirst());
        button.disableProperty().bind(this.addressBookFile.getEmptyProperty());
        toolset.add(button);
        
        // Next
        button = new Button("Next");
        button.setOnMouseClicked(e -> this.doNext());
        button.disableProperty().bind(this.addressBookFile.getEmptyProperty());
        toolset.add(button);
        
        // Previous
        button = new Button("Previous");
        button.setOnMouseClicked(e -> this.doPrevious());
        button.disableProperty().bind(this.addressBookFile.getEmptyProperty());
        toolset.add(button);
        
        // Last
        button = new Button("Last");
        button.setOnMouseClicked(e -> this.doLast());
        button.disableProperty().bind(this.addressBookFile.getEmptyProperty());
        toolset.add(button);
        
        // Update
        button = new Button("Update");
        button.setOnMouseClicked(e -> this.doUpdate());
        button.disableProperty().bind(this.disableUpdate);
        toolset.add(button);
        
        return toolset;
    }
    
    /* Load Existing
     * Loads an Existing Address Into the Fields
     */
    private void loadExisting(Address address) {
        this.nameField.setText(
            address.getNameProperty().getValue().trim()
        );
        this.streetField.setText(
            address.getStreetProperty().getValue().trim()
        );
        this.cityField.setText(
            address.getCityProperty().getValue().trim()
        );
        this.stateField.setText(
            address.getStateProperty().getValue().trim()
        );
        String zip = address.getZipProperty().getValue().toString();
        this.zipField.setText(
            (zip.equals("0") ? "" : zip)
        );
        this.setTitle("Viewing: " + address.getNameProperty().getValue());
    }
    
    /* Create Address
     * Creates an Address object using the field data.
     */
    private Address createAddress() {
        Address address = null;
        Integer zip = 0b0;
        try {
            String zipStr = this.trimtoChars(this.zipField.getText(), 5);
            zip = Integer.valueOf(zipStr);
        } catch (NumberFormatException e) {
            // Do Nothing...Already 0!
        }
        address = new Address(
            this.trimtoChars(this.nameField.getText().trim(), AddressBookFile.NAME_LENGTH),
            this.trimtoChars(this.streetField.getText().trim(), AddressBookFile.STREET_LENGTH),
            this.trimtoChars(this.cityField.getText().trim(), AddressBookFile.CITY_LENGTH),
            this.trimtoChars(this.stateField.getText().trim().toUpperCase(), AddressBookFile.STATE_LENGTH),
            zip
        );
        return address;
    }
    
    private String trimtoChars(String str, int charCount) {
        if (str.length() > charCount) {
            return str.substring(0, charCount);
        }
        return str;
    }
    
    /* New
     * Clears the current fields.
     */
    private void doNew() {
        this.nameField.setText("");
        this.streetField.setText("");
        this.cityField.setText("");
        this.stateField.setText("");
        this.zipField.setText("");
        this.setTitle("New Entry");
    }
    
    /* Add Function
     * Creates a New Record for the Fields
     */
    private void doAdd() {
        Address address = this.createAddress();
        this.addressBookFile.addAddress(address);
        this.loadExisting(address);
        this.disableUpdate.setValue(false);
    }
    
    /* Show First
     * If an address exist, show the first one made.
     */
    private void doFirst() {
        Address address;
        address = this.addressBookFile.firstAddress();
        if (address != null) {
            this.loadExisting(address);
            this.disableUpdate.setValue(false);
        }
    }
    
    /* Show Next
     * If there is another address, show the next one.
     */
    private void doNext() {
        Address address;
        address = this.addressBookFile.nextAddress();
        if (address != null) {
            this.loadExisting(address);
            this.disableUpdate.setValue(false);
        }
    }
    
    /* Show Previous
     * If there is an address added prior, show it.
     */
    private void doPrevious() {
        Address address;
        address = this.addressBookFile.previousAddress();
        if (address != null) {
            this.loadExisting(address);
            this.disableUpdate.setValue(false);
        }
    }
    
    /* Show Last
     * If addresses exist, show the last.
     */
    private void doLast() {
        Address address;
        address = this.addressBookFile.lastAddress();
        if (address != null) {
            this.loadExisting(address);
            this.disableUpdate.setValue(false);
        }
    }
    
    /* Update Function
     * Update the current record to match.
     */
    private void doUpdate() {
        Address address = this.createAddress();
        if (address != null) {
            this.addressBookFile.updateAddress(address);
            this.loadExisting(address);
        }
    }
    
    /* Set Title
     * Changes the current title.
     */
    private void setTitle(String text) {
        this.title.setValue(String.format("Address Book | %s", text.trim()));
    }

    /* Main
     * Launches the Program
     */
    public static void main(String[] args) {
        launch();
    }

}