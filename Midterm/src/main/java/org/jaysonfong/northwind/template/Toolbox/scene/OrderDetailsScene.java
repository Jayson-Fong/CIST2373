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
import org.jaysonfong.northwind.template.Toolbox.scene.data.Order;
import org.jaysonfong.northwind.template.Toolbox.util.DisplayScene;
import org.jaysonfong.northwind.template.Toolbox.util.HeightWidthService;
import org.jaysonfong.northwind.template.Toolbox.util.SceneManager;

/**
 * 1.	Print order total (not including the freight cost) for a given order number 
 * @author Jayson Fong <contact@jaysonfong.org>
 */
public class OrderDetailsScene implements IScene {
    
    private final Scene scene;
    private final VBox container;
    
    private final TextField orderIdField;
    private final TableView view;
    
    public OrderDetailsScene() {
        this.container = new VBox(10);
        this.orderIdField = new TextField();
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
        SceneManager.titleProperty().set(AppData.APP_NAME + " | Order Details");
        HeightWidthService.setResizeListener(this.scene);
    }
    
    private void build() {
        this.container.setAlignment(Pos.CENTER);
        Label label = new Label("Order ID:");
        Button submitButton = new Button("Query");
        
        submitButton.setOnMouseClicked(e -> this.update());
        
        HBox horizontalBox = new HBox(15);
        horizontalBox.setAlignment(Pos.CENTER);
        horizontalBox.getChildren().addAll(label, this.orderIdField, submitButton);
        
        this.view.setPlaceholder(new Label("No Orders Matched the Given ID"));
        
        TableColumn<Order, String> orderDate = new TableColumn("Order Date");
        orderDate.setCellValueFactory(cellData -> cellData.getValue().orderDateProperty());
        orderDate.setResizable(true);
        
        TableColumn<Order, String> freight = new TableColumn("Freight");
        freight.setCellValueFactory(cellData -> cellData.getValue().freightProperty());
        freight.setResizable(true);
        
        TableColumn<Order, String> productName = new TableColumn("Product Name");
        productName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        productName.setResizable(true);
        
        TableColumn<Order, String> unitPrice = new TableColumn("Unit Price");
        unitPrice.setCellValueFactory(cellData -> cellData.getValue().unitPriceProperty());
        unitPrice.setResizable(true);
        
        TableColumn<Order, String> quantity = new TableColumn("Quantity");
        quantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        quantity.setResizable(true);
        
        TableColumn<Order, String> discount = new TableColumn("Discount");
        discount.setCellValueFactory(cellData -> cellData.getValue().discountProperty()); 
        discount.setResizable(true);

        this.view.getColumns().addAll(orderDate, freight, productName, unitPrice, quantity, discount);
        
        this.orderIdField.setPrefHeight(20);
        label.setFont(new Font(20));
                
        this.container.getChildren().addAll(
            horizontalBox,
            this.view
        );
    }
    
    private void update() {
        try {
            PreparedStatement stmt = Database.getStatement("SELECT Orders.OrderDate, Orders.Freight, Products.ProductName, [Order Details].UnitPrice, [Order Details].Quantity, [Order Details].Discount FROM Products INNER JOIN (Orders INNER JOIN [Order Details] ON Orders.OrderID = [Order Details].OrderID) ON Products.ProductID = [Order Details].ProductID WHERE ((([Order Details].OrderID)=?));");
            stmt.setString(1, this.orderIdField.getText());
            ResultSet result = stmt.executeQuery();
        
            this.view.getItems().clear();
            while(result.next()) {
                this.view.getItems().add(new Order(
                    result.getString(1), result.getString(2),
                    result.getString(3), result.getString(4),
                    result.getString(5), result.getString(6)
                ));
            }
        } catch (NumberFormatException nfException) {
            // Do Nothing
        } catch (SQLException sqlException) {
            SceneManager.switchScene(DisplayScene.ERROR);
        }
    }
    
}
