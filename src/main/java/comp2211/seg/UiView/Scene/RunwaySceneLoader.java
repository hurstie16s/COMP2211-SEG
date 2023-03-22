package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.HandlerPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;

public class RunwaySceneLoader extends SceneAbstract{
    public RunwayScene scene;
    /**
     * Constructor to create a SceneAbstract object.
     *
     * @param root      the root pane of the scene
     * @param appWindow the application window of the scene
     */
    public RunwaySceneLoader(Pane root, AppWindow appWindow, double width, double height) {
        super(root, appWindow, width, height);
        this.width = width;
        this.height = height;
    }

    @Override
    public void initialise() {
        setOnKeyPressed((keyEvent -> {
            switch (keyEvent.getCode()){
                case ESCAPE:
                    appWindow.startBaseSceneObstacle();
                    break;
                case W:
                    scene.group.translateYProperty().set(scene.group.getTranslateY()-10);
                    break;
                case A:
                    scene.group.translateXProperty().set(scene.group.getTranslateX()-10);
                    break;
                case S:
                    scene.group.translateYProperty().set(scene.group.getTranslateY()+10);
                    break;
                case D:
                    scene.group.translateXProperty().set(scene.group.getTranslateX()+10);
                    break;
                case T:
                    scene.toggleView();
                    break;
                case H:
                    help.toggleHelp(this.getClass().getCanonicalName());
                    scene.refresh();
                    break;
            }
        }));
        setOnMousePressed(event -> {
            scene.x = event.getSceneX();
            scene.y = event.getSceneY();
            scene.anglex = scene.angleXProperty.get();
            scene.angley = scene.angleZProperty.get();
        });
        setOnMouseDragged(event ->{
            if (!scene.sideProperty.get()) {
                //angleXProperty.set(Math.min(Math.max(anglex + y - event.getSceneY(), -90), 0));
                scene.angleZProperty.set(scene.angley - scene.x + event.getSceneX());
            }
        });
        setOnScroll(event -> scene.camera.translateZProperty().set(scene.camera.getTranslateZ()+event.getDeltaY()));


    }
    public void build(){
        super.build();
        scene = new RunwayScene(new Pane(),appWindow, width, height,false);
        scene.buildmenuless();
        scene.initialise();
        mainPane.getChildren().add(scene.getRoot());
        root.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                scene.mainPane.setMinWidth((Double) t1);
                scene.mainPane.setMaxWidth((Double) t1);
            }
        });
        root.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                scene.mainPane.setMinHeight((Double) t1);
                scene.mainPane.setMaxHeight((Double) t1);

            }
        });
    }
    public void buildmenuless(){
        super.buildmenuless();
        scene = new RunwayScene(new Pane(),appWindow, width, height,false);
        scene.buildmenuless();
        scene.initialise();
        mainPane.getChildren().add(scene.getRoot());
        root.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                scene.mainPane.setMinWidth((Double) t1);
                scene.mainPane.setMaxWidth((Double) t1);
            }
        });
        root.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                scene.mainPane.setMinHeight((Double) t1);
                scene.mainPane.setMaxHeight((Double) t1);

            }
        });
    }
}
