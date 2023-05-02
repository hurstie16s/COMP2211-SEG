package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.Settings;
import comp2211.seg.ProcessDataModel.Airport;
import comp2211.seg.ProcessDataModel.FileHandler;
import comp2211.seg.ProcessDataModel.Obstacle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

/**
 * This abstract class serves as a template for all JavaFX scenes.
 * It extends the JavaFX Scene class
 * and contains common functionality shared by all scenes.
 */
public abstract class SceneAbstract extends Scene {

  private static final Logger logger = LogManager.getLogger(SceneAbstract.class);
  /**
   * The App window.
   */
  protected final AppWindow appWindow;

  /**
   * The root node of the scene.
   */
  public Pane root;

  /**
   * The main pane of the scene.
   */
  public StackPane mainPane;
  public VBox topMenu;

  /**
   * The width of the scene.
   */
  protected double width;

  /**
   * The height of the scene.
   */
  protected double height;
  /**
   * The Help.
   */
  protected HelpScene help;
  protected String type;

  public static ArrayList<SceneAbstract> refreshables = new ArrayList<>();

  /**
   * Constructor to create a SceneAbstract object.
   *
   * @param root      the root pane of the scene
   * @param appWindow the application window of the scene
   * @param width     the width
   * @param height    the height
   */
  public SceneAbstract(Pane root, AppWindow appWindow, SimpleDoubleProperty width, SimpleDoubleProperty height) {
    super(root, width.get(), height.get(), Color.BLACK);

    logger.info(Double.toString(appWindow.getWidth()) + " " + Double.toString(appWindow.getHeight()));
    this.root = root;
    width.bind(root.widthProperty());
    height.bind(root.heightProperty());

    this.appWindow = appWindow;
  }

  /**
   * Instantiates a new Scene abstract.
   *
   * @param root        the root
   * @param appWindow   the app window
   * @param width       the width
   * @param height      the height
   * @param depthBuffer the depth buffer
   */
  public SceneAbstract(Pane root, AppWindow appWindow, SimpleDoubleProperty width, SimpleDoubleProperty height, boolean depthBuffer) {
    super(root, width.get(), height.get(), depthBuffer, SceneAntialiasing.BALANCED);
    logger.info(Double.toString(appWindow.getWidth()) + " " + Double.toString(appWindow.getHeight()));
    this.root = root;
    width.bind(root.widthProperty());
    height.bind(root.heightProperty());
    this.appWindow = appWindow;
  }

  /**
   * Abstract method for initialization.
   */
  public abstract void initialise();

  public void refresh(){
    for (SceneAbstract scene: refreshables) {
      scene.refresh();
    }
  }

  public void reset(){
    for (SceneAbstract scene: refreshables) {
      scene.reset();
    }
    refresh();
  }

