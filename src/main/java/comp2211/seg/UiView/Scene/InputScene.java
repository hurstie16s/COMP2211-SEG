package comp2211.seg.UiView.Scene;

import comp2211.seg.App;
import comp2211.seg.Controller.Interfaces.GlobalVars;
import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.HandlerPane;
import comp2211.seg.ProcessDataModel.Obstacle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
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
  private static SimpleStringProperty units = new SimpleStringProperty("m");
  /**
   * The BorderPane object for the scene.
   */
  private static BorderPane borderPane;
  /**
   * The AppWindow object for the scene.
   */
  public AppWindow appWindow;
  public SimpleDoubleProperty height = new SimpleDoubleProperty(0);

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

    //mainPane.getStyleClass().add("home-background");
    var layout = new HBox();
    layout.setMaxWidth(width);
    layout.setMinWidth(width);
    var inputs = new VBox();
    var calculations = new VBox();
    var outputs = new VBox();
    layout.getChildren().addAll(inputs, calculations, outputs);

    makeTextField(inputs, new SimpleStringProperty("Runway Length (").concat(units).concat(")"), appWindow.runway.runwayLengthProperty());
    makeTextField(inputs, new SimpleStringProperty("Runway Designator").concat(""), appWindow.runway.runwayDesignatorProperty(), "([0-9]|0[1-9]|[1-2][0-9]|3[0-6])[lrcLRC]?");
    makeTextField(inputs, new SimpleStringProperty("Displaced Threshold (").concat(units).concat(")"), appWindow.runway.dispThresholdProperty());

    //var landingMode = makeButton(calculations, "Landing","Taking off", appWindow.runway.landingModeProperty());
    var direction = makeButton(calculations, "Towards","Away", appWindow.runway.directionProperty());

    var leftTora = makeOutputLabel(outputs, "rightTora", appWindow.runway.rightToraProperty(), new SimpleDoubleProperty(0));
    var leftToda = makeOutputLabel(outputs, "rightToda", appWindow.runway.rightTodaProperty(), appWindow.runway.clearwayProperty());
    var leftAsda = makeOutputLabel(outputs, "rightAsda", appWindow.runway.rightAsdaProperty(), appWindow.runway.stopwayProperty());
    var leftLda = makeOutputLabel(outputs, "rightLda", appWindow.runway.rightLdaProperty(), new SimpleDoubleProperty(0));
    var rightTora = makeOutputLabel(outputs, "leftTora", appWindow.runway.leftToraProperty(), new SimpleDoubleProperty(0));
    var rightToda = makeOutputLabel(outputs, "leftToda", appWindow.runway.leftTodaProperty(), appWindow.runway.clearwayProperty());
    var rightAsda = makeOutputLabel(outputs, "leftAsda", appWindow.runway.leftAsdaProperty(), appWindow.runway.stopwayProperty());
    var rightLda = makeOutputLabel(outputs, "leftLda", appWindow.runway.leftLdaProperty(), new SimpleDoubleProperty(0));


    Obstacle obstacle = new Obstacle("Test",10,700);
    obstacle.lengthProperty().bind(appWindow.runway.runwayWidthProperty());
    obstacle.widthProperty().bind(obstacle.heightProperty());
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
  public Node makeTextField(javafx.scene.layout.Pane parent, StringExpression label, SimpleStringProperty property, String regex) {
    TextField entry = new TextField();
    entry.setFont(GlobalVars.font);

    HBox segment = makeBoundingBox(label,width/3,entry);
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
  public Node makeTextField(javafx.scene.layout.Pane parent, StringExpression label, SimpleDoubleProperty property) {
    TextField entry = new TextField();
    entry.setFont(GlobalVars.font);

    HBox segment = makeBoundingBox(label,width/3,entry);
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
   * @param label1  the label to use for the first Button.
   * @param label2  the label to use for the second Button.
   * @return the created Button Node.
   */
  public Node makeButton(javafx.scene.layout.Pane parent, String label1, String label2, SimpleBooleanProperty property) {
    double w = width/(6);
    HBox segment = new HBox();
    ToggleButton button = new ToggleButton(label1);
    button.setMinWidth(w);
    button.setMaxWidth(w);
    ToggleButton button2 = new ToggleButton(label2);
    button2.setMinWidth(w);
    button2.setMaxWidth(w);
    segment.getChildren().addAll(button,button2);
    parent.getChildren().add(segment);
    button.selectedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
        if (button2.selectedProperty().get() == t1){
          button2.selectedProperty().set(!t1);
        }
        if (t1) {
          property.set(true);
        }
      }
    });
    button2.selectedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
        if (button.selectedProperty().get() == t1){
          button.selectedProperty().set(!t1);
        }
        if (t1) {
          property.set(false);
        }
      }
    });



    button.fire();
    return segment;
  }

  /**
   * Creates a new Label and a Label for displaying data with the specified label and adds them to the given parent Pane.
   *
   * @param parent the Pane to add the Labels to.
   * @param label  the label to use for the Labels.
   * @return the created Label Node for displaying data.
   */
  public Label makeOutputLabel(javafx.scene.layout.Pane parent, String label, SimpleDoubleProperty property,SimpleDoubleProperty limit) {
    Label data = new Label();
    data.setFont(GlobalVars.font);
    data.setTextFill(GlobalVars.fg);
    data.setText(String.valueOf(property.getValue()));

    HBox segment = makeBoundingBox(new SimpleStringProperty(label),width/3,data);
    parent.getChildren().add(segment);
    data.textProperty().bind(Bindings.when(Bindings.lessThan(limit,property)).then(property.asString()).otherwise(new
            SimpleStringProperty("Error")));
    return data;
  }
  public HBox makeBoundingBox(StringExpression label, double width, Node node){

    HBox segment = new HBox();
    Label title = new Label(label.getValue());
    title.textProperty().bind(label);
    title.setFont(GlobalVars.font);
    title.setTextFill(GlobalVars.fg);
    title.setMinWidth(width / 2);
    title.setMaxWidth(width / 2);
    node.minWidth(width / 2);
    node.maxWidth(width / 2);
    segment.getChildren().addAll(title,node);
    return segment;
  }
}