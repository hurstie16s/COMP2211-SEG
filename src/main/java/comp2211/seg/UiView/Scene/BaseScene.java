package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Interfaces.GlobalVariables;
import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.HandlerPane;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

public class BaseScene extends SceneAbstract{
    private Button airportButton;
    private Button obstacleButton;
    private HBox topbar;
    private VBox layout;


    /**
     * Constructor to create a SceneAbstract object.
     *
     * @param root      the root pane of the scene
     * @param appWindow the application window of the scene
     * @param width
     * @param height
     */
    public BaseScene(Pane root, AppWindow appWindow, double width, double height) {
        super(root, appWindow, width, height);
    }

    @Override
    public void initialise() {

    }

    public void build(){
        super.build();
        root.setBackground(new Background(new BackgroundFill(GlobalVariables.unfocusedBG,null,null)));
        mainPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));

        Map<String,Pane> tabs = new HashMap<>();
        tabs.put("Airport Configuration", makeAirportConfig());
        tabs.put("Obstacle Configuration", makeObstacleConfig());
        TabLayout tabLayout = new TabLayout(tabs,root.heightProperty().add(0),root.widthProperty().add(0));
        mainPane.getChildren().add(tabLayout);
    }
    public Pane makeAirportConfig(){
        return new VBox();
    }
    public Pane makeObstacleConfig(){
        HBox obstacleLayout = new HBox();
        VBox leftPane = new VBox();
        Map<String,Pane> viewTabs = new HashMap<>();



        RunwayScene runwayScene1 = new RunwayScene(new Pane(), appWindow,appWindow.getWidth()/2.0,appWindow.getHeight()/2.0);
        RunwayScene runwayScene2 = new RunwayScene(new Pane(), appWindow,appWindow.getWidth()/2.0,appWindow.getHeight()/2.0);
        runwayScene1.build();
        runwayScene2.build();
        runwayScene2.toggleView();
        VBox dualView = new VBox(runwayScene1.getRoot(),runwayScene2.getRoot());
        viewTabs.put("Both Views", dualView);
        viewTabs.put("Top-Down Views", dualView);
        viewTabs.put("Side-On Views", dualView);
        TabLayout viewPane = new TabLayout(viewTabs, obstacleLayout.heightProperty().add(0),obstacleLayout.widthProperty().add(0));
        obstacleLayout.getChildren().addAll(leftPane,viewPane);
        return obstacleLayout;
    }


}
