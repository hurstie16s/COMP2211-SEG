package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.HandlerPane;
import javafx.scene.Node;
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
    private boolean visible = false;

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
        root.setBackground(new Background(new BackgroundFill(Color.rgb(21,21,21,0.4),null,null)));
    }
    public void makeKey(String key, String desc){
        HBox box = new HBox();
        StackPane imagePane = new StackPane();

        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/keycap.png")).toExternalForm()));
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        Text keyView = new Text(key);
        keyView.setFill(Color.WHITE);
        keyView.setFont(new Font("Calibri",16));
        keyView.setScaleX((16/keyView.boundsInLocalProperty().get().getWidth()));
        Text descView = new Text(desc);
        descView.setFill(Color.WHITE);
        descView.setFont(new Font("Calibri",16));

        imagePane.getChildren().add(imageView);
        imagePane.getChildren().add(keyView);
        box.getChildren().add(imagePane);
        box.getChildren().add(descView);
        root.setMaxWidth(Math.max(root.getMaxWidth(),(descView.boundsInLocalProperty().get().getWidth()+20)));
        root.getChildren().add(box);
        root.setMaxHeight(20*root.getChildren().size());
    }
    public void toggleHelp(String className){
        logger.info(className);
        if (!visible) {
            makeKey("H", "Toggle this menu");
            switch (className) {
                case "comp2211.seg.UiView.Scene.RunwayScene":
                    makeKey("Esc", "Navigate back to home screen");
                    break;
                case "comp2211.seg.UiView.Scene.MainScene":
                    break;

            }
        }else {
            root.getChildren().removeAll(root.getChildren());
            root.setMaxHeight(0);
            root.setMaxWidth(0);
        }
        visible = !visible;
    }
}
