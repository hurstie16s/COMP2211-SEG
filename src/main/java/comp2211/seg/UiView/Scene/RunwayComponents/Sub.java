package comp2211.seg.UiView.Scene.RunwayComponents;

import javafx.scene.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class Sub extends SubScene {
    public Sub(Parent parent, double v, double v1) {
        super(parent, v, v1);
        buildAlt();
    }
    public Sub(Parent parent, double v, double v1, boolean b, SceneAntialiasing sceneAntialiasing) {
        super(parent, v, v1, b, sceneAntialiasing);
        build();
    }
    public void build(){

        getRoot().getStyleClass().add("transparent");
        PerspectiveCamera camera = new PerspectiveCamera();
        setCamera(camera);
    }
    public void buildAlt(){

        getRoot().getStyleClass().add("transparent");
        ParallelCamera camera = new ParallelCamera();
        setCamera(camera);
    }
}
