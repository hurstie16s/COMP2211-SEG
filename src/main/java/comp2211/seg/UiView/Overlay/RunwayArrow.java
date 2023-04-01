package comp2211.seg.UiView.Overlay;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/**
 * The RunwayArrow class represents an arrow that can be used to indicate the direction and length of a runway.
 * The arrow consists of a line, a polygon for the arrowhead, and an optional label.
 */
public class RunwayArrow extends Group {
    /**
     * The Arrow line.
     */
    protected Line arrowLine;
    /**
     * The Arrow head.
     */
    protected Polygon arrowHead;
    /**
     * The Arrow label.
     */
    protected Label arrowLabel;

    /**
     * The Color.
     */
    protected Color color = Color.RED;
    /**
     * The Label color.
     */
    protected Color labelColor = Color.WHITE;
    /**
     * The Stroke width.
     */
    protected double strokeWidth = 3;
    /**
     * The Head width.
     */
    protected double headWidth = 4 * strokeWidth;
    /**
     * The Head height.
     */
    protected double headHeight = 1.75 * strokeWidth;
    /**
     * The Scale factor.
     */
    protected SimpleDoubleProperty scaleFactor;
    /**
     * The Direction.
     */
    protected boolean direction;
    /**
     * The Length.
     */
    protected DoubleBinding length;
    /**
     * The X offset.
     */
    protected double xOffset;
    /**
     * The Y offset.
     */
    protected double yOffset;
    /**
     * The Label text.
     */
    public String labelText;


    /**
     * The Translation listener.
     */
    protected ChangeListener<Number> translationListener;

    /**
     * Constructs a new RunwayArrow with the specified color, scale factor, length, and direction.
     *
     * @param col       The color of the arrow.
     * @param scale     The scale factor that determines the size of the arrow.
     * @param length    The length of the arrow line.
     * @param direction The direction of the arrow.
     */
    protected RunwayArrow(Color col, SimpleDoubleProperty scale, DoubleBinding length, boolean direction) {
        color = col;
        scaleFactor = scale;
        scaleFactor.addListener((obs, oldVal, newVal) -> setLength(length));
        this.direction = direction;

        buildArrowLine();
        buildArrowHead();
        setLength(length);
        setXOffset(length);
    }

    /**
     * Builds the arrow line.
     */
    protected void buildArrowLine() {
        arrowLine = new Line();
        arrowLine.setStroke(color);
        arrowLine.setStrokeWidth(strokeWidth);
        getChildren().add(arrowLine);

    }

    /**
     * Builds the arrowhead polygon.
     */
    protected void buildArrowHead() {
        arrowHead = new Polygon();
        arrowHead.setFill(color);
        getChildren().add(arrowHead);
        if (direction){
            buildArrowHeadRight();
        } else {
            buildArrowHeadLeft();
        }
    }

    /**
     * Builds the arrow label.
     */
    protected void buildArrowLabel() {
        arrowLabel = new Label(labelText);
        arrowLabel.setTextFill(labelColor);
        getChildren().addAll(arrowLabel);
    }

    /**
     * Sets the x-offset of the arrow.
     *
     * @param l The length of the arrow line.
     */
    public void setXOffset(DoubleBinding l) {
        if (direction) {
            arrowLine.startXProperty().bind(l.multiply(scaleFactor).multiply(-0).subtract(strokeWidth));
        } else {
            arrowLine.startXProperty().bind(l.multiply(scaleFactor).multiply(-0).add(strokeWidth));

        }
    }

    /**
     * Sets the length of the arrow line.
     *
     * @param l The length of the arrow line.
     */
    public void setLength(DoubleBinding l) {
        if (direction) {
            arrowLine.endXProperty().bind(l.multiply(scaleFactor).add(strokeWidth*0.5));
        } else {
            arrowLine.endXProperty().bind(l.multiply(scaleFactor).subtract(strokeWidth*0.5));

        }
        //* scale.get()
    }

    /**
     * Builds the arrowhead for a right-facing arrow.
     */
    protected void buildArrowHeadRight() {
        arrowHead.getPoints().addAll(
            -headWidth, headHeight,
            0.0, 0.0,
            -headWidth, -headHeight);
    }

    /**
     * Builds the arrowhead for a left-facing arrow.
     */
    protected void buildArrowHeadLeft() {
        arrowHead.getPoints().addAll(
            headWidth, headHeight,
            0.0, 0.0,
            headWidth, -headHeight);
    }
}



