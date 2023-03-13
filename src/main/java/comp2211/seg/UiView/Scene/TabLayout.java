package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Interfaces.GlobalVariables;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Map;

public class TabLayout extends VBox {
    private final DoubleBinding width;
    private final DoubleBinding height;
    private ArrayList<Button> tabButtons = new ArrayList<Button>();
    private HBox topbar;
    private VBox layout;
    private StackPane contents;
    public TabLayout(Map<String, Pane> tabs, DoubleBinding height, DoubleBinding width){
        this.width = width;
        this.height = height;
        contents = new StackPane();
        topbar = new HBox();

        contents.maxHeightProperty().bind(height.subtract(topbar.heightProperty()));
        contents.minHeightProperty().bind(height.subtract(topbar.heightProperty()));
        contents.setBackground(new Background(new BackgroundFill(GlobalVariables.focusedBG,null,null)));
        contents.maxWidthProperty().bind(width);
        contents.minWidthProperty().bind(width);
        topbar.maxWidthProperty().bind(width);
        topbar.minWidthProperty().bind(width);
        topbar.setPadding(new Insets(10,0,0,0));


        getChildren().addAll(topbar,contents);
        maxWidthProperty().bind(width);
        minWidthProperty().bind(width);


        for (Map.Entry<String, Pane> tab: tabs.entrySet()) {
            Button tabButton = makeTab(tab);
            tabButtons.add(tabButton);
            topbar.getChildren().add(tabButton);
        }
        tabButtons.get(0).fire();
    }

    private Button makeTab(Map.Entry<String, Pane> tab) {
        Button tabButton = new Button(tab.getKey());
        tabButton.setTextFill(GlobalVariables.fg);
        tabButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (Button tb:tabButtons) {
                    tb.setBackground(new Background(new BackgroundFill(GlobalVariables.unfocusedBG,null,null)));
                }
                tabButton.setBackground(new Background(new BackgroundFill(GlobalVariables.focusedBG,null,null)));
                contents.getChildren().removeAll(contents.getChildren());
                contents.getChildren().add(tab.getValue());
            }
        });
        return tabButton;
    }
}
