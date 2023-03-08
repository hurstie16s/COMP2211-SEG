package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Interfaces.GlobalVars;
import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.HandlerPane;
import comp2211.seg.ProcessDataModel.Obstacle;
import comp2211.seg.UiView.Overlay.RunwayLabel;
import comp2211.seg.UiView.Scene.RunwayComponents.ClearedGradedArea;
import comp2211.seg.UiView.Scene.RunwayComponents.Slope;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * RunwayScene class represents the runway scene of the airport
 * runway operations system.
 * It extends the abstract SceneAbstract class.
 */
public class RunwayScene extends SceneAbstract {
  private final SimpleDoubleProperty runwayOffset = new SimpleDoubleProperty(5);
  private static final Logger logger = LogManager.getLogger(RunwayScene.class);

  /**
   * The group object that holds the 3D models for the runway scene.
   */
  protected Group group;

  /**
   * The main application window for the interface.
   */
  public AppWindow appWindow;

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

  private final SimpleDoubleProperty borderx = new SimpleDoubleProperty(10);

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
      angleXProperty.set(Math.min(Math.max(anglex + y - event.getSceneY(),-90),0));
      angleZProperty.set(angley - x + event.getSceneX());
    });
    setOnScroll(event -> camera.translateZProperty().set(camera.getTranslateZ()+event.getDeltaY()));
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
        case H:
          help.toggleHelp(this.getClass().getCanonicalName());
          break;
      }
    }));
  }

  /**
   * Initializes the scene by setting up the base keyboard event listeners.
   */
  @Override
  public void initialise() {
    initBaseControls();
  }

  /**
   * Toggles the "view" mode of the runway scene, which changes the camera angle to view the scene from above or from the side.
   */
  public void toggleView(){
    view = !view;
    if (view){
      angleXProperty.set(-90);
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
  public Box addCuboid(DoubleBinding x, DoubleBinding y, DoubleBinding z, DoubleBinding w, DoubleBinding l, DoubleBinding d, Color color){

    PhongMaterial material = new PhongMaterial();
    material.setDiffuseColor(color);
    //import these from runway somehow
    Box box = new Box(0,0,0);
    box.translateXProperty().bind(x.multiply(scaleFactor));
    box.translateYProperty().bind(y.multiply(scaleFactorHeight));
    box.translateZProperty().bind(z.subtract(d.divide(2)).multiply(scaleFactorDepth).multiply(-1));
    box.widthProperty().bind(w.multiply(scaleFactor));
    box.heightProperty().bind(l.multiply(scaleFactorHeight));
    box.depthProperty().bind(d.multiply(scaleFactorDepth));
    box.setMaterial(material);
    group.getChildren().add(box);
    return box;
  }

  /**
   * Creates a 3D box representing the runway, textured with
   * an image of a runway.
   */
  public void makeRunway() {
    PhongMaterial material = new PhongMaterial();
    material.setDiffuseColor(Color.GREY);
    //import these from runway somehow
    Box box = new Box(0,0,runwayOffset.get());
    box.widthProperty().bind(appWindow.runway.runwayLengthProperty().multiply(scaleFactor));
    box.heightProperty().bind(appWindow.runway.runwayWidthProperty().multiply(scaleFactorHeight));
    box.depthProperty().bind(runwayOffset.multiply(scaleFactorDepth));
    box.translateZProperty().bind(runwayOffset.divide(2).multiply(scaleFactorDepth));
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

  /**
   * Configures the camera by adding ambient light, creating a perspective camera,
   * and setting up rotations for the group.
   * Binds angle properties to corresponding rotate angles.
   */
  public void configureCamera() {
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
  /**
   * Creates a background box, adds a phong material and sets it to the group.
   */
  public void makeBackground() {
    Box background = new Box(width,height,0);
    PhongMaterial material = new PhongMaterial();
//material.setDiffuseMap(new Image(Objects.requireNonNull(getClass().getResource("/images/grass.jpg")).toExternalForm()));
    material.setDiffuseColor(Color.DARKGREEN);
    background.setMaterial(material);
    group.getChildren().add(background);
  }

  /**
   * Builds the scene, adding the 3D objects to the scene and
   * initializing camera and rotation controls.
   */
  @Override
  public void build() {
    super.build();
    makeBackground();
    configureCamera();
    scaleFactor.set(0.3);
    mainPane.getChildren().add(group);
    root.setMaxWidth(width);
    root.setMaxHeight(height);
    root.setMinWidth(width);
    root.setMinHeight(height);
    mainPane.setBackground(new Background(new BackgroundFill(GlobalVars.bgRunway,null,null)));
    //root.getStyleClass().add("runway-background");

    makeCGA(true);
    addTopView();
    makeRunway();
    makeCGA(false);
    scaleFactor.bind(widthProperty().subtract(borderx).divide(appWindow.runway.runwayLengthProperty().add(appWindow.runway.clearwayWidthProperty()).add(appWindow.runway.clearwayWidthProperty())));
    scaleFactorHeight.bind(heightProperty().subtract(borderx).divide(420));
    logger.info(getClass().toString());

    try {
      renderObstacle(appWindow.runway.getRunwayObstacle());
    } catch (Exception e){
      logger.error(e);
    }


    Pane arrowPane = new Pane();
    root.getChildren().add(arrowPane);
    buildLabels();



    //CentreLine
    addCuboid(
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            widthProperty().divide(scaleFactor).multiply(1),
            new SimpleDoubleProperty(1).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            Color.BLACK);
    logger.info("building");
  }

  /**
   * Renders an obstacle as a cuboid and a slope, and adds them to the 3D scene.
   * @param obstacle The obstacle to render.
   */
  public void renderObstacle(Obstacle obstacle){

    Box obstacleView = addCuboid(
            appWindow.runway.runwayLengthProperty().divide(-2).add(obstacle.distFromThresholdProperty()),
            new SimpleDoubleProperty(0).multiply(1),
            obstacle.heightProperty().multiply(1),
            obstacle.widthProperty().multiply(1),
            obstacle.lengthProperty().multiply(1),
            obstacle.heightProperty().multiply(1),
            Color.DARKRED
            );
    obstacleView.visibleProperty().bind(appWindow.runway.hasRunwayObstacleProperty());

    group.getChildren().add(new Slope(
            appWindow,
            appWindow.runway.runwayLengthProperty().divide(-2),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.runwayWidthProperty().multiply(1),
            obstacle.heightProperty().multiply(1),
            Color.DARKCYAN,
            new SimpleBooleanProperty(true),
            scaleFactor,
            scaleFactorHeight,
            scaleFactorDepth
    ));

    group.getChildren().add(new Slope(
            appWindow,
            appWindow.runway.runwayLengthProperty().divide(-2),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.runwayWidthProperty().multiply(1),
            obstacle.heightProperty().multiply(1),
            Color.DARKCYAN,
            new SimpleBooleanProperty(false),
            scaleFactor,
            scaleFactorHeight,
            scaleFactorDepth
    ));

  }

  /**
   * Adds the top view of the runway to the 3D scene.
   */
  public void addTopView(){

    //Clearway Right
    addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(0.5).add( appWindow.runway.clearwayWidthProperty().divide(2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.clearwayWidthProperty().multiply(1),
            appWindow.runway.clearwayHeightProperty().multiply(1),
            new SimpleDoubleProperty(10).multiply(1),
            Color.DARKGOLDENROD);

    //Clearway Left
    addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(-0.5).add( appWindow.runway.clearwayWidthProperty().divide(-2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.clearwayWidthProperty().multiply(1),
            appWindow.runway.clearwayHeightProperty().multiply(1),
            new SimpleDoubleProperty(10).multiply(1),
            Color.DARKGOLDENROD);


    //Stopway Left
    addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(-0.5).add(appWindow.runway.stopwayProperty().divide(-2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.stopwayProperty().multiply(1),
            appWindow.runway.runwayWidthProperty().multiply(1),
            new SimpleDoubleProperty(5).multiply(1),
            Color.VIOLET);

    //Stopway Right
    addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(0.5).add(appWindow.runway.stopwayProperty().divide(2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.stopwayProperty().multiply(1),
            appWindow.runway.runwayWidthProperty().multiply(1),
            new SimpleDoubleProperty(5).multiply(1),
            Color.VIOLET);


    //RESA Right
    addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(0.5).add( appWindow.runway.stopwayProperty()).add(appWindow.runway.stripEndProperty()).add(appWindow.runway.RESAWidthProperty().divide(2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.RESAWidthProperty().multiply(1),
            appWindow.runway.RESAHeightProperty().multiply(1),
            new SimpleDoubleProperty(5).multiply(1),
            Color.BROWN);

    //RESA Left
    addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(-0.5).subtract( appWindow.runway.stopwayProperty()).subtract(appWindow.runway.stripEndProperty()).subtract(appWindow.runway.RESAWidthProperty().divide(2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.RESAWidthProperty().multiply(1),
            appWindow.runway.RESAHeightProperty().multiply(1),
            new SimpleDoubleProperty(5).multiply(1),
            Color.BROWN);
  }
  /**
   * Creates the Cleared and Graded Area (CGA) and adds it to the 3D group.
   * The CGA is constructed using a {@link ClearedGradedArea} object.
   * The properties of the CGA are bound to the corresponding properties
   * of the Runway object and the scaling factors.
   * @see ClearedGradedArea
   */
  public void makeCGA(boolean filled){

    //Cleared and graded area
    ClearedGradedArea cga = new ClearedGradedArea(group,filled);
    cga.leftProperty().bind(appWindow.runway.runwayLengthProperty().multiply(-0.5).subtract( appWindow.runway.stopwayProperty()).subtract(appWindow.runway.stripEndProperty()).multiply(scaleFactor));
    cga.leftStartProperty().bind(appWindow.runway.runwayLengthProperty().multiply(-0.5).multiply(scaleFactor));
    cga.leftEndProperty().bind(appWindow.runway.runwayLengthProperty().multiply(-0.5).add(150).multiply(scaleFactor));
    cga.rightProperty().bind(appWindow.runway.runwayLengthProperty().multiply(0.5).add( appWindow.runway.stopwayProperty()).add(appWindow.runway.stripEndProperty()).multiply(scaleFactor));
    cga.rightStartProperty().bind(appWindow.runway.runwayLengthProperty().multiply(0.5).multiply(scaleFactor));
    cga.rightEndProperty().bind(appWindow.runway.runwayLengthProperty().multiply(0.5).subtract(150).multiply(scaleFactor));
    cga.innerHeightProperty().bind(scaleFactorHeight.multiply(-75));
    cga.outerHeightProperty().bind(scaleFactorHeight.multiply(-105));
    group.getChildren().add(cga);
  }

  /**
   * Adds labels to the 3D space represented by the Group object, by calling the addLabel() method with the
   * appropriate parameters. The labels are added to a new Group object, which is then added to the main Group object.
  public void addLabels(){
    Group labels = new Group();
    group.getChildren().add(labels);
    addLabel(
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(1000).multiply(1),
            0.5,
            labels,
            Color.WHITE,
            "Test"
    );
  }

   */

  public void buildLabels() {
    Pane labelPane = new Pane();
    //Lengths and xOffsets need binding to back-end variables, work hasn't been done yet so constants used

    //RunwayArrow TODARightLabel = new RunwayArrowRight("TODA", Color.RED, scaleFactor, 100, 25, 3000);
    RunwayLabel TORALeftLabel = new RunwayLabel("TORA", Color.AQUA, appWindow.runway.runwayLengthProperty().multiply(-0.5), 0.9, appWindow.runway.leftToraProperty().multiply(-1),this,true);
    RunwayLabel TODALeftLabel = new RunwayLabel("TODA", Color.MAGENTA, appWindow.runway.runwayLengthProperty().multiply(-0.5).subtract(appWindow.runway.clearwayProperty()), 0.7, appWindow.runway.leftTodaProperty().multiply(-1),this,true);
    RunwayLabel ASDALeftLabel = new RunwayLabel("ASDA", Color.RED, appWindow.runway.runwayLengthProperty().multiply(-0.5).subtract(appWindow.runway.stopwayProperty()), 0.5, appWindow.runway.leftAsdaProperty().multiply(-1),this,true);
    RunwayLabel LDALeftLabel = new RunwayLabel("LDA", Color.YELLOW, appWindow.runway.runwayLengthProperty().multiply(-0.5).add(appWindow.runway.dispThresholdProperty()), 0.3, appWindow.runway.leftLdaProperty().multiply(-1),this,true);
    RunwayLabel TORARightLabel = new RunwayLabel("TORA", Color.AQUA, appWindow.runway.runwayLengthProperty().multiply(0.5), -0.9, appWindow.runway.rightToraProperty().multiply(1),this,false);
    RunwayLabel TODARightLabel = new RunwayLabel("TODA", Color.MAGENTA, appWindow.runway.runwayLengthProperty().multiply(0.5).add(appWindow.runway.clearwayProperty()), -0.7, appWindow.runway.rightTodaProperty().multiply(1),this,false);
    RunwayLabel ASDARightLabel = new RunwayLabel("ASDA", Color.RED, appWindow.runway.runwayLengthProperty().multiply(0.5).add(appWindow.runway.stopwayProperty()), -0.5, appWindow.runway.rightAsdaProperty().multiply(1),this,false);
    RunwayLabel LDARightLabel = new RunwayLabel("LDA", Color.YELLOW, appWindow.runway.runwayLengthProperty().multiply(0.5).subtract(appWindow.runway.dispThresholdProperty()), -0.3, appWindow.runway.rightLdaProperty().multiply(1),this,false);
    group.getChildren().addAll(TODARightLabel, ASDARightLabel, TORARightLabel, LDARightLabel, TODALeftLabel, ASDALeftLabel, TORALeftLabel, LDALeftLabel);
  }

  public double getAngleXProperty() {
    return angleXProperty.get();
  }

  public DoubleProperty angleXProperty() {
    return angleXProperty;
  }

  public double getAngleYProperty() {
    return angleYProperty.get();
  }

  public DoubleProperty angleYProperty() {
    return angleYProperty;
  }

  public double getAngleZProperty() {
    return angleZProperty.get();
  }

  public DoubleProperty angleZProperty() {
    return angleZProperty;
  }

  public double getScaleFactor() {
    return scaleFactor.get();
  }

  public SimpleDoubleProperty scaleFactorProperty() {
    return scaleFactor;
  }
}
