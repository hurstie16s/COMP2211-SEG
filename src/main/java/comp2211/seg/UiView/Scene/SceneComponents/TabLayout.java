package comp2211.seg.UiView.Scene.SceneComponents;

import comp2211.seg.Controller.Interfaces.GlobalVariables;
import comp2211.seg.Controller.Stage.AppWindow;
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
    public static TabButton oldTabButton;
    public final Color bg;
    public final Color fg;
    private SimpleDoubleProperty width;
    private SimpleDoubleProperty height;
    /**
     * The Tab buttons.
     */
    public ArrayList<TabButton> tabButtons = new ArrayList<TabButton>();
    public HBox topbar;
    private VBox layout;
    public StackPane contents;

    /**
     * Instantiates a new Tab layout.
     *
     * @param tabs the tabs
     * @param bg   the bg
     * @param fg   the fg
     */
    public TabLayout(ArrayList<Pair<String, Pane>> tabs, Color bg, Color fg){
        this.bg = bg;
        this.fg = fg;
        parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observableValue, Parent parent, Parent t1) {
                if (t1 != null) {
                    width.bind(((Pane) t1).widthProperty());
                    height.bind(((Pane) t1).heightProperty());
                }
            }
        });
        width = new SimpleDoubleProperty(0);
        height = new SimpleDoubleProperty(0);
        contents = new StackPane();
        topbar = new HBox();
        contents.setBackground(new Background(new BackgroundFill(fg,null,null)));
        topbar.maxWidthProperty().bind(width);
        topbar.minWidthProperty().bind(width);


        getChildren().addAll(topbar,contents);
        maxWidthProperty().bind(width);
        minWidthProperty().bind(width);


        for (Pair<String, Pane> tab: tabs) {
            makeTab(tab);
        }
        tabButtons.get(0).run();
    }

    public TabButton makeTab(Pair<String, Pane> tab) {
        TabButton tabButton = new TabButton(tab, this);
        tab.getValue().maxHeightProperty().bind(height.subtract(topbar.heightProperty()));
        tab.getValue().minHeightProperty().bind(height.subtract(topbar.heightProperty()));
        tab.getValue().maxWidthProperty().bind(width);
        tab.getValue().minWidthProperty().bind(width);
        tabButtons.add(tabButton);
        topbar.getChildren().add(tabButton);
        return tabButton;
    }

    public void removeTab(TabButton tab){
        topbar.getChildren().remove(tab);
        tabButtons.remove(tab);
        if (tabButtons.size() == 0){
            Parent p = getParent();
            Node c = this;
            while (!(p instanceof TabsPaneHorizontal || p instanceof TabsPaneVertical)){
                c = p;
                p = p.getParent();
            }
            if (p instanceof TabsPaneHorizontal) {
                ((TabsPaneHorizontal) p).remove(c);
            } else {
                ((TabsPaneVertical) p).remove(c);

            }
        }else {
            if (contents.getChildren().get(0).equals(tab.tab.getValue())){
                tabButtons.get(0).run();
            }
        }
    }
}
