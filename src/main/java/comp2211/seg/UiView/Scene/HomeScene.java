package comp2211.seg.UiView.Scene;

import comp2211.seg.App;
import comp2211.seg.Controller.Interfaces.GlobalVariables;
import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.ProcessDataModel.Airport;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A concrete implementation of the SceneAbstract class representing the home scene of the application.
 * Implements temporary Temp interface with final Strings to be edited if needed.
 */
public class HomeScene extends SceneAbstract implements GlobalVariables {

  /*
   * Logger object used for logging messages.
   */
  private static final Logger logger = LogManager.getLogger(HomeScene.class);

  /**
   * The main application window for the interface.
   */
  protected AppWindow appWindow;
  protected TextField nameEntry;
  private ComboBox airports;

  /**
   * Constructor to create a HomeScene object.
   *
   * @param root      the root pane of the scene
   * @param appWindow the application window of the scene
   */
  public HomeScene(Pane root, AppWindow appWindow, double width, double height) {
    super(root, appWindow, width, height);
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
    }));
  }

  /**
   * This method starts the main application scene on the JavaFX application thread.
   *
   * @param mouseEvent The MouseEvent that triggered the method call.
   * @return void
   */
  private void startApplication(MouseEvent mouseEvent) {
    Platform.runLater(appWindow::startBaseScene);
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
    projectInfo.setFont(GlobalVariables.font);
    projectInfo.setFill(GlobalVariables.fg);
    projectInfo.wrappingWidthProperty().bind(mainPane.widthProperty());
    projectInfo.maxHeight(mainPane.heightProperty().get());
    projectInfo.minHeight(mainPane.heightProperty().get());
    Text entryInfo = new Text(HOME_SCENE_INFO);
    entryInfo.setFont(GlobalVariables.font);
    entryInfo.setFill(GlobalVariables.fg);
    nameEntry = new TextField();
    nameEntry.setPromptText("new airport name");
    nameEntry.setStyle("-fx-prompt-text-fill: gray;");

    var entryBox = new VBox();
    entryBox.getChildren().addAll(entryInfo, nameEntry);
    BorderPane.setMargin(entryBox, new Insets(20, 300, 20, 300));
    BorderPane.setMargin(projectInfo, new Insets(20, 20, 20, 20));
    VBox.setMargin(entryInfo, new Insets(5, 5, 25, 5));
    var menuPane = new BorderPane();
    menuPane.setCenter(entryBox);
    menuPane.maxWidthProperty().bind(mainPane.widthProperty());
    menuPane.minWidthProperty().bind(mainPane.widthProperty());
    menuPane.maxHeightProperty().bind(mainPane.heightProperty());
    menuPane.minHeightProperty().bind(mainPane.heightProperty());

    VBox centrePane = new VBox();
    centrePane.maxWidthProperty().bind(mainPane.widthProperty().divide(2));
    centrePane.minWidthProperty().bind(mainPane.widthProperty().divide(2));
    centrePane.maxHeightProperty().bind(mainPane.heightProperty());
    centrePane.minHeightProperty().bind(mainPane.heightProperty());
    centrePane.setAlignment(Pos.CENTER);


    Text title = new Text("Runway Redeclaration Tool\n\n");
    title.setFont(GlobalVariables.font);
    title.setFill(GlobalVariables.fg);

    Text desciption = new Text("Welcome! This runway redeclaration tool is designed to help air traffic control professionals visualise the impact of obstacles on declared distances to understand how to continue runway operations");
    desciption.setFont(GlobalVariables.font);
    desciption.setFill(GlobalVariables.fg);



    airports = new ComboBox(FXCollections.observableArrayList(appWindow.getAirports()));
    airports.setBackground(new Background(new BackgroundFill(focusedBG,null,null)));
    airports.valueProperty().addListener(new ChangeListener() {
      @Override
      public void changed(ObservableValue observableValue, Object o, Object t1) {
        appWindow.setAirport((Airport) t1);
      }
    });
    airports.valueProperty().set(appWindow.airport);
    Button startApplication = new Button("Start Application");
    startApplication.setOnMousePressed(this::startApplication);
    Button importAirport = new Button("Import Airport");
    Button newAirport = new Button("New Airport");
    startApplication.setTextFill(GlobalVariables.fg);
    startApplication.setBackground(new Background(new BackgroundFill(focusedBG,null,null)));
    importAirport.setTextFill(GlobalVariables.fg);
    importAirport.setBackground(new Background(new BackgroundFill(focusedBG,null,null)));
    newAirport.setTextFill(GlobalVariables.fg);
    newAirport.setBackground(new Background(new BackgroundFill(focusedBG,null,null)));

    importAirport.setDisable(true);
    newAirport.setDisable(true);

    TextFlow text = new TextFlow(title,desciption);
    text.setTextAlignment(TextAlignment.CENTER);

    VBox buttonsPane = new VBox(startApplication,importAirport,newAirport);
    VBox airportsPane = new VBox(airports);
    airportsPane.maxHeightProperty().bind(buttonsPane.heightProperty());
    airportsPane.minHeightProperty().bind(buttonsPane.heightProperty());

    HBox bottomPane = new HBox(airportsPane,buttonsPane);
    bottomPane.setAlignment(Pos.CENTER);
    bottomPane.setPadding(new Insets(20));
    buttonsPane.setPadding(new Insets(20));
    airportsPane.setPadding(new Insets(20));
    buttonsPane.setSpacing(10);

    centrePane.getChildren().addAll(text, bottomPane);
    mainPane.getChildren().add(projectInfo);
    mainPane.getChildren().add(centrePane);
    mainPane.maxWidthProperty().bind(root.widthProperty());
    mainPane.minWidthProperty().bind(root.widthProperty());
  }

}
