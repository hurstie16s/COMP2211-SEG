package comp2211.seg.UiView.Scene;

import comp2211.seg.App;
import comp2211.seg.UiView.Stage.Pane;
import comp2211.seg.UiView.Stage.Window;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HomeScene extends SceneAbstract{
  private static final Logger logger = LogManager.getLogger(HomeScene.class);

  private static BorderPane borderPane;
  public HomeScene(Window window) {
    super(window);
    logger.info("creating");
  }

  @Override
  public void initialise() {
    scene.setOnKeyPressed((keyEvent -> {
      if(keyEvent.getCode().equals(KeyCode.ESCAPE)) {
        App.getInstance().shutdown();
      }
    }));
  }

  @Override
  public void build() {
    logger.info("building");
    root = new Pane(window.getWidth(), window.getHeight());

    var stackPane = new StackPane();
    stackPane.setMaxWidth(window.getWidth());
    stackPane.setMaxHeight(window.getHeight());
    stackPane.getStyleClass().add("home-background");
    root.getChildren().add(stackPane);

    borderPane = new BorderPane();
    stackPane.getChildren().add(borderPane);
  }
}
