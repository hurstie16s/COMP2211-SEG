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
        return new VBox();
    }


}
