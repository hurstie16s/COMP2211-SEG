package comp2211.seg.UiView.Scene.SceneComponents;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class TabButton extends Label {
    public final Pair<String, Pane> tab;
    public TabLayout tabLayout;
    public TabButton(Pair<String, Pane> tab, TabLayout tabLayout) {
        super(tab.getKey());
        this.tabLayout = tabLayout;
        this.tab = tab;
        getStyleClass().addAll(tabLayout.bg, "tabUnselected");
        setPadding(new Insets(5));
    }
    public void run(){
        for (TabButton tb:tabLayout.tabButtons) {
            tb.getStyleClass().clear();
            tb.getStyleClass().addAll(tabLayout.bg, "tabUnselected");
            tb.getStyleClass().remove("tabSelected");
        }
        getStyleClass().remove("tabUnselected");
        getStyleClass().addAll(tabLayout.fg, "tabSelected");
        tabLayout.contents.getChildren().removeAll(tabLayout.contents.getChildren());
        tabLayout.contents.getChildren().add(tab.getValue());
        tabLayout.currentContent = tab.getValue();
    }
}
