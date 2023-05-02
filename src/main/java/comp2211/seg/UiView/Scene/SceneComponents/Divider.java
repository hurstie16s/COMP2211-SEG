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
    public SimpleDoubleProperty offset = new SimpleDoubleProperty(0);
    public double offsetold;
    public Pane below = new Pane();
    public Pane above = new Pane();
    public Divider(Pane pane) {
        below.maxHeightProperty().set(1000);
        above.maxHeightProperty().set(1000);
        below.maxWidthProperty().set(1000);
        above.maxWidthProperty().set(1000);
        setFill(Color.TRANSPARENT);
        setStroke(Color.TRANSPARENT);
        setStrokeWidth(10);
        if (pane instanceof HBox){
            startYProperty().set(0);
            endYProperty().bind(pane.heightProperty());
            setOnMousePressed(event -> {
                x = event.getSceneX();
                offsetold = offset.get();
            });
            setOnMouseDragged(event ->{
                if ((offsetold - event.getSceneX() + x > offset.get() && below.maxWidthProperty().get()>300) || (offsetold - event.getSceneX() + x < offset.get() && above.maxWidthProperty().get()>300)){
                    offset.set(offsetold - event.getSceneX() + x);
                }
            });
        } else if (pane instanceof VBox) {
            startXProperty().set(0);
            endXProperty().bind(pane.widthProperty());
            setOnMousePressed(event -> {
                y = event.getSceneY();
                offsetold = offset.get();
            });
            setOnMouseDragged(event ->{
                if ((offsetold - event.getSceneY() + y > offset.get() && below.maxHeightProperty().get()>100) || (offsetold - event.getSceneY() + y < offset.get() && above.maxHeightProperty().get()>100)){
                    offset.set(offsetold - event.getSceneY() + y);
                }
            });
        } else {
            throw new RuntimeException("Unknown parent");
        }


        pane.getChildren().add(this);
    }

    public void setBelow(Pane below) {
        this.below = below;
    }

    public void setAbove(Pane above) {
        this.above = above;
    }
}
