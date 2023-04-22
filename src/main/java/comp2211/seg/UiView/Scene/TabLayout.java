package comp2211.seg.UiView.Scene;

import comp2211.seg.Controller.Interfaces.GlobalVariables;
import comp2211.seg.Controller.Stage.Theme;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Map;

/**
 * The type Tab layout.
 */
public class TabLayout extends VBox {
    private final String bg;
    private final String fg;
    private SimpleDoubleProperty width;
    private SimpleDoubleProperty height;
    /**
     * The Tab buttons.
     */
    public ArrayList<Button> tabButtons = new ArrayList<Button>();
    private HBox topbar;
    private VBox layout;
    private StackPane contents;

    /**
     * Instantiates a new Tab layout.
     *
     * @param tabs the tabs
     * @param bg   the bg
     * @param fg   the fg
     */
    //public TabLayout(ArrayList<Pair<String, Pane>> tabs, Color bg, Color fg){
    public TabLayout(ArrayList<Pair<String, Pane>> tabs, String bg, String fg){
        this.bg = bg;
        this.fg = fg;
        parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observableValue, Parent parent, Parent t1) {

                width.bind(((Pane) t1).widthProperty());
                height.bind(((Pane) t1).heightProperty());
            }
        });
        setPadding(new Insets(5));
        width = new SimpleDoubleProperty(0);
        height = new SimpleDoubleProperty(0);
        contents = new StackPane();
        topbar = new HBox();
        topbar.setPadding(new Insets(5,0,0,0));


        contents.maxHeightProperty().bind(height.subtract(topbar.heightProperty()).subtract(20));
        contents.minHeightProperty().bind(height.subtract(topbar.heightProperty()).subtract(20));
        //contents.setBackground(new Background(new BackgroundFill(fg,null,null)));
        contents.getStyleClass().add(fg);
        contents.maxWidthProperty().bind(width.subtract(10));
        contents.minWidthProperty().bind(width.subtract(10));
        topbar.maxWidthProperty().bind(width);
        topbar.minWidthProperty().bind(width);


        getChildren().addAll(topbar,contents);
        maxWidthProperty().bind(width);
        minWidthProperty().bind(width);


        for (Pair<String, Pane> tab: tabs) {
            Button tabButton = makeTab(tab);
            tabButtons.add(tabButton);
            topbar.getChildren().add(tabButton);
        }
        tabButtons.get(0).fire();
    }

    private Button makeTab(Pair<String, Pane> tab) {
        Button tabButton = new Button(tab.getKey());
        tab.getValue().maxHeightProperty().bind(contents.heightProperty());
        tab.getValue().minHeightProperty().bind(contents.heightProperty());
        tab.getValue().maxWidthProperty().bind(contents.widthProperty());
        tab.getValue().minWidthProperty().bind(contents.widthProperty());
        //tabButton.setTextFill(Theme.fg);
        tabButton.getStyleClass().add(fg);
        tabButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (Button tb:tabButtons) {
                    //tb.setBackground(new Background(new BackgroundFill(bg,null,null)));
                    tb.getStyleClass().add(bg);
                }
                //tabButton.setBackground(new Background(new BackgroundFill(fg,null,null)));
                tabButton.getStyleClass().add(fg);
                contents.getChildren().removeAll(contents.getChildren());
                contents.getChildren().add(tab.getValue());
            }
        });
        tabButton.setFont(Theme.font);
        return tabButton;
    }
}
