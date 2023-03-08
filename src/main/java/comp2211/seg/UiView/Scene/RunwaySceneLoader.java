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
                    appWindow.startMainScene();
                    break;
                case T:
                    scene.toggleView();
                    break;
                case H:
                    help.toggleHelp(this.getClass().getName());
                    scene.render();
                    break;
            }
        }));

    }
    public void build(){
        super.build();
        makeHelp(true);
        scene = new RunwayScene(new Pane(),appWindow, width, height);
        scene.build();
        scene.initialise();
        mainPane.getChildren().add(scene.getRoot());
        root.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                scene.root.setMinWidth((Double) t1);
                scene.root.setMaxWidth((Double) t1);
                scene.mainPane.setMinWidth((Double) t1);
                scene.mainPane.setMaxWidth((Double) t1);
            }
        });
        root.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                scene.root.setMinHeight((Double) t1);
                scene.root.setMaxHeight((Double) t1);
                scene.mainPane.setMinHeight((Double) t1);
                scene.mainPane.setMaxHeight((Double) t1);

            }
        });
    }
}