  /**
   * Generic build method to create the basic requirements for a JavaFX Scene
   * This is used to define a generic structure used by all the children
   */
  public void build() {
    type = "build";
    //try {
      //getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/darkStyle.css")).toExternalForm());
      //getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/lightStyle.css")).toExternalForm());

    //} catch (Exception e) {
      //logger.error(e);
    //}
    mainPane = new StackPane();
    mainPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
    mainPane.setPickOnBounds(false);
    root.setPickOnBounds(false);
    //root.setBackground(new Background(new BackgroundFill(Theme.unfocusedBG, null, null)));
    root.getStyleClass().add("unfocusedBG");

    //Top menu
    Menu fileMenu = new Menu("File");
    Menu optionsMenu = new Menu("Options");
    Menu themeMenu = new Menu("Colour Schemes");
    MenuItem darkStyle = new MenuItem("Dark Theme");
    MenuItem lightStyle = new MenuItem("Light Theme");
    MenuItem blueYellowCBStyle = new MenuItem("Tritanopia Theme");
    MenuItem redGreenCBStyle = new MenuItem("Deuteranomaly Theme");
    themeMenu.getItems().addAll(darkStyle,lightStyle, blueYellowCBStyle, redGreenCBStyle);
    optionsMenu.getItems().add(themeMenu);


    Menu helpMenu = new Menu("Help");

    MenuItem menu8 = new MenuItem("Help menu");
    helpMenu.getItems().add(menu8);

    Menu menu4 = new Menu("Import from XML");

    MenuItem menuImport1 = new MenuItem("Import Airport & Obstacle...");
    MenuItem menuImport2 = new MenuItem("Import Obstacle...");
    MenuItem menuImport3 = new MenuItem("Import Airport without Obstacle...");

    Menu menu5 = new Menu("Export to XML");
    MenuItem menu6 = new MenuItem("Export Obstacle...");
    MenuItem menu7 = new MenuItem("Export Airport & Obstacle...");
    MenuItem menu15 = new MenuItem("Export Airport...");

    //Aleks exporting image:
    Menu menu11 = new Menu("Export to PNG Image");
    MenuItem menu12 = new MenuItem("Export Top-down View");
    MenuItem menu13 = new MenuItem("Export Side-on View");
    menu11.getItems().addAll(menu12, menu13);
    //Aleks formats:
//    MenuItem menu12png = new MenuItem("PNG");
//    MenuItem menu12jpg = new MenuItem("jpg");
//    MenuItem menu12gif = new MenuItem("gif");
//    MenuItem menu13png = new MenuItem("PNG");
//    MenuItem menu13jpg = new MenuItem("jpg");
//    MenuItem menu13gif = new MenuItem("gif");

    if(!(this instanceof HomeScene)) {
      fileMenu.getItems().addAll(menu4, menu5, menu11); //Alex add menu11 to File menu
    } else {
      fileMenu.getItems().addAll(menu4);
    }
    menu4.getItems().addAll(menuImport1, menuImport2, menuImport3);
    menu5.getItems().addAll(menu7, menu15, menu6);

//      menu12.getItems().addAll(menu12png);//, menu12jpg, menu12gif);
//      menu13.getItems().addAll(menu13png);//, menu13jpg, menu13gif);
//      menu11.getItems().addAll(menu12, menu13);


    MenuBar menuBar = new MenuBar();
    menuBar.getMenus().addAll(fileMenu, optionsMenu, helpMenu);

    //Region region = new Region();
    //Button sillyButton = new Button("Silly");
    //HBox sillyHBox = new HBox();
    //sillyHBox.getChildren().addAll(menuBar,region,sillyButton);
    //HBox.setHgrow(region,Priority.ALWAYS);
    topMenu = new VBox(menuBar);
    //topMenu = new VBox(sillyHBox);
    topMenu.setAlignment(Pos.TOP_CENTER);
    topMenu.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
    VBox layoutPane = new VBox(topMenu,mainPane);

    root.getChildren().add(layoutPane);

    //
    menu7.setOnAction(e -> exportAirportButtonEvent());

    menu6.setOnAction(e -> exportObstacleButtonEvent());

    menu15.setOnAction(e -> exportAirportNoObsButtonEvent());

    menu8.setOnAction(e -> help.toggleHelp(this.getClass().getCanonicalName()));

    menuImport1.setOnAction(e -> importAirportWithObstacleButtonEvent());

    menuImport2.setOnAction(e -> importObstacleButtonEvent());

    menuImport3.setOnAction(e -> importAirportNoObsEvent());

    menu12.setOnAction(e -> exportTopDownViewButtonEvent("png"));
   // menu12jpg.setOnAction(e -> exportTopDownViewButtonEvent("jpg"));
   // menu12gif.setOnAction(e -> exportTopDownViewButtonEvent("gif"));
    menu13.setOnAction(e -> exportSideViewButtonEvent("png"));
   // menu13jpg.setOnAction(e -> exportSideViewButtonEvent("jpg"));
   // menu13gif.setOnAction(e -> exportTopDownViewButtonEvent("gif"));
    darkStyle.setOnAction(e -> appWindow.setStyle("/style/darkStyle.css","d"));
    lightStyle.setOnAction(e -> appWindow.setStyle("/style/lightStyle.css","l"));
    blueYellowCBStyle.setOnAction(e -> appWindow.setStyle("/style/blueYellowCB.css","f"));
    redGreenCBStyle.setOnAction(e -> appWindow.setStyle("/style/redGreenCB.css","e"));

  }

  /**
   * Generic build method to create the basic requirements for a JavaFX Scene
   * This is used to define a generic structure used by all the children
   */
  public void buildmenuless() {
    type = "buildmenuless";
//    try {
//      getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/darkStyle.css")).toExternalForm());
//      getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/lightStyle.css")).toExternalForm());
//
//    } catch (Exception e) {
//     logger.error(e);
//    }
    mainPane = new StackPane();
    mainPane.maxWidthProperty().bind(root.widthProperty());
    mainPane.minWidthProperty().bind(root.widthProperty());
    mainPane.maxHeightProperty().bind(root.heightProperty());
    mainPane.minHeightProperty().bind(root.heightProperty());
    mainPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
    mainPane.setPickOnBounds(false);
    root.setPickOnBounds(false);
    //root.setBackground(new Background(new BackgroundFill(Theme.unfocusedBG, null, null)));
    root.getStyleClass().add("unfocusedBG");
    root.getChildren().add(mainPane);
  }

