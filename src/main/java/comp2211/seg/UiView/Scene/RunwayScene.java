package comp2211.seg.UiView.Scene;
import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.Theme;
import comp2211.seg.UiView.Overlay.RunwayLabel;
import comp2211.seg.UiView.Scene.RunwayComponents.ClearedGradedArea;
import comp2211.seg.UiView.Scene.RunwayComponents.Slope;
import comp2211.seg.UiView.Scene.Utilities.CssColorParser;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Rotate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;



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
   * The Side property.
   */
  public SimpleBooleanProperty sideProperty = new SimpleBooleanProperty(false);

  /**
   * The camera used to view the runway scene.
   */
  public Camera camera;
  /**
   * The Background.
   */
  protected Box background ;

  /**
   * A boolean flag indicating whether the scene is in "view" mode.
   */
  private Boolean view = false;
  /**
   * The Portrait.
   */
  public SimpleBooleanProperty portrait = new SimpleBooleanProperty(false);

  /**
   * The x-coordinate of the mouse when it is clicked.
   */
  public double x;

  /**
   * The y-coordinate of the mouse when it is clicked.
   */
  public double y;

  /**
   * The x angle of rotation of the runway scene.
   */
  public double anglex = 0;

  /**
   * The y angle of rotation of the runway scene.
   */
  public double angley = 0;

  /**
   * The Scale factor.
   */
  protected SimpleDoubleProperty scaleFactor = new SimpleDoubleProperty(0.5);
  /**
   * The Scale factor height.
   */
  protected SimpleDoubleProperty scaleFactorHeight = new SimpleDoubleProperty(2);
  /**
   * The Scale factor depth.
   */
  protected SimpleDoubleProperty scaleFactorDepth = new SimpleDoubleProperty(4);
  /**
   * The Refresh.
   */
  protected SimpleBooleanProperty refresh = new SimpleBooleanProperty(true);


  /**
   * A DoubleProperty object representing the x angle of rotation of the runway scene.
   */
  public final DoubleProperty angleXProperty = new SimpleDoubleProperty();

  /**
   * A DoubleProperty object representing the y angle of rotation of the runway scene.
   */
  public final DoubleProperty angleYProperty = new SimpleDoubleProperty();

  /**
   * A DoubleProperty object representing the z angle of rotation of the runway scene.
   */
  public final DoubleProperty angleZProperty = new SimpleDoubleProperty();
  /**
   * A DoubleProperty object representing the x angle of rotation of the runway scene.
   */
  public final DoubleProperty lableAngleXProperty = new SimpleDoubleProperty();

  /**
   * A DoubleProperty object representing the y angle of rotation of the runway scene.
   */
  public final DoubleProperty lableAngleYProperty = new SimpleDoubleProperty();

  /**
   * A DoubleProperty object representing the z angle of rotation of the runway scene.
   */
  public final DoubleProperty lableAngleZProperty = new SimpleDoubleProperty();


  /**
   * Constructs a new RunwayScene object.
   *
   * @param root        the root handler pane for the scene
   * @param appWindow   the main application window
   * @param width       the width
   * @param height      the height
   * @param depthBuffer the depth buffer
   */
  public RunwayScene(Pane root, AppWindow appWindow, double width, double height, boolean depthBuffer) {
    super(root, appWindow, new SimpleDoubleProperty(width), new SimpleDoubleProperty(height), depthBuffer);
    this.width = width;
    this.height = height;

    this.group = new Group();
    this.appWindow = appWindow;

  }

  /**
   * Initializes the scene by setting up the keyboard and mouse event listeners.
   */
  @Override
  public void initialise() {
    setOnKeyPressed((keyEvent -> {
      switch (keyEvent.getCode()){
        case ESCAPE:
          appWindow.startBaseScene();
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
        case H:
          help.toggleHelp(this.getClass().getCanonicalName());
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
      angleXProperty.set(Math.min(Math.max(anglex + y - event.getSceneY(), -90), 0));
      angleZProperty.set(angley - x + event.getSceneX());
    });
    setOnScroll(event -> mainPane.translateZProperty().set(mainPane.getTranslateZ()-event.getDeltaY()));
  }

  /**
   * Toggles the "view" mode of the runway scene, which changes the camera angle to view the scene from above or from the side.
   */
  public void toggleView(){
    view = !view;
    if (view){
      angleXProperty.set(-90);
      angleYProperty.set(0);
      angleZProperty.set(0);
      sideProperty.set(true);
    }
    else{
      angleXProperty.set(0);
      angleYProperty.set(0);
      angleZProperty.set(0);
      sideProperty.set(false);

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
   * @return the box
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
   * Make scale.
   */
  public void makeScale(){
    double length = 500;
    RunwayLabel scale = new RunwayLabel(Color.WHITE,appWindow.runway.runwayLengthProperty().divide(2),0.15,new SimpleDoubleProperty(length).add(0),this,new SimpleBooleanProperty(false).not());
    group.getChildren().add(scale);
  }

  /**
   * Creates a 3D box representing the runway, textured with
   * an image of a runway.
   */
  public void makeRunway() {
    // Parse the color value from the .obstacle class in the current stylesheet

    PhongMaterial material = new PhongMaterial();
    logger.info("making runway " + appWindow.runway + " of " + appWindow.airport);
    material.setDiffuseColor(Theme.getRunway());

    //import these from runway somehow
    Box box = new Box(0,0,runwayOffset.get());
    box.widthProperty().bind(appWindow.runway.runwayLengthProperty().multiply(scaleFactor));
    box.heightProperty().bind(appWindow.runway.runwayWidthProperty().multiply(scaleFactorHeight));
    box.depthProperty().bind(runwayOffset.multiply(scaleFactorDepth));
    box.translateZProperty().bind(runwayOffset.divide(2).multiply(scaleFactorDepth));
    box.setMaterial(material);

    group.getChildren().add(box);
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
   * Configures the camera by adding ambient light, creating a perspective camera,
   * and setting up rotations for the group.
   * Binds angle properties to corresponding rotate angles.
   */
  public void configureCameraAlt() {
    AmbientLight light = new AmbientLight();
    light.setLightOn(true);
    group.getChildren().add(light);
    camera = new ParallelCamera();
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

    background = new Box(widthProperty().get(),heightProperty().get(),0);
    PhongMaterial material = new PhongMaterial();
    material.setDiffuseColor(Theme.getGrass());

    background.setMaterial(material);
    background.widthProperty().bind(Bindings.when(portrait).then(mainPane.heightProperty()).otherwise(mainPane.widthProperty()));
    background.heightProperty().bind(Bindings.when(portrait).then(mainPane.widthProperty()).otherwise(mainPane.heightProperty()));
    background.translateXProperty().bind(appWindow.runway.clearwayLeftProperty().add(appWindow.runway.clearwayRightProperty()).add(appWindow.runway.runwayLengthProperty()).divide(2)
            .subtract(appWindow.runway.clearwayLeftProperty().add(appWindow.runway.runwayLengthProperty().divide(2))).multiply(scaleFactor));
    background.setTranslateZ(2.1);
    group.getChildren().add(background);
  }
  public void buildmenuless(){
    super.buildmenuless();
    //setFill(Theme.bgRunway);
    root.getStyleClass().clear();
    mainPane.getStyleClass().clear();
    root.getStyleClass().add("transparent");
    mainPane.getStyleClass().add("transparent");
    logger.info("building");
    configureCamera();
    render();



    mainPane.maxWidthProperty().bind(root.widthProperty());
    mainPane.minWidthProperty().bind(root.widthProperty());
    mainPane.maxHeightProperty().bind(root.heightProperty());
    mainPane.minHeightProperty().bind(root.heightProperty());

    root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
    mainPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
    scaleFactor.bind(Bindings.when(portrait).then(mainPane.heightProperty()).otherwise(mainPane.widthProperty()).divide(appWindow.runway.runwayLengthProperty().add(Bindings.max(appWindow.runway.getMINRESA()+150,appWindow.runway.clearwayRight)).add(Bindings.max(appWindow.runway.getMINRESA()+150,appWindow.runway.clearwayLeft))));
    scaleFactorHeight.bind(Bindings.when(portrait).then(mainPane.widthProperty()).otherwise(mainPane.heightProperty()).divide(420));
    mainPane.getChildren().add(group);


    addListeners();
  }


  /**
   * Buildmenulessalt.
   */
  public void buildmenulessalt(){
    super.buildmenuless();
    //setFill(Theme.getRunway());
    getRoot().getStyleClass().add("transparent");
    root.getStyleClass().clear();
    mainPane.getStyleClass().clear();
    root.getStyleClass().add("transparent");
    mainPane.getStyleClass().add("transparent");
    logger.info("building");
    configureCameraAlt();
    render();

    mainPane.maxWidthProperty().bind(root.widthProperty());
    mainPane.minWidthProperty().bind(root.widthProperty());
    mainPane.maxHeightProperty().bind(root.heightProperty());
    mainPane.minHeightProperty().bind(root.heightProperty());
    root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
    mainPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));

    scaleFactor.bind(Bindings.when(portrait).then(mainPane.heightProperty()).otherwise(mainPane.widthProperty()).divide(appWindow.runway.runwayLengthProperty().add(Bindings.max(appWindow.runway.getMINRESA()+150,appWindow.runway.clearwayRight)).add(Bindings.max(appWindow.runway.getMINRESA()+150,appWindow.runway.clearwayLeft))));    scaleFactorHeight.bind(Bindings.when(portrait).then(mainPane.widthProperty()).otherwise(mainPane.heightProperty()).divide(420));
    mainPane.getChildren().add(group);

    addListeners();
  }


  /**
   * Builds the scene, adding the 3D objects to the scene and
   * initializing camera and rotation controls.
   */
  @Override
  public void build() {
    super.build();
    //setFill(Theme.bgRunway);
    root.getStyleClass().clear();
    mainPane.getStyleClass().clear();
    root.getStyleClass().add("transparent");
    mainPane.getStyleClass().add("transparent");
    logger.info("building");
    configureCamera();
    render();
    mainPane.maxWidthProperty().bind(root.widthProperty());
    mainPane.minWidthProperty().bind(root.widthProperty());
    mainPane.maxHeightProperty().bind(root.heightProperty());
    mainPane.minHeightProperty().bind(root.heightProperty());
    root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
    mainPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));

    scaleFactor.bind(Bindings.when(portrait).then(mainPane.heightProperty()).otherwise(mainPane.widthProperty()).divide(appWindow.runway.runwayLengthProperty().add(Bindings.max(appWindow.runway.getMINRESA()+150,appWindow.runway.clearwayRight)).add(Bindings.max(appWindow.runway.getMINRESA()+150,appWindow.runway.clearwayLeft))));    scaleFactorHeight.bind(Bindings.when(portrait).then(mainPane.widthProperty()).otherwise(mainPane.heightProperty()).divide(420));
    mainPane.getChildren().add(group);

    addListeners();


  }

  /**
   * Add listeners.
   */
  public void addListeners(){
    ChangeListener refresh = new ChangeListener<>() {
      @Override
      public void changed(ObservableValue<?> observableValue, Object o, Object t1) {
        refresh();
      }
    };
    appWindow.runway.runwayObstacle.lengthProperty().addListener(refresh);
    appWindow.runway.runwayObstacle.widthProperty().addListener(refresh);
    appWindow.runway.runwayObstacle.heightProperty().addListener(refresh);
    appWindow.runway.runwayObstacle.distFromThresholdProperty().addListener(refresh);
    appWindow.runway.directionLeftProperty().addListener(refresh);
    appWindow.runway.directionRightProperty().addListener(refresh);
  }

  /**
   * Render.
   */
  public void render(){
    group.getChildren().removeAll(group.getChildren());

    makeBackground();
    //root.getStyleClass().add("runway-background");

    makeCGA(true);
    addTopView();
    makeRunway();
    makeCGA(false);
    buildLabels();

    group.translateXProperty().set(0);
    makeRunwayOverlay();
    try {
      renderObstacle();
    } catch (Exception e){
      logger.error(e);
    }
    makeScale();
  }

  /**
   * Make runway overlay.
   */
  public void makeRunwayOverlay(){

    //LeftDispThreshold
    addCuboid(
            appWindow.runway.runwayLengthProperty().divide(-2).add(appWindow.runway.dispThresholdLeftProperty()),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(4).divide(scaleFactor),
            appWindow.runway.runwayWidthProperty().multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            Color.WHITE);
    //RightDispThreshold
    addCuboid(
            appWindow.runway.runwayLengthProperty().divide(2).subtract(appWindow.runway.dispThresholdRightProperty()),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(4).divide(scaleFactor),
            appWindow.runway.runwayWidthProperty().multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            Color.WHITE);
    TextFlow leftDesignator = makeRwyID(appWindow.runway.runwayDesignatorLeftProperty());
    leftDesignator.getTransforms().addAll(new Rotate(90, Rotate.Z_AXIS));
    leftDesignator.translateYProperty().bind(leftDesignator.widthProperty().divide(-2));
    leftDesignator.translateXProperty().bind(appWindow.runway.runwayLengthProperty().divide(-2).add(appWindow.runway.dispThresholdLeftProperty()).multiply(scaleFactor).add(leftDesignator.heightProperty().add(10)));
    group.getChildren().add(leftDesignator);
    TextFlow rightDesignator = makeRwyID(appWindow.runway.runwayDesignatorRightProperty());
    rightDesignator.getTransforms().addAll(new Rotate(-90, Rotate.Z_AXIS));
    rightDesignator.translateYProperty().bind(rightDesignator.widthProperty().divide(2));
    rightDesignator.translateXProperty().bind(appWindow.runway.runwayLengthProperty().divide(2).subtract(appWindow.runway.dispThresholdRightProperty()).multiply(scaleFactor).subtract(rightDesignator.heightProperty().add(10)));
    group.getChildren().add(rightDesignator);


    //CentreLine
    addCuboid(
            appWindow.runway.dispThresholdLeftProperty().add(leftDesignator.heightProperty().add(10)).subtract(appWindow.runway.dispThresholdRightProperty()).subtract(rightDesignator.heightProperty().add(10)).divide(2),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.runwayLengthProperty().subtract(appWindow.runway.dispThresholdLeftProperty()).subtract(appWindow.runway.dispThresholdRightProperty()).subtract(leftDesignator.heightProperty().add(rightDesignator.heightProperty()).add(30).divide(scaleFactor)),
            new SimpleDoubleProperty(2).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            Color.WHITE);

  }

  /**
   * Make rwy id text flow.
   *
   * @param designator the designator
   * @return the text flow
   */
  public TextFlow makeRwyID(SimpleStringProperty designator){
    Group id = new Group();
    Text rwyDir = new Text(designator.getValue());
    Text rwyLabel = new Text("");
    Text bars = new Text("\n||||| |||||");
    rwyDir.setFill(Theme.getLabelFg());
    rwyLabel.setFill(Theme.getLabelFg());
    bars.setFill(Theme.getLabelFg());

    designator.addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        rwyDir.textProperty().set(t1.substring(0,2));
        if (designator.length().get() >2) {
          rwyLabel.textProperty().set(t1.substring(2));
          rwyLabel.setFont(new Font(appWindow.runway.runwayWidth.get()*3/rwyLabel.getBoundsInLocal().getWidth()));
        }
      }
    });
    rwyDir.textProperty().set(designator.getValue().substring(0,2));
    if (designator.length().get() >2) {
      rwyLabel.textProperty().set(designator.getValue().substring(2));
      rwyLabel.setFont(new Font(appWindow.runway.runwayWidth.get()*3/rwyLabel.getBoundsInLocal().getWidth()));
    }
    else {
      rwyLabel.setFont(new Font(0));
    }
    //rwyDir.setFont(Theme.font);
    appWindow.runway.runwayWidth.addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
        rwyDir.setFont(new Font(appWindow.runway.runwayWidth.get()*5/rwyDir.getBoundsInLocal().getWidth()));
        bars.setFont(new Font(appWindow.runway.runwayWidth.get()*8/bars.getBoundsInLocal().getWidth()));

        if (designator.length().get() >2) {
          rwyLabel.setFont(new Font(appWindow.runway.runwayWidth.get()*3/rwyLabel.getBoundsInLocal().getWidth()));
        }
        else {
          rwyLabel.setFont(new Font(0));
        }
      }
    });
    rwyDir.setFont(new Font(appWindow.runway.runwayWidth.get()*5/rwyDir.getBoundsInLocal().getWidth()));
    bars.setFont(new Font(appWindow.runway.runwayWidth.get()*8/bars.getBoundsInLocal().getWidth()));

    TextFlow data = new TextFlow(rwyDir,new Text("\n"),rwyLabel,bars);
    data.setTextAlignment(TextAlignment.CENTER);
    return data;
  }

  /**
   * Renders an obstacle as a cuboid and a slope, and adds them to the 3D scene.
   */
  public void renderObstacle() {

    // Render the obstacle using the retrieved color value
    Box obstacleView = addCuboid(
            appWindow.runway.runwayLengthProperty().divide(-2).add(appWindow.runway.runwayObstacle.distFromThresholdProperty()),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.runwayObstacle.heightProperty().multiply(1),
            appWindow.runway.runwayObstacle.lengthProperty().multiply(1),
            appWindow.runway.runwayObstacle.widthProperty().multiply(1),
            appWindow.runway.runwayObstacle.heightProperty().multiply(1),
            Theme.getObstacle()
    );
    obstacleView.visibleProperty().bind(appWindow.runway.hasRunwayObstacleProperty());

    group.getChildren().add(new Slope(
            appWindow,
            appWindow.runway.runwayLengthProperty().divide(-2),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.runwayWidthProperty().multiply(1),
            appWindow.runway.runwayObstacle.heightProperty().multiply(1),
            Theme.getSlope(),
            appWindow.runway.directionLeftProperty(),
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
            appWindow.runway.runwayObstacle.heightProperty().multiply(1),
            Theme.getSlope(),
            appWindow.runway.directionRightProperty(),
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
            appWindow.runway.runwayLengthProperty().multiply(0.5).add( appWindow.runway.clearwayRightProperty().divide(2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(-.1).multiply(1),
            appWindow.runway.clearwayRightProperty().multiply(1),
            appWindow.runway.clearwayHeightProperty().multiply(1),
            new SimpleDoubleProperty(9.9).multiply(1),
            Theme.getClearway());

    //Clearway Left
    addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(-0.5).add( appWindow.runway.clearwayLeftProperty().divide(-2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(-.1).multiply(1),
            appWindow.runway.clearwayLeftProperty().multiply(1),
            appWindow.runway.clearwayHeightProperty().multiply(1),
            new SimpleDoubleProperty(9.9).multiply(1),
            Theme.getClearway());


    //RESA Right
    addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(0.5).add( appWindow.runway.stripEndProperty()).add(appWindow.runway.RESAWidthProperty().divide(2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(-0.05).multiply(1),
            appWindow.runway.RESAWidthProperty().multiply(1),
            appWindow.runway.RESAHeightProperty().multiply(1),
            new SimpleDoubleProperty(5).multiply(1),
            Theme.getPhysicalResa());


    //RESA Left
    Box resaLeft = addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(-0.5).subtract( appWindow.runway.stripEndProperty()).subtract(appWindow.runway.RESAWidthProperty().divide(2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(-0.05).multiply(1),
            appWindow.runway.RESAWidthProperty().multiply(1),
            appWindow.runway.RESAHeightProperty().multiply(1),
            new SimpleDoubleProperty(5).multiply(1),
            Theme.getPhysicalResa());
    resaLeft.visibleProperty().bind(appWindow.runway.dualDirectionRunway);

    //Stopway Left
    addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(-0.5).add(appWindow.runway.stopwayLeftProperty().divide(-2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.stopwayLeftProperty().multiply(1),
            appWindow.runway.runwayWidthProperty().multiply(1),
            new SimpleDoubleProperty(5).multiply(1),
            Theme.getStopway());


    //Stopway Right
    addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(0.5).add(appWindow.runway.stopwayRightProperty().divide(2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.stopwayRightProperty().multiply(1),
            appWindow.runway.runwayWidthProperty().multiply(1),
            new SimpleDoubleProperty(5).multiply(1),
            Theme.getStopway());


  }

  /**
   * Refresh.
   */
  public void refresh(){
    refresh.set(refresh.not().get());
    if (refresh.get()){
      //appWindow.currentScene.getWindow().setWidth(getWidth()-0.0001);
      scaleFactor.bind(Bindings.when(portrait).then(mainPane.heightProperty()).otherwise(mainPane.widthProperty()).add(0.0001).divide(appWindow.runway.runwayLengthProperty().add(Bindings.max(appWindow.runway.getMINRESA()+150,appWindow.runway.clearwayRight)).add(Bindings.max(appWindow.runway.getMINRESA()+150,appWindow.runway.clearwayLeft))));
    }else {
      //appWindow.currentScene.getWindow().setWidth(getWidth()+0.0001);
      scaleFactor.bind(Bindings.when(portrait).then(mainPane.heightProperty()).otherwise(mainPane.widthProperty()).subtract(0.0001).divide(appWindow.runway.runwayLengthProperty().add(Bindings.max(appWindow.runway.getMINRESA()+150,appWindow.runway.clearwayRight)).add(Bindings.max(appWindow.runway.getMINRESA()+150,appWindow.runway.clearwayLeft))));
    }
  }


  /**
   * Creates the Cleared and Graded Area (CGA) and adds it to the 3D group.
   * The CGA is constructed using a {@link ClearedGradedArea} object.
   * The properties of the CGA are bound to the corresponding properties
   * of the Runway object and the scaling factors.
   *
   * @param filled the filled
   * @see ClearedGradedArea
   */
  public void makeCGA(boolean filled){

    //Cleared and graded area
    ClearedGradedArea cga = new ClearedGradedArea(group,filled,this); //added scene for stylesheet
    cga.leftProperty().bind(appWindow.runway.runwayLengthProperty().multiply(-0.5).subtract( appWindow.runway.stopwayLeftProperty()).subtract(appWindow.runway.stripEndProperty()).multiply(scaleFactor));
    cga.leftStartProperty().bind(appWindow.runway.runwayLengthProperty().multiply(-0.5).multiply(scaleFactor));
    cga.leftEndProperty().bind(appWindow.runway.runwayLengthProperty().multiply(-0.5).add(150).multiply(scaleFactor));
    cga.rightProperty().bind(appWindow.runway.runwayLengthProperty().multiply(0.5).add( appWindow.runway.stopwayRightProperty()).add(appWindow.runway.stripEndProperty()).multiply(scaleFactor));
    cga.rightStartProperty().bind(appWindow.runway.runwayLengthProperty().multiply(0.5).multiply(scaleFactor));
    cga.rightEndProperty().bind(appWindow.runway.runwayLengthProperty().multiply(0.5).subtract(150).multiply(scaleFactor));
    cga.innerHeightProperty().bind(scaleFactorHeight.multiply(-75));
    cga.outerHeightProperty().bind(scaleFactorHeight.multiply(-105));
    cga.setTranslateZ(2);
    group.getChildren().add(cga);
  }

  public void makeAlignButton(){
    Button button = new Button("Align");
    button.setFocusTraversable(false);

    Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/compass.png")).toExternalForm());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(50);
    imageView.setFitHeight(50);
    imageView.rotateProperty().bind(angleZProperty.subtract(Double.parseDouble(appWindow.runway.runwayDesignatorLeftProperty().getValue().substring(0,2))*10-90));
    VBox gui = new VBox(button, imageView);

    //Region region = new Region();
    //HBox box = new HBox(region,gui);
    HBox box = new HBox(gui);
    //HBox.setHgrow(region,Priority.ALWAYS);
    //box.setAlignment(Pos.TOP_RIGHT);


    ImageView fullScreen = new ImageView(new Image(Objects.requireNonNull(getClass()
        .getResource("/images/close.png")).toExternalForm()));

    fullScreen.setOnMouseEntered(event -> {
      fullScreen.setCursor(Cursor.HAND);
    });

    // Set the default cursor when the mouse leaves the ImageView
    fullScreen.setOnMouseExited(event -> {
      fullScreen.setCursor(Cursor.DEFAULT);
    });


    // Create an ImageView of the icon and add it to the StackPane
    //ImageView iconView = new ImageView(icon);
    fullScreen.setFitHeight(25); // adjust the height and width to your liking
    fullScreen.setFitWidth(25);
    fullScreen.setPreserveRatio(true);
    fullScreen.setPickOnBounds(false);
    Button button2 = new Button();
    button2.setFocusTraversable(false);
    button2.setGraphic(fullScreen);
    StackPane transparentPane = new StackPane(box,button2);
    transparentPane.setAlignment(button2, Pos.TOP_RIGHT); // align the icon to the top right corner
    transparentPane.setAlignment(box, Pos.BOTTOM_RIGHT); // align the icon to the bottom right corner
    transparentPane.setMargin(button2, new Insets(10));
    transparentPane.setMargin(box, new Insets(10));
    transparentPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
    transparentPane.maxWidthProperty().bind(root.widthProperty());
    transparentPane.minWidthProperty().bind(root.widthProperty());
    transparentPane.maxHeightProperty().bind(root.heightProperty());
    transparentPane.minHeightProperty().bind(root.heightProperty());
    AppWindow.currentScene.mainPane.getChildren().add(transparentPane);

    button2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                  appWindow.startBaseScene();
                                }
    });
    button.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {

        angleXProperty.set(0);
        angleYProperty.set(0);
        angleZProperty.set(Double.parseDouble(appWindow.runway.runwayDesignatorLeftProperty().getValue().substring(0,2))*10-90);
      }
    });
  }

  /**
   * Adds labels to the 3D space represented by the Group object, by calling the addLabel() method with the
   * appropriate parameters. The labels are added to a new Group object, which is then added to the main Group object.
   * public void addLabels(){
   * Group labels = new Group();
   * group.getChildren().add(labels);
   * addLabel(
   * new SimpleDoubleProperty(0).multiply(1),
   * new SimpleDoubleProperty(1000).multiply(1),
   * 0.5,
   * labels,
   * Color.WHITE,
   * "Test"
   * );
   * }
   */
  public void buildLabels() {
    Pane labelPane = new Pane();

    //Lengths and xOffsets need binding to back-end variables, work hasn't been done yet so constants used
    DoubleBinding leftDisplacementO = appWindow.runway.runwayLengthProperty().multiply(-0.5).add(appWindow.runway.runwayObstacle.distFromThresholdProperty().add(appWindow.runway.runwayObstacle.lengthProperty().divide(2)));
    DoubleBinding leftDisplacementT = appWindow.runway.runwayLengthProperty().multiply(-0.5).add(Bindings.when(appWindow.runway.directionLeftProperty().not().and(appWindow.runway.hasRunwayObstacleProperty())).then(appWindow.runway.runwayObstacle.distFromThresholdProperty().add(appWindow.runway.runwayObstacle.lengthProperty().divide(2)).add(appWindow.runway.BLASTZONEProperty())).otherwise(0));
    DoubleBinding leftDisplacementL = appWindow.runway.runwayLengthProperty().multiply(-0.5).add(Bindings.when(appWindow.runway.directionLeftProperty().not().and(appWindow.runway.hasRunwayObstacleProperty())).then(appWindow.runway.runwayObstacle.distFromThresholdProperty().add(appWindow.runway.runwayObstacle.lengthProperty().divide(2)).add(Bindings.max(appWindow.runway.BLASTZONEProperty(),appWindow.runway.stripEndProperty().add(Bindings.max(appWindow.runway.RESAWidthProperty(),appWindow.runway.slopeLengthProperty()))))).otherwise(appWindow.runway.dispThresholdLeftProperty()));
    DoubleBinding rightDisplacementO = appWindow.runway.runwayLengthProperty().multiply(0.5).subtract(appWindow.runway.dispThresholdLeftProperty()).subtract(appWindow.runway.runwayObstacle.distFromOtherThresholdProperty().add(appWindow.runway.runwayObstacle.lengthProperty().divide(2)));
    DoubleBinding rightDisplacementT = appWindow.runway.runwayLengthProperty().multiply(0.5).subtract(Bindings.when(appWindow.runway.directionRightProperty().and(appWindow.runway.hasRunwayObstacleProperty())).then(appWindow.runway.runwayObstacle.distFromOtherThresholdProperty().add(appWindow.runway.runwayObstacle.lengthProperty().divide(2)).add(appWindow.runway.BLASTZONEProperty()).add(appWindow.runway.dispThresholdLeftProperty())).otherwise(0));
    DoubleBinding rightDisplacementL = appWindow.runway.runwayLengthProperty().multiply(0.5).subtract(appWindow.runway.dispThresholdLeftProperty()).subtract(Bindings.when(appWindow.runway.directionRightProperty().and(appWindow.runway.hasRunwayObstacleProperty())).then(appWindow.runway.runwayObstacle.distFromOtherThresholdProperty().add(appWindow.runway.runwayObstacle.lengthProperty().divide(2)).add(Bindings.max(appWindow.runway.BLASTZONEProperty(),appWindow.runway.stripEndProperty().add(Bindings.max(appWindow.runway.RESAWidthProperty(),appWindow.runway.slopeLengthProperty()))))).otherwise(appWindow.runway.dispThresholdRightProperty().subtract(appWindow.runway.dispThresholdLeftProperty())));

    DoubleBinding slopeLength = (DoubleBinding) Bindings.max(appWindow.runway.runwayObstacle.heightProperty().multiply(50).subtract(appWindow.runway.runwayObstacle.lengthProperty().divide(2)), new SimpleDoubleProperty(240));

    //RunwayArrow TODARightLabel = new RunwayArrowRight("TODA", Color.RED, scaleFactor, 100, 25, 3000);
    RunwayLabel TODALeftLabel = new RunwayLabel("TODA", Theme.getToda(), leftDisplacementT,
            0.95, appWindow.runway.leftTodaProperty().multiply(-1),this,true, appWindow.runway.leftTakeOffProperty());
    RunwayLabel ASDALeftLabel = new RunwayLabel("ASDA", Theme.getAsda(), leftDisplacementT,
            0.8, appWindow.runway.leftAsdaProperty().multiply(-1),this,true, appWindow.runway.leftTakeOffProperty());
    RunwayLabel TORALeftLabel = new RunwayLabel("TORA", Theme.getTora(), leftDisplacementT,
            0.65, appWindow.runway.leftToraProperty().multiply(-1),this,true, appWindow.runway.leftTakeOffProperty());
    RunwayLabel LDALeftLabel = new RunwayLabel("LDA", Theme.getLda(), leftDisplacementL,
            0.5, appWindow.runway.leftLdaProperty().multiply(-1),this,true, appWindow.runway.leftLandProperty());
    RunwayLabel TODARightLabel = new RunwayLabel("TODA", Theme.getToda(), rightDisplacementT,
            -0.95, appWindow.runway.rightTodaProperty().multiply(1),this,false, appWindow.runway.rightTakeOffProperty());
    RunwayLabel ASDARightLabel = new RunwayLabel("ASDA", Theme.getAsda(), rightDisplacementT,
            -0.8, appWindow.runway.rightAsdaProperty().multiply(1),this,false, appWindow.runway.rightTakeOffProperty());
    RunwayLabel TORARightLabel = new RunwayLabel("TORA", Theme.getTora(), rightDisplacementT,
            -0.65, appWindow.runway.rightToraProperty().multiply(1),this,false, appWindow.runway.rightTakeOffProperty());
    RunwayLabel LDARightLabel = new RunwayLabel("LDA", Theme.getLda(), rightDisplacementL,
            -0.5, appWindow.runway.rightLdaProperty().multiply(1),this,false, appWindow.runway.rightLandProperty());
    group.getChildren().addAll(TODARightLabel, ASDARightLabel, TORARightLabel, LDARightLabel, TODALeftLabel, ASDALeftLabel, TORALeftLabel, LDALeftLabel);

    RunwayLabel resaLeft = new RunwayLabel(Theme.getResa(),leftDisplacementO,0.15,appWindow.runway.RESAWidthProperty().multiply(-1),this,appWindow.runway.directionLeftProperty().not().and(appWindow.runway.hasRunwayObstacleProperty()));
    RunwayLabel stripendLeft = new RunwayLabel(Theme.getStripEnd(),leftDisplacementO.add(Bindings.max(slopeLength,appWindow.runway.RESAWidthProperty())),0.25,appWindow.runway.stripEndProperty().multiply(-1),this,appWindow.runway.directionLeftProperty().not().and(appWindow.runway.hasRunwayObstacleProperty()));
    RunwayLabel blastAllowanceLeft = new RunwayLabel(Theme.getBlastAllowance(),leftDisplacementO.add(0),0.35,appWindow.runway.BLASTZONEProperty().multiply(-1),this,appWindow.runway.directionLeftProperty().not().and(appWindow.runway.hasRunwayObstacleProperty()));
    RunwayLabel resaRight2 = new RunwayLabel(Theme.getResa(),leftDisplacementO,-0.15,appWindow.runway.RESAWidthProperty().multiply(-1),this,appWindow.runway.directionRightProperty().not().and(appWindow.runway.hasRunwayObstacleProperty()));
    RunwayLabel stripendRight2 = new RunwayLabel(Theme.getStripEnd(),leftDisplacementO.add(appWindow.runway.RESAWidthProperty()),-0.25,appWindow.runway.stripEndProperty().multiply(-1),this,appWindow.runway.directionRightProperty().not().and(appWindow.runway.hasRunwayObstacleProperty()).and(appWindow.runway.rightLandProperty()));
    RunwayLabel stripendRight2Slope = new RunwayLabel(Theme.getStripEnd(),leftDisplacementO.add(slopeLength),-0.25,appWindow.runway.stripEndProperty().multiply(-1),this,appWindow.runway.directionRightProperty().not().and(appWindow.runway.hasRunwayObstacleProperty()).and(appWindow.runway.rightLandProperty()).and(Bindings.greaterThan(slopeLength,appWindow.runway.RESAWidthProperty())));
    RunwayLabel blastAllowanceRight2 = new RunwayLabel(Theme.getBlastAllowance(),leftDisplacementO.add(0),-0.35,appWindow.runway.BLASTZONEProperty().multiply(-1),this,appWindow.runway.directionRightProperty().not().and(appWindow.runway.hasRunwayObstacleProperty()));
    RunwayLabel resaRight = new RunwayLabel(Theme.getResa(),rightDisplacementO,-0.15,appWindow.runway.RESAWidthProperty().add(0),this,appWindow.runway.directionRightProperty().and(appWindow.runway.hasRunwayObstacleProperty()).and(appWindow.runway.rightLandProperty()));
    RunwayLabel stripendRight = new RunwayLabel(Theme.getStripEnd(),rightDisplacementO.subtract(Bindings.max(slopeLength,appWindow.runway.RESAWidthProperty())),-0.25,appWindow.runway.stripEndProperty().add(0),this,appWindow.runway.directionRightProperty().and(appWindow.runway.hasRunwayObstacleProperty()).and(appWindow.runway.rightLandProperty()));
    RunwayLabel blastAllowanceRight = new RunwayLabel(Theme.getBlastAllowance(),rightDisplacementO.add(0),-0.35,appWindow.runway.BLASTZONEProperty().add(0),this,appWindow.runway.directionRightProperty().and(appWindow.runway.hasRunwayObstacleProperty()).and(appWindow.runway.rightTakeOffProperty()));
    RunwayLabel resaLeft2 = new RunwayLabel(Theme.getResa(),rightDisplacementO,0.15,appWindow.runway.RESAWidthProperty().add(0),this,appWindow.runway.directionLeftProperty().and(appWindow.runway.hasRunwayObstacleProperty()).and(appWindow.runway.rightLandProperty()));
    RunwayLabel stripendLeft2 = new RunwayLabel(Theme.getStripEnd(),rightDisplacementO.subtract(appWindow.runway.RESAWidthProperty()),0.25,appWindow.runway.stripEndProperty().add(0),this,appWindow.runway.directionLeftProperty().and(appWindow.runway.hasRunwayObstacleProperty()));
    RunwayLabel stripendLeft2Slope = new RunwayLabel(Theme.getStripEnd(),rightDisplacementO.subtract(slopeLength),0.25,appWindow.runway.stripEndProperty().add(0),this,appWindow.runway.directionLeftProperty().and(appWindow.runway.hasRunwayObstacleProperty()).and(Bindings.greaterThan(slopeLength,appWindow.runway.RESAWidthProperty())));
    RunwayLabel blastAllowanceLeft2 = new RunwayLabel(Theme.getBlastAllowance(),rightDisplacementO.add(0),0.35,appWindow.runway.BLASTZONEProperty().add(0),this,appWindow.runway.directionLeftProperty().and(appWindow.runway.hasRunwayObstacleProperty()).and(appWindow.runway.rightTakeOffProperty()));

    group.getChildren().addAll(resaLeft, stripendLeft, blastAllowanceLeft, resaRight, stripendRight, blastAllowanceRight, resaLeft2, stripendLeft2, blastAllowanceLeft2, resaRight2, stripendRight2, blastAllowanceRight2, stripendLeft2Slope, stripendRight2Slope);

    TextField lx = new TextField();
    lx.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        angleXProperty.set(Double.parseDouble(t1));
      }
    });
    TextField ly = new TextField();
    ly.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        angleYProperty.set(Double.parseDouble(t1));
      }
    });
    TextField lz = new TextField();
    lz.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        angleZProperty.set(Double.parseDouble(t1));
      }
    });
    Text x = new Text();
    x.textProperty().bind(angleXProperty.asString());

    Text z = new Text();
    z.textProperty().bind(angleZProperty.asString());
    VBox box = new VBox(lx,ly,lz,x,z);
    //root.getChildren().add(box);

  }

  /**
   * Gets angle x property.
   *
   * @return the angle x property
   */
  public double getAngleXProperty() {
    return angleXProperty.get();
  }

  /**
   * Angle x property double property.
   *
   * @return the double property
   */
  public DoubleProperty angleXProperty() {
    return angleXProperty;
  }

  /**
   * Gets angle y property.
   *
   * @return the angle y property
   */
  public double getAngleYProperty() {
    return angleYProperty.get();
  }

  /**
   * Angle y property double property.
   *
   * @return the double property
   */
  public DoubleProperty angleYProperty() {
    return angleYProperty;
  }

  /**
   * Gets angle z property.
   *
   * @return the angle z property
   */
  public double getAngleZProperty() {
    return angleZProperty.get();
  }

  /**
   * Angle z property double property.
   *
   * @return the double property
   */
  public DoubleProperty angleZProperty() {
    return angleZProperty;
  }

  /**
   * Gets scale factor.
   *
   * @return the scale factor
   */
  public double getScaleFactor() {
    return scaleFactor.get();
  }

  /**
   * Scale factor property simple double property.
   *
   * @return the simple double property
   */
  public SimpleDoubleProperty scaleFactorProperty() {
    return scaleFactor;
  }
}
