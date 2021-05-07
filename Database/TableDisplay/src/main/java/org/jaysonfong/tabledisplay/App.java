package org.jaysonfong.tabledisplay;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


/**
 * JavaFX App
 */
public class App extends Application {

    // Example: "jdbc:ucanaccess:///database.accdb";
    public static final String OVERRIDE_DATABASE_URL = null;
    
    private TextArea resultBox;
    private ComboBox tableSelector;
    private Text errorText;
    
    private Connection connection;
    private ObservableList tables;
    
    @Override
    public void start(Stage stage) {
        // Create Root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        
        // Create TableView
        this.resultBox = new TextArea("No Table Selected");
        this.resultBox.editableProperty().set(false);
        root.setCenter(this.resultBox);
        
        // Create table selector
        this.createTableSelector();
        
        // Create error text
        this.errorText = new Text();
        
        // Setup grid for top
        GridPane grid = new GridPane();
        grid.setVgap(15);
        grid.setHgap(15);
        
        VBox topHolder = new VBox(15);
        topHolder.getChildren().addAll(grid, this.errorText);
        
        root.setTop(topHolder);
        grid.add(new Label("Table:"), 0, 0);
        grid.add(this.tableSelector, 1, 0);
        grid.add(this.getUpdateButton(), 2, 0);
        
        // Create copyright
        root.setBottom(new Label("Created by Jayson Fong 2021"));
        
        // Create connection + setup tables
        try {
            this.initializeConnection();
            this.initializeTables();
        } catch (SQLException sqlException) {
            this.errorText.setText("SQLException: " + sqlException.getLocalizedMessage());
        }
        
        // Create scene + show
        var scene = new Scene(root, 640, 480);
        stage.setTitle("Access Database Viewer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    
    private void createTableSelector() {
        this.tables = FXCollections.observableArrayList();
        this.tableSelector = new ComboBox();
        this.tableSelector.setItems(this.tables);        
    }
    
    private Button getUpdateButton() {
        Button button = new Button("Show Contents");
        
        button.setOnMouseClicked(e -> this.updateView());
        
        return button;
    }
    
    private void updateView() {
        this.errorText.setText("");
        try {
            Statement statement = this.connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM " + this.tableSelector.getValue());
                        
            int columnCount = results.getMetaData().getColumnCount();
            
            String resultText = "";
            // Create top -- column list
            // weird and starts at 1 instead of 0...
            for (int columnIndex = 0b1; columnIndex <= columnCount; columnIndex++) {
                resultText += results.getMetaData().getColumnName(columnIndex) + (columnIndex != columnCount ? '\t' : "");
            }
            
            // Fill data
            while (results.next()) {
                resultText += '\n';
                for (int columnIndex = 0b1; columnIndex <= columnCount; columnIndex++) {
                    resultText += results.getString(columnIndex) + (columnIndex != columnCount ? '\t' : "");
                }
            }

            this.resultBox.setText(resultText);
        } catch (SQLException sqlException) {
            this.errorText.setText(("Error: " + sqlException.getLocalizedMessage()));
        }
    }
    
    private String getDatabaseUrl() {
        if (App.OVERRIDE_DATABASE_URL != null) {
            return App.OVERRIDE_DATABASE_URL;
        }
        File databaseFile = new File("database.accdb");
        return "jdbc:ucanaccess:///" + databaseFile.getAbsolutePath();
    }
    
    private void initializeConnection() throws SQLException {         
        this.connection = DriverManager.getConnection(this.getDatabaseUrl());
    }
    
    private void initializeTables() throws SQLException {
        ResultSet tableResults = this.connection.getMetaData().getTables(null, null, null, null);
        while (tableResults.next()) {
            this.tables.add(tableResults.getString("TABLE_NAME"));
        }
    }

}
