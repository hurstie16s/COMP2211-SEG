package comp2211.seg.UiView.Scene;

import comp2211.seg.UiView.Stage.Pane;
import comp2211.seg.UiView.Stage.Window;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

/** Handles common functionality between all scenes. */
public abstract class SceneAbstract {

  private static final Logger logger = LogManager.getLogger(SceneAbstract.class);
  protected final Window window;
  protected Pane root;
  protected Scene scene;

  protected StackPane mainPane;

  public SceneAbstract(Window window) {
    this.window = window;
  }
  public abstract void initialise();
  public void build() {
    root = new Pane(window.getWidth(), window.getHeight());

    mainPane = new StackPane();
    mainPane.setMaxWidth(window.getWidth());
    mainPane.setMaxHeight(window.getHeight());
    root.getChildren().add(mainPane);

  }

  public Scene setScene() {
    var previous = window.getScene();
    Scene scene = new Scene(root, previous.getWidth(), previous.getHeight(), Color.BLACK);
    scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/stylesheet.css")).toExternalForm());
    this.scene = scene;
    return scene;
  }

  public Scene getScene() {
    return this.scene;
  }
}
