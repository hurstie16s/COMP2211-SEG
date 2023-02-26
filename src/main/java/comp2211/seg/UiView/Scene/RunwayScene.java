package comp2211.seg.UiView.Scene;

import comp2211.seg.UiView.Stage.AppWindow;
import comp2211.seg.UiView.Stage.Pane;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RunwayScene extends SceneAbstract {
  private static final Logger logger = LogManager.getLogger(RunwayScene.class);
  protected Group group;
  protected AppWindow appWindow;
  protected PerspectiveCamera camera;
  private Boolean view = false;

  private double x;
  private double y;
  private double anglex = 0;
  private double angley = 0;
  protected double runwaywidth = 1200;
  protected double runwayheight = 100;
  private final DoubleProperty angleXProperty = new SimpleDoubleProperty();
  private final DoubleProperty angleYProperty = new SimpleDoubleProperty();
  private final DoubleProperty angleZProperty = new SimpleDoubleProperty();


  public RunwayScene(Pane root, AppWindow appWindow) {
    super(root, appWindow);

    this.group = new Group();
    this.appWindow = appWindow;
    width = root.getParentWidth()-20;
    height = root.getParentHeight()-20;
  }

  public void initControls(){
    setOnKeyPressed((keyEvent -> {
      switch (keyEvent.getCode()){
        case ESCAPE:
          appWindow.startHomeScene();
          break;
        case W:
          group.translateYProperty().set(group.getTranslateY()+10);
          break;
        case A:
          group.translateXProperty().set(group.getTranslateX()+10);
          break;
        case S:
          group.translateYProperty().set(group.getTranslateY()-10);
          break;
        case D:
          group.translateXProperty().set(group.getTranslateX()-10);
          break;
        case T:
          toggleView();
          break;
      }
    }));
    setOnMousePressed(event -> {
      x = event.getSceneX();
      y = event.getSceneY();
      anglex = angleXProperty.get();
      angley = angleZProperty.get();
    });
    setOnMouseDragged(event ->{
      angleXProperty.set(anglex - y + event.getSceneY());
      angleZProperty.set(angley + x - event.getSceneX());
    });
    setOnScroll(event -> {
      camera.translateZProperty().set(camera.getTranslateZ()+event.getDeltaY());

    });
  }
  public void initBaseControls(){
    setOnKeyPressed((keyEvent -> {
      switch (keyEvent.getCode()){
        case ESCAPE:
          appWindow.startHomeScene();
          break;
        case T:
          toggleView();
          break;
      }
    }));

  }
  @Override
  public void initialise() {
    initBaseControls();
  }
  public void toggleView(){

    view = !view;
    if (view){
      angleXProperty.set(0);
      angleZProperty.set(-90);
    }
    else{
      angleXProperty.set(-90);
      angleZProperty.set(-90);

    }
  }

  /**
   * @param x distance from the start of the runway
   * @param y distance from the centre of the runway (to the left side)
   * @param w width of the object (from side to side) - in the y axis
   * @param l length of the object (from start to end) - in the x axis
   * @param d height of the object
   * @param color colour of the bounding box
   */
  public void addObject(double x,double y,double w,double l,double d,Color color){

    double scaleFactor = width/runwaywidth;

    PhongMaterial material = new PhongMaterial();
    material.setDiffuseColor(color);
    //import these from runway somehow
    Box box = new Box(w*scaleFactor,l*scaleFactor,d*scaleFactor);
    box.translateYProperty().set((width/2)-(l*scaleFactor/2)-(x*scaleFactor));
    box.translateXProperty().set(-(y*scaleFactor));
    box.translateZProperty().set(1+(d*scaleFactor/2));
    box.setMaterial(material);
    group.getChildren().add(box);


  }
  public Box makeRunway() throws FileNotFoundException {
    PhongMaterial material = new PhongMaterial();
    material.setDiffuseMap(new Image(new FileInputStream("src/main/resources/images/runway.jpg")));
    //import these from runway somehow
    Box box = new Box(width*runwayheight/runwaywidth,width,1);
    box.setMaterial(material);
    return box;
  }

  public void build() {
    try {

      camera = new PerspectiveCamera();
      angleXProperty.set(-90);
      angleYProperty.set(-180);
      angleZProperty.set(-90);
      setCamera(camera);
      Rotate xRotate;
      Rotate yRotate;
      Rotate zRotate;
      group.getTransforms().addAll(
              xRotate = new Rotate(0,Rotate.X_AXIS),
              yRotate = new Rotate(0,Rotate.Y_AXIS),
              zRotate = new Rotate(0,Rotate.Z_AXIS)
      );
      xRotate.angleProperty().bind(angleXProperty);
      yRotate.angleProperty().bind(angleYProperty);
      zRotate.angleProperty().bind(angleZProperty);
      root.getChildren().add(group);
      root.setMaxWidth(width);
      root.setMaxHeight(height);
      root.setMinWidth(width);
      root.setMinHeight(height);
      root.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
      group.translateXProperty().set(width/2-(width*runwayheight/runwaywidth)/2+10);
      group.translateYProperty().set(height/2-width/2+10);
      group.getChildren().add(makeRunway());
      addObject(0,0,100,150,50,Color.AQUA);

    } catch (FileNotFoundException e) {
      logger.error(e);
    }
    logger.info("building");
  }

}
