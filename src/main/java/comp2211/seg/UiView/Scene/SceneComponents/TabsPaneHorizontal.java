package comp2211.seg.UiView.Scene.SceneComponents;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collection;

public class TabsPaneHorizontal extends  HBox{
    public TabsPaneHorizontal(){
        super();

        getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> change) {
                refresh();
            }
        });

        refresh();
    }
    public TabsPaneHorizontal(TabLayout layout, TabButton button){
        super();
        getChildren().add(layout);
        new Divider(this);
        ArrayList pairs = new ArrayList<Pair<String,Pane>>();
        pairs.add(button.tab);
        getChildren().add(new TabLayout(pairs,layout.bg,layout.fg));
        getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> change) {
                refresh();
            }
        });

        widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                refresh();
            }
        });

        refresh();
    }
    public void replace(int index, Pane newPane){
        ArrayList<Node> kids = new ArrayList<>();
        for (Node child:getChildren()) {
            if (getChildren().indexOf(child) == index){
                kids.add(newPane);
            } if (!(child instanceof Divider)){
                kids.add(child);
            }
        }
        getChildren().removeAll(getChildren());
        for (Node kid:kids) {
            getChildren().add(kid);
            if (kids.indexOf(kid) < kids.size()){
                new Divider(this);
            }
        }
    }
    public void remove(Node c){
        getChildren().remove(c);

        ArrayList<Node> kids = new ArrayList<>();
        for (Node child:getChildren()) {
            if (!(child instanceof Divider)){
                kids.add(child);
            }
        }
        getChildren().removeAll(getChildren());
        for (Node kid:kids) {
            getChildren().add(kid);
            if (kids.indexOf(kid) < kids.size()){
                new Divider(this);
            }
        }
    }

    public void add(Node c){
        if (getChildren().size() > 0){
            new Divider(this);
        }
        getChildren().add(c);
    }
    public void refresh(){

        double totalWidth = 0;
        double maxWidth = widthProperty().get();
        for (Node child: getChildren()) {
            if (!(child instanceof Divider)){
                totalWidth += ((Pane) child).getWidth();
            }else {
                maxWidth -= 5;
            }
        }
        for (Node child: getChildren()) {
            if (!(child instanceof Divider)){
                totalWidth += ((Pane) child).getWidth();
            }else {
                maxWidth -= 5;
            }
        }
        rebalance();
    }
    public void rebalance(){
        int total = 0;
        for (Node child: getChildren()){

            if (!(child instanceof Divider)){
                total += 1;
            }
        }
        for (Node child: getChildren()){
            if (child instanceof TabLayout){
                ((TabLayout) child).maxWidthProperty().bind(widthProperty().divide(total));
                ((TabLayout) child).minWidthProperty().bind(widthProperty().divide(total));

                ((TabLayout) child).maxHeightProperty().bind(heightProperty());
                ((TabLayout) child).minHeightProperty().bind(heightProperty());
            } else if ((child instanceof TabsPaneVertical)){
                ((TabsPaneVertical) child).maxWidthProperty().bind(widthProperty().divide(total));
                ((TabsPaneVertical) child).minWidthProperty().bind(widthProperty().divide(total));

                ((TabsPaneVertical) child).maxHeightProperty().bind(heightProperty());
                ((TabsPaneVertical) child).minHeightProperty().bind(heightProperty());
            } else if ((child instanceof TabsPaneHorizontal)){
                ((TabsPaneHorizontal) child).maxWidthProperty().bind(widthProperty().divide(total));
                ((TabsPaneHorizontal) child).minWidthProperty().bind(widthProperty().divide(total));

                ((TabsPaneHorizontal) child).maxHeightProperty().bind(heightProperty());
                ((TabsPaneHorizontal) child).minHeightProperty().bind(heightProperty());
            }
        }
    }
}