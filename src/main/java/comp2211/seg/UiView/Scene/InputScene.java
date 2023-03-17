package comp2211.seg.UiView.Scene;

import comp2211.seg.App;
import comp2211.seg.Controller.Interfaces.GlobalVariables;
import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.Theme;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
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
  private static SimpleStringProperty units = new SimpleStringProperty();
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
  public InputScene(Pane root, AppWindow appWindow, double width, double height) {
    super(root, appWindow, width, height);
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
    units.bind(appWindow.runway.unitsProperty());

    var layout = new BorderPane();
    //var layout = new HBox();
    root.widthProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
        layout.setMaxWidth((Double) t1);
        layout.setMinWidth((Double) t1);

      }
    });
    layout.setMaxWidth(width);
    layout.setMinWidth(width);
    var inputs = new VBox();
    var calculationsButtons = new HBox();
    var outputs = new VBox();
    var inputsOutputsBox = new HBox();
    inputsOutputsBox.getChildren().addAll(inputs, outputs);
    layout.setTop(calculationsButtons);
    layout.setCenter(inputsOutputsBox);

    makeTextField(inputs, new SimpleStringProperty(
        "TORA (").concat(units).concat(")"),
        //left direction
        appWindow.runway.inputLeftToraProperty(),
        //right direction
        appWindow.runway.inputRightToraProperty());
    makeTextField(inputs, new SimpleStringProperty("TODA (").concat(units).concat(")"), appWindow.runway.inputLeftTodaProperty(), appWindow.runway.inputRightTodaProperty());
    makeTextField(inputs, new SimpleStringProperty("ASDA (").concat(units).concat(")"), appWindow.runway.inputLeftAsdaProperty(), appWindow.runway.inputRightAsdaProperty());
    makeTextField(inputs, new SimpleStringProperty("LDA (").concat(units).concat(")"), appWindow.runway.inputLeftLdaProperty(), appWindow.runway.inputRightLdaProperty());
    makeTextField(inputs, new SimpleStringProperty("Runway Designator").concat(""), appWindow.runway.runwayDesignatorLeftProperty(), "([0-9]|0[1-9]|[1-2][0-9]|3[0-6])[lrcLRC]?");
    makeTextField(inputs, new SimpleStringProperty("Obstacle width (").concat(units).concat(")"), appWindow.runway.getRunwayObstacle().widthProperty());
    makeTextField(inputs, new SimpleStringProperty("Obstacle length (").concat(units).concat(")"), appWindow.runway.getRunwayObstacle().lengthProperty());
    makeTextField(inputs, new SimpleStringProperty("Obstacle height (").concat(units).concat(")"), appWindow.runway.getRunwayObstacle().heightProperty());
    makeTextField(inputs, new SimpleStringProperty("Obstacle displacement (").concat(units).concat(")"), appWindow.runway.getRunwayObstacle().distFromThresholdProperty());

    //var landingMode = makeButton(calculationsButtons, "Landing","Taking off", appWindow.runway.landingModeProperty());
    makeButton(calculationsButtons, "Towards","Away", appWindow.runway.directionProperty());
    makeButton(calculationsButtons, "Obstacle","Clear", appWindow.runway.hasRunwayObstacleProperty());

    makeOutputLabel(outputs, "Top Tora", appWindow.runway.leftToraProperty(), new SimpleDoubleProperty(0), appWindow.runway.leftTakeOffProperty());
    makeOutputLabel(outputs, "Top Toda", appWindow.runway.leftTodaProperty(), appWindow.runway.clearwayRightProperty(), appWindow.runway.leftTakeOffProperty());
    makeOutputLabel(outputs, "Top Lda", appWindow.runway.leftLdaProperty(), new SimpleDoubleProperty(0), appWindow.runway.leftLandProperty());
    makeOutputLabel(outputs, "Top Asda", appWindow.runway.leftAsdaProperty(), appWindow.runway.stopwayRightProperty(), appWindow.runway.leftTakeOffProperty());
    makeOutputLabel(outputs, "Bottom Tora", appWindow.runway.rightToraProperty(), new SimpleDoubleProperty(0), appWindow.runway.rightTakeOffProperty());
    makeOutputLabel(outputs, "Bottom Toda", appWindow.runway.rightTodaProperty(), appWindow.runway.clearwayLeftProperty(), appWindow.runway.rightTakeOffProperty());
    makeOutputLabel(outputs, "Bottom Lda", appWindow.runway.rightLdaProperty(), new SimpleDoubleProperty(0), appWindow.runway.rightLandProperty());
    makeOutputLabel(outputs, "Bottom Asda", appWindow.runway.rightAsdaProperty(), appWindow.runway.stopwayLeftProperty(), appWindow.runway.rightTakeOffProperty());

    root.getChildren().add(layout);
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
    //remove autofocus
    entry.setFocusTraversable(false);
    entry.setFont(Theme.font);

    HBox segment = makeBoundingBoxAlt(label,root.widthProperty().divide(2),entry);
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
    entry.setFocusTraversable(false);
    entry.setFont(Theme.font);

    HBox segment = makeBoundingBoxAlt(label,root.widthProperty().divide(2),entry);
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
  }/**
   * Creates a new TextField with the specified label and adds it to the given parent Pane.
   *
   * @param parent the Pane to add the TextField to.
   * @param label  the label to use for the TextField.
   * @return the created TextField Node.
   */
  public Node makeTextField(javafx.scene.layout.Pane parent, StringExpression label, SimpleDoubleProperty property, SimpleDoubleProperty property2) {
    TextField entry = new TextField();
    entry.setFocusTraversable(false);
    entry.setFont(Theme.font);
    TextField entry2 = new TextField();
    entry2.setFocusTraversable(false);
    entry2.setFont(Theme.font);
    GridPane entries = new GridPane();
    entries.addColumn(0,entry);
    entries.addColumn(1,entry2);

    HBox segment = makeBoundingBox(label,root.widthProperty().divide(2),entries);
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
    entry2.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        if (Objects.equals(t1, "")) {
          property2.set(0);
        } else {
          try {
            property2.set(Double.parseDouble(t1));
          } catch (Exception e) {
            displayErrorMessage("Invalid Entry", label + " must be a number");
            entry2.setText(s);
          }
        }
      }
    });
    property2.addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
        entry2.setText(Double.toString(t1.doubleValue()));
      }
    });
    entry2.setText(property2.getValue().toString());
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
    DoubleBinding w = root.widthProperty().divide(5);
    HBox segment = new HBox();
    ToggleButton button = new ToggleButton(label1);
    button.minWidthProperty().bind(w);
    button.maxWidthProperty().bind(w);
    ToggleButton button2 = new ToggleButton(label2);
    button2.minWidthProperty().bind(w);
    button2.maxWidthProperty().bind(w);
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
    button.selectedProperty().set(property.get());
    button2.selectedProperty().set(!property.get());
    return segment;
  }

  /**
   * Creates a new Label and a Label for displaying data with the specified label and adds them to the given parent Pane.
   *
   * @param parent the Pane to add the Labels to.
   * @param label  the label to use for the Labels.
   * @return the created Label Node for displaying data.
   */
  public Label makeOutputLabel(javafx.scene.layout.Pane parent, String label, SimpleDoubleProperty property,SimpleDoubleProperty limit, SimpleBooleanProperty visibility) {
    Label data = new Label();
    data.setFont(Theme.font);
    data.setTextFill(Theme.fg);
    data.setPadding(new Insets(5,10,5,10));
    data.setText(String.valueOf(property.getValue()));

    HBox segment = makeBoundingBox(new SimpleStringProperty(label),root.widthProperty().divide(2),data);
    parent.getChildren().add(segment);
    data.textProperty().bind(Bindings.when(Bindings.and(visibility, Bindings.lessThan(limit,property))).then(property.asString().concat(units)).otherwise(new
            SimpleStringProperty("Error")));
    return data;
  }
  public HBox makeBoundingBox(StringExpression label, DoubleBinding width, Node node){

    HBox segment = new HBox();
    Label title = new Label(label.getValue());
    title.textProperty().bind(label);
    title.setFont(Theme.font);
    title.setTextFill(Theme.fg);
    title.setPadding(new Insets(5,10,5,10));
    title.setMaxWidth(root.widthProperty().get() / 10);
    title.setMinWidth(root.widthProperty().get() / 10);
    segment.minWidthProperty().bind(width);
    segment.maxWidthProperty().bind(width);
    segment.getChildren().addAll(title,node);
    return segment;
  }
  public HBox makeBoundingBoxAlt(StringExpression label, DoubleBinding width, Node node){

    HBox segment = new HBox();
    Label title = new Label(label.getValue());
    title.textProperty().bind(label);
    title.setFont(Theme.font);
    title.setTextFill(Theme.fg);
    title.setPadding(new Insets(5,10,5,10));
    title.setMaxWidth(root.widthProperty().get() / 5);
    title.setMinWidth(root.widthProperty().get() / 5);
    segment.minWidthProperty().bind(width);
    segment.maxWidthProperty().bind(width);
    segment.getChildren().addAll(title,node);
    return segment;
  }
}