package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Stage.AppWindow;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The MainScene class represents the main scene of the application,
 * which includes the input form and two runway scenes for top-down and side views.
 * This class extends the SceneAbstract class and defines the layout, event listeners,
 * and handlers for the main scene.
 * The MainScene class also provides methods to configure the layout size
 * and set the minimum and maximum width and height of nodes.
 @see SceneAbstract
 @see RunwayScene
 @see InputScene
 */
public class MainScene extends SceneAbstract{

    private static final Logger logger = LogManager.getLogger(MainScene.class);

    /**
     * The input scene of the main scene.
     */
    protected SceneAbstract inputScene;

    /**
     * The runway scene for top-down view display.
     */
    protected RunwayScene runwayTopDownView;

    /**
     * The runway scene for side view display.
     */
    protected RunwayScene runwaySideView;

    /**
     * The stage on which the main scene is displayed.
     */
    protected AppWindow appWindow;

    /**
     * The root pane of the maine scene.
     */
    protected Pane root;

    /**
     * Constructs a new MainScene object.
     *
     * @param root      the root handler pane for the scene
     * @param appWindow the main application window
     */
    public MainScene(Pane root, AppWindow appWindow, double width, double height) {
        super(root, appWindow, width, height);
        this.root = root;
        inputScene = new InputScene(new Pane(),appWindow,appWindow.getWidth(),appWindow.getHeight()/2.0);
        runwayTopDownView = new RunwayScene(new Pane(), appWindow,appWindow.getWidth()/2.0,appWindow.getHeight()/2.0);
        runwaySideView = new RunwayScene(new Pane(), appWindow,appWindow.getWidth()/2.0,appWindow.getHeight()/2.0);
        this.appWindow = appWindow;
    }

    /**
     * Initializes the main scene by adding event listeners
     */
    @Override
    public void initialise() {
        setOnKeyPressed((keyEvent -> {
            switch (keyEvent.getCode()){
                case H:
                    help.toggleHelp(this.getClass().getCanonicalName());
                    break;
                case ESCAPE:
                    Platform.runLater(appWindow::startHomeScene);
            }
        }));
    }

    /**
     * Builds the main scene by setting up the grid layout and adding scenes to it.
     * Also sets up event handlers and listeners for the nodes in the layout.
     */

    public void build() {
        super.build();
        makeHelp(false);
        logger.info("building main scene");
        //build and initialise Scenes
        buildInitialiseScenes(inputScene,runwayTopDownView,runwaySideView);
        //construct new Pane instances
        GridPane inputLayout = new GridPane();
        GridPane runwaysLayout = new GridPane();
        StackPane multiLevelLayout = new StackPane();
        //set initial size of inputLayout grid
        configureLayoutSize(inputLayout, height / 2, width);
        //gap to ensure that input scene is fully visible during stage resizing
        inputLayout.setVgap(20);
        //place scenes into the grid
        inputLayout.addRow(0, inputScene.getRoot());
        runwaysLayout.addColumn(0, runwayTopDownView.getRoot());
        runwaysLayout.addColumn(1, runwaySideView.getRoot());
        inputLayout.addRow(1, multiLevelLayout);
        //reset size after setting up the grid
        configureLayoutSize(inputLayout, height, width);
        //addNodes
        multiLevelLayout.getChildren().addAll(runwaysLayout, appWindow.runway.makeErrorScene(mainPane.widthProperty().divide(1), mainPane.heightProperty().divide(2)));
        mainPane.getChildren().add(inputLayout);
        //set events
        inputLayout.setOnMousePressed((e) -> inputLayout.requestFocus());
        runwaysLayout.setOnMousePressed((e) -> appWindow.startRunwayScene());
        //add listeners to width and height properties of the nodes
        root.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                setMinMaxWidth(t1,1, mainPane, inputLayout, inputScene.root);
                setMinMaxWidth(t1,2, runwayTopDownView.root, runwaySideView.root, runwayTopDownView.mainPane, runwaySideView.mainPane);
            }
        });
        root.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                setMinMaxHeight(t1,1,mainPane,inputLayout);
                setMinMaxHeight(t1,2, inputScene.root, runwayTopDownView.root, runwaySideView.root, runwayTopDownView.mainPane, runwaySideView.mainPane);
            }
        });
    }

    /**
     * Sets the size of the given layout to the specified height and width,
     * and configures the minimum and maximum width of any child nodes
     * to be half the specified width.
     *
     * @param layout the GridPane layout to configure
     * @param height the desired height of the layout
     * @param width the desired width of the layout
     */
    private void configureLayoutSize(GridPane layout, double height, double width) {
        setMinMaxWidth(width, 1, layout);
        setMinMaxHeight(height, 1, layout);
    }

    /**
     * Sets the minimum and maximum width of the specified nodes
     * to be equal to the given width divided by the specified divisor.
     *
     * @param t1 the width value to set
     * @param div the divisor value to use
     * @param nodes the nodes to set the width on
     */
    private void setMinMaxWidth(Number t1, int div, Region... nodes) {
        for (Region node : nodes) {
            node.setMinWidth((Double) t1 / div);
            node.setMaxWidth((Double) t1 / div);
        }
    }

    /**
     * Sets the minimum and maximum height of the specified nodes
     * to be equal to the given height divided by the specified divisor.
     *
     * @param t1 the height value to set
     * @param div the divisor value to use
     * @param nodes the nodes to set the height on
     */
    private void setMinMaxHeight(Number t1, int div, Region... nodes) {
        for (Region node : nodes) {
            node.setMinHeight((Double) t1 / div);
            node.setMaxHeight((Double) t1 / div);
        }
    }

    /**
     * Builds and initialises the specified scenes.
     *
     * @param scenes the scenes to build and initialise
     */
    private void buildInitialiseScenes(SceneAbstract... scenes) {
        for (SceneAbstract scene : scenes) {
            scene.initialise();
            scene.build();
        }
    }
}

