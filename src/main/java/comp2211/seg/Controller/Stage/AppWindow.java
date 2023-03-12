package comp2211.seg.Controller.Stage;

import comp2211.seg.App;
import comp2211.seg.ProcessDataModel.Airport;
import comp2211.seg.ProcessDataModel.Runway;
import comp2211.seg.UiView.Scene.HomeScene;
import comp2211.seg.UiView.Scene.MainScene;
import comp2211.seg.UiView.Scene.RunwaySceneLoader;
import comp2211.seg.UiView.Scene.SceneAbstract;
import javafx.application.Platform;
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
    public HandlerPane root;
    public Runway runway;
    public Airport airport;

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

        addAirport(new Airport("Heathrow"));
        addAirport(new Airport("Gatwick"));
        addAirport(new Airport("Southampton"));
        // Setup appWindow
        setupStage();
        startHomeScene();

        //for individual scene testing:
        //startMainScene();
        //startRunwayScene();
    }
    public void addAirport(Airport airport){
        airports.add(airport);
        runway = airport.getRunways().get(0);
    }
    public void setAirport(Airport airport){
        if (airport.name.equals("")){
            stage.setTitle("Runway tool");
        }else {
            stage.setTitle(airport.name);
        }
        this.airport = airport;
        runway = airport.getRunways().get(0);
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
        stage.setTitle("Runway tool");
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.setOnCloseRequest(ev -> App.getInstance().shutdown());
    }

    /**
     * This method starts the home scene of the application.
     * It loads a new instance of the {@link HomeScene} class using a new {@link Pane}
     * as the root node and sets the width and height of the scene.
     */
    public void startHomeScene() {
        loadScene(new HomeScene(new Pane(), this, width, height));
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
     * Returns a list of all the airports stored in this object.
     *
     * @return An {@link ArrayList} of {@link Airport} objects.
     */
    public ArrayList<Airport> getAirports() {
        return airports;
    }

    /**
     * Cleans up the remains of the previous scene.
     */
    public void cleanup(){
        /* if(this.root != null) {
            root.getChildren().removeAll(root.getChildren());
            logger.info("Scene clean");
        } else {
            logger.info("Nothing to clean");
        }*/
    }
}