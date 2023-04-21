package comp2211.seg.UiView.Scene.SceneComponents;

import comp2211.seg.Controller.Stage.Theme;
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
        setTextFill(Theme.fg);
        setFont(Theme.font);
        setPadding(new Insets(5));
    }
    public void run(){
        for (TabButton tb:tabLayout.tabButtons) {
            tb.setBackground(new Background(new BackgroundFill(tabLayout.bg,null,null)));
        }
        setBackground(new Background(new BackgroundFill(tabLayout.fg,null,null)));
        tabLayout.contents.getChildren().removeAll(tabLayout.contents.getChildren());
        tabLayout.contents.getChildren().add(tab.getValue());
    }
}
