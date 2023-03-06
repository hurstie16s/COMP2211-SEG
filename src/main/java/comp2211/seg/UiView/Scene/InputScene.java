package comp2211.seg.UiView.Scene;

import comp2211.seg.App;
import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.HandlerPane;
import comp2211.seg.ProcessDataModel.Obstacle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

/**
 * This class represents the scene where user inputs data and performs calculations.
 */
public class InputScene extends SceneAbstract {
  /**
   * Logger object used for logging messages.
   */
  private static final Logger logger = LogManager.getLogger(InputScene.class);
  /**
   * The BorderPane object for the scene.
   */
  private static BorderPane borderPane;
  /**
   * The AppWindow object for the scene.
   */
  public AppWindow appWindow;

  /**
   * /**
   * Constructor for the InputScene class.
   *
   * @param root      the root handler pane for the scene
   * @param appWindow the application window for the scene
   */
  public InputScene(HandlerPane root, AppWindow appWindow) {
    super(root, appWindow);
    this.appWindow = appWindow;
  }

  /**
   * Initializes the InputScene by setting up the key event to listen
   * for the ESCAPE key press and calling the
   * shutdown() method of the App instance when it occurs.
   */
  @Override
  public void initialise() {
    setOnKeyPressed((keyEvent -> {
      if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
        App.getInstance().shutdown();
      }
    }));
  }

  /**
   * Builds the scene by setting the main pane and adding UI elements to it.
   */
  public void build() {
    super.build();
    logger.info("building");
    mainPane.getStyleClass().add("home-background");

    var layout = new HBox();
    layout.setMaxWidth(width);
    layout.setMinWidth(width);
    var inputs = new VBox();
    var calculations = new VBox();
    var outputs = new VBox();
    layout.getChildren().addAll(inputs, calculations, outputs);

    makeTextField(inputs, "Runway Designator", appWindow.runway.runwayDesignatorProperty(), "[0-9]|0[1-9]|[1-2][0-9]|3[0-6]");
    makeTextField(inputs, "TORA", appWindow.runway.toraProperty());
    makeTextField(inputs, "TODA", appWindow.runway.todaProperty());
    makeTextField(inputs, "ASDA", appWindow.runway.asdaProperty());
    makeTextField(inputs, "LDA", appWindow.runway.ldaProperty());
    makeTextField(inputs, "Display Threshold", appWindow.runway.dispThresholdProperty());

    var landingMode = makeButton(calculations, "Landing Mode", appWindow.runway.landingModeProperty());
    var direction = makeButton(calculations, "Direction", appWindow.runway.directionProperty());

    var workingTora = makeOutputLabel(outputs, "workingTora", appWindow.runway.workingToraProperty());
    var workingToda = makeOutputLabel(outputs, "workingToda", appWindow.runway.workingTodaProperty());
    var workingAsda = makeOutputLabel(outputs, "workingAsda", appWindow.runway.workingAsdaProperty());
    var workingLda = makeOutputLabel(outputs, "workingLda", appWindow.runway.workingLdaProperty());


    Obstacle obstacle = new Obstacle("Test",10,300);
    obstacle.widthProperty().set(30);
    obstacle.lengthProperty().set(40);
    appWindow.runway.addObstacle(obstacle);

    mainPane.getChildren().add(layout);
  }

  /**
   * Displays an error message dialog box with the specified title and message.
   *
   * @param title   the title of the dialog box
   * @param message the message to display in the dialog box
   */
  private void displayErrorMessage(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Creates a new TextField with the specified label and adds it to the given parent Pane.
   *
   * @param parent the Pane to add the TextField to.
   * @param label  the label to use for the TextField.
   * @return the created TextField Node.
   */
  public Node makeTextField(javafx.scene.layout.Pane parent, String label, SimpleStringProperty property, String regex) {
    HBox segment = new HBox();
    Label text = new Label(label);
    TextField entry = new TextField();
    text.setMinWidth(width / 5);
    text.setMaxWidth(width / 5);
    segment.getChildren().addAll(text, entry);
    entry.setMinWidth(width / 5);
    entry.setMaxWidth(width / 5);
    parent.getChildren().add(segment);
    entry.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        if (t1.matches("|0")) {
          property.set("36");
        } else {
          if (t1.matches(regex)) {
            property.set(t1);
          } else {
            displayErrorMessage("Invalid Entry", label + " must match " + regex);
            entry.setText(s);
          }
        }
      }
    });
    property.addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        entry.setText(t1);
      }
    });
    entry.setText(property.getValue());
    return entry;
  }

  /**
   * Creates a new TextField with the specified label and adds it to the given parent Pane.
   *
   * @param parent the Pane to add the TextField to.
   * @param label  the label to use for the TextField.
   * @return the created TextField Node.
   */
  public Node makeTextField(javafx.scene.layout.Pane parent, String label, SimpleDoubleProperty property) {
    HBox segment = new HBox();
    Label text = new Label(label);
    TextField entry = new TextField();
    text.setMinWidth(width / 5);
    text.setMaxWidth(width / 5);
    segment.getChildren().addAll(text, entry);
    entry.setMinWidth(width / 5);
    entry.setMaxWidth(width / 5);
    parent.getChildren().add(segment);
    entry.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        if (Objects.equals(t1, "")) {
          property.set(0);
        } else {
          try {
            property.set(Double.parseDouble(t1));
          } catch (Exception e) {
            displayErrorMessage("Invalid Entry", label + " must be a number");
            entry.setText(s);
          }
        }
      }
    });
    property.addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
        entry.setText(Double.toString(t1.doubleValue()));
      }
    });
    entry.setText(property.getValue().toString());
    return entry;
  }

  /**
   * Creates a new Button with the specified label and adds it to the given parent Pane.
   *
   * @param parent the Pane to add the Button to.
   * @param label  the label to use for the Button.
   * @return the created Button Node.
   */
  public ToggleButton makeButton(javafx.scene.layout.Pane parent, String label, SimpleBooleanProperty property) {
    ToggleButton button = new ToggleButton(label);
    button.setMinWidth(width / 5);
    button.setMaxWidth(width / 5);
    parent.getChildren().add(button);
    property.bindBidirectional(button.selectedProperty());

    return button;
  }

  /**
   * Creates a new Label and a Label for displaying data with the specified label and adds them to the given parent Pane.
   *
   * @param parent the Pane to add the Labels to.
   * @param label  the label to use for the Labels.
   * @return the created Label Node for displaying data.
   */
  public Label makeOutputLabel(javafx.scene.layout.Pane parent, String label, SimpleDoubleProperty property) {
    HBox segment = new HBox();
    Label title = new Label(label);
    title.setMinWidth(width / 5);
    title.setMaxWidth(width / 5);
    Label data = new Label();
    data.setMinWidth(width / 5);
    data.setMaxWidth(width / 5);
    data.setText(String.valueOf(property.getValue()));
    segment.getChildren().addAll(title, data);
    parent.getChildren().add(segment);
    property.addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
        data.setText(String.valueOf(property.getValue()));
      }
    });
    return data;
  }
}