package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.HandlerPane;
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
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * RunwayScene class represents the runway scene of the airport
 * runway operations system.
 * It extends the abstract SceneAbstract class.
 */
public class RunwayScene extends SceneAbstract {
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

  /**
   * The length of the runway.
   */
  protected double runwaylength = 1200;

  /**
   * The width of the runway.
   */
  protected double runwaywidth = 100;

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
    width = root.getParentWidth()-20;
    height = root.getParentHeight()-20;

    Pane arrowPane = new Pane();
    root.getChildren().add(arrowPane);

    RunwayArrow arrow = new RunwayArrow("TORA");
    arrow.setXOffset(100);
    arrow.setLength(100);
    arrowPane.getChildren().addAll(arrow);

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
      angleXProperty.set(anglex + y - event.getSceneY());
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
    initBaseControls();
  }

  /**
   * Toggles the "view" mode of the runway scene, which changes the camera angle to view the scene from above or from the side.
   */
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
   * Adds a cuboid object to the runway scene.
   *
   * @param x     the distance from the start of the runway
   * @param y     the distance from the centre of the runway (to the left side)
   * @param w     the width of the object (from side to side) - in the y axis
   * @param l     the length of the object (from start to end) - in the x axis
   * @param d     the height of the object
   * @param color the colour of the bounding box
   */
  public void addCuboid(double x, double y, double w, double l, double d, Color color){

    double scaleFactor = width/ runwaylength;

    PhongMaterial material = new PhongMaterial();
    material.setDiffuseColor(color);
    //import these from runway somehow
    Box box = new Box(w*scaleFactor,l*scaleFactor,d*scaleFactor);
    box.translateYProperty().set((width/2)-(l*scaleFactor/2)-(x*scaleFactor));
    box.translateXProperty().set((y*scaleFactor));
    box.translateZProperty().set((d*scaleFactor/2));
    box.setMaterial(material);
    group.getChildren().add(box);
  }

  /**
   * Creates a prism MeshView object with the specified coordinates, faces, and color.
   *
   * @param coords the coordinates of the vertices of the prism
   * @param faces  the indices of the vertices used to construct each face of the prism
   * @param color  the color of the prism
   * @return the MeshView object representing the prism
   */
  public MeshView makePrism(float [] coords, int[] faces, Color color){

    PhongMaterial material = new PhongMaterial();
    material.setDiffuseColor(color);
    TriangleMesh mesh = new TriangleMesh();
    mesh.getPoints().addAll(coords);
    mesh.getFaces().addAll(faces);
    mesh.getTexCoords().addAll(1,1);
    MeshView mv = new MeshView(mesh);
    mv.setMaterial(material);
    return mv;
  }

  /**
   * Adds a triangular prism object to the runway scene.
   *
   * @param x         the distance from the start of the runway
   * @param y         the distance from the centre of the runway (to the left side)
   * @param w         the width of the object (from side to side) - in the y axis
   * @param l         the length of the object (from start to end) - in the x axis
   * @param d         the height of the object
   * @param color     the colour of the object
   * @param direction the direction the ramp is facing
   */
  public void addTriangularPrism(double x, double y, double w, double l, double d, Color color, boolean direction){

    double scaleFactor = width/ runwaylength;

    float left = (float) (y * scaleFactor + w * scaleFactor/2);
    float right = (float) (y * scaleFactor - w * scaleFactor/2);
    float start = (float) ((width/2) - x * scaleFactor);
    float end = (float) ((width/2)- x * scaleFactor - l * scaleFactor);
    float bottom = 0;
    float top = (float) (d * scaleFactor);
    MeshView mv;
    if (direction) {
      mv = makePrism(new float[]{
              left, start, bottom,
              left, end, bottom,
              left, end, top,
              right, start, bottom,
              right, end, bottom,
              right, end, top
      }, new int[]{
              0, 0, 2, 0, 1, 0,
              3, 0, 4, 0, 5, 0,
              0, 0, 4, 0, 3, 0,
              0, 0, 1, 0, 4, 0,
              0, 0, 3, 0, 5, 0,
              0, 0, 5, 0, 2, 0,
              1, 0, 5, 0, 4, 0,
              1, 0, 2, 0, 5, 0,
      }, color);
    } else {
       mv = makePrism(new float[]{
              left, start, bottom,
              left, end, bottom,
              left, start, top,
              right, start, bottom,
              right, end, bottom,
              right, start, top
      }, new int[]{
              0, 0, 2, 0, 1, 0,
              3, 0, 4, 0, 5, 0,
              0, 0, 4, 0, 3, 0,
              0, 0, 1, 0, 4, 0,
              0, 0, 3, 0, 5, 0,
              0, 0, 5, 0, 2, 0,
              1, 0, 5, 0, 4, 0,
              1, 0, 2, 0, 5, 0,
      }, color);
    }
    group.getChildren().add(mv);
  }

  /**
   * Creates a 3D box representing the runway, textured with
   * an image of a runway.
   * @return The created 3D box.
   * @throws FileNotFoundException If the image of the runway is not found.
   */
  public Box makeRunway() throws FileNotFoundException {
    PhongMaterial material = new PhongMaterial();
    material.setDiffuseMap(new Image(new FileInputStream("src/main/resources/images/runway.jpg")));
    //import these from runway somehow
    Box box = new Box(width* runwaywidth / runwaylength,width,1);
    box.translateZProperty().set(-1);
    box.setMaterial(material);
    return box;
  }

  /**
   * Builds the scene, adding the 3D objects to the scene and
   * initializing camera and rotation controls.
   */
  @Override
  public void build() {
    try {
      AmbientLight light = new AmbientLight();
      light.setLightOn(true);
      group.getChildren().add(light);
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
      group.translateXProperty().set(width/2-(width* runwaywidth / runwaylength)/2+10);
      group.translateYProperty().set(height/2-width/2+10);
      group.getChildren().add(makeRunway());
      addCuboid(0,0, runwaywidth,150,50,Color.AQUA);
      addTriangularPrism(150,0, runwaywidth,150,50,Color.RED,false);

    } catch (FileNotFoundException e) {
      logger.error(e);
    }
    logger.info("building");
  }
}