  /**
   * Make help.
   *
   * @param left the left
   */
  public void makeHelp(boolean left){
    if (!left) {

      help = new HelpScene(new VBox(), appWindow);
      VBox alignPane = new VBox();
      alignPane.setMaxWidth(root.getMaxWidth());
      alignPane.setMinWidth(root.getMaxWidth());
      alignPane.getChildren().add(help.getRoot());
      alignPane.setAlignment(Pos.TOP_RIGHT);

      root.getChildren().add(alignPane);
      help.getRoot().setPickOnBounds(false);
      alignPane.setPickOnBounds(false);
      help.toggleHelp(this.getClass().getName());
      help.toggleHelp(this.getClass().getName());
      alignPane.maxWidthProperty().bind(root.widthProperty());
      alignPane.minWidthProperty().bind(root.widthProperty());
    } else{

      help = new HelpScene(new VBox(), appWindow);
      root.getChildren().add(help.getRoot());
      help.toggleHelp(this.getClass().getName());

    }
  }

  //exporting the Airport with runways and no objects
  public void exportAirportNoObsButtonEvent() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choose file to export");
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML format(*.xml)","*.xml");
    fileChooser.getExtensionFilters().add(extFilter);
    fileChooser.setInitialFileName("Airport");
    File file = fileChooser.showSaveDialog(new Stage());

    try {
      if (file == null) {
        return;
      }

      if (!file.getName().contains(".xml")) {
        logger.info("reached");
        file = new File(file.getAbsolutePath() + ".xml");
        logger.info(file.getAbsolutePath());
      }

      if (FileHandler.exportAirport(file, appWindow.airport, appWindow.runway.runwayObstacle)) {
        FileHandler.exportAirport(file, appWindow.airport,appWindow.runway.runwayObstacle);
        logger.info("Exporting Successful");
      } else {
        logger.info("Exporting Airport failed");
      }
    } catch (NullPointerException nullPointerException) {
      logger.info("No airport initiated, hence: " +
              "Exception in thread \"JavaFX Application Thread\" java.lang.NullPointerException: " +
              "Cannot invoke \"comp2211.seg.ProcessDataModel.Airport.toString()\" because \"airport\" is null");
    }
  }

  /**
   * Export airport button event.
   */
// repeated method to export button (remove in base scene when done)
  public void exportAirportButtonEvent() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choose file to export");
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML format(*.xml)","*.xml");
    fileChooser.getExtensionFilters().add(extFilter);
    fileChooser.setInitialFileName("Airport");
    File file = fileChooser.showSaveDialog(new Stage());

    try {
      if (file == null) {
        return;
      }

      if (!file.getName().contains(".xml")) {
        logger.info("reached");
        file = new File(file.getAbsolutePath() + ".xml");
        logger.info(file.getAbsolutePath());
      }

      if (FileHandler.exportAirportAndOb(file, appWindow.airport, appWindow.runway.runwayObstacle)) {
        FileHandler.exportAirportAndOb(file, appWindow.airport,appWindow.runway.runwayObstacle);
        logger.info("Exporting Successful");
      } else {
        logger.info("Exporting Airport failed");
      }
    } catch (NullPointerException nullPointerException) {
      logger.info("No airport initiated, hence: " +
              "Exception in thread \"JavaFX Application Thread\" java.lang.NullPointerException: " +
              "Cannot invoke \"comp2211.seg.ProcessDataModel.Airport.toString()\" because \"airport\" is null");
    }
  }

  /**
   * Export obstacle button event.
   */
