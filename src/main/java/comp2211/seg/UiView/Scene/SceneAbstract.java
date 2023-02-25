package comp2211.seg.UiView.Scene;

import comp2211.seg.UiView.Stage.Pane;
import comp2211.seg.UiView.Stage.Window;
import javafx.scene.Parent;
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

  public SceneAbstract(Pane root) {
    super(root, root.getParentWidth(), root.getParentHeight(),Color.BLACK);
    this.root = root;
  }
  public SceneAbstract(Parent parent, double width,double height) {
    super(parent, width, height,Color.BLACK);
  }

  public abstract void initialise();
  public void build() {
    getStylesheets().add(Objects.requireNonNull(getClass().getResource("/stylesheet.css")).toExternalForm());
    mainPane = new StackPane();
    mainPane.setMaxWidth(getWidth());
    mainPane.setMaxHeight(getHeight());
    root.getChildren().add(mainPane);


  }

}
