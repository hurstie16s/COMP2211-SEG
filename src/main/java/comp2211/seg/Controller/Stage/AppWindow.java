package comp2211.seg.Controller.Stage;

import comp2211.seg.App;
import comp2211.seg.Controller.Interfaces.AirportsData;
import comp2211.seg.ProcessDataModel.Airport;
import comp2211.seg.ProcessDataModel.Obstacle;
import comp2211.seg.ProcessDataModel.Runway;
import comp2211.seg.UiView.Scene.*;
import comp2211.seg.UiView.Scene.Utilities.CssColorParser;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.css.CSSRuleList;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The AppWindow class is responsible for managing the application's window and scenes.
 */
public class AppWindow {

    private static final Logger logger = LogManager.getLogger(AppWindow.class);

    private final Stage stage;
    private final SimpleDoubleProperty width = new SimpleDoubleProperty(1280);
    private final SimpleDoubleProperty height = new SimpleDoubleProperty(720);
    /**
     * The Current scene.
     */
    public static SceneAbstract currentScene;
    private final ArrayList<Airport> airports;
    private Scene scene;

    /**
     * The Root.
     */
    public HandlerPane root;

    /**
     * The Runway.
     */
    public Runway runway;
    /**
     * The Airport.
     */
    public Airport airport;
    /**
     * The Obstacle presets.
     */
    public final ArrayList<Obstacle> obstaclePresets;
    public static SimpleStringProperty pathToStyle = new SimpleStringProperty("/style/darkStyle.css");
    public Theme theme;
    private ArrayList<Color> cssDarkColors;
    private ArrayList<Color> cssLightColors;

    private ArrayList<Color> cssBlueYellowCBColors;

    private ArrayList<Color> cssRedGreenCBColors;

    /**
     * Constructs an AppWindow object with the specified stage, width, and height.
     *
     * @param stage  the primary stage of the application
     * @param width  the width of the application's window
     * @param height the height of the application's window
     */
    public AppWindow(Stage stage, int width, int height) {
        this.stage = stage;
        this.width.set(width);
        this.height.set(height);
        setupResources("/style/darkStyle.css","/style/lightStyle.css", "/style/blueYellowCB.css", "/style/redGreenCB.css");
        stage.minWidthProperty().set(960);
        stage.minHeightProperty().set(640);

        airports = AirportsData.getAirports();
        obstaclePresets = new ArrayList<>();
        obstaclePresetSetup();
        //Theme.makeDark();

        airport = airports.get(0);
        logger.info("airport: " + airport);

        logger.info("gets runway object");
        runway = airport.getRunways().get(0);
        logger.info("runways of " + airport + " are " + runway);
        // Setup appWindow
        setupStage();
        startHomeScene();
        //startMainScene();
        //startRunwayScene();
    }


