package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.HandlerPane;
import comp2211.seg.ProcessDataModel.Obstacle;
import comp2211.seg.UiView.Scene.RunwayComponents.ClearedGradedArea;
import comp2211.seg.UiView.Scene.RunwayComponents.Slope;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.AmbientLight;
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

import java.io.FileNotFoundException;
import java.util.Objects;

/**
 * RunwayScene class represents the runway scene of the airport
 * runway operations system.
 * It extends the abstract SceneAbstract class.
 */
public class RunwayScene extends SceneAbstract {
  private SimpleDoubleProperty runwayOffset = new SimpleDoubleProperty(5);
  private static final Logger logger = LogManager.getLogger(RunwayScene.class);

  /**
   * The group object that holds the 3D models for the runway scene.
   */
  protected Group group;

  /**
   * The main application window for the interface.
   */
  protected AppWindow appWindow;

  /**
   * The camera used to view the runway scene.
   */
  protected PerspectiveCamera camera;

  /**
   * A boolean flag indicating whether the scene is in "view" mode.
   */
  private Boolean view = false;

  /**
   * The x-coordinate of the mouse when it is clicked.
   */
  private double x;

  /**
   * The y-coordinate of the mouse when it is clicked.
   */
  private double y;

  /**
   * The x angle of rotation of the runway scene.
   */
  private double anglex = 0;

  /**
   * The y angle of rotation of the runway scene.
   */
  private double angley = 0;

  protected SimpleDoubleProperty scaleFactor = new SimpleDoubleProperty(0.5);
  protected SimpleDoubleProperty scaleFactorHeight = new SimpleDoubleProperty(2);
  protected SimpleDoubleProperty scaleFactorDepth = new SimpleDoubleProperty(4);


  /**
   * A DoubleProperty object representing the x angle of rotation of the runway scene.
   */
  private final DoubleProperty angleXProperty = new SimpleDoubleProperty();

  /**
   * A DoubleProperty object representing the y angle of rotation of the runway scene.
   */
  private final DoubleProperty angleYProperty = new SimpleDoubleProperty();

  /**
   * A DoubleProperty object representing the z angle of rotation of the runway scene.
   */
  private final DoubleProperty angleZProperty = new SimpleDoubleProperty();

  private SimpleDoubleProperty borderx = new SimpleDoubleProperty(10);










  /**
   * Constructs a new RunwayScene object.
   *
   * @param root      the root handler pane for the scene
   * @param appWindow the main application window
   */
  public RunwayScene(HandlerPane root, AppWindow appWindow) {
    super(root, appWindow);

    this.group = new Group();
    this.appWindow = appWindow;
    width = root.getParentWidth();
    height = root.getParentHeight();
  }

