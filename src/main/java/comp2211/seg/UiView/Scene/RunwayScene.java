package comp2211.seg.UiView.Scene;

import comp2211.seg.UiView.Stage.Pane;
import comp2211.seg.UiView.Stage.Window;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RunwayScene extends SceneAbstract{
  private static final Logger logger = LogManager.getLogger(RunwayScene.class);

  public RunwayScene(Pane root) {
    super(root);
  }


  @Override
  public void initialise() {

  }

  public void build() {
    super.build();
    try {

      ImageView imageView = new ImageView();
      imageView.setImage(new Image(new FileInputStream("src/main/resources/images/heathrowRunway.jpg")));
      root.getChildren().add(imageView);
    } catch (FileNotFoundException e) {
      logger.error(e);
    }
    logger.info("building");
  }

}
