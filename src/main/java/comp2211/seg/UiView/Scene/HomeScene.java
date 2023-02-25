package comp2211.seg.UiView.Scene;

import comp2211.seg.App;
import comp2211.seg.UiView.Stage.Pane;
import comp2211.seg.UiView.Stage.Window;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HomeScene extends SceneAbstract{
  private static final Logger logger = LogManager.getLogger(HomeScene.class);

  private static BorderPane borderPane;

  public HomeScene(Pane root,Window window) {
    super(root, window);
  }

  @Override
  public void initialise() {
    setOnKeyPressed((keyEvent -> {
      if(keyEvent.getCode().equals(KeyCode.ESCAPE)) {
        App.getInstance().shutdown();
      }
    }));
  }
  public void build() {
    super.build();
    logger.info("building");
    borderPane = new BorderPane();
    mainPane.getChildren().add(borderPane);
  }
}
