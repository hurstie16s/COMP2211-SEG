package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.HandlerPane;

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
        scene = new RunwayScene(new HandlerPane(appWindow.getWidth(),appWindow.getHeight()),appWindow);
        scene.build();
        scene.initialise();
        root.getChildren().add(scene.getRoot());
    }
}
