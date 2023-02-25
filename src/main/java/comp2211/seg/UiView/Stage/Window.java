package comp2211.seg.UiView.Stage;

import comp2211.seg.App;
import comp2211.seg.UiView.Scene.HomeScene;
import comp2211.seg.UiView.Scene.SceneAbstract;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Window {

  private static final Logger logger = LogManager.getLogger(Window.class);

  private final Stage stage;
  private final int width;
  private final int height;
  private SceneAbstract currentScene;
  private Scene scene;

  public Window(Stage stage, int width, int height) {
    this.stage = stage;
    this.width = width;
    this.height = height;

    // Setup window
    setupStage();
    setupDefaultScene();
    startHomeScene();
  }

  public void startHomeScene() {
    loadScene(new HomeScene(this));
  }

  private void setupResources() {
    logger.info("Loading resources"); //no resources used atm i.e. CSS/FXML
  }
  public void setupStage() {
    stage.setTitle("Window");
    stage.setMinWidth(width);
    stage.setMinHeight(height);
    stage.setOnCloseRequest(ev -> App.getInstance().shutdown());
  }

  /** Setup the default scene (an empty black scene) when no scene is loaded */
  public void setupDefaultScene() {
    this.scene = new Scene(new Pane(), width, height, Color.BLACK);
    stage.setScene(this.scene);
  }

  public void loadScene(SceneAbstract newScene) {
    // Cleanup remains of the previous scene
    // cleanup();

    // Create the new scene and set it up
    newScene.build();
    currentScene = newScene;
    scene = newScene.setScene();
    stage.setScene(scene);

    // Initialise the scene when ready
    Platform.runLater(() -> currentScene.initialise());
  }

  /**
   * Get the current scene being displayed
   *
   * @return scene
   */
  public Scene getScene() {
    return scene;
  }

  /**
   * Get the width of the Game Window
   *
   * @return width
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Get the height of the Game Window
   *
   * @return height
   */
  public int getHeight() {
    return this.height;
  }
}

