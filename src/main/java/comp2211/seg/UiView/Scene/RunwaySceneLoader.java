package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.HandlerPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;

public class RunwaySceneLoader extends SceneAbstract{
    private RunwayScene scene;
    /**
     * Constructor to create a SceneAbstract object.
     *
     * @param root      the root pane of the scene
     * @param appWindow the application window of the scene
     */
    public RunwaySceneLoader(HandlerPane root, AppWindow appWindow) {
        super(root, appWindow);
    }

    @Override
    public void initialise() {
        setOnKeyPressed((keyEvent -> {
            switch (keyEvent.getCode()){
                case ESCAPE:
                    appWindow.startMainScene();
                    break;
                case T:
                    scene.toggleView();
                    break;
                case H:
                    help.toggleHelp(this.getClass().getCanonicalName());
                    break;
            }
        }));

    }
    public void build(){
        super.build();
        scene = new RunwayScene(new Pane(),appWindow,appWindow.getWidth(), appWindow.getHeight());
        scene.build();
        scene.initialise();
        root.getChildren().add(scene.getRoot());
        root.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                scene.root.setMinWidth((Double) t1);
                scene.root.setMaxWidth((Double) t1);
            }
        });
        root.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                scene.root.setMinHeight((Double) t1);
                scene.root.setMaxHeight((Double) t1);

            }
        });
    }
}
