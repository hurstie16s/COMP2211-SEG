package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.HandlerPane;
import javafx.scene.layout.GridPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * MainScene class represents the main graphical user
 * interface for the airport runway operations system.
 * It extends the abstract SceneAbstract class.
 */
public class MainScene extends SceneAbstract{

    private static final Logger logger = LogManager.getLogger(MainScene.class);

    /**
     * The input scene for the main interface.
     */
    protected SceneAbstract scene1;

    /**
     * The first runway scene for the main interface.
     */
    protected RunwayScene scene2;

    /**
     * The second runway scene for the main interface.
     */
    protected RunwayScene scene3;

    /**
     * The main application window for the interface.
     */
    protected AppWindow appWindow;

    /**
     * Constructs a new MainScene object.
     *
     * @param root      the root handler pane for the scene
     * @param appWindow the main application window
     */
    public MainScene(HandlerPane root, AppWindow appWindow) {
        super(root, appWindow);
        scene1 = new InputScene(new HandlerPane(appWindow.getWidth(),appWindow.getHeight()/2.0),appWindow);
        scene2 = new RunwayScene(new HandlerPane(appWindow.getWidth()/2.0,appWindow.getHeight()/2.0),appWindow);
        scene3 = new RunwayScene(new HandlerPane(appWindow.getWidth()/2.0,appWindow.getHeight()/2.0),appWindow);
        this.appWindow = appWindow;
    }

    /**
     * Initializes the main scene by adding event listeners
     * and setting up the GUI components.
     */
    @Override
    public void initialise() {
        setOnKeyPressed((keyEvent -> {
            switch (keyEvent.getCode()){
                case H:
                    help.toggleHelp("Main");
                    break;
            }
        }));
    }

    /**
     * Builds the main scene by adding the GUI components
     * to the root handler pane.
     */
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
        mainPane.getChildren().add(layout);
        layout.setOnMousePressed((e) -> layout.requestFocus());
        layout2.setOnMousePressed((e) -> appWindow.startRunwayScene());
    }
}

