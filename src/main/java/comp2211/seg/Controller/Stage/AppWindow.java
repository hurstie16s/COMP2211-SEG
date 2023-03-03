package comp2211.seg.Controller.Stage;

import comp2211.seg.App;
import comp2211.seg.ProcessDataModel.Runway;
import comp2211.seg.UiView.Scene.HomeScene;
import comp2211.seg.UiView.Scene.MainScene;
import comp2211.seg.UiView.Scene.RunwayScene;
import comp2211.seg.UiView.Scene.SceneAbstract;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The AppWindow class is responsible for managing the application's window and scenes.
 */
public class AppWindow {

    private static final Logger logger = LogManager.getLogger(AppWindow.class);

    private final Stage stage;
    private final int width;
    private final int height;
    private SceneAbstract currentScene;
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
        runway = new Runway();

        // Setup appWindow
        setupStage();
        startHomeScene();
        //startMainScene();
        //startRunwayScene();
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
        loadScene(new MainScene(new HandlerPane(width,height),this));
    }

    /**
     * Starts the runway scene.
     */
    public void startRunwayScene() {
        loadScene(new RunwayScene(new HandlerPane(getWidth(),getHeight()),this));
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