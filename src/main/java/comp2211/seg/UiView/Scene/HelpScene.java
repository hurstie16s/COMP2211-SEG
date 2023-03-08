package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Stage.AppWindow;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class HelpScene extends Scene {

    private static final Logger logger = LogManager.getLogger(HelpScene.class);
    private final AppWindow appWindow;
    private final VBox root;
    private boolean visible = true;

    /**
     * Constructor to create a SceneAbstract object.
     * @param root      the root pane of the scene
     * @param appWindow the application window of the scene
     */
    public HelpScene(VBox root, AppWindow appWindow) {
        super(root, appWindow.getWidth(), appWindow.getHeight());
        appWindow.getStage().requestFocus();
        this.appWindow = appWindow;
        this.root = root;
        root.setBackground(new Background(new BackgroundFill(Color.rgb(21,21,21,0.8),null,null)));

    }
    public void makeKey(String key, String desc){
        HBox box = new HBox();
        StackPane imagePane = new StackPane();

        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/keycap.png")).toExternalForm()));
        imageView.setFitWidth(24);
        imageView.setFitHeight(24);
        Text keyView = new Text(key);
        keyView.setFill(Color.WHITE);
        keyView.setFont(new Font("Calibri",16));
        double scale = Math.min((16/keyView.boundsInLocalProperty().get().getWidth()),16/keyView.boundsInLocalProperty().get().getHeight());
        keyView.setScaleX(scale);
        keyView.setScaleY(scale);
        Text descView = new Text(desc);
        descView.setFill(Color.WHITE);
        descView.setFont(new Font("Calibri",24));

        imagePane.getChildren().add(imageView);
        imagePane.getChildren().add(keyView);
        box.getChildren().add(imagePane);
        box.getChildren().add(descView);
        root.setMaxWidth(Math.max(root.getMaxWidth(),(descView.boundsInLocalProperty().get().getWidth()+20)));
        root.getChildren().add(box);
        root.setMaxHeight(24*root.getChildren().size());
    }
    public void makeColour(Color colour, String desc){

        HBox box = new HBox();
        StackPane colourPane = new StackPane();


        colourPane.setMinWidth(24);
        colourPane.setMaxWidth(24);
        colourPane.setMinHeight(24);
        colourPane.setMaxHeight(24);
        colourPane.setBackground(new Background(new BackgroundFill(colour,null,null)));

        Text descView = new Text(desc);
        descView.setFill(Color.WHITE);
        descView.setFont(new Font("Calibri",24));

        box.getChildren().add(colourPane);
        box.getChildren().add(descView);
        root.setMaxWidth(Math.max(root.getMaxWidth(),(descView.boundsInLocalProperty().get().getWidth()+20)));
        root.getChildren().add(box);
        root.setMaxHeight(24*root.getChildren().size());
    }
    public void toggleHelp(String className){
        //logger.info(className);
        if (!visible) {
            switch (className) {
                case "comp2211.seg.UiView.Scene.RunwayScene":
                    makeKey("Esc", "Navigate back to home screen");
                    makeKey("T", "Toggle between top and side views");
                    break;
                case "comp2211.seg.UiView.Scene.RunwaySceneLoader":
                    makeKey("Esc", "Navigate back to home screen");
                    makeKey("T", "Toggle between top and side views");
                    makeColour(Color.DARKRED,"Obstacle");
                    makeColour(Color.DARKCYAN,"Approach/Take off Slope");
                    makeColour(Color.BLUE,"Cleared and graded area");
                    makeColour(Color.DARKGOLDENROD,"Clearway");
                    makeColour(Color.VIOLET,"Stopway");
                    makeColour(Color.SADDLEBROWN,"RESA");
                    makeColour(Color.GREY,"Runway");
                    break;
                case "comp2211.seg.UiView.Scene.MainScene":
                    makeKey("Esc", "Exit application");
                    makeColour(Color.DARKRED,"Obstacle");
                    makeColour(Color.BLUE,"Cleared and graded area");
                    makeColour(Color.DARKCYAN,"Approach/Take off Slope");
                    makeColour(Color.DARKGOLDENROD,"Clearway");
                    makeColour(Color.VIOLET,"Stopway");
                    makeColour(Color.SADDLEBROWN,"RESA");
                    makeColour(Color.GREY,"Runway");
                    break;

            }
        }else {
            root.getChildren().removeAll(root.getChildren());
            makeKey("H", "Toggle the help menu");
        }
        visible = !visible;
    }
}
