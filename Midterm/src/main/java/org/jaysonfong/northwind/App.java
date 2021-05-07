package org.jaysonfong.northwind;

import java.sql.SQLException;
import javafx.application.Application;
import javafx.stage.Stage;
import org.jaysonfong.northwind.template.Toolbox.util.DisplayScene;
import org.jaysonfong.northwind.template.Toolbox.util.SceneManager;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        SceneManager.build(stage);
        SceneManager.switchScene(DisplayScene.MAIN);
        
        try {
            Database.initializeConnection();
        } catch (SQLException sqlException) {
            SceneManager.switchScene(DisplayScene.ERROR);
        }
    }

    public static void main(String[] args) {
        launch();
    }

}
