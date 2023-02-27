package comp2211.seg.UiView.Scene;

import comp2211.seg.App;
import comp2211.seg.Controller.Stage.AppWindow;
import comp2211.seg.Controller.Stage.Pane;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InputScene extends SceneAbstract{
  private static final Logger logger = LogManager.getLogger(InputScene.class);

  private static BorderPane borderPane;

  public AppWindow appWindow;

  public InputScene(Pane root, AppWindow appWindow) {
    super(root,appWindow);
    this.appWindow = appWindow;

  }

  @Override
  public void initialise() {
    setOnKeyPressed((keyEvent -> {
      if(keyEvent.getCode().equals(KeyCode.ESCAPE)) {
        App.getInstance().shutdown();
      }
    }));
  }
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


    var entry1 = makeTextField(inputs,"Entry 1");
    var entry2 = makeTextField(inputs,"Entry 2");
    var calculation1 = makeButton(calculations, "Calculation 1");
    var calculation2 = makeButton(calculations, "Calculation 2");
    var output1 = makeOutputLabel(outputs, "Output 1");
    var output2 = makeOutputLabel(outputs, "Output 2");
    var output3 = makeOutputLabel(outputs, "Output 3");
    output1.setText("Example output");
    output2.setText("Example output 2");
    output3.setText("Example output 3");

    mainPane.getChildren().add(layout);
  }
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
  public Button makeButton(javafx.scene.layout.Pane parent, String label){
    Button button = new Button(label);
    button.setMinWidth(width/5);
    button.setMaxWidth(width/5);
    parent.getChildren().add(button);
    return button;
  }
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