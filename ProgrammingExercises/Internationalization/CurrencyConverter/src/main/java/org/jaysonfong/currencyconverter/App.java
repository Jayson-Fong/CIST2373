package org.jaysonfong.currencyconverter;

import java.text.NumberFormat;
import java.util.Locale;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Currency Exchanger
 * @author Jayson Fong
 */
public class App extends Application {

    private final TextField dollarsInput = new TextField("0");
    
    private final TextField cadAmount = new TextField("0");
    private final TextField euroAmount = new TextField("0");
    private final TextField poundAmount = new TextField("0");
    
    private final TextField cadExchange = new TextField("1.22");
    private final TextField euroExchange = new TextField("0.84");
    private final TextField poundExchange = new TextField("0.74");
    
    private final Text errorText = new Text();
    
    @Override
    public void start(Stage stage) {
        // Convert From
        Label convertFromLabel = new Label("Enter Dollar Amount");
        convertFromLabel.setFont(new Font(16));
        GridPane convertFromGrid = new GridPane();
        convertFromGrid.setPadding(new Insets(10, 10, 10, 10));
        convertFromGrid.setVgap(15);
        convertFromGrid.setHgap(15);
        convertFromGrid.add(new Label("US Dollars"), 0, 0);
        convertFromGrid.add(this.dollarsInput, 1, 0);
        convertFromGrid.add(this.getConvertButton(), 2, 0);
        
        // Convert To
        Label convertToLabel = new Label("Display Exchange");
        convertToLabel.setFont(new Font(16));
        
        GridPane convertToGrid = new GridPane();
        convertToGrid.setPadding(new Insets(10, 10, 10, 10));
        convertToGrid.setVgap(15);
        convertToGrid.setHgap(15);
        
        // Header
        convertToGrid.add(new Label("Exchange Rate"), 1, 0);
        convertToGrid.add(new Label("Converted Amount"), 2, 0);
        
        // Canadian Dollars
        convertToGrid.add(new Label("Canadian Dollars"), 0, 1);
        convertToGrid.add(this.cadExchange, 1, 1);
        convertToGrid.add(this.cadAmount, 2, 1);
        
        // Euro
        convertToGrid.add(new Label("Euro"), 0, 2);
        convertToGrid.add(this.euroExchange, 1, 2);
        convertToGrid.add(this.euroAmount, 2, 2);
        
        // British Pounds
        convertToGrid.add(new Label("British Pounds"), 0, 3);
        convertToGrid.add(this.poundExchange, 1, 3);
        convertToGrid.add(this.poundAmount, 2, 3);
        
        // Combine
        VBox center = new VBox(15);
        center.getChildren().addAll(
            convertFromLabel,
            convertFromGrid,
            convertToLabel,
            convertToGrid,
            errorText
        );
        
        BorderPane root = new BorderPane();
        root.setCenter(center);
        root.setBottom(new Label("Created by Jayson Fong 2021"));
        root.setPadding(new Insets(10, 10, 10, 10));
        
        // Locks all and sets error text font
        this.initializeFields();
        
        // Add currency symbols to zeroes...
        this.recalculate();
        
        var scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
    }
    
    private Button getConvertButton() {
        Button button = new Button("Convert");
        
        button.setOnMouseClicked(e -> this.recalculate());
        
        return button;
    }
    
    private void recalculate() {
        this.errorText.setText("");
        try {
            double amount = Double.valueOf(this.dollarsInput.getText());
            this.formatCad(this.calculateAmount(amount, this.cadExchange.getText()));
            this.formatEuro(this.calculateAmount(amount, this.euroExchange.getText()));
            this.formatPound(this.calculateAmount(amount, this.poundExchange.getText()));
        } catch (NumberFormatException numberFormatException) {
            this.errorText.setText("Invalid Dollar Amount or Exchange Rate(s)");
        }
    }
    
    private void initializeFields() {
        this.cadAmount.setEditable(false);
        this.euroAmount.setEditable(false);
        this.poundAmount.setEditable(false);
        this.errorText.setFont(new Font(24));
        this.errorText.setFill(Color.RED);
    }
    
    private void formatCad(double amount) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);
        this.cadAmount.setText(currencyFormat.format(amount));
    }
    
    private void formatEuro(double amount) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        this.euroAmount.setText(currencyFormat.format(amount));
    }
    
    private void formatPound(double amount) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.UK);
        this.poundAmount.setText(currencyFormat.format(amount));
    }
    
    private double calculateAmount(double amount, String conversion) {
        return amount * Double.valueOf(conversion);
    }

    public static void main(String[] args) {
        launch();
    }

}
