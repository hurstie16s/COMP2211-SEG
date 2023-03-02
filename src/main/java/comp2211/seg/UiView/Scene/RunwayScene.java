package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.HandlerPane;
import comp2211.seg.UiView.Overlay.RunwayArrow;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Line;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
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
  protected double runwaylength = 1200;
  protected double runwaywidth = 100;
  private final DoubleProperty angleXProperty = new SimpleDoubleProperty();
  private final DoubleProperty angleYProperty = new SimpleDoubleProperty();
  private final DoubleProperty angleZProperty = new SimpleDoubleProperty();


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

//  public void topRunwayArrow () {
//
//    Pane labelPane = new Pane();
//    root.getChildren().add(labelPane);
//    Line line = new Line(10, 40, 12, 50);
//    line.setStrokeWidth(5);
//    line.setStroke(Color.BEIGE);
//    labelPane.getChildren().addAll(line);
//  }

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
   * @param x distance from the start of the runway
   * @param y distance from the centre of the runway (to the left side)
   * @param w width of the object (from side to side) - in the y axis
   * @param l length of the object (from start to end) - in the x axis
   * @param d height of the object
   * @param color colour of the object
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
  public Box makeRunway() throws FileNotFoundException {
    PhongMaterial material = new PhongMaterial();
    material.setDiffuseMap(new Image(new FileInputStream("src/main/resources/images/runway.jpg")));
    //import these from runway somehow
    Box box = new Box(width* runwaywidth / runwaylength,width,1);
    box.translateZProperty().set(-1);
    box.setMaterial(material);
    return box;
  }
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
