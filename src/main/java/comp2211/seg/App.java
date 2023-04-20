package comp2211.seg;

import comp2211.seg.Controller.Stage.AppWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
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
    /*The logging level determines which log messages will be recorded and which will be discarded.
    There are several logging levels available, including:
    SEVERE (highest value),WARNING,INFO,CONFIG,FINE,FINER,FINEST (lowest value).
    ALL - sets the logger to log all messages, regardless of their logging level.*/
    logger.atLevel(Level.ALL);

    instance = this;
    this.stage = stage;

    showAppWindow();
  }

    /**
     * This method creates a new instance of the {@link AppWindow} class
     * with the given width and height,using this stage as the parent.
     * The method then displays the window on the stage.
     * <p>
     * The window width and height are currently hardcoded:
     * at {@link App#width} and {@link App#height} respectively.
     */
    public void showAppWindow() {
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

