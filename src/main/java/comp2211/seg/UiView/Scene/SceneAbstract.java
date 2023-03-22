package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Interfaces.GlobalVariables;
import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.HandlerPane;
import comp2211.seg.Controller.Stage.Theme;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

/**
 * This abstract class serves as a template for all JavaFX scenes.
 * It extends the JavaFX Scene class
 * and contains common functionality shared by all scenes.
 */
public abstract class SceneAbstract extends Scene{

  private static final Logger logger = LogManager.getLogger(SceneAbstract.class);
  protected final AppWindow appWindow;

  /** The root node of the scene.*/
  public Pane root;

  /**The main pane of the scene.*/
  public StackPane mainPane;

  /** The width of the scene.*/
  protected double width;

  /** The height of the scene.*/
  protected double height;
  protected HelpScene help;

  /**
   * Constructor to create a SceneAbstract object.
   * @param root the root pane of the scene
   * @param appWindow the application window of the scene
   */

  public SceneAbstract(Pane root, AppWindow appWindow, double width,double height) {
    super(root, appWindow.getWidth(), appWindow.getHeight(),Color.BLACK);
    this.root = root;
    this.width = width;
    this.height = height;
    this.appWindow = appWindow;
  }
public SceneAbstract(Pane root, AppWindow appWindow, double width,double height, boolean depthBuffer) {
    super(root, appWindow.getWidth(), appWindow.getHeight(),depthBuffer, SceneAntialiasing.BALANCED);
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

    }catch (Exception e){
      logger.error(e);
    }
    mainPane = new StackPane();
    mainPane.setMaxWidth(width);
    mainPane.setMaxHeight(height);

    mainPane.setMinWidth(width);
    mainPane.setMinHeight(height);
    mainPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
    mainPane.setPickOnBounds(false);
    root.setPickOnBounds(false);
    root.setBackground(new Background(new BackgroundFill(Theme.unfocusedBG,null,null)));
    root.getChildren().add(mainPane);

    root.setMaxWidth(width);
    root.setMaxHeight(height);
    root.setMinWidth(width);
    root.setMinHeight(height);

    Menu fileMenu = new Menu("File");
    Menu OptionsMenu = new Menu("Options");
    Menu helpMenu = new Menu("Help");

    MenuItem menu4 = new MenuItem("Import from XML");
    Menu menu5 = new Menu("Export to XML");

    MenuItem menu6 = new MenuItem("Export Obstacle");
    MenuItem menu7 = new MenuItem("Export Airport & Obstacle");

    fileMenu.getItems().addAll(menu4, menu5);
    menu5.getItems().addAll(menu6, menu7);

    MenuBar menuBar = new MenuBar();
    menuBar.getMenus().addAll(fileMenu, OptionsMenu, helpMenu);

    VBox topMenu = new VBox(menuBar);
    topMenu.setAlignment(Pos.TOP_CENTER);
    mainPane.getChildren().add(topMenu);

    Button exportAirObsButton = new Button("Export Airport & Obstacle");
    Button importAirObsButton = new Button("Import Airport & Obstacle");

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
      alignPane.maxWidthProperty().bind(root.widthProperty());
      alignPane.minWidthProperty().bind(root.widthProperty());
    } else{

      help = new HelpScene(new VBox(), appWindow);
      root.getChildren().add(help.getRoot());
      help.toggleHelp(this.getClass().getName());

    }
  }

}
