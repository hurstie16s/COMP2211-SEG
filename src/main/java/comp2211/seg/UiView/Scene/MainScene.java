package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.HandlerPane;
import javafx.scene.layout.GridPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainScene extends SceneAbstract{
    private static final Logger logger = LogManager.getLogger(MainScene.class);
    protected SceneAbstract scene1;
    protected RunwayScene scene2;
    protected RunwayScene scene3;
    protected AppWindow appWindow;


    public MainScene(HandlerPane root, AppWindow appWindow) {
        super(root, appWindow);
        scene1 = new InputScene(new HandlerPane(appWindow.getWidth(),appWindow.getHeight()/2.0),appWindow);
        scene2 = new RunwayScene(new HandlerPane(appWindow.getWidth()/2.0,appWindow.getHeight()/2.0),appWindow);
        scene3 = new RunwayScene(new HandlerPane(appWindow.getWidth()/2.0,appWindow.getHeight()/2.0),appWindow);
        this.appWindow = appWindow;
    }

    @Override
    public void initialise() {

    }

    public void build() {
        super.build();
        logger.info("building main scene");
        scene1.build();
        scene2.build();
        scene3.build();
        scene1.initialise();
        scene2.initialise();
        scene3.initialise();
        scene3.toggleView();
        GridPane layout = new GridPane();
        GridPane layout2 = new GridPane();
        layout.setMinHeight(height/2);
        layout.setMaxHeight(height/2);
        layout.setMaxWidth(width);
        layout.setMinWidth(width);
        layout.setVgap(0);
        layout.addRow(0,scene1.getRoot());
        layout2.addColumn(0,scene2.getRoot());
        layout2.addColumn(1,scene3.getRoot());
        layout.addRow(1,layout2);
        layout.setMinHeight(height);
        layout.setMaxHeight(height);
        layout.setMaxWidth(width);
        layout.setMinWidth(width);
        //mainPane.getChildren().add(scene2.getRoot());
        mainPane.getChildren().add(layout);
        layout2.setOnMousePressed((e) -> appWindow.startRunwayScene());



    }
}
