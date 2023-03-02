package comp2211.seg;

import comp2211.seg.Controller.Stage.AppWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The entry point of the application.
 */
public class App extends Application {

  /** Base resolution width */
  private final int width = 1280; // 1920
  /** Base resolution height */
  private final int height = 720; // 1080

  /** The logger instance for this class. */
  private static final Logger logger = LogManager.getLogger(App.class);

  /** The singleton instance of this class. */
  private static App instance;

  /** The main stage of the application. */
  private Stage stage;

  /**
   * The main method of the application.
   *
   * @param args The command-line arguments passed to the application.
   */
  public static void main(String[] args) {
    logger.info("Starting");
    launch();
  }

  /**
   * The start method of the JavaFX application.
   *
   * @param stage The primary stage for this application, onto which the application scene can be set.
   */
  @Override
  public void start(Stage stage) {
    var javaVersion = SystemInfo.javaVersion();
    var javafxVersion = SystemInfo.javafxVersion();
    logger.info("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

    instance = this;
    this.stage = stage;

    displayHomeView();
  }

  /**
   * Displays the home view of the application.
   */
  public void displayHomeView() {
    logger.info("Opening AppWindow");
    new AppWindow(stage, width, height);
    stage.show();
  }

  /**
   * Shuts down the application.
   */
  public void shutdown() {
    logger.info("Shutting down");
    System.exit(0);
  }

  /**
   * Gets the singleton instance of this class.
   *
   * @return The singleton instance of this class.
   */
  public static App getInstance() {
    return instance;
  }
}

