package comp2211.seg.UiView.Scene;

import comp2211.seg.App;
import comp2211.seg.Controller.Interfaces.GlobalVars;
import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.HandlerPane;
import comp2211.seg.ProcessDataModel.Airport;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A concrete implementation of the SceneAbstract class representing the home scene of the application.
 * Implements temporary Temp interface with final Strings to be edited if needed.
 */
public class HomeScene extends SceneAbstract implements GlobalVars {

  /**
   * Logger object used for logging messages.
   */
  private static final Logger logger = LogManager.getLogger(HomeScene.class);

  /**
   * The main application window for the interface.
   */
  protected AppWindow appWindow;
  protected TextField nameEntry;

  /**
   * Constructor to create a HomeScene object.
   *
   * @param root      the root pane of the scene
   * @param appWindow the application window of the scene
   */
  public HomeScene(HandlerPane root, AppWindow appWindow) {
    super(root, appWindow);
    this.appWindow = appWindow;
  }

  /**
   * Initializes the HomeScene object.
   * This method sets up the event handlers for key presses on the scene.
   */
  @Override
  public void initialise() {
    this.setOnKeyPressed((keyEvent -> {
      logger.info(keyEvent.getCode());
      if(keyEvent.getCode().equals(KeyCode.ESCAPE)) {
        App.getInstance().shutdown();
      }
      if(keyEvent.getCode().equals(KeyCode.ENTER)) {
        appWindow.addAirport(new Airport(nameEntry.getText()));
        Platform.runLater(appWindow::startMainScene);
      }
    }));
  }

  /**
   * Builds the HomeScene object.
   * This method constructs the user interface for the home scene, including text and input fields.
   * The TextField nameEntry is ignored atm.
   */
  public void build() {
    super.build();
    logger.info("building");

    Text projectInfo = new Text(SEG_INFO);
    projectInfo.setFill(Color.WHITE);
    Text entryInfo = new Text(HOME_SCENE_INFO);
    entryInfo.setFill(Color.WHITE);
    nameEntry = new TextField();
    nameEntry.setPromptText("new airport name");
    nameEntry.setStyle("-fx-prompt-text-fill: gray;");

    var entryBox = new VBox();
    entryBox.getChildren().addAll(entryInfo, nameEntry);
    BorderPane.setMargin(entryBox, new Insets(20, 500, 20, 500));
    BorderPane.setMargin(projectInfo, new Insets(20, 20, 20, 20));
    VBox.setMargin(entryInfo, new Insets(5, 5, 25, 5));
    var menuPane = new BorderPane();
    menuPane.setTop(projectInfo);
    menuPane.setCenter(entryBox);
    mainPane.getChildren().add(menuPane);
  }
}
