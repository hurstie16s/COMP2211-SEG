package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Stage.AppWindow;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
        scene1 = new InputScene(new Pane(),appWindow,appWindow.getWidth(),appWindow.getHeight()/2.0);
        scene2 = new RunwayScene(new Pane(), appWindow,appWindow.getWidth()/2.0,appWindow.getHeight()/2.0);
        scene3 = new RunwayScene(new Pane(), appWindow,appWindow.getWidth()/2.0,appWindow.getHeight()/2.0);
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
                    help.toggleHelp(this.getClass().getCanonicalName());
                    break;
                case ESCAPE:
                    Platform.runLater(appWindow::startHomeScene);
            }
        }));
    }

    /**
     * Builds the main scene by adding the GUI components
     * to the root handler pane.
     */

    public void build() {
        super.build();
        makeHelp(false);
        logger.info("building main scene");
        scene1.build();
        scene2.build();
        scene3.build();
        scene1.initialise();
        scene2.initialise();
        scene3.initialise();
        scene3.toggleView();

        GridPane layout = createGridPane();
        GridPane layout2 = createGridPane();
        StackPane layout3 = new StackPane();
        configureLayout(layout, height / 2, width);
        configureLayout(layout2, height, width);

        layout.addRow(0, scene1.getRoot());
        layout2.addColumn(0, scene2.getRoot());
        layout2.addColumn(1, scene3.getRoot());
        layout.addRow(1, layout3);
        mainPane.getChildren().add(layout);

        layout.setOnMousePressed((e) -> layout.requestFocus());
        layout3.getChildren().addAll(layout2, appWindow.runway.makeErrorScene(mainPane.widthProperty().divide(1), mainPane.heightProperty().divide(2)));
        layout2.setOnMousePressed((e) -> appWindow.startRunwayScene());

        addWidthListeners(layout, scene1.root, scene2.root, scene3.root, scene2.mainPane, scene3.mainPane);
        addHeightListeners(layout, scene1.root, scene2.root, scene3.root, scene2.mainPane, scene3.mainPane);
    }

    private void configureLayout(GridPane layout, double height, double width) {
        layout.setMinHeight(height);
        layout.setMaxHeight(height);
        layout.setMaxWidth(width);
        layout.setMinWidth(width);
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(0);
        return gridPane;
    }

    private void addWidthListeners(GridPane layout, Node... nodes) {
        root.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mainPane.setMinWidth((Double) t1);
                mainPane.setMaxWidth((Double) t1);
                layout.setMaxWidth((Double) t1);
                layout.setMinWidth((Double) t1);
                for (Node node : nodes) {
                    node.minWidth((Double) t1);
                    node.maxWidth((Double) t1);
                }
            }
        });
    }

    private void addHeightListeners(GridPane layout, Node... nodes) {
        root.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mainPane.setMinHeight((Double) t1);
                mainPane.setMaxHeight((Double) t1);
                layout.setMinHeight((Double) t1);
                layout.setMaxHeight((Double) t1);
                for (Node node : nodes) {
                    node.maxHeight((Double) t1 / 2);
                    node.maxHeight((Double) t1 / 2);
                }
            }
        });
    }



}

