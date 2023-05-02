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


        for (int i = 0; i < getChildren().size(); i++) {
            if (i%2==0){
                if (i == 0 && getChildren().size() > 1){
                    ((Pane) getChildren().get(i)).maxWidthProperty().bind(widthProperty().subtract((getChildren().size()-1) * 5).divide((getChildren().size()+1)/2)
                            .subtract(((Divider) getChildren().get(i+1)).offset));
                    ((Pane) getChildren().get(i)).minWidthProperty().bind(widthProperty().subtract((getChildren().size()-1) * 5).divide((getChildren().size()+1)/2)
                            .subtract(((Divider) getChildren().get(i+1)).offset));
                    ((Divider) getChildren().get(i+1)).setBelow((Pane) getChildren().get(i));
                } else if (i == 0) {
                    ((Pane) getChildren().get(i)).maxWidthProperty().bind(widthProperty().subtract((getChildren().size()-1) * 5).divide((getChildren().size()+1)/2));
                    ((Pane) getChildren().get(i)).minWidthProperty().bind(widthProperty().subtract((getChildren().size()-1) * 5).divide((getChildren().size()+1)/2));
                } else if (i == getChildren().size()-1) {
                    ((Pane) getChildren().get(i)).maxWidthProperty().bind(widthProperty().subtract((getChildren().size()-1) * 5).divide((getChildren().size()+1)/2)
                            .add(((Divider) getChildren().get(i-1)).offset));
                    ((Pane) getChildren().get(i)).minWidthProperty().bind(widthProperty().subtract((getChildren().size()-1) * 5).divide((getChildren().size()+1)/2)
                            .add(((Divider) getChildren().get(i-1)).offset));

                    ((Divider) getChildren().get(i-1)).setAbove((Pane) getChildren().get(i));
                } else {
                    ((Pane) getChildren().get(i)).maxWidthProperty().bind(widthProperty().subtract((getChildren().size()-1) * 5).divide((getChildren().size()+1)/2)
                            .subtract(((Divider) getChildren().get(i+1)).offset)
                            .add(((Divider) getChildren().get(i-1)).offset));
                    ((Pane) getChildren().get(i)).minWidthProperty().bind(widthProperty().subtract((getChildren().size()-1) * 5).divide((getChildren().size()+1)/2)
                            .subtract(((Divider) getChildren().get(i+1)).offset)
                            .add(((Divider) getChildren().get(i-1)).offset));
                    ((Divider) getChildren().get(i+1)).setBelow((Pane) getChildren().get(i));
                    ((Divider) getChildren().get(i-1)).setAbove((Pane) getChildren().get(i));
                }
                ((Pane) getChildren().get(i)).maxHeightProperty().bind(heightProperty());
                ((Pane) getChildren().get(i)).minHeightProperty().bind(heightProperty());
            }
        }
    }
}