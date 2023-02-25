package comp2211.seg.UiView.Scene;

import comp2211.seg.UiView.Stage.Pane;
import comp2211.seg.UiView.Stage.Window;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RunwayScene extends SceneAbstract{
  private static final Logger logger = LogManager.getLogger(RunwayScene.class);
  protected Group root;
  private double width;
  private double height;

  public RunwayScene(Group root, double width, double height) {
    super(root,width,height);
    this.root = root;
    this.width = width;
    this.height = height;
  }


  @Override
  public void initialise() {

  }

  public Box makeRunway() throws FileNotFoundException {
    PhongMaterial material = new PhongMaterial();
    material.setDiffuseMap(new Image(new FileInputStream("src/main/resources/images/runway.jpg")));
    //import these from runway somehow
    double width = 30;
    double height = 100;
    Box box = new Box(width,height,1);
    box.setMaterial(material);
    box.translateXProperty().set(this.width/2);
    box.translateYProperty().set(this.height/2);
    return box;
  }

  public void build() {
    try {

      ImageView imageView = new ImageView();
      imageView.setImage(new Image(new FileInputStream("src/main/resources/images/heathrowRunway.jpg")));
      root.getChildren().add(makeRunway());
    } catch (FileNotFoundException e) {
      logger.error(e);
    }
    logger.info("building");
  }

}
