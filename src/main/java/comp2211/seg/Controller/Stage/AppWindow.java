package comp2211.seg.Controller.Stage;

import comp2211.seg.App;
import comp2211.seg.ProcessDataModel.Airport;
import comp2211.seg.ProcessDataModel.Runway;
import comp2211.seg.UiView.Scene.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * The AppWindow class is responsible for managing the application's window and scenes.
 */
public class AppWindow {

    private static final Logger logger = LogManager.getLogger(AppWindow.class);

    private final Stage stage;
    private final int width;
    private final int height;
    private SceneAbstract currentScene;
    private ArrayList<Airport> airports;
    private Scene scene;

    public HandlerPane root;

    public Runway runway;

    /**
     * Constructs an AppWindow object with the specified stage, width, and height.
     *
     * @param stage the primary stage of the application
     * @param width the width of the application's window
     * @param height the height of the application's window
     */
    public AppWindow(Stage stage, int width, int height) {
        this.stage = stage;
        this.width = width;
        this.height = height;
        airports = new ArrayList<>();



        // Setup appWindow
        setupStage();
        startHomeScene();
        //addAirport(new Airport(""));
        //startMainScene();
        //startRunwayScene();
    }
    public void addAirport(Airport airport){
        airports.add(airport);
        runway = airports.get(0).getRunways().get(0);
    }

    /**
     * Loads any required resources for the application.
     */
    private void setupResources() {
        logger.info("Loading resources"); //no resources used atm i.e. CSS/FXML
    }

    /**
     * Sets up the primary stage of the application.
     */
    public void setupStage() {
        stage.setTitle("AppWindow");
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.setOnCloseRequest(ev -> App.getInstance().shutdown());
    }

    /**
     * Starts the home scene.
     */
    public void startHomeScene() {
        loadScene(new HomeScene(new HandlerPane(width,height),this));
    }

    /**
     * Starts the main scene.
     */
    public void startMainScene() {
        loadScene(new MainScene(new Pane(),this, getWidth(),getHeight()));
    }

    /**
     * Starts the runway scene.
     */
    public void startRunwayScene() {
        loadScene(new RunwaySceneLoader(new Pane(),this,getWidth(),getHeight()));
    }

    /**
     * Loads a new scene.
     *
     * @param newScene the scene to load
     */
    public void loadScene(SceneAbstract newScene) {
        // Cleanup remains of the previous scene
        cleanup();
        // Create the new scene and set it up
        newScene.build();
        currentScene = newScene;
        stage.setScene(currentScene);

        // Initialise the scene when ready
        Platform.runLater(() -> currentScene.initialise());
    }

    /**
     * Gets the primary stage of the application.
     *
     * @return the primary stage of the application
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Gets the width of the application's window.
     *
     * @return the width of the application's window
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the application's window.
     *
     * @return the height of the application's window
     */
    public int getHeight() {
        return height;
    }

    /**
     * Cleans up the remains of the previous scene.
     */
    public void cleanup(){
        //clear listeners
        //root.getChildren().removeAll(root.getChildren());
    }
}