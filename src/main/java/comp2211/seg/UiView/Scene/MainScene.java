package comp2211.seg.UiView.Scene;

import comp2211.seg.App;
import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.HandlerPane;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
        GridPane layout = new GridPane();
        GridPane layout2 = new GridPane();
        StackPane layout3 = new StackPane();
        layout.setMinHeight(height/2);
        layout.setMaxHeight(height/2);
        layout.setMaxWidth(width);
        layout.setMinWidth(width);
        layout.setVgap(0);
        layout.addRow(0,scene1.getRoot());
        layout2.addColumn(0,scene2.getRoot());
        layout2.addColumn(1,scene3.getRoot());
        layout.addRow(1,layout3);
        layout.setMinHeight(height);
        layout.setMaxHeight(height);
        layout.setMaxWidth(width);
        layout.setMinWidth(width);
        mainPane.getChildren().add(layout);
        layout.setOnMousePressed((e) -> layout.requestFocus());
        layout3.getChildren().addAll(layout2,appWindow.runway.makeErrorScene(root.widthProperty().divide(1),root.heightProperty().divide(1)));

        layout2.setOnMousePressed((e) -> appWindow.startRunwayScene());
        root.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mainPane.setMinWidth((Double) t1);
                mainPane.setMaxWidth((Double) t1);
                layout.setMaxWidth((Double) t1);
                layout.setMinWidth((Double) t1);
                scene1.root.setMinWidth((Double) t1);
                scene1.root.setMaxWidth((Double) t1);
                scene2.root.setMinWidth((Double) t1/2);
                scene2.root.setMaxWidth((Double) t1/2);
                scene3.root.setMinWidth((Double) t1/2);
                scene3.root.setMaxWidth((Double) t1/2);
                scene2.mainPane.setMinWidth((Double) t1/2);
                scene2.mainPane.setMaxWidth((Double) t1/2);
                scene3.mainPane.setMinWidth((Double) t1/2);
                scene3.mainPane.setMaxWidth((Double) t1/2);
            }
        });
        root.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mainPane.setMinHeight((Double) t1);
                mainPane.setMaxHeight((Double) t1);
                layout.setMinHeight((Double) t1);
                layout.setMaxHeight((Double) t1);
                scene1.root.setMinHeight((Double) t1/2);
                scene1.root.setMaxHeight((Double) t1/2);
                scene2.root.setMinHeight((Double) t1/2);
                scene2.root.setMaxHeight((Double) t1/2);
                scene3.root.setMinHeight((Double) t1/2);
                scene3.root.setMaxHeight((Double) t1/2);
                scene2.mainPane.setMinHeight((Double) t1/2);
                scene2.mainPane.setMaxHeight((Double) t1/2);
                scene3.mainPane.setMinHeight((Double) t1/2);
                scene3.mainPane.setMaxHeight((Double) t1/2);

            }
        });
    }
}

