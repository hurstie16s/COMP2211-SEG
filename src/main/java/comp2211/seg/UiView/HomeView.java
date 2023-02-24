package comp2211.seg.UiView;

import comp2211.seg.App;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HomeView {

  private static final Logger logger = LogManager.getLogger(HomeView.class);

  private final Stage stage;
  //private final double width;
  //private final double height;

  public HomeView (Stage stage) { //, int width, int height) {
    this.stage = stage;
    //this.width = width;
    //this.height = height;

    // Setup window
    setupStage();
  }

  public void setupStage() {
    StackPane root = new StackPane();
    Scene scene = new Scene(root);
    stage.setScene(scene); //at the moment max size of the scene. TO DO: Abstract Scene
    stage.setTitle("HomeView");
    //stage.setMinWidth(width);
    //stage.setMinHeight(height);

    stage.setOnCloseRequest(ev -> App.getInstance().shutdown());
  }
}
