package comp2211.seg.UiView.Scene;

import comp2211.seg.App;
import comp2211.seg.UiView.Stage.AppWindow;
import comp2211.seg.UiView.Stage.Pane;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InputScene extends SceneAbstract{
  private static final Logger logger = LogManager.getLogger(InputScene.class);

  private static BorderPane borderPane;

  public AppWindow appWindow;

  public InputScene(Pane root, AppWindow appWindow) {
    super(root,appWindow);
    this.appWindow = appWindow;

  }

  @Override
  public void initialise() {
    setOnKeyPressed((keyEvent -> {
      if(keyEvent.getCode().equals(KeyCode.ESCAPE)) {
        App.getInstance().shutdown();
      }
    }));
  }
  public void build() {
    super.build();
    logger.info("building");
    mainPane.getStyleClass().add("home-background");
    borderPane = new BorderPane();
    mainPane.getChildren().add(borderPane);

    var buttonBox = new VBox();
    buttonBox.setAlignment(Pos.CENTER);
    var nextSceneButton = new Button("Next");
    buttonBox.getChildren().addAll(nextSceneButton);
    borderPane.setCenter(buttonBox);


    nextSceneButton.setOnAction((e) -> appWindow.startRunwayScene());
  }
}
