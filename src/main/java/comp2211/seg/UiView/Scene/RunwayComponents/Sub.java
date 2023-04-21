package comp2211.seg.UiView.Scene.RunwayComponents;

import javafx.scene.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class Sub extends SubScene {
    public Sub(Parent parent, double v, double v1) {
        super(parent, v, v1);
        build();
    }
    public Sub(Parent parent, double v, double v1, boolean b, SceneAntialiasing sceneAntialiasing) {
        super(parent, v, v1, b, sceneAntialiasing);
        build();
    }
    public void build(){

        PerspectiveCamera camera = new PerspectiveCamera();
        setCamera(camera);
    }
}
