package comp2211.seg.UiView.Scene.SceneComponents;

import comp2211.seg.Controller.Stage.AppWindow;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Divider extends Line {
    double x;
    double y;
    double size1;
    double size2;
    public Divider(Pane pane) {
        setFill(Color.TRANSPARENT);
        setStroke(Color.TRANSPARENT);
        setStrokeWidth(10);
        if (pane instanceof HBox){
            startYProperty().set(0);
            endYProperty().bind(pane.heightProperty());
            setOnMousePressed(event -> {
                x = event.getSceneX();
                size1 = ((Pane) pane.getChildren().get(pane.getChildren().indexOf(this)+1)).widthProperty().get();
                size2 = ((Pane) pane.getChildren().get(pane.getChildren().indexOf(this)-1)).widthProperty().get();
            });
            setOnMouseDragged(event ->{
                if (size2 - x + event.getSceneX() > 40 && size1 + x - event.getSceneX() > 40) {
                    if (((Pane) pane.getChildren().get(pane.getChildren().indexOf(this) + 1)).minWidthProperty().isBound()) {
                        ((Pane) pane.getChildren().get(pane.getChildren().indexOf(this) + 1)).minWidthProperty().unbind();
                    }
                    if (((Pane) pane.getChildren().get(pane.getChildren().indexOf(this) - 1)).minWidthProperty().isBound()) {
                        ((Pane) pane.getChildren().get(pane.getChildren().indexOf(this) - 1)).minWidthProperty().unbind();
                    }
                    if (((Pane) pane.getChildren().get(pane.getChildren().indexOf(this) + 1)).maxWidthProperty().isBound()) {
                        ((Pane) pane.getChildren().get(pane.getChildren().indexOf(this) + 1)).maxWidthProperty().unbind();
                    }
                    if (((Pane) pane.getChildren().get(pane.getChildren().indexOf(this) - 1)).maxWidthProperty().isBound()) {
                        ((Pane) pane.getChildren().get(pane.getChildren().indexOf(this) - 1)).maxWidthProperty().unbind();
                    }
                    ((Pane) pane.getChildren().get(pane.getChildren().indexOf(this) + 1)).minWidthProperty().bind(new SimpleDoubleProperty(size1 + x - event.getSceneX()));
                    ((Pane) pane.getChildren().get(pane.getChildren().indexOf(this) + 1)).maxWidthProperty().bind(new SimpleDoubleProperty(size1 + x - event.getSceneX()));
                    ((Pane) pane.getChildren().get(pane.getChildren().indexOf(this) - 1)).minWidthProperty().bind(new SimpleDoubleProperty(size2 - x + event.getSceneX()));
                    ((Pane) pane.getChildren().get(pane.getChildren().indexOf(this) - 1)).maxWidthProperty().bind(new SimpleDoubleProperty(size2 - x + event.getSceneX()));

                    AppWindow.currentScene.refresh();
                }


            });
        } else if (pane instanceof VBox) {
            startXProperty().set(0);
            endXProperty().bind(pane.widthProperty());
            setOnMousePressed(event -> {
                y = event.getSceneY();
                size1 = ((Pane) pane.getChildren().get(pane.getChildren().indexOf(this)+1)).heightProperty().get();
                size2 = ((Pane) pane.getChildren().get(pane.getChildren().indexOf(this)-1)).heightProperty().get();
            });
            setOnMouseDragged(event ->{
                if (size1 + y - event.getSceneY() > 40 && size2 - y + event.getSceneY() > 40) {
                    ((Pane) pane.getChildren().get(pane.getChildren().indexOf(this) + 1)).minHeightProperty().bind(new SimpleDoubleProperty(size1 + y - event.getSceneY()));
                    ((Pane) pane.getChildren().get(pane.getChildren().indexOf(this) + 1)).maxHeightProperty().bind(new SimpleDoubleProperty(size1 + y - event.getSceneY()));
                    ((Pane) pane.getChildren().get(pane.getChildren().indexOf(this) - 1)).minHeightProperty().bind(new SimpleDoubleProperty(size2 - y + event.getSceneY()));
                    ((Pane) pane.getChildren().get(pane.getChildren().indexOf(this) - 1)).maxHeightProperty().bind(new SimpleDoubleProperty(size2 - y + event.getSceneY()));
                    AppWindow.currentScene.refresh();
                }

            });
        } else {
            throw new RuntimeException("Unknown parent");
        }


        pane.getChildren().add(this);
    }
}
