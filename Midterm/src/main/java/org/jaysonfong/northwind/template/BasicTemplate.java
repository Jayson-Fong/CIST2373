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
package org.jaysonfong.northwind.template;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.jaysonfong.northwind.template.Toolbox.BasicToolbox;

/**
 *
 * @author Jayson Fong <contact@jaysonfong.org>
 */
public final class BasicTemplate implements ITemplate {
    
    private List<Node> topNodes;
    private List<Node> leftNodes;
    private List<Node> rightNodes;
    private List<Node> bottomNodes;
    
    @Override
    public final Pane build(Node centerNode) {
        BorderPane pane = new BorderPane();
        
        // Style
        pane.setPadding(new Insets(0, 0, 15, 0));
        
        // Centeer
        VBox innerCenter = new VBox();
        innerCenter.getChildren().add(centerNode);
        innerCenter.setPadding(new Insets(15, 15, 15, 15));
        pane.setCenter(innerCenter);
        
        // Everything else
        pane.setTop(this.getTop());
        pane.setLeft(this.getLeft());
        pane.setRight(this.getRight());
        pane.setBottom(this.getBottom());
        
        return pane;
    }
    
    public final void setTop(List<Node> topNodes) {
        this.topNodes = topNodes;
    }
    
    public final void setLeft(List<Node> leftNodes) {
        this.leftNodes = leftNodes;
    }
    
    public final void setRight(List<Node> rightNodes) {
        this.rightNodes = rightNodes;
    }
    
    public final void setBottom(List<Node> bottomNodes) {
        this.bottomNodes = bottomNodes;
    }
    
    private VBox getTop() {
        VBox container = new VBox();
        if (this.topNodes != null) {
            container.getChildren().addAll(this.topNodes);
        }
        container.getChildren().add(new BasicToolbox().get());
        return container;
    }
    
    private VBox getLeft() {
        VBox container = new VBox();
        if (this.leftNodes != null) {
            container.getChildren().addAll(this.leftNodes);
        }
        container.setPadding(new Insets(15, 15, 15, 15));
        return container;
    }
    
    private VBox getRight() {
        VBox container = new VBox();
        if (this.rightNodes != null) {
            container.getChildren().addAll(this.rightNodes);
        }
        container.setPadding(new Insets(15, 15, 15, 15));
        return container;
    }
    
    private VBox getBottom() {
        VBox container = new VBox();
        if (this.bottomNodes != null) {
            container.getChildren().addAll(this.bottomNodes);
        }
        container.getChildren().add(new Label("Created by Jayson Fong 2021"));
        container.setPadding(new Insets(15, 15, 0, 15));
        return container;
    }
    
}
