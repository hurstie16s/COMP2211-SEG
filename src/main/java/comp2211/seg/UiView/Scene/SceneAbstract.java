package comp2211.seg.UiView.Scene;

import comp2211.seg.UiView.Stage.AppWindow;
import comp2211.seg.UiView.Stage.Pane;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

  public SceneAbstract(Pane root, AppWindow appWindow) {
    super(root, appWindow.getWidth(), appWindow.getHeight(),Color.BLACK);
    this.root = root;
  }
  public SceneAbstract(Parent parent, AppWindow appWindow) {
    super(parent, appWindow.getWidth(), appWindow.getHeight(),Color.BLACK);

  }

  public abstract void initialise();
  public void build() {
    getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/stylesheet.css")).toExternalForm());
    mainPane = new StackPane();
    mainPane.setMaxWidth(getWidth());
    mainPane.setMaxHeight(getHeight());
    root.getChildren().add(mainPane);


  }

}
