package comp2211.seg.UiView.Scene;

import comp2211.seg.UiView.Stage.Pane;
import comp2211.seg.UiView.Stage.Window;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.util.Objects;

/** Handles common functionality between all scenes. */
public abstract class SceneAbstract {

  protected final Window window;
  protected Pane root;
  protected Scene scene;

  public SceneAbstract(Window window) {
    this.window = window;
  }
  public abstract void initialise();
  public abstract void build();

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
