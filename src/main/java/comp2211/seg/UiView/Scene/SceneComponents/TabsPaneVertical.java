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

public class TabsPaneVertical extends  VBox{
    public TabsPaneVertical(){
        super();

        getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> change) {
                refresh();
            }
        });

        refresh();
    }
    public TabsPaneVertical(TabLayout layout, TabButton button){
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

        heightProperty().addListener(new ChangeListener<Number>() {
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
            }
            kids.add(child);
        }
        if (getChildren().size() <= index){
            kids.add(newPane);
        }

        getChildren().removeAll(getChildren());
        getChildren().addAll(kids);

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
                ((TabLayout) child).maxHeightProperty().bind(heightProperty().subtract((getChildren().size()-1) *5).divide(total));
                ((TabLayout) child).minHeightProperty().bind(heightProperty().subtract((getChildren().size()-1) *5).divide(total));

                ((TabLayout) child).maxWidthProperty().bind(widthProperty());
                ((TabLayout) child).minWidthProperty().bind(widthProperty());
            } else if ((child instanceof TabsPaneHorizontal)){
                ((TabsPaneHorizontal) child).maxHeightProperty().bind(heightProperty().subtract((getChildren().size()-1) *5).divide(total));
                ((TabsPaneHorizontal) child).minHeightProperty().bind(heightProperty().subtract((getChildren().size()-1) *5).divide(total));

                ((TabsPaneHorizontal) child).maxWidthProperty().bind(widthProperty());
                ((TabsPaneHorizontal) child).minWidthProperty().bind(widthProperty());
            } else if ((child instanceof TabsPaneVertical)){
                ((TabsPaneVertical) child).maxHeightProperty().bind(heightProperty().subtract((getChildren().size()-1) *5).divide(total));
                ((TabsPaneVertical) child).minHeightProperty().bind(heightProperty().subtract((getChildren().size()-1) *5).divide(total));

                ((TabsPaneVertical) child).maxWidthProperty().bind(widthProperty());
                ((TabsPaneVertical) child).minWidthProperty().bind(widthProperty());
            }
        }
    }
}