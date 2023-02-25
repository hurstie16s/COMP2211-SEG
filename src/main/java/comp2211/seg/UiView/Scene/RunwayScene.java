package comp2211.seg.UiView.Scene;

import comp2211.seg.App;
import comp2211.seg.UiView.Stage.Pane;
import comp2211.seg.UiView.Stage.Window;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RunwayScene extends SceneAbstract{
  private static final Logger logger = LogManager.getLogger(RunwayScene.class);
  protected Group root;
  protected Window window;
  protected Camera camera;

  private double x;
  private double y;
  private double anglex = 0;
  private double angley = 0;
  private final DoubleProperty angleXProperty = new SimpleDoubleProperty();
  private final DoubleProperty angleYProperty = new SimpleDoubleProperty();


  public RunwayScene(Group root, Window window) {
    super(root, window);
    this.root = root;
    this.window = window;
  }


  @Override
  public void initialise() {
    setOnKeyPressed((keyEvent -> {
      switch (keyEvent.getCode()){
        case ESCAPE:
          window.startHomeScene();
          break;
        case W:
          camera.translateYProperty().set(camera.getTranslateY()+10);
          break;
        case A:
          camera.translateXProperty().set(camera.getTranslateX()+10);
          break;
        case S:
          camera.translateYProperty().set(camera.getTranslateY()-10);
          break;
        case D:
          camera.translateXProperty().set(camera.getTranslateX()-10);
          break;
      }
    }));
    setOnMousePressed(event -> {
      x = event.getSceneX();
      y = event.getSceneY();
      anglex = angleXProperty.get();
      angley = angleYProperty.get();
    });
    setOnMouseDragged(event ->{
      angleXProperty.set(anglex - (y-event.getSceneY()));
      angleYProperty.set(angley - (x-event.getSceneX()));
    });
  }

  public Box makeRunway() throws FileNotFoundException {
    PhongMaterial material = new PhongMaterial();
    material.setDiffuseMap(new Image(new FileInputStream("src/main/resources/images/runway.jpg")));
    //import these from runway somehow
    double width = 30;
    double height = 100;
    Box box = new Box(width,height,1);
    box.setMaterial(material);
    return box;
  }

  public void build() {
    try {

      camera = new PerspectiveCamera();
      camera.translateXProperty().set(-window.getWidth()/2);
      camera.translateYProperty().set(-window.getHeight()/2);
      setCamera(camera);
      Rotate xRotate;
      Rotate yRotate;
      root.getTransforms().addAll(
              xRotate = new Rotate(0,Rotate.X_AXIS),
              yRotate = new Rotate(0,Rotate.Y_AXIS)
      );
      xRotate.angleProperty().bindBidirectional(angleXProperty);
      yRotate.angleProperty().bindBidirectional(angleYProperty);

      root.getChildren().add(makeRunway());
    } catch (FileNotFoundException e) {
      logger.error(e);
    }
    logger.info("building");
  }

}
