package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.Pane;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

/** Handles common functionality between all scenes. */
public abstract class SceneAbstract extends Scene{

  private static final Logger logger = LogManager.getLogger(SceneAbstract.class);
  protected Pane root;

  protected StackPane mainPane;
  protected double width;
  protected double height;

  public SceneAbstract(Pane root, AppWindow appWindow) {
    super(root, root.getParentWidth(), root.getParentHeight(),Color.BLACK);
    this.root = root;
    this.width = root.getParentWidth();
    this.height = root.getParentHeight();
  }


  public abstract void initialise();

  /**
   * Generic build method to create the basic requirements for a JavaFX Scene
   * This is used to define a generic structure used by all the children
   */
  public void build() {
    logger.info("building1234");
    getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/stylesheet.css")).toExternalForm());
    mainPane = new StackPane();
    mainPane.setMaxWidth(width);
    mainPane.setMaxHeight(height);

    mainPane.setMinWidth(width);
    mainPane.setMinHeight(height);
    mainPane.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
    root.getChildren().add(mainPane);

    root.setMaxWidth(width);
    root.setMaxHeight(height);
    root.setMinWidth(width);
    root.setMinHeight(height);
  }

}
