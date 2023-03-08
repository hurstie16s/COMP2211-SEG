package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Interfaces.GlobalVariables;
import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.HandlerPane;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
  protected StackPane mainPane;

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
  public SceneAbstract(HandlerPane root, AppWindow appWindow) {
    super(root, root.getParentWidth(), root.getParentHeight(),Color.BLACK);
    this.root = root;
    this.width = root.getParentWidth();
    this.height = root.getParentHeight();
    this.appWindow = appWindow;
  }

  public SceneAbstract(Pane root, AppWindow appWindow, double width,double height) {
    super(root, appWindow.getWidth(), appWindow.getHeight(),Color.BLACK);
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
    root.setBackground(new Background(new BackgroundFill(GlobalVariables.bg,null,null)));
    root.getChildren().add(mainPane);

    root.setMaxWidth(width);
    root.setMaxHeight(height);
    root.setMinWidth(width);
    root.setMinHeight(height);

  }
  public void makeHelp(){

    help = new HelpScene(new VBox(),appWindow);
    VBox alignPane = new VBox();
    alignPane.setMaxWidth(root.getMaxWidth());
    alignPane.setMinWidth(root.getMaxWidth());
    alignPane.getChildren().add(help.getRoot());
    alignPane.setAlignment(Pos.TOP_RIGHT);

    root.getChildren().add(alignPane);
    help.getRoot().setPickOnBounds(false);
    alignPane.setPickOnBounds(false);
    root.setPickOnBounds(false);
    help.toggleHelp(this.getClass().getName());
  }

}
