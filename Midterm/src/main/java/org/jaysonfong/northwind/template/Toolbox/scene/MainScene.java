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

import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.jaysonfong.northwind.AppData;
import org.jaysonfong.northwind.template.BasicTemplate;
import org.jaysonfong.northwind.template.Toolbox.util.DisplayScene;
import org.jaysonfong.northwind.template.Toolbox.util.HeightWidthService;
import org.jaysonfong.northwind.template.Toolbox.util.SceneManager;

/**
 *
 * @author Jayson Fong <contact@jaysonfong.org>
 */
public class MainScene implements IScene {
    
    private final Scene scene;
    private final VBox container;
    
    public MainScene() {
        this.container = new VBox(10);
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
        SceneManager.titleProperty().set(AppData.APP_NAME);
        HeightWidthService.setResizeListener(this.scene);
    }
    
    private void build() {
        Label label = new Label("Select an Operation");
        label.setFont(new Font(20));
        this.container.setAlignment(Pos.CENTER);
        this.container.getChildren().addAll(
            label,
            SceneManager.getSceneButton("Get Order Total", DisplayScene.DISPLAY_ORDER_SUMMARY, 100, 250, 16),
            SceneManager.getSceneButton("Get Order Details", DisplayScene.DISPLAY_ORDER_DETAILS, 100, 250, 16),
            SceneManager.getSceneButton("Get Customers in a State", DisplayScene.DISPLAY_CUSTOMERS_BY_STATE, 100, 250, 16),
            SceneManager.getSceneButton("Get Employees by Birth Year", DisplayScene.DISPLAY_EMPLOYEES_BY_BIRTHYEAR, 100, 250, 16)
        );
    }
    
}
