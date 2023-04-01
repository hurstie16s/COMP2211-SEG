package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.Theme;
import comp2211.seg.ProcessDataModel.Airport;
import comp2211.seg.ProcessDataModel.FileHandler;
import comp2211.seg.ProcessDataModel.Obstacle;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Objects;

/**
 * This abstract class serves as a template for all JavaFX scenes.
 * It extends the JavaFX Scene class
 * and contains common functionality shared by all scenes.
 */
public abstract class SceneAbstract extends Scene {

  private static final Logger logger = LogManager.getLogger(SceneAbstract.class);
  protected final AppWindow appWindow;

  /**
   * The root node of the scene.
   */
  public Pane root;

  /**
   * The main pane of the scene.
   */
  public StackPane mainPane;

  /**
   * The width of the scene.
   */
  protected double width;

  /**
   * The height of the scene.
   */
  protected double height;
  protected HelpScene help;

  /**
   * Constructor to create a SceneAbstract object.
   *
   * @param root      the root pane of the scene
   * @param appWindow the application window of the scene
   */

  public SceneAbstract(Pane root, AppWindow appWindow, double width, double height) {
    super(root, appWindow.getWidth(), appWindow.getHeight(), Color.BLACK);
    this.root = root;
    this.width = width;
    this.height = height;
    this.appWindow = appWindow;
  }

  public SceneAbstract(Pane root, AppWindow appWindow, double width, double height, boolean depthBuffer) {
    super(root, appWindow.getWidth(), appWindow.getHeight(), depthBuffer, SceneAntialiasing.BALANCED);
    this.root = root;
    this.width = width;
    this.height = height;
    this.appWindow = appWindow;
  }

  /**
   * Abstract method for initialization.
   */
  public abstract void initialise();

  /**
   * Generic build method to create the basic requirements for a JavaFX Scene
   * This is used to define a generic structure used by all the children
   */
  public void build() {
    try {
      getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/stylesheet.css")).toExternalForm());

    } catch (Exception e) {
      logger.error(e);
    }
    mainPane = new StackPane();
    mainPane.setMaxWidth(width);
    mainPane.setMaxHeight(height);

    mainPane.setMinWidth(width);
    mainPane.setMinHeight(height);
    mainPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
    mainPane.setPickOnBounds(false);
    root.setPickOnBounds(false);
    root.setBackground(new Background(new BackgroundFill(Theme.unfocusedBG, null, null)));

    root.setMaxWidth(width);
    root.setMaxHeight(height);
    root.setMinWidth(width);
    root.setMinHeight(height);

    //Top menu
    Menu fileMenu = new Menu("File");
    Menu OptionsMenu = new Menu("Options");
    Menu helpMenu = new Menu("Help");

    MenuItem menu8 = new MenuItem("Help menu");
    helpMenu.getItems().add(menu8);

    Menu menu4 = new Menu("Import from XML");

    MenuItem menu9 = new MenuItem("Import Airport & Obstacle");
    MenuItem menu10 = new MenuItem("Import Obstacle");

    Menu menu5 = new Menu("Export to XML");
    MenuItem menu6 = new MenuItem("Export Obstacle...");
    MenuItem menu7 = new MenuItem("Export Airport & Obstacle...");

    fileMenu.getItems().addAll(menu4, menu5);
    menu4.getItems().addAll(menu9, menu10);
    menu5.getItems().addAll(menu6, menu7);

    MenuBar menuBar = new MenuBar();
    menuBar.getMenus().addAll(fileMenu, OptionsMenu, helpMenu);

    VBox topMenu = new VBox(menuBar);
    topMenu.setAlignment(Pos.TOP_CENTER);
    VBox layoutPane = new VBox(topMenu,mainPane);

    root.getChildren().add(layoutPane);

    //
    menu7.setOnAction(e -> exportAirportButtonEvent());

    menu6.setOnAction(e -> exportObstacleButtonEvent());

    menu8.setOnAction(e -> help.toggleHelp(this.getClass().getCanonicalName()));

    menu9.setOnAction(e -> importAirportButtonEvent());

    menu10.setOnAction(e -> importObstacleButtonEvent());
  }

  /**
   * Generic build method to create the basic requirements for a JavaFX Scene
   * This is used to define a generic structure used by all the children
   */
  public void buildmenuless() {
    try {
      getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/stylesheet.css")).toExternalForm());

    } catch (Exception e) {
      logger.error(e);
    }
    mainPane = new StackPane();
    mainPane.setMaxWidth(width);
    mainPane.setMaxHeight(height);

    mainPane.setMinWidth(width);
    mainPane.setMinHeight(height);
    mainPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
    mainPane.setPickOnBounds(false);
    root.setPickOnBounds(false);
    root.setBackground(new Background(new BackgroundFill(Theme.unfocusedBG, null, null)));

    root.setMaxWidth(width);
    root.setMaxHeight(height);
    root.setMinWidth(width);
    root.setMinHeight(height);
    root.getChildren().add(mainPane);
  }

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

  private void importAirportButtonEvent() {
    try {
      File file = Objects.requireNonNull(generateImportFileChooser("airport").showOpenDialog(new Stage()));

      Airport airport = Objects.requireNonNull(FileHandler.importAirport(file));
      // Add Airport to AppWindow
      appWindow.addAirport(airport);
    } catch (NullPointerException e) {
      logger.warn(e.getMessage());
    }

  }

  private void importObstacleButtonEvent() {
    try {
      File file = Objects.requireNonNull(generateImportFileChooser("obstacle").showOpenDialog(new Stage()));

      Obstacle obstacle = Objects.requireNonNull(FileHandler.importObstacle(file));
      // TODO: Put data in right place
    } catch (NullPointerException e) {
      logger.warn(e.getMessage());
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
