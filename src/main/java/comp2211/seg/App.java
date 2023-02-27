package comp2211.seg;

import comp2211.seg.Controller.Stage.AppWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App extends Application {

  /** Base resolution width */
  private final int width = 1280;//1920
  /** Base resolution height */
  private final int height = 720;//1080
  private static final Logger logger = LogManager.getLogger(App.class);
  private static App instance;
  private Stage stage;


  public static void main(String[] args) {
    logger.info("Starting");
    launch();

  }

  @Override
  public void start(Stage stage) {

    var javaVersion = SystemInfo.javaVersion();
    var javafxVersion = SystemInfo.javafxVersion();
    logger.info("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

    instance = this;
    this.stage = stage;
    //
    displayHomeView();
  }

  public void displayHomeView() {
    logger.info("Opening AppWindow");
    new AppWindow(stage, width, height);
    stage.show();

  }

  public void shutdown() {
    logger.info("Shutting down");
    System.exit(0);
  }

  public static App getInstance() {
    return instance;
  }
}
