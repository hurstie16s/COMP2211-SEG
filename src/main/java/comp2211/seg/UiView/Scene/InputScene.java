package comp2211.seg.UiView.Scene;

import comp2211.seg.App;
import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.HandlerPane;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class represents the scene where user inputs data and performs calculations.
 */
public class InputScene extends SceneAbstract{
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

  /**
   * Constructor for the InputScene class.
   * @param root the root handler pane for the scene
   * @param appWindow the application window for the scene
   */
  public InputScene(HandlerPane root, AppWindow appWindow) {
    super(root,appWindow);
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
      if(keyEvent.getCode().equals(KeyCode.ESCAPE)) {
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
    layout.getChildren().addAll(inputs,calculations,outputs);

    TextField entry1 = (TextField)makeTextField(inputs,"Entry 1");
    TextField entry2 = (TextField)makeTextField(inputs,"Entry 2");
    var calculation1 = makeButton(calculations, "Calculation 1");
    var calculation2 = makeButton(calculations, "Calculation 2");
    var output1 = makeOutputLabel(outputs, "Output 1");
    var output2 = makeOutputLabel(outputs, "Output 2");
    var output3 = makeOutputLabel(outputs, "Output 3");
    output1.setText("Example output");
    output2.setText("Example output 2");
    output3.setText("Example output 3");

    calculation1.setOnAction(e -> {
      try {
        int x = Integer.parseInt(entry1.getText());
        int y = Integer.parseInt(entry2.getText());
        String result = String.valueOf((x + y));
        output1.setText(result);
      } catch (NumberFormatException numberFormatException) {
        displayErrorMessage("Error","Input must be integer");
      }
    });

    mainPane.getChildren().add(layout);
  }

  /**
   * Displays an error message dialog box with the specified title and message.
   * @param title the title of the dialog box
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
   * @param parent the Pane to add the TextField to.
   * @param label the label to use for the TextField.
   * @return the created TextField Node.
   */
public Node makeTextField(javafx.scene.layout.Pane parent, String label){
    HBox segment = new HBox();
    Label text = new Label(label);
    TextField entry = new TextField();
    text.setMinWidth(width/5);
    text.setMaxWidth(width/5);
    segment.getChildren().addAll(text,entry);
    entry.setMinWidth(width/5);
    entry.setMaxWidth(width/5);
    parent.getChildren().add(segment);
    return entry;
  }

  /**
   * Creates a new Button with the specified label and adds it to the given parent Pane.
   * @param parent the Pane to add the Button to.
   * @param label the label to use for the Button.
   * @return the created Button Node.
   */
  public Button makeButton(javafx.scene.layout.Pane parent, String label){
    Button button = new Button(label);
    button.setMinWidth(width/5);
    button.setMaxWidth(width/5);
    parent.getChildren().add(button);
    return button;
  }

  /**
   * Creates a new Label and a Label for displaying data with the specified label and adds them to the given parent Pane.
   * @param parent the Pane to add the Labels to.
   * @param label the label to use for the Labels.
   * @return the created Label Node for displaying data.
   */
  public Label makeOutputLabel(javafx.scene.layout.Pane parent, String label){
    HBox segment = new HBox();
    Label title = new Label(label);
    title.setMinWidth(width/5);
    title.setMaxWidth(width/5);
    Label data = new Label();
    data.setMinWidth(width/5);
    data.setMaxWidth(width/5);
    segment.getChildren().addAll(title,data);
    parent.getChildren().add(segment);
    return data;
  }
}