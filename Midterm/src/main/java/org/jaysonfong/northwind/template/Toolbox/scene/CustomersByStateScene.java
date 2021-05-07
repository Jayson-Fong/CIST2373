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
package org.jaysonfong.northwind.template.Toolbox.scene;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.jaysonfong.northwind.AppData;
import org.jaysonfong.northwind.Database;
import org.jaysonfong.northwind.template.BasicTemplate;
import org.jaysonfong.northwind.template.Toolbox.scene.data.Customer;
import org.jaysonfong.northwind.template.Toolbox.util.HeightWidthService;
import org.jaysonfong.northwind.template.Toolbox.util.SceneManager;

/**
 *
 * @author Jayson Fong <contact@jaysonfong.org>
 */
public class CustomersByStateScene implements IScene {
    
    private final Scene scene;
    private final VBox container;
    
    private final TextField stateField;
    private final TableView view;
    
    public CustomersByStateScene() {
        this.container = new VBox(10);
        this.stateField = new TextField();
        this.view = new TableView();
        this.scene = new Scene(
            new BasicTemplate().build(container),
            HeightWidthService.getWidth(),
            HeightWidthService.getHeight()
        );
        this.build();
    }
    
    /**
     *
     * @return
     */
    @Override
    public final Scene get() {
        return this.scene;
    }
    
    @Override
    public final void build(Map<String, Object> params) {
        // No Processing Needed
    }
    
    @Override
    public final void showing() {
        SceneManager.titleProperty().set(AppData.APP_NAME + " | View Customers by State");
        HeightWidthService.setResizeListener(this.scene);
    }
    
    private void build() {
        this.container.setAlignment(Pos.CENTER);
        Label label = new Label("State:");
        Button submitButton = new Button("Query");
        
        submitButton.setOnMouseClicked(e -> this.update());
        
        HBox horizontalBox = new HBox(15);
        horizontalBox.setAlignment(Pos.CENTER);
        horizontalBox.getChildren().addAll(label, this.stateField, submitButton);
        
        this.view.setPlaceholder(new Label("No Customers Matched the Queried State"));
        
        TableColumn<Customer, String> firstName = new TableColumn("Contact Name");
        firstName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        TableColumn<Customer, String> birthYear = new TableColumn("City");
        birthYear.setCellValueFactory(cellData -> cellData.getValue().cityProperty());

        this.view.getColumns().addAll(firstName, birthYear);
        
        this.stateField.setPrefHeight(20);
        label.setFont(new Font(20));
                
        this.container.getChildren().addAll(
            horizontalBox,
            this.view
        );
    }
    
    private void update() {
        try {
            PreparedStatement stmt = Database.getStatement("SELECT Customers.ContactName, Customers.City FROM Customers WHERE (((Customers.Region)=?)) ORDER BY Customers.City;");
            stmt.setString(1, this.stateField.getText());
            ResultSet result = stmt.executeQuery();
        
            this.view.getItems().clear();
            while(result.next()) {
                this.view.getItems().add(new Customer(result.getString(1), result.getString(2)));
            }
        } catch (SQLException | NumberFormatException exception) {
            // Do Nothing
        }
    }
    
}
