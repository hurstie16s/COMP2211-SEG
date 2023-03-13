package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Interfaces.GlobalVariables;
import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.HandlerPane;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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
    public void showAirportConfig(MouseEvent mouseEvent){
        airportButton.setBackground(new Background(new BackgroundFill(GlobalVariables.focusedBG,null,null)));
        obstacleButton.setBackground(new Background(new BackgroundFill(GlobalVariables.unfocusedBG,null,null)));



    }
    public void showObstacleConfig(MouseEvent mouseEvent){
        airportButton.setBackground(new Background(new BackgroundFill(GlobalVariables.unfocusedBG,null,null)));
        obstacleButton.setBackground(new Background(new BackgroundFill(GlobalVariables.focusedBG,null,null)));

    }


    public void build(){
        super.build();
        root.setBackground(new Background(new BackgroundFill(GlobalVariables.unfocusedBG,null,null)));
        mainPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
        airportButton = new Button("Airport Configuration");
        obstacleButton = new Button("Obstacle Configuration");
        airportButton.setTextFill(GlobalVariables.fg);
        obstacleButton.setTextFill(GlobalVariables.fg);
        airportButton.setOnMouseClicked(this::showAirportConfig);
        obstacleButton.setOnMouseClicked(this::showObstacleConfig);
        airportButton.maxHeightProperty().bind(root.heightProperty().divide(20).subtract(10));
        airportButton.minHeightProperty().bind(root.heightProperty().divide(20).subtract(10));
        obstacleButton.maxHeightProperty().bind(root.heightProperty().divide(20).subtract(10));
        obstacleButton.minHeightProperty().bind(root.heightProperty().divide(20).subtract(10));
        topbar = new HBox(airportButton,obstacleButton);
        topbar.setPadding(new Insets(10,0,0,0));
        showAirportConfig(null);


        layout = new VBox(topbar);
        mainPane.getChildren().add(layout);
    }

}
