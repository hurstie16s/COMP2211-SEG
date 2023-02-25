package comp2211.seg.UiView.Scene;

import comp2211.seg.UiView.Stage.Window;
import javafx.scene.layout.BorderPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RunwayScene extends SceneAbstract{
  private static final Logger logger = LogManager.getLogger(RunwayScene.class);
  public RunwayScene(Window window) {
    super(window);
  }

  @Override
  public void initialise() {

  }

  public void build() {

    super.build();
    logger.info("building");
  }
}
