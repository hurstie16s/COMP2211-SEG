package comp2211.seg.UiView.Scene;

import comp2211.seg.UiView.Stage.AppWindow;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RunwayScene extends SceneAbstract{
  private static final Logger logger = LogManager.getLogger(RunwayScene.class);
  protected Group root;
  protected AppWindow appWindow;
  protected Camera camera;
  private Boolean view = false;

  private double x;
  private double y;
  private double anglex = 0;
  private double angley = 0;
  private final DoubleProperty angleXProperty = new SimpleDoubleProperty();
  private final DoubleProperty angleYProperty = new SimpleDoubleProperty();


  public RunwayScene(Group root, AppWindow appWindow) {
    super(root, appWindow);
    this.root = root;
    this.appWindow = appWindow;
  }


  @Override
  public void initialise() {
    /**
    setOnKeyPressed((keyEvent -> {
      switch (keyEvent.getCode()){
        case ESCAPE:
          appWindow.startHomeScene();
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
      angleXProperty.set(anglex - y + event.getSceneY());
      angleYProperty.set(angley + x - event.getSceneX());
    });
    setOnScroll(event -> {
      camera.translateZProperty().set(camera.getTranslateZ()+event.getDeltaY());

    });
     */
    setOnKeyPressed((keyEvent -> {
      switch (keyEvent.getCode()){
        case ESCAPE:
          appWindow.startHomeScene();
          break;
        case T:
          view = !view;
          if (view){
            angleXProperty.set(0);
            angleYProperty.set(90);
          }
          else{
            angleXProperty.set(90);
            angleYProperty.set(90);

          }
      }
    }));
  }

  public Box makeRunway() throws FileNotFoundException {
    PhongMaterial material = new PhongMaterial();
    material.setDiffuseMap(new Image(new FileInputStream("src/main/resources/images/runway.jpg")));
    //import these from runway somehow
    double width = 100;
    double height = 1100;
    Box box = new Box(width,height,1);
    box.setMaterial(material);
    return box;
  }

  public void build() {
    try {

      camera = new PerspectiveCamera();
      camera.translateXProperty().set(-appWindow.getWidth()/2);
      camera.translateYProperty().set(-appWindow.getHeight()/2);
      angleYProperty.set(90);
      setCamera(camera);
      Rotate xRotate;
      Rotate yRotate;
      root.getTransforms().addAll(
              xRotate = new Rotate(0,Rotate.X_AXIS),
              yRotate = new Rotate(0,Rotate.Z_AXIS)
      );
      xRotate.angleProperty().bind(angleXProperty);
      yRotate.angleProperty().bind(angleYProperty);

      root.getChildren().add(makeRunway());
    } catch (FileNotFoundException e) {
      logger.error(e);
    }
    logger.info("building");
  }

}
