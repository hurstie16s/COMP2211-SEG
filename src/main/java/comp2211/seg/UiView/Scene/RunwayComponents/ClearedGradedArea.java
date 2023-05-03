package comp2211.seg.UiView.Scene.RunwayComponents;

import comp2211.seg.Controller.Stage.Theme;
import comp2211.seg.UiView.Scene.RunwayScene;
import comp2211.seg.UiView.Scene.Utilities.CssColorParser;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * A custom polygon representing a cleared and graded area for an airport runway.
 */
public class ClearedGradedArea extends Polygon {
    private final Parent parent;

    private final SimpleDoubleProperty left = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty leftStart = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty leftEnd = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty right = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty rightStart = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty innerHeight = new SimpleDoubleProperty(75);
    private final SimpleDoubleProperty outerHeight = new SimpleDoubleProperty(105);
    private final SimpleDoubleProperty rightEnd = new SimpleDoubleProperty(0);

    /**
     * Constructs a new instance of the ClearedGradedArea class with the specified parent.
     *
     * @param parent      the parent object of this cleared graded area
     * @param filled      the filled
     * @param runwayScene added to get stylesheet
     */
    public ClearedGradedArea(Parent parent, boolean filled, RunwayScene runwayScene){
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

        if (filled) {
            setFill(Theme.getCga());
        } else {
            setStroke(Theme.getCga());
            setFill(Color.TRANSPARENT);
            setStrokeWidth(2);
        }
    }

    /**
     * Redraws the polygon using the current property values.
     */
    public void redraw(){
        getPoints().removeAll(getPoints());
        getPoints().addAll(left.get(), innerHeight.get(),
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
            left.get(), -innerHeight.get());
    }

    /**
     * Returns the value of the inner height property.
     *
     * @return The value of the inner height property.
     */
    public double getInnerHeight() {
        return innerHeight.get();
    }

    /**
     * Returns the inner height property.
     *
     * @return The inner height property.
     */
    public SimpleDoubleProperty innerHeightProperty() {
        return innerHeight;
    }

    /**
     * Returns the value of the outer height property.
     *
     * @return The value of the outer height property.
     */
    public double getOuterHeight() {
        return outerHeight.get();
    }

    /**
     * Returns the outer height property.
     *
     * @return The outer height property.
     */
    public SimpleDoubleProperty outerHeightProperty() {
        return outerHeight;
    }

    /**
     * Returns the value of the left property.
     *
     * @return The value of the left property.
     */
    public double getLeft() {
        return left.get();
    }

    /**
     * Returns the left property.
     *
     * @return The left property.
     */
    public SimpleDoubleProperty leftProperty() {
        return left;
    }

    /**
     * Returns the value of the left start property.
     *
     * @return The value of the left start property.
     */
    public double getLeftStart() {
        return leftStart.get();
    }

    /**
     * Returns the left start property.
     *
     * @return The left start property.
     */
    public SimpleDoubleProperty leftStartProperty() {
        return leftStart;
    }

    /**
     * Returns the value of the left end property.
     *
     * @return The value of the left end property.
     */
    public double getLeftEnd() {
        return leftEnd.get();
    }

    /**
     * Returns the left end property.
     *
     * @return The left end property.
     */
    public SimpleDoubleProperty leftEndProperty() {
        return leftEnd;
    }

    /**
     * Returns the value of the right property.
     *
     * @return The value of the right property.
     */
    public double getRight() {
        return right.get();
    }

    /**
     * Returns the right property.
     *
     * @return The right property.
     */
    public SimpleDoubleProperty rightProperty() {
        return right;
    }

    /**
     * Returns the value of the right start property.
     *
     * @return The value of the right start property.
     */
    public double getRightStart() {
        return rightStart.get();
    }

    /**
     * Returns the right start property.
     *
     * @return The right start property.
     */
    public SimpleDoubleProperty rightStartProperty() {
        return rightStart;
    }

    /**
     * Returns the value of the right end property.
     *
     * @return The value of the right end property.
     */
    public double getRightEnd() {
        return rightEnd.get();
    }

    /**
     * Returns the right end property.
     *
     * @return The right end property.
     */
    public SimpleDoubleProperty rightEndProperty() {
        return rightEnd;
    }
}
