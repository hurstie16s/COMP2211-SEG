package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.HandlerPane;
import comp2211.seg.UiView.Scene.RunwayComponents.Sub;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * The type Runway scene loader.
 */
public class RunwaySceneLoader extends SceneAbstract{
    /**
     * The Scene.
     */
    public RunwayScene scene;
    public SubScene subScene;
    public double width;
    public double height;

    /**
     * Constructor to create a SceneAbstract object.
     *
     * @param root      the root pane of the scene
     * @param appWindow the application window of the scene
     * @param width     the width
     * @param height    the height
     */
    public RunwaySceneLoader(Pane root, AppWindow appWindow, double width, double height) {
        super(root, appWindow, new SimpleDoubleProperty(width), new SimpleDoubleProperty(height));
        this.width = width;
        this.height = height;
    }
    public RunwaySceneLoader(Pane root, AppWindow appWindow, SimpleDoubleProperty width, SimpleDoubleProperty height) {
        super(root, appWindow, width, height);
        this.width = width.get();
        this.height = height.get();
    }

    @Override
    public void initialise() {
        setOnKeyPressed((keyEvent -> {
            switch (keyEvent.getCode()){
                case ESCAPE:
                    appWindow.startBaseScene();
                    break;
                case W,UP:
                    scene.group.translateYProperty().set(scene.group.getTranslateY()-10);
                    break;
                case A,LEFT:
                    scene.group.translateXProperty().set(scene.group.getTranslateX()-10);
                    break;
                case S,DOWN:
                    scene.group.translateYProperty().set(scene.group.getTranslateY()+10);
                    break;
                case D,RIGHT:
                    scene.group.translateXProperty().set(scene.group.getTranslateX()+10);
                    break;
                case T:
                    scene.toggleView();
                    break;
                case H:
                    help.toggleHelp(this.getClass().getCanonicalName());
                    scene.refresh();
                    break;
            }
        }));
        setOnMousePressed(event -> {
            scene.x = event.getSceneX();
            scene.y = event.getSceneY();
            scene.anglex = scene.angleXProperty.get();
            scene.angley = scene.angleZProperty.get();
        });
        setOnMouseDragged(event ->{
            scene.angleXProperty.set(Math.min(Math.max(scene.anglex + scene.y - event.getSceneY(), -90), 0));
            scene.angleZProperty.set(scene.angley - scene.x + event.getSceneX());
        });
        setOnScroll(event -> subScene.cameraProperty().get().translateZProperty().set(subScene.cameraProperty().get().getTranslateZ()+event.getDeltaY()));


    }
    public void build(){
        super.build();
        Pane runwayPane = new Pane();
        Pane subPane = new Pane();

        scene = new RunwayScene(runwayPane,appWindow, width, height,false);
        scene.buildmenuless();
        scene.initialise();
        subScene = new Sub(subPane,width,height,true, SceneAntialiasing.BALANCED);
        subPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
        runwayPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
        getRoot().getStyleClass().add("transparent");
        root.getStyleClass().add("sky");
        mainPane.getStyleClass().add("transparent");
        subPane.getChildren().add(runwayPane);
        root.getChildren().removeAll(root.getChildren());
        root.getChildren().add(subScene);
        VBox layoutPane = new VBox(topMenu,mainPane);

        root.getChildren().add(layoutPane);

        scene.root.maxWidthProperty().bind(root.widthProperty());
        scene.root.minWidthProperty().bind(root.widthProperty());
        scene.root.maxHeightProperty().bind(root.heightProperty());
        scene.root.minHeightProperty().bind(root.heightProperty());

        subScene.widthProperty().bind(root.widthProperty());
        subScene.heightProperty().bind(root.heightProperty());
    }
    public void buildmenuless(){
        super.buildmenuless();
        scene = new RunwayScene(new Pane(),appWindow, root.getWidth(), root.getHeight(),false);
        scene.buildmenuless();
        scene.initialise();
        mainPane.getChildren().add(scene.getRoot());

        scene.root.maxWidthProperty().bind(root.widthProperty());
        scene.root.minWidthProperty().bind(root.widthProperty());
        scene.root.maxHeightProperty().bind(root.heightProperty());
        scene.root.minHeightProperty().bind(root.heightProperty());
    }
    public void buildmenulessalt(){
        super.buildmenuless();
        type = "buildmenulessalt";
        Pane runwayPane = new Pane();
        Pane subPane = new Pane();

        scene = new RunwayScene(runwayPane,appWindow, root.widthProperty().get(), root.heightProperty().get(),false);
        scene.buildmenulessalt();
        scene.initialise();
        subScene = new Sub(subPane, root.getWidth(), root.getHeight());
        subPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
        subPane.getStylesheets().add(AppWindow.pathToStyle.get());
        runwayPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
        getRoot().getStyleClass().add("transparent");
        root.getStyleClass().add("sky");
        mainPane.getStyleClass().add("transparent");
        subPane.getChildren().add(runwayPane);
        root.getChildren().removeAll(root.getChildren());
        root.getChildren().add(subScene);

        root.getChildren().add(mainPane);
        subScene.widthProperty().bind(root.widthProperty());
        subScene.heightProperty().bind(root.heightProperty());

        scene.root.maxWidthProperty().bind(root.widthProperty());
        scene.root.minWidthProperty().bind(root.widthProperty());
        scene.root.maxHeightProperty().bind(root.heightProperty());
        scene.root.minHeightProperty().bind(root.heightProperty());
    }
    public void buildwithTime() {
        super.buildmenuless();
        Text text = new Text();
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("EEE, MMM d"));
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
        text.setText(formattedDate + "\n" + formattedTime);

        //text.getStyleClass().addAll("fontbig","bold");
        text.setFill(Color.RED);
        text.setFont(Font.font("Times New Roman", 20));


        var vBox = new VBox(text);
        //Pane timePaneTransparent = new Pane(vBox);
        scene = new RunwayScene(new Pane(vBox),appWindow, root.getWidth(), root.getHeight(),false);
        scene.buildmenuless();
        scene.initialise();
        mainPane.getChildren().addAll(scene.getRoot());

        scene.root.maxWidthProperty().bind(root.widthProperty());
        scene.root.minWidthProperty().bind(root.widthProperty());
        scene.root.maxHeightProperty().bind(root.heightProperty());
        scene.root.minHeightProperty().bind(root.heightProperty());
    }

    public void reset(){
        double anglex = scene.angleXProperty.get();
        double angley = scene.angleYProperty.get();
        double anglez = scene.angleZProperty.get();
        root.getChildren().clear();
        root.getStyleClass().clear();
        subScene = null;
        scene = null;

        if (type.equals("build")){
            build();
        } else if (type.equals("buildmenuless")){
            buildmenuless();
        } else if (type.equals("buildmenulessalt")){
            buildmenulessalt();
        } else {
            //build();
        }
        scene.angleXProperty.set(anglex);
        scene.angleYProperty.set(angley);
        scene.angleZProperty.set(anglez);
    }
    public void refresh(){
        scene.refresh();
    }

}