// Repeated method for exporting obstacle event, remove this from base scene when done
  public void exportObstacleButtonEvent() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choose file to export");
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML format(*.xml)","*.xml");
    fileChooser.getExtensionFilters().add(extFilter);
    fileChooser.setInitialFileName("Obstacle");
    File file = fileChooser.showSaveDialog(new Stage());

    try {
      if (file == null) {
        return;
      }

      if (!file.getName().contains(".xml")) {
        logger.info("reached");
        file = new File(file.getAbsolutePath() + ".xml");
        logger.info(file.getAbsolutePath());
      }

      if (FileHandler.exportObstacle(file, appWindow.runway.runwayObstacle)) {
        FileHandler.exportObstacle(file, appWindow.runway.runwayObstacle);
        logger.info("Exporting Successful");
      } else {
        logger.info("Exporting Obstacle failed");
      }
    } catch (NullPointerException nullPointerException) {
      logger.info("No Obstacle initiated, hence: " +
              "Exception in thread \"JavaFX Application Thread\" java.lang.NullPointerException: " +
              "Cannot invoke \"comp2211.seg.ProcessDataModel.Airport.toString()\" because \"airport\" is null");
    }
  }

  /**
   * Import airport button event.
   */
  protected void importAirportWithObstacleButtonEvent() {
    try {
      logger.info("Import airport");
      File file = Objects.requireNonNull(generateImportFileChooser("airport").showOpenDialog(new Stage()));

      Airport airport = Objects.requireNonNull(FileHandler.importAirportWithObstacles(file));
      // Add airport to AppWindow
      logger.info("Sending airport: "+airport.getName()+" to AppWindow");
      appWindow.addAirport(airport);
    } catch (NullPointerException e) {
      logger.warn(e.getMessage());
    }

  }

  protected void importAirportNoObsEvent() {
      try {
          logger.info("Import airport");
          File file = Objects.requireNonNull(generateImportFileChooser("airport").showOpenDialog(new Stage()));

          Airport airport = Objects.requireNonNull(FileHandler.importAirport(file));
          // Add airport to AppWindow
          logger.info("Sending airport: "+airport.getName()+" to AppWindow");
          appWindow.addAirport(airport);
      } catch (NullPointerException e) {
          logger.warn(e.getMessage());
      }

  }

  /**
   * Import obstacle button event.
   */
  protected void importObstacleButtonEvent() {
    try {
      logger.info("Import obstacle");
      File file = Objects.requireNonNull(generateImportFileChooser("obstacle").showOpenDialog(new Stage()));

      Obstacle obstacle = Objects.requireNonNull(FileHandler.importObstacle(file));
      // Add obstacle to AppWindow
      logger.info("Sending obstacle: "+obstacle.getObstacleDesignator()+" to AppWindow");
      appWindow.addObstacle(obstacle);
    } catch (NullPointerException e) {
      logger.warn(e.getMessage());
    }

  }

  /**
   * Export Top-down View button event
   */
  protected void exportTopDownViewButtonEvent(String format) {
    logger.info("exporting TopDownView ... ");
    double outputWidth = 1280;
    double outputHeight = 720;
    RunwaySceneLoader runwayScene = new RunwaySceneLoader(new Pane(), appWindow,outputWidth,outputHeight);
    runwayScene.buildmenulessalt();
    if (Settings.portrait.get()) {
      runwayScene.scene.angleXProperty().set(180);
      runwayScene.scene.angleYProperty().set(0);
      runwayScene.scene.angleZProperty().set(-90);
      runwayScene.scene.portrait.set(true);
    }
    runwayScene.scene.root.getStyleClass().add("sky");
    WritableImage image = runwayScene.scene.root.snapshot(null,null);
    exportImage(format,image);
  }
  /**
   * Export Top-down View button event
   */
  protected void exportSideViewButtonEvent(String format) {
    logger.info("exporting SideView ... ");
    double outputWidth = 1280;
    double outputHeight = 720;
    RunwaySceneLoader runwayScene = new RunwaySceneLoader(new Pane(), appWindow, outputWidth, outputHeight);
    runwayScene.buildmenulessalt();
    if (Settings.portrait.get()) {
      runwayScene.scene.angleYProperty().set(90);
      runwayScene.scene.angleXProperty().set(90);
      runwayScene.scene.portrait.set(true);
    } else {
      runwayScene.scene.toggleView();
    }
    runwayScene.scene.root.getStyleClass().add("sky");
    WritableImage image = runwayScene.scene.root.snapshot(null, null);
    exportImage(format, image);
  }

  protected void exportImage(String format, WritableImage image) {

    // Create an ImageView to display the preview image
    ImageView preview = new ImageView(image);
    preview.setPreserveRatio(true);
    preview.setFitWidth(600);

    // Create a dialog to display the preview
    Dialog<Void> previewDialog = new Dialog<>();
    previewDialog.setTitle("Preview");
    previewDialog.getDialogPane().setContent(preview);

    // Add a "Export" button to the dialog
    ButtonType exportButtonType = new ButtonType("Export", ButtonBar.ButtonData.OK_DONE);
    previewDialog.getDialogPane().getButtonTypes().add(exportButtonType);

    // Add a "Cancel" button to the dialog
    previewDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

    // Show the preview dialog and wait for a button to be clicked
    Optional result = previewDialog.showAndWait();

    // If the "Export" button was clicked, save the image to a file
    if (result.isPresent()) {
      if (result.get() == exportButtonType) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export side view");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(format.toUpperCase() + " format(*." + format + ")", "*." + format);
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("Side_View");
        File file = fileChooser.showSaveDialog(new Stage());

        try {
          ImageIO.write(SwingFXUtils.fromFXImage(image, null), format, file);
          logger.info("image exported");
        } catch (IOException e) {
          logger.error(e);
        }
      }
    }
  }


  private FileChooser generateImportFileChooser(String item) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choose file to import "+item);
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML format(*.xml)","*.xml");
    fileChooser.getExtensionFilters().add(extFilter);
    return fileChooser;
  }


}