    /**
     * Sets up the resources for the application.
     */
    private void setupResources(String styleDark, String styleLight, String styleBlueYellowCB, String styleRedGreenCB) {
        logger.info("Getting stylesheets rules...");
        CSSRuleList cssRuleList1;
        try {
            cssRuleList1 = CssColorParser.getCssRules(styleDark);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CSSRuleList cssRuleList2;
        try {
            cssRuleList2 = CssColorParser.getCssRules(styleLight);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CSSRuleList cssRuleList3;
        try {
            cssRuleList3 = CssColorParser.getCssRules(styleBlueYellowCB);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CSSRuleList cssRuleList4;
        try {
            cssRuleList4 = CssColorParser.getCssRules(styleRedGreenCB);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> cssClasses = new ArrayList<>();
        cssClasses.add(".labelfg");
        cssClasses.add(".runway");
        cssClasses.add(".grass");
        cssClasses.add(".obstacle");
        cssClasses.add(".slope");
        cssClasses.add(".clearway");
        cssClasses.add(".stopway");
        cssClasses.add(".lda");
        cssClasses.add(".tora");
        cssClasses.add(".asda");
        cssClasses.add(".toda");
        cssClasses.add(".resa");
        cssClasses.add(".cga");
        cssClasses.add(".stripend");
        cssClasses.add(".blastallowance");
        cssClasses.add(".physicalResa");
        ArrayList<String> cssPrefixes = new ArrayList<>();
        cssPrefixes.add("-fx-text-fill:");
        cssPrefixes.add("-fx-background-color:");
        cssPrefixes.add("-fx-background-color:");
        cssPrefixes.add("-fx-background-color:");
        cssPrefixes.add("-fx-background-color:");
        cssPrefixes.add("-fx-background-color:");
        cssPrefixes.add("-fx-background-color:");
        cssPrefixes.add("-fx-background-color:");
        cssPrefixes.add("-fx-background-color:");
        cssPrefixes.add("-fx-background-color:");
        cssPrefixes.add("-fx-background-color:");
        cssPrefixes.add("-fx-background-color:");
        cssPrefixes.add("-fx-background-color:");
        cssPrefixes.add("-fx-background-color:");
        cssPrefixes.add("-fx-background-color:");
        cssPrefixes.add("-fx-background-color:");
        cssDarkColors = CssColorParser.getCssColors(cssRuleList1,cssClasses,cssPrefixes);
        cssLightColors = CssColorParser.getCssColors(cssRuleList2,cssClasses,cssPrefixes);
        cssBlueYellowCBColors = CssColorParser.getCssColors(cssRuleList3,cssClasses,cssPrefixes);
        cssRedGreenCBColors = CssColorParser.getCssColors(cssRuleList4,cssClasses,cssPrefixes);
        theme = new Theme();
        logger.info("Setting theme colors...");
        assert cssDarkColors != null;
        //set default
        theme.setThemeColors(cssDarkColors);
    }

    /**
     * Add airport.
     *
     * @param airport the airport
     */
    public void addAirport(Airport airport){
        airports.add(airport);
        logger.info("Added airport: "+airport.getName()+" to list of airports");
    }

    /**
     * Add obstacle.
     *
     * @param obstacle the obstacle
     */
    public void addObstacle(Obstacle obstacle) {
        obstaclePresets.add(obstacle);
        logger.info("Added obstacle: "+obstacle.getObstacleDesignator()+ "to list of pre-defined obstacles");
    }

    /**
     * Set airport.
     *
     * @param airport the airport
     */
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
        obstaclePresets.add(new Obstacle("Default", 5, 0));
        obstaclePresets.get(7).lengthProperty().set(100.0);
        obstaclePresets.get(7).widthProperty().set(60.0);
    }

    /**
     * Set runway.
     *
     * @param runway the runway
     */
    public void setRunway(Runway runway){
        logger.info("set Runway with: " + runway);
        Runway temp = new Runway();

        temp.dualDirectionRunway.set(this.runway.dualDirectionRunway.get());
        temp.setRunwayDesignatorLeft(this.runway.getRunwayDesignatorLeft());
        temp.setRunwayDesignatorRight(this.runway.getRunwayDesignatorRight());
        temp.setInputLeftTora(this.runway.getInputLeftTora());
        temp.setInputLeftToda(this.runway.getInputLeftToda());
        temp.setInputLeftLda(this.runway.getInputLeftLda());
        temp.setInputLeftAsda(this.runway.getInputLeftAsda());
        temp.setInputRightTora(this.runway.getInputRightTora());
        temp.setInputRightToda(this.runway.getInputRightToda());
        temp.setInputRightLda(this.runway.getInputRightLda());
        temp.setInputRightAsda(this.runway.getInputRightAsda());

        if(!(runway == null)) {
            this.runway.dualDirectionRunway.set(runway.dualDirectionRunway.get());
            this.runway.setRunwayDesignatorLeft(runway.getRunwayDesignatorLeft());
            this.runway.setRunwayDesignatorRight(runway.getRunwayDesignatorRight());
            this.runway.setInputLeftTora(runway.getInputLeftTora());
            this.runway.setInputLeftToda(runway.getInputLeftToda());
            this.runway.setInputLeftLda(runway.getInputLeftLda());
            this.runway.setInputLeftAsda(runway.getInputLeftAsda());
            this.runway.setInputRightTora(runway.getInputRightTora());
            this.runway.setInputRightToda(runway.getInputRightToda());
            this.runway.setInputRightLda(runway.getInputRightLda());
            this.runway.setInputRightAsda(runway.getInputRightAsda());


            runway.dualDirectionRunway.set(temp.dualDirectionRunway.get());
            runway.setRunwayDesignatorLeft(temp.getRunwayDesignatorLeft());
            runway.setRunwayDesignatorRight(temp.getRunwayDesignatorRight());
            runway.setInputLeftTora(temp.getInputLeftTora());
            runway.setInputLeftToda(temp.getInputLeftToda());
            runway.setInputLeftLda(temp.getInputLeftLda());
            runway.setInputLeftAsda(temp.getInputLeftAsda());
            runway.setInputRightTora(temp.getInputRightTora());
            runway.setInputRightToda(temp.getInputRightToda());
            runway.setInputRightLda(temp.getInputRightLda());
            runway.setInputRightAsda(temp.getInputRightAsda());
        }
    }

    /**
     * Sets up the primary stage of the application.
     */
    public void setupStage() {
        stage.setTitle("Runway tool");
        stage.setOnCloseRequest(ev -> App.getInstance().shutdown());

    }
    public void reloadScene(){
        if (currentScene instanceof HomeScene){
            startHomeScene();
        }else if (currentScene instanceof BaseScene){
            startBaseScene();
        }  else if (currentScene instanceof RunwayScene || currentScene instanceof RunwaySceneLoader){
            startRunwayScene();
        }
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

    /**
     * Start base scene.
     */
    public void startBaseScene() {
        loadScene(new BaseScene(new Pane(),this, width,height));
    }

    /**
     * Starts the runway scene.
     */
    public void startRunwayScene() {
        //loadScene(new RunwayScene(new Pane(),this,getWidth(),getHeight(),true));
        //((RunwayScene) currentScene).makeAlignButton();
        loadScene(new RunwaySceneLoader(new Pane(),this, width,height));
        ((RunwaySceneLoader) currentScene).scene.makeAlignButton();
    }

    /**
     * Starts the runway scene.
     */
    public void startRunwaySceneRotated() {
        startRunwayScene();
        if (currentScene instanceof RunwayScene){
            ((RunwayScene) currentScene).toggleView();
        } else if (currentScene instanceof RunwaySceneLoader) {
            ((RunwaySceneLoader) currentScene).scene.toggleView();
        }
    }

    /**
     * Loads a new scene.
     *
     * @param newScene the scene to load
     */
    public void loadScene(SceneAbstract newScene) {

        // Create the new scene and set it up
        newScene.build();
        currentScene = newScene;
        currentScene.getRoot().getStylesheets().add(pathToStyle.get());

        stage.setScene(currentScene);
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
     * Gets airports.
     *
     * @return the airports
     */
    public ArrayList<Airport> getAirports() {
        return airports;
    }


    /**
     * Cleans up the remains of the previous scene.
     */
    public void cleanup(){
        //clear listeners
        stage.setScene(null);
    }

    public void setStyle(String pathToStyle,String style) {

        if(!(pathToStyle.equals(AppWindow.pathToStyle.get()))) {

            logger.info("theme changed to "+ pathToStyle);
            AppWindow.pathToStyle.set(pathToStyle);
            if (style.equals("d")) {
                theme.setThemeColors(cssDarkColors);
            } else if (style.equals("e")) {
                theme.setThemeColors(cssRedGreenCBColors);
            } else if (style.equals("f")) {
                theme.setThemeColors(cssBlueYellowCBColors);
            } else {
                theme.setThemeColors(cssLightColors);
            }
            // Cleanup remains of the previous scene
            //cleanup();
            //this.loadScene(currentScene);
            reloadScene();
        } else {
            logger.info(pathToStyle + " is current style");
        }
    }

    public double getWidth() {
        return width.get();
    }
    public double getHeight() {
        return height.get();
    }


}