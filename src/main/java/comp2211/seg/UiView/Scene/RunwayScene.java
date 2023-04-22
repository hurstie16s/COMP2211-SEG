package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.Theme;
import comp2211.seg.UiView.Overlay.RunwayLabel;
import comp2211.seg.UiView.Scene.RunwayComponents.ClearedGradedArea;
import comp2211.seg.UiView.Scene.RunwayComponents.Slope;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
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
    public PerspectiveCamera camera;
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
    super(root, appWindow, width, height, depthBuffer);
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
          appWindow.startBaseSceneObstacle();
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
      if (!sideProperty.get()) {
        angleXProperty.set(Math.min(Math.max(anglex + y - event.getSceneY(), -90), 0));
        angleZProperty.set(angley - x + event.getSceneX());
      }
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
    double length = 200;
    addCuboid(appWindow.runway.runwayLengthProperty().divide(2).add(appWindow.runway.clearwayLeftProperty()).subtract(new SimpleDoubleProperty(length+20).divide(2)),
            new SimpleDoubleProperty(100).add(5),
            (DoubleBinding) Bindings.when(portrait).then(mainPane.widthProperty()).otherwise(mainPane.heightProperty()).subtract(20).divide(-2).divide(scaleFactorDepth),
            new SimpleDoubleProperty(length).add(0),
            new SimpleDoubleProperty(2).divide(scaleFactorHeight),
            new SimpleDoubleProperty(2).divide(scaleFactorDepth),
            Color.WHITE
    );

  }

    /**
     * Creates a 3D box representing the runway, textured with
     * an image of a runway.
     */
    public void makeRunway() {
    PhongMaterial material = new PhongMaterial();
    material.setDiffuseColor(Theme.runway);
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
    background = new Box(width,height,0);
    PhongMaterial material = new PhongMaterial();
//material.setDiffuseMap(new Image(Objects.requireNonNull(getClass().getResource("/images/grass.jpg")).toExternalForm()));
    material.setDiffuseColor(Theme.grass);
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
    getRoot().getStyleClass().add("bgRunway");
    logger.info("building");
    configureCamera();
    render();
    root.widthProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
        mainPane.setMinWidth((Double) t1);
        mainPane.setMaxWidth((Double) t1);
        group.translateXProperty().set(group.translateXProperty().get()+((double) t1 - (double) number)/2);
      }
    });
    root.heightProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
        mainPane.setMinHeight((Double) t1);
        mainPane.setMaxHeight((Double) t1);
        group.translateYProperty().set(group.translateYProperty().get()+((double) t1 - (double) number)/2);

      }
    });
    root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
    mainPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
    scaleFactor.bind(Bindings.when(portrait).then(mainPane.heightProperty()).otherwise(mainPane.widthProperty()).divide(appWindow.runway.runwayLengthProperty().add(appWindow.runway.getMINRESA()*2+300)));
    scaleFactorHeight.bind(Bindings.when(portrait).then(mainPane.widthProperty()).otherwise(mainPane.heightProperty()).divide(420));
    mainPane.getChildren().add(group);

    addListeners();
  }


  /**
     * Buildmenulessalt.
     */
    public void buildmenulessalt(){
    super.buildmenuless();
      //setFill(Theme.bgRunway);
    getRoot().getStyleClass().add("bgRunway");
    logger.info("building");
    configureCamera();
    render();

    mainPane.maxWidthProperty().bind(root.widthProperty());
    mainPane.minWidthProperty().bind(root.widthProperty());
    mainPane.maxHeightProperty().bind(root.heightProperty());
    mainPane.minHeightProperty().bind(root.heightProperty());
    root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
    mainPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
    scaleFactor.bind(Bindings.when(portrait).then(mainPane.heightProperty()).otherwise(mainPane.widthProperty()).divide(appWindow.runway.runwayLengthProperty().add(appWindow.runway.getMINRESA()*2+300)));
    scaleFactorHeight.bind(Bindings.when(portrait).then(mainPane.widthProperty()).otherwise(mainPane.heightProperty()).divide(420));
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
    getRoot().getStyleClass().add("bgRunway");
    logger.info("building");
    configureCamera();
    render();
    mainPane.maxWidthProperty().bind(root.widthProperty());
    mainPane.minWidthProperty().bind(root.widthProperty());
    mainPane.maxHeightProperty().bind(root.heightProperty());
    mainPane.minHeightProperty().bind(root.heightProperty());
    root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
    mainPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
    scaleFactor.bind(Bindings.when(portrait).then(mainPane.heightProperty()).otherwise(mainPane.widthProperty()).divide(appWindow.runway.runwayLengthProperty().add(appWindow.runway.getMINRESA()*2+300)));
    scaleFactorHeight.bind(Bindings.when(portrait).then(mainPane.widthProperty()).otherwise(mainPane.heightProperty()).divide(420));
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

    makeScale();
    group.translateXProperty().set(0);
    makeRunwayOverlay();
    try {
      renderObstacle();
    } catch (Exception e){
      logger.error(e);
    }
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
    Text rwyLabel = new Text(designator.getValue());

    designator.addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        rwyDir.textProperty().set(t1.substring(0,2));
        rwyLabel.textProperty().set(t1.substring(2));
      }
    });
    rwyDir.textProperty().set(designator.getValue().substring(0,2));
    rwyLabel.textProperty().set(designator.getValue().substring(2));
    //rwyDir.setFont(Theme.font);
      rwyDir.getStyleClass().add("font");
    //rwyDir.setFill(Theme.labelfg);
      rwyDir.getStyleClass().add("labelfg");
    //rwyLabel.setFont(Theme.font);
      rwyLabel.getStyleClass().add("font");
    //rwyLabel.setFill(Theme.labelfg);
      rwyLabel.getStyleClass().add("labelfg");
    Text bars = new Text("\n||||| |||||");
    //bars.setFill(Theme.labelfg);
      bars.getStyleClass().add("labelfg");
    TextFlow data = new TextFlow(rwyDir,new Text("\n"),rwyLabel,bars);
    data.setTextAlignment(TextAlignment.CENTER);
    return data;
  }

    /**
     * Renders an obstacle as a cuboid and a slope, and adds them to the 3D scene.
     */
    public void renderObstacle(){

    Box obstacleView = addCuboid(
            appWindow.runway.runwayLengthProperty().divide(-2).add(appWindow.runway.runwayObstacle.distFromThresholdProperty()),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.runwayObstacle.heightProperty().multiply(1),
            appWindow.runway.runwayObstacle.lengthProperty().multiply(1),
            appWindow.runway.runwayObstacle.widthProperty().multiply(1),
            appWindow.runway.runwayObstacle.heightProperty().multiply(1),
            Theme.obstacle
            );
    obstacleView.getStyleClass().add("obstacle");
    obstacleView.visibleProperty().bind(appWindow.runway.hasRunwayObstacleProperty());

    group.getChildren().add(new Slope(
            appWindow,
            appWindow.runway.runwayLengthProperty().divide(-2),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.runwayWidthProperty().multiply(1),
            appWindow.runway.runwayObstacle.heightProperty().multiply(1),
            Theme.slope,
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
            Theme.slope,
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
            Theme.clearway);

    //Clearway Left
    addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(-0.5).add( appWindow.runway.clearwayLeftProperty().divide(-2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(-.1).multiply(1),
            appWindow.runway.clearwayLeftProperty().multiply(1),
            appWindow.runway.clearwayHeightProperty().multiply(1),
            new SimpleDoubleProperty(9.9).multiply(1),
            Theme.clearway);



    //RESA Right
    addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(0.5).add( appWindow.runway.stripEndProperty()).add(appWindow.runway.RESAWidthProperty().divide(2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(-0.05).multiply(1),
            appWindow.runway.RESAWidthProperty().multiply(1),
            appWindow.runway.RESAHeightProperty().multiply(1),
            new SimpleDoubleProperty(5).multiply(1),
            Theme.physicalResa);

    //RESA Left
    addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(-0.5).subtract( appWindow.runway.stripEndProperty()).subtract(appWindow.runway.RESAWidthProperty().divide(2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(-0.05).multiply(1),
            appWindow.runway.RESAWidthProperty().multiply(1),
            appWindow.runway.RESAHeightProperty().multiply(1),
            new SimpleDoubleProperty(5).multiply(1),
            Theme.physicalResa);

    //Stopway Left
    addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(-0.5).add(appWindow.runway.stopwayLeftProperty().divide(-2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.stopwayLeftProperty().multiply(1),
            appWindow.runway.runwayWidthProperty().multiply(1),
            new SimpleDoubleProperty(5).multiply(1),
            Theme.stopway);

    //Stopway Right
    addCuboid(
            appWindow.runway.runwayLengthProperty().multiply(0.5).add(appWindow.runway.stopwayRightProperty().divide(2)),
            new SimpleDoubleProperty(0).multiply(1),
            new SimpleDoubleProperty(0).multiply(1),
            appWindow.runway.stopwayRightProperty().multiply(1),
            appWindow.runway.runwayWidthProperty().multiply(1),
            new SimpleDoubleProperty(5).multiply(1),
            Theme.stopway);

  }

    /**
     * Refresh.
     */
    public void refresh(){
    refresh.set(refresh.not().get());
    if (refresh.get()){
      //appWindow.currentScene.getWindow().setWidth(getWidth()-0.0001);
      scaleFactor.bind(Bindings.when(portrait).then(mainPane.heightProperty()).otherwise(mainPane.widthProperty()).add(0.0001).divide(appWindow.runway.runwayLengthProperty().add(appWindow.runway.getMINRESA()*2+300)));
    }else {
      //appWindow.currentScene.getWindow().setWidth(getWidth()+0.0001);
      scaleFactor.bind(Bindings.when(portrait).then(mainPane.heightProperty()).otherwise(mainPane.widthProperty()).subtract(0.0001).divide(appWindow.runway.runwayLengthProperty().add(appWindow.runway.getMINRESA()*2+300)));


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
    ClearedGradedArea cga = new ClearedGradedArea(group,filled);
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
    Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/compass.png")).toExternalForm());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(50);
    imageView.setFitHeight(50);
    imageView.rotateProperty().bind(angleZProperty.subtract(Double.parseDouble(appWindow.runway.runwayDesignatorLeftProperty().getValue().substring(0,2))*10+90));
    VBox gui = new VBox(button, imageView);

    HBox box = new HBox(gui);
    box.setAlignment(Pos.CENTER_LEFT);
    appWindow.currentScene.topMenu.getChildren().add(box);


    button.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {

        angleXProperty.set(0);
        angleYProperty.set(0);
        angleZProperty.set(Double.parseDouble(appWindow.runway.runwayDesignatorLeftProperty().getValue().substring(0,2))*10+90);
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
    DoubleBinding leftDisplacementT = appWindow.runway.runwayLengthProperty().multiply(-0.5).add(Bindings.when(appWindow.runway.directionLeftProperty().not().and(appWindow.runway.hasRunwayObstacleProperty())).then(appWindow.runway.runwayObstacle.distFromThresholdProperty().add(appWindow.runway.runwayObstacle.lengthProperty().divide(2)).add(appWindow.runway.getBLASTZONE())).otherwise(0));
    DoubleBinding leftDisplacementL = appWindow.runway.runwayLengthProperty().multiply(-0.5).add(appWindow.runway.dispThresholdLeftProperty()).add(Bindings.when(appWindow.runway.directionLeftProperty().not().and(appWindow.runway.hasRunwayObstacleProperty())).then(appWindow.runway.runwayObstacle.distFromThresholdProperty().add(appWindow.runway.runwayObstacle.lengthProperty().divide(2)).add(appWindow.runway.slopeLengthProperty())).otherwise(0));
    DoubleBinding rightDisplacementT = appWindow.runway.runwayLengthProperty().multiply(0.5).subtract(Bindings.when(appWindow.runway.directionRightProperty().and(appWindow.runway.hasRunwayObstacleProperty())).then(appWindow.runway.runwayObstacle.distFromOtherThresholdProperty().add(appWindow.runway.runwayObstacle.lengthProperty().divide(2)).add(appWindow.runway.getBLASTZONE())).otherwise(0));
    DoubleBinding rightDisplacementL = appWindow.runway.runwayLengthProperty().multiply(0.5).subtract(appWindow.runway.dispThresholdRightProperty()).subtract(Bindings.when(appWindow.runway.directionRightProperty().and(appWindow.runway.hasRunwayObstacleProperty())).then(appWindow.runway.runwayObstacle.distFromOtherThresholdProperty().add(appWindow.runway.runwayObstacle.lengthProperty().divide(2)).add(appWindow.runway.slopeLengthProperty())).otherwise(0));

    //RunwayArrow TODARightLabel = new RunwayArrowRight("TODA", Color.RED, scaleFactor, 100, 25, 3000);
    RunwayLabel TODALeftLabel = new RunwayLabel("TODA", Theme.toda, leftDisplacementT,
            0.9, appWindow.runway.leftTodaProperty().multiply(-1),this,true, appWindow.runway.leftTakeOffProperty());
    RunwayLabel ASDALeftLabel = new RunwayLabel("ASDA", Theme.asda, leftDisplacementT,
            0.7, appWindow.runway.leftAsdaProperty().multiply(-1),this,true, appWindow.runway.leftTakeOffProperty());
    RunwayLabel TORALeftLabel = new RunwayLabel("TORA", Theme.tora, leftDisplacementT,
            0.5, appWindow.runway.leftToraProperty().multiply(-1),this,true, appWindow.runway.leftTakeOffProperty());
    RunwayLabel LDALeftLabel = new RunwayLabel("LDA", Theme.lda, leftDisplacementL,
            0.3, appWindow.runway.leftLdaProperty().multiply(-1),this,true, appWindow.runway.leftLandProperty());
    RunwayLabel TODARightLabel = new RunwayLabel("TODA", Theme.toda, rightDisplacementT,
            -0.9, appWindow.runway.rightTodaProperty().multiply(1),this,false, appWindow.runway.rightTakeOffProperty());
    RunwayLabel ASDARightLabel = new RunwayLabel("ASDA", Theme.asda, rightDisplacementT,
            -0.7, appWindow.runway.rightAsdaProperty().multiply(1),this,false, appWindow.runway.rightTakeOffProperty());
    RunwayLabel TORARightLabel = new RunwayLabel("TORA", Theme.tora, rightDisplacementT,
            -0.5, appWindow.runway.rightToraProperty().multiply(1),this,false, appWindow.runway.rightTakeOffProperty());
    RunwayLabel LDARightLabel = new RunwayLabel("LDA", Theme.lda, rightDisplacementL,
            -0.3, appWindow.runway.rightLdaProperty().multiply(1),this,false, appWindow.runway.rightLandProperty());
    group.getChildren().addAll(TODARightLabel, ASDARightLabel, TORARightLabel, LDARightLabel, TODALeftLabel, ASDALeftLabel, TORALeftLabel, LDALeftLabel);

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
