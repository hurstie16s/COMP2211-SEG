package comp2211.seg.UiView.Scene;

import comp2211.seg.App;
import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.ProcessDataModel.Airport;
import comp2211.seg.ProcessDataModel.Runway;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A concrete implementation of the SceneAbstract class representing the home scene of the application.
 * Implements temporary Temp interface with final Strings to be edited if needed.
 */
public class HomeScene extends SceneAbstract{

  /*
   * Logger object used for logging messages.
   */
  private static final Logger logger = LogManager.getLogger(HomeScene.class);

  /**
   * The main application window for the interface.
   */
  protected AppWindow appWindow;
  /**
   * The Name entry.
   */
  protected TextField nameEntry;
  private ComboBox airports;
  private ComboBox runways;


  /**
   * Constructor to create a HomeScene object.
   *
   * @param root      the root pane of the scene
   * @param appWindow the application window of the scene
   * @param width     the width
   * @param height    the height
   */
  public HomeScene(Pane root, AppWindow appWindow, SimpleDoubleProperty width, SimpleDoubleProperty height) {
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

    nameEntry = new TextField();
    nameEntry.setPromptText("new airport name");
    nameEntry.setStyle("-fx-prompt-text-fill: gray;");
    VBox centrePane = new VBox();
    centrePane.setAlignment(Pos.CENTER);

    Text title = new Text("Runway Redeclaration Aid\n");
    title.getStyleClass().addAll("fontbig", "bold", "fg");

    Text desciption = new Text("This tool provides calculation and visual aids to\n" +
        "the runway redeclaration process\n");
    desciption.getStyleClass().addAll("font", "fg");

    airports = new ComboBox(FXCollections.observableArrayList(appWindow.getAirports()));
    airports.getStyleClass().add("focusedBG");
    airports.getStyleClass().add("font");
// Add a listener to update the runways ComboBox when a new airport is selected
    airports.valueProperty().addListener((observableValue, oldAirport, newAirport) -> {
      appWindow.setAirport((Airport) newAirport);

      // Update the options of the runways ComboBox with the runways for the selected airport
      runways.setItems(FXCollections.observableArrayList(((Airport)newAirport).getRunways()));
      // Select the first runway in the new list, if it exists
      if (!((Airport)newAirport).getRunways().isEmpty()) {
        runways.setValue(((Airport)newAirport).getRunways().get(0));
      }
    });
    // Create the runways ComboBox
    runways = new ComboBox(FXCollections.observableArrayList(appWindow.airport.getRunways()));
    runways.getStyleClass().add("focusedBG");
    runways.getStyleClass().add("font");
// Add a listener to update the selected runway in the appWindow when a new runway is selected
    runways.valueProperty().addListener(new ChangeListener() {
      @Override
      public void changed(ObservableValue observableValue, Object o, Object t1) {
        logger.info("changed runway: "+ t1);
        appWindow.setRunway((Runway) t1);
        if (! (o == null)) {
          if (!o.equals(t1)) {
            appWindow.startHomeScene();
          }
        }
      }
    });
// Set the initial value of the airports ComboBox
    //airports.setValue(appWindow.getAirports().get(0));
    airports.setValue(appWindow.airport);
// Set the initial value of the runways ComboBox
    runways.setValue(appWindow.runway);



    Button startApplication = new Button("Start Application");
    startApplication.setOnMousePressed(this::startApplication);
    Button importAirportWithObstacle = new Button("Import Airport with Obstacle");
    Button importAirportWithoutObs = new Button("Import Airport without Obstacle");
    Button newAirport = new Button("New Airport");
    //startApplication.setTextFill(Theme.fg);
    startApplication.getStyleClass().add("fg");
    //startApplication.setBackground(new Background(new BackgroundFill(Theme.focusedBG,null,null)));
    startApplication.getStyleClass().add("focusedBG");
    startApplication.getStyleClass().add("font");
    //importAirportWithObstacle.setTextFill(Theme.fg);
    importAirportWithObstacle.getStyleClass().add("fg");
    //importAirportWithObstacle.setBackground(new Background(new BackgroundFill(Theme.focusedBG,null,null)));
    importAirportWithObstacle.getStyleClass().add("focusedBG");
    importAirportWithObstacle.getStyleClass().add("font");
    importAirportWithoutObs.getStyleClass().add("focusedBG");
    importAirportWithoutObs.getStyleClass().add("font");
    //newAirport.setTextFill(Theme.fg);
    newAirport.getStyleClass().add("fg");
    //newAirport.setBackground(new Background(new BackgroundFill(Theme.focusedBG,null,null)));
    newAirport.getStyleClass().add("focusedBG");
    newAirport.getStyleClass().add("fontsmall");

    //importAirportWithObstacle.setDisable(true);
    // Import Airport
    importAirportWithObstacle.setOnAction(e -> importAirportWithObstacleButtonEvent());
    importAirportWithoutObs.setOnAction(e -> importAirportNoObsEvent());

    newAirport.setDisable(true);

    TextFlow text = new TextFlow(title,desciption);
    text.setTextAlignment(TextAlignment.CENTER);

    Label airportText = new Label("Airport");
    //airportText.setFont(Theme.font);
    airportText.getStyleClass().addAll("font", "bold");
    //airportText.setTextFill(Theme.fgBright);
    airportText.getStyleClass().add("fgBright");
    Label runwayText = new Label("Runway");
    //runwayText.setFont(Theme.font);
    runwayText.getStyleClass().addAll("font", "bold");
    //runwayText.setTextFill(Theme.fgBright);
    runwayText.getStyleClass().add("fgBright");

    GridPane buttons = new GridPane();

    buttons.addColumn(0,airportText,runwayText);
    buttons.addColumn(1,airports, runways, startApplication);
    buttons.addColumn(2,importAirportWithObstacle, importAirportWithoutObs);

    ColumnConstraints ccx = new ColumnConstraints();
    ccx.setPercentWidth(20);
    buttons.getColumnConstraints().add(ccx);
    for (int i = 0; i < 2; i++) {
      ccx = new ColumnConstraints();
      ccx.setPercentWidth(40);
      buttons.getColumnConstraints().add(ccx);
    }

    buttons.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    runways.maxWidthProperty().bind(airports.widthProperty());
    runways.minWidthProperty().bind(airports.widthProperty());
    startApplication.maxWidthProperty().bind(airports.widthProperty());
    startApplication.minWidthProperty().bind(airports.widthProperty());
    importAirportWithObstacle.maxWidth(350);
    importAirportWithObstacle.minWidth(350);
    importAirportWithoutObs.maxWidth(350);
    importAirportWithoutObs.minWidth(350);
    HBox buttonsPane = new HBox(buttons);
    VBox buttonsPane2 = new VBox(buttonsPane);

    centrePane.getChildren().addAll(text, buttonsPane2);
    buttons.setAlignment(Pos.CENTER);
    centrePane.setAlignment(Pos.CENTER);
    HBox.setHgrow(buttons,Priority.NEVER);
    buttonsPane.fillHeightProperty().set(false);
    VBox.setVgrow(buttonsPane,Priority.NEVER);
    buttonsPane2.fillWidthProperty().set(false);
    buttonsPane2.setAlignment(Pos.CENTER);

    Label projectInfo = new Label(
            "Josh Douglas " +
                    "(jod1n21) | " +
                    "Lam Giang " +
                    "(lg1n20) | " +
                    "Samuel Hurst " +
                    "(shjh1g21) | " +
                    "David Kuc " +
                    "(drk1g21) | " +
                    "Aleksander Pilski " +
                    "(ajp1e19) | " +
                    "Josh Willson " +
                    "(jjrw1g21) \n");
    projectInfo.setTextAlignment(TextAlignment.CENTER);
    //projectInfo.setFont(Theme.font);
    projectInfo.getStyleClass().add("font");
    //projectInfo.setTextFill(Theme.fg);
    projectInfo.getStyleClass().add("fg");
    HBox namesBox = new HBox(projectInfo);
    namesBox.setAlignment(Pos.CENTER);

    VBox layout = new VBox();
    layout.getChildren().addAll(centrePane,namesBox);
    namesBox.prefHeight(0);
    centrePane.maxHeightProperty().bind(mainPane.heightProperty().subtract(namesBox.heightProperty().multiply(2.5)));
    centrePane.minHeightProperty().bind(mainPane.heightProperty().subtract(namesBox.heightProperty().multiply(2.5)));

    mainPane.getChildren().add(layout);
    mainPane.maxWidthProperty().bind(root.widthProperty());
    mainPane.minWidthProperty().bind(root.widthProperty());
    mainPane.maxHeightProperty().bind(root.heightProperty());
    mainPane.minHeightProperty().bind(root.heightProperty());
  }


}
