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
package org.jaysonfong.northwind.template.Toolbox.util;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.jaysonfong.northwind.template.Toolbox.scene.IScene;
import org.jaysonfong.northwind.template.Toolbox.handler.ISceneBuildHandler;

/**
 *
 * @author Jayson Fong <contact@jaysonfong.org>
 */
public final class SceneManager {
    
    private static Stage stage;
    
    public static final void build(Stage stage) {
        if (SceneManager.stage != null) {
            throw new RuntimeException("SceneManager Exists");
        }
        SceneManager.stage = stage;
    }
    
    public static final void switchScene(DisplayScene displayScene, ISceneBuildHandler handler) {
        IScene scene = displayScene.getScene();
        handler.run(scene);
        SceneManager.stage.setScene(scene.get());
        SceneManager.stage.show();
        scene.showing();
    }
    
    public static final void switchScene(DisplayScene displayScene) {
        IScene scene = displayScene.getScene();
        SceneManager.stage.setScene(scene.get());
        SceneManager.stage.show();
        scene.showing();
    }
    
    public static final Button getSceneButton(String buttonText, DisplayScene scene, ISceneBuildHandler handler) {
        Button switchButton = new Button(buttonText);
        
        switchButton.setOnMouseClicked(e -> SceneManager.switchScene(scene, handler));
        
        return switchButton;
    }
    
    public static final Button getSceneButton(String buttonText, DisplayScene scene) {
        Button switchButton = new Button(buttonText);
        
        switchButton.setOnMouseClicked(e -> SceneManager.switchScene(scene));
        
        return switchButton;
    }
    
    public static final Button getSceneButton(String buttonText, DisplayScene scene, int height, int width, int font) {
        Button switchButton = new Button(buttonText);
        
        switchButton.setPrefHeight(height);
        switchButton.setPrefWidth(width);
        switchButton.setFont(new Font(font));
        
        switchButton.setOnMouseClicked(e -> SceneManager.switchScene(scene));
        
        return switchButton;
    }
    
    public static final StringProperty titleProperty() {
        return SceneManager.stage.titleProperty();
    }
    
}
