package comp2211.seg.Controller.Stage;

import comp2211.seg.App;
import comp2211.seg.Controller.Interfaces.GlobalVariables;
import comp2211.seg.ProcessDataModel.Airport;
import comp2211.seg.ProcessDataModel.Obstacle;
import comp2211.seg.ProcessDataModel.Runway;
import comp2211.seg.UiView.Scene.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The AppWindow class is responsible for managing the application's window and scenes.
 */
public class AppWindow {

    private static final Logger logger = LogManager.getLogger(AppWindow.class);

    private final Stage stage;
    private final int width;
    private final int height;
    public SceneAbstract currentScene;
    private final ArrayList<Airport> airports;
    private Scene scene;

    public HandlerPane root;

    public Runway runway;
    public Airport airport;
    public final ArrayList<Obstacle> obstaclePresets;

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
        obstaclePresets = new ArrayList<>();
        obstaclePresetSetup();
        Theme.makeDark();

        addAirport(new Airport("Heathrow"));
        addAirport(new Airport("Gatwick"));
        addAirport(new Airport("Southampton"));
        for (Airport a: airports) {
            a.makeRunway();
        }
        airport = airports.get(0);

        runway = airport.getRunways().get(0);
        // Setup appWindow
        setupStage();
        startHomeScene();
        //startMainScene();
        //startRunwayScene();
    }
    public void addAirport(Airport airport){
        airports.add(airport);
        logger.info("Added airport: "+airport.getName()+" to list of airports");
    }
    public void addObstacle(Obstacle obstacle) {
        obstaclePresets.add(obstacle);
        logger.info("Added obstacle: "+obstacle.getObstacleDesignator()+ "to list of pre-defined obstacles");
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

    private void obstaclePresetSetup() {
        obstaclePresets.add(new Obstacle("Airbus A320-200", 11.76, 0));
        obstaclePresets.get(0).lengthProperty().set(37.57);
        obstaclePresets.get(0).widthProperty().set(35.8);
        obstaclePresets.add(new Obstacle("Boeing 737-800", 12.6, 0));
        obstaclePresets.get(1).lengthProperty().set(34.32);
        obstaclePresets.get(1).widthProperty().set(39.5);
        obstaclePresets.add(new Obstacle("Boeing 777-9", 19.68, 0));
        obstaclePresets.get(2).lengthProperty().set(76.72);
        obstaclePresets.get(2).widthProperty().set(64.84);
        obstaclePresets.add(new Obstacle("Piper M350", 3.4, 0));
        obstaclePresets.get(3).lengthProperty().set(8.8);
        obstaclePresets.get(3).widthProperty().set(13.1);
        obstaclePresets.add(new Obstacle("Pothole", 0, 0));
        obstaclePresets.get(4).lengthProperty().set(0);
        obstaclePresets.get(4).widthProperty().set(0);
        obstaclePresets.add(new Obstacle("Pushback tug", 2.5, 0));
        obstaclePresets.get(5).lengthProperty().set(5);
        obstaclePresets.get(5).widthProperty().set(2);
        obstaclePresets.add(new Obstacle("Maintenance truck", 3, 0));
        obstaclePresets.get(6).lengthProperty().set(6);
        obstaclePresets.get(6).widthProperty().set(2.5);
    }
    public void setRunway(Runway runway){
        this.runway = runway;
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
     * Starts the home scene.
     */
    public void startHomeScene() {
        loadScene(new HomeScene(new Pane(),this, width, height));
    }

    /**
     * Starts the main scene.
     */
    public void startMainScene() {
        loadScene(new MainScene(new Pane(),this, getWidth(),getHeight()));
    }
    public void startBaseScene() {
        loadScene(new BaseScene(new Pane(),this, getWidth(),getHeight()));
    }

    /**
     * Starts the runway scene.
     */
    public void startRunwayScene() {
        loadScene(new RunwayScene(new Pane(),this,getWidth(),getHeight(),true));
        //loadScene(new RunwaySceneLoader(new Pane(),this,getWidth(),getHeight()));
    }

    /**
     * Starts the runway scene.
     */
    public void startRunwaySceneRotated() {
        startRunwayScene();
        try {

            ((RunwayScene) currentScene).toggleView();
        }catch (Exception e){

            ((RunwaySceneLoader) currentScene).scene.toggleView();
        }
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
        Theme.retheme(currentScene);
        currentScene.makeHelp(false);

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

    public ArrayList<Airport> getAirports() {
        return airports;
    }

    public void startBaseSceneObstacle() {
        loadScene(new BaseScene(new Pane(),this, getWidth(),getHeight()));
        ((BaseScene) currentScene).selectObstacleMenu();
    }
}