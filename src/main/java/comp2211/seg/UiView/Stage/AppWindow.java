package comp2211.seg.UiView.Stage;

import comp2211.seg.App;
import comp2211.seg.UiView.Scene.HomeScene;
import comp2211.seg.UiView.Scene.MainScene;
import comp2211.seg.UiView.Scene.RunwayScene;
import comp2211.seg.UiView.Scene.SceneAbstract;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppWindow {

  private static final Logger logger = LogManager.getLogger(AppWindow.class);

  private final Stage stage;
  private final int width;
  private final int height;
  private SceneAbstract currentScene;
  private Scene scene;

  public Pane root;

  public AppWindow(Stage stage, int width, int height) {
    this.stage = stage;
    this.width = width;
    this.height = height;

    // Setup appWindow
    setupStage();
    startMainScene();
    //startRunwayScene();
  }

  private void setupResources() {
    logger.info("Loading resources"); //no resources used atm i.e. CSS/FXML
  }
  public void setupStage() {
    stage.setTitle("AppWindow");
    stage.setMinWidth(width);
    stage.setMinHeight(height);
    stage.setOnCloseRequest(ev -> App.getInstance().shutdown());
  }

  /** Start the home scene */
  public void startHomeScene() {
    loadScene(new HomeScene(new Pane(width,height),this));
  }

  /** Start the main scene */
  public void startMainScene() {
    loadScene(new MainScene(new Pane(width,height),this));
  }
  /** Start the runway scene */
  public void startRunwayScene() {
    loadScene(new RunwayScene(new Pane(getWidth(),getHeight()),this));
  }


  public void loadScene(SceneAbstract newScene) {
    // Cleanup remains of the previous scene
    cleanup();
    // Create the new scene and set it up
    newScene.build();
    currentScene = newScene;
    stage.setScene(currentScene);

    // Initialise the scene when ready
    Platform.runLater(() -> currentScene.initialise());
  }

  /**
   * Get the current scene being displayed
   *
   * @return scene
   */
  public Stage getStage() {
    return stage;
  }

  /**
   * Get the width of the Game AppWindow
   *
   * @return width
   */
  public int getWidth() {
    return width;
  }

  /**
   * Get the height of the Game AppWindow
   *
   * @return height
   */
  public int getHeight() {
    return height;
  }
  public void cleanup(){
    //clearlisteners
    //root.getChildren().removeAll(root.getChildren());
  }
}

