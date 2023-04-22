package comp2211.seg.UiView.Scene.SceneComponents;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.Parent;
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
        System.out.println(getChildren().size());

        ArrayList<Node> kids = new ArrayList<>();
        for (Node child:getChildren()) {
            if (getChildren().indexOf(child) == index){
                kids.add(newPane);
            }
            kids.add(child);
        }
        if (getChildren().size()  <= index){
            kids.add(newPane);
        }
        System.out.println(getChildren().size());
        getChildren().removeAll(getChildren());
        getChildren().addAll(kids);
        System.out.println(getChildren().size());

        //reDivide();
    }
    public void remove(Node c){
        int index = getChildren().indexOf(c);
        getChildren().remove(c);
        if (index >1){
            if (getChildren().get(index-1) instanceof Divider){
                getChildren().remove(index-1);
            }
        }
        if (getChildren().size() == 0){
            Parent parent = getParent();
            if ((parent instanceof TabsPaneHorizontal)){
                ((TabsPaneHorizontal) parent).remove(this);
            } else if ((parent instanceof TabsPaneVertical)){
                ((TabsPaneVertical) parent).remove(this);
            }
        }
        reDivide();

    }
    public void reDivide(){

        ArrayList<Node> kids = new ArrayList<>();
        for (int i = 0; i < getChildren().size()-1; i++) {
            if (getChildren().get(i) instanceof Divider && getChildren().get(i+1) instanceof Divider){
                kids.add(getChildren().get(i));
            }
        }
        getChildren().removeAll(kids);
        refresh();
    }

    public void add(Node c){
        if (getChildren().size() > 0){
            new Divider(this);
        }
        getChildren().add(c);
        reDivide();
    }
    public void refresh(){
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
                ((TabLayout) child).maxWidthProperty().bind(widthProperty().subtract((getChildren().size()-1) * 5).divide(total));
                ((TabLayout) child).minWidthProperty().bind(widthProperty().subtract((getChildren().size()-1) * 5).divide(total));

                ((TabLayout) child).maxHeightProperty().bind(heightProperty());
                ((TabLayout) child).minHeightProperty().bind(heightProperty());
            } else if ((child instanceof TabsPaneVertical)){
                ((TabsPaneVertical) child).maxWidthProperty().bind(widthProperty().subtract((getChildren().size()-1) * 5).divide(total));
                ((TabsPaneVertical) child).minWidthProperty().bind(widthProperty().subtract((getChildren().size()-1) * 5).divide(total));

                ((TabsPaneVertical) child).maxHeightProperty().bind(heightProperty());
                ((TabsPaneVertical) child).minHeightProperty().bind(heightProperty());
            } else if ((child instanceof TabsPaneHorizontal)){
                ((TabsPaneHorizontal) child).maxWidthProperty().bind(widthProperty().subtract((getChildren().size()-1) * 5).divide(total));
                ((TabsPaneHorizontal) child).minWidthProperty().bind(widthProperty().subtract((getChildren().size()-1) * 5).divide(total));

                ((TabsPaneHorizontal) child).maxHeightProperty().bind(heightProperty());
                ((TabsPaneHorizontal) child).minHeightProperty().bind(heightProperty());
            }
        }
    }
}