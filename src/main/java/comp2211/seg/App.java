package comp2211.seg;

import comp2211.seg.UiView.Stage.Window;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App extends Application {

  private static App instance;
  private Stage stage;
  private static final Logger logger = LogManager.getLogger(App.class);

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
    logger.info("Opening Window");
    int width = 1920;
    int height = 1080;
    new Window(stage, width, height);
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