  /**
   * Initializes the mouse and keyboard event listeners for
   * the runway scene.
   */
  public void initControls(){
    setOnKeyPressed((keyEvent -> {
      switch (keyEvent.getCode()){
        case ESCAPE:
          appWindow.startMainScene();
          break;
        case W:
          group.translateYProperty().set(group.getTranslateY()-10);
          break;
        case A:
          group.translateXProperty().set(group.getTranslateX()-10);
          break;
        case S:
          group.translateYProperty().set(group.getTranslateY()+10);
          break;
        case D:
          group.translateXProperty().set(group.getTranslateX()+10);
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
      angleXProperty.set(Math.min(Math.max(anglex + y - event.getSceneY(),90),180));
      angleZProperty.set(angley - x + event.getSceneX());
    });
    setOnScroll(event -> {
      camera.translateZProperty().set(camera.getTranslateZ()+event.getDeltaY());

    });
  }
  /**
   * Initializes the base keyboard event listeners for the runway scene.
   */
  public void initBaseControls(){
    setOnKeyPressed((keyEvent -> {
      switch (keyEvent.getCode()){
        case ESCAPE:
          appWindow.startMainScene();
          break;
        case T:
          toggleView();
          break;
      }
    }));
  }

  /**
   * Initializes the scene by setting up the base keyboard event listeners.
   */
  @Override
  public void initialise() {
    initControls();
  }

  /**
   * Toggles the "view" mode of the runway scene, which changes the camera angle to view the scene from above or from the side.
   */
  public void toggleView(){
    view = !view;
    if (view){
      angleXProperty.set(90);
    }
    else{
      angleXProperty.set(0);

    }
  }

  /**
   * Adds a cuboid object to the runway scene.
   *
   * @param x     the distance from the start of the runway
   * @param y     the distance from the centre of the runway
   * @param z     the distance above the runway
   * @param w     the width of the object (from side to side) - in the y axis
   * @param l     the length of the object (from start to end) - in the x axis
   * @param d     the height of the object
   * @param color the colour of the bounding box
   */
  public void addCuboid(DoubleBinding x, DoubleBinding y, DoubleBinding z, DoubleBinding w, DoubleBinding l, DoubleBinding d, Color color){


    PhongMaterial material = new PhongMaterial();
    material.setDiffuseColor(color);
    //import these from runway somehow
    Box box = new Box(0,0,0);
    box.translateXProperty().bind(x.multiply(scaleFactor));
    box.translateYProperty().bind(y.multiply(scaleFactorHeight));
    box.translateZProperty().bind(z.subtract(d.divide(2)).multiply(scaleFactorDepth));
    box.widthProperty().bind(w.multiply(scaleFactor));
    box.heightProperty().bind(l.multiply(scaleFactorHeight));
    box.depthProperty().bind(d.multiply(scaleFactorDepth));
    box.setMaterial(material);
    group.getChildren().add(box);
  }




  /**
   * Creates a 3D box representing the runway, textured with
   * an image of a runway.
   * @throws FileNotFoundException If the image of the runway is not found.
   */
  public void makeRunway() {

    PhongMaterial material = new PhongMaterial();
    material.setDiffuseColor(Color.GREY);
    //import these from runway somehow
    Box box = new Box(0,0,runwayOffset.get());
    box.widthProperty().bind(appWindow.runway.runwayLengthProperty().multiply(scaleFactor));
    box.heightProperty().bind(appWindow.runway.runwayWidthProperty().multiply(scaleFactorHeight));
    box.depthProperty().bind(runwayOffset.multiply(scaleFactorDepth));
    box.translateZProperty().bind(runwayOffset.divide(-2).multiply(scaleFactorDepth));
    box.setMaterial(material);

    group.getChildren().add(box);
    /*
    try {
      material = new PhongMaterial();
      material.setDiffuseMap(new Image(Objects.requireNonNull(getClass().getResource("/images/runway.png")).toExternalForm()));
      //import these from runway somehow
      Box overlay = new Box(0,0,0);
      overlay.widthProperty().bind(appWindow.runway.runwayLengthProperty().multiply(scaleFactor));
      overlay.heightProperty().bind(appWindow.runway.runwayWidthProperty().multiply(scaleFactorHeight));
      overlay.setMaterial(material);
      overlay.translateZProperty().set(1);
      group.getChildren().add(overlay);

    }catch (Exception e){
      logger.error(e);
    }
     */
  }

  public void configureCamera(){
    AmbientLight light = new AmbientLight();
    light.setLightOn(true);
    group.getChildren().add(light);
    camera = new PerspectiveCamera();
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
  }
  public void makeBackground(){
    Box background = new Box(width,height,0);
    PhongMaterial material = new PhongMaterial();
    //material.setDiffuseMap(new Image(Objects.requireNonNull(getClass().getResource("/images/grass.jpg")).toExternalForm()));
    material.setDiffuseColor(Color.GREEN);
    background.setMaterial(material);
    group.getChildren().add(background);
  }

  /**
   * Builds the scene, adding the 3D objects to the scene and
   * initializing camera and rotation controls.
   */
  @Override
  public void build() {
    makeBackground();
    configureCamera();
    scaleFactor.set(0.3);
    root.getChildren().add(group);
    root.setMaxWidth(width);
    root.setMaxHeight(height);
    root.setMinWidth(width);
    root.setMinHeight(height);
    root.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
    //root.getStyleClass().add("runway-background");

    makeCGA();
    addTopView();
    makeRunway();
    scaleFactor.bind(widthProperty().subtract(borderx).divide(appWindow.runway.runwayLengthProperty().add(appWindow.runway.clearwayLeftWidthProperty()).add(appWindow.runway.clearwayRightWidthProperty())));
    scaleFactorHeight.bind(heightProperty().subtract(borderx).divide(420));

    Obstacle obstacle = new Obstacle("Test",10,300);
    obstacle.widthProperty().set(30);
    obstacle.lengthProperty().set(40);
    renderObstacle(obstacle);


    logger.info("building");
  }

  public void renderObstacle(Obstacle obstacle){
    addCuboid(
            obstacle.distFromThresholdProperty().multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            obstacle.heightProperty().multiply(1),
            obstacle.widthProperty().multiply(1),
            obstacle.lengthProperty().multiply(1),
            obstacle.heightProperty().multiply(1),
            Color.DARKRED

            );

    group.getChildren().add(new Slope(
            obstacle,
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.runwayWidthProperty().multiply(1),
            obstacle.heightProperty().multiply(1),
            Color.DARKCYAN,
            appWindow.runway.directionProperty(),
            scaleFactor,
            scaleFactorHeight,
            scaleFactorDepth
    ));

  }

  public void addTopView(){
    //Clearway Right
    addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(0.5).add( appWindow.runway.clearwayRightWidthProperty().divide(2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.clearwayRightWidthProperty().multiply(1),
            appWindow.runway.clearwayRightHeightProperty().multiply(1),
            new SimpleDoubleProperty(5).multiply(1),
            Color.TAN);

    //Clearway Left
    addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(-0.5).add( appWindow.runway.clearwayLeftWidthProperty().divide(-2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.clearwayLeftWidthProperty().multiply(1),
            appWindow.runway.clearwayLeftHeightProperty().multiply(1),
            new SimpleDoubleProperty(5).multiply(1),
            Color.TAN);


    //Stopway Left
    addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(-0.5).add(appWindow.runway.stopwayLeftProperty().divide(-2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.stopwayLeftProperty().multiply(1),
            appWindow.runway.runwayWidthProperty().multiply(1),
            new SimpleDoubleProperty(10).multiply(1),
            Color.DARKGREY);

    //Stopway Right
    addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(0.5).add(appWindow.runway.stopwayRightProperty().divide(2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.stopwayRightProperty().multiply(1),
            appWindow.runway.runwayWidthProperty().multiply(1),
            new SimpleDoubleProperty(10).multiply(1),
            Color.DARKGREY);


    //RESA Right
    addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(0.5).add( appWindow.runway.stopwayRightProperty()).add(appWindow.runway.stripEndRightProperty()).add(appWindow.runway.RESARightWidthProperty().divide(2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.RESARightWidthProperty().multiply(1),
            appWindow.runway.RESARightHeightProperty().multiply(1),
            new SimpleDoubleProperty(10).multiply(1),
            Color.LIGHTGRAY);

    //RESA Left
    addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(-0.5).subtract( appWindow.runway.stopwayLeftProperty()).subtract(appWindow.runway.stripEndLeftProperty()).subtract(appWindow.runway.RESALeftWidthProperty().divide(2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.RESALeftWidthProperty().multiply(1),
            appWindow.runway.RESALeftHeightProperty().multiply(1),
            new SimpleDoubleProperty(10).multiply(1),
            Color.LIGHTGRAY);




  }
  public void makeCGA(){

    //Cleared and graded area
    ClearedGradedArea cga = new ClearedGradedArea(group);
    cga.leftProperty().bind(appWindow.runway.runwayLengthProperty().multiply(-0.5).subtract( appWindow.runway.stopwayLeftProperty()).subtract(appWindow.runway.stripEndLeftProperty()).multiply(scaleFactor));
    cga.leftStartProperty().bind(appWindow.runway.runwayLengthProperty().multiply(-0.5).multiply(scaleFactor));
    cga.leftEndProperty().bind(appWindow.runway.runwayLengthProperty().multiply(-0.5).add(150).multiply(scaleFactor));
    cga.rightProperty().bind(appWindow.runway.runwayLengthProperty().multiply(0.5).add( appWindow.runway.stopwayLeftProperty()).add(appWindow.runway.stripEndLeftProperty()).multiply(scaleFactor));
    cga.rightStartProperty().bind(appWindow.runway.runwayLengthProperty().multiply(0.5).multiply(scaleFactor));
    cga.rightEndProperty().bind(appWindow.runway.runwayLengthProperty().multiply(0.5).subtract(150).multiply(scaleFactor));
    cga.innerHeightProperty().bind(scaleFactorHeight.multiply(-75));
    cga.outerHeightProperty().bind(scaleFactorHeight.multiply(-105));

    group.getChildren().add(cga);
  }
}
