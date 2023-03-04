package comp2211.seg.UiView.Scene.RunwayComponents;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class ClearedGradedArea extends Polygon {
    private Parent parent;

    private SimpleDoubleProperty left = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty leftStart = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty leftEnd = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty right = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty rightStart = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty innerHeight = new SimpleDoubleProperty(75);
    private SimpleDoubleProperty outerHeight = new SimpleDoubleProperty(105);



    private SimpleDoubleProperty rightEnd = new SimpleDoubleProperty(0);

    public ClearedGradedArea(Parent parent){
        this.parent = parent;
        for (Property prop: new Property[] {
                left,
                leftStart,
                leftEnd,
                right,
                rightStart,
                rightEnd,
                innerHeight,
                outerHeight
        }) {
            prop.addListener((observableValue, o, t1) -> redraw());
        }
        setFill(Color.BLUE);
    }
    public void redraw(){
        getPoints().removeAll(getPoints());
        getPoints().addAll(new Double[]{
                left.get(), innerHeight.get(),
                leftStart.get(), innerHeight.get(),
                leftEnd.get(), outerHeight.get(),
                rightEnd.get(), outerHeight.get(),
                rightStart.get(), innerHeight.get(),
                right.get(), innerHeight.get(),
                right.get(), -innerHeight.get(),
                rightStart.get(), -innerHeight.get(),
                rightEnd.get(), -outerHeight.get(),
                leftEnd.get(), -outerHeight.get(),
                leftStart.get(), -innerHeight.get(),
                left.get(), -innerHeight.get(),

        });

    }
    public double getInnerHeight() {
        return innerHeight.get();
    }

    public SimpleDoubleProperty innerHeightProperty() {
        return innerHeight;
    }

    public double getOuterHeight() {
        return outerHeight.get();
    }

    public SimpleDoubleProperty outerHeightProperty() {
        return outerHeight;
    }

    public double getLeft() {
        return left.get();
    }

    public SimpleDoubleProperty leftProperty() {
        return left;
    }

    public double getLeftStart() {
        return leftStart.get();
    }

    public SimpleDoubleProperty leftStartProperty() {
        return leftStart;
    }

    public double getLeftEnd() {
        return leftEnd.get();
    }

    public SimpleDoubleProperty leftEndProperty() {
        return leftEnd;
    }

    public double getRight() {
        return right.get();
    }

    public SimpleDoubleProperty rightProperty() {
        return right;
    }

    public double getRightStart() {
        return rightStart.get();
    }

    public SimpleDoubleProperty rightStartProperty() {
        return rightStart;
    }

    public double getRightEnd() {
        return rightEnd.get();
    }

    public SimpleDoubleProperty rightEndProperty() {
        return rightEnd;
    }
}
