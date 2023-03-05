package comp2211.seg.UiView.Overlay;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class RunwayArrow extends Group {
    protected Line arrowLine;
    protected Polygon arrowHead;
    protected Label arrowLabel;

    protected Color color = Color.RED;
    protected Color labelColor = Color.WHITE;
    protected double strokeWidth = 3;
    protected double headWidth = 4 * strokeWidth;
    protected double headHeight = 1.75 * strokeWidth;
    protected SimpleDoubleProperty scaleFactor;
    protected boolean direction;
    protected DoubleBinding length;
    protected double xOffset;
    protected double yOffset;
    public String labelText;


    protected ChangeListener<Number> translationListener;

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

    protected void buildArrowLine() {
        arrowLine = new Line();
        arrowLine.setStroke(color);
        arrowLine.setStrokeWidth(strokeWidth);
        getChildren().add(arrowLine);

    }

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

    protected void buildArrowLabel() {
        arrowLabel = new Label(labelText);
        arrowLabel.setTextFill(labelColor);
        getChildren().addAll(arrowLabel);
    }



    public void setXOffset(DoubleBinding l) {
        arrowLine.startXProperty().bind(l.multiply(scaleFactor).multiply(-0).add(1));
    }

    public void setLength(DoubleBinding l) {
        arrowLine.endXProperty().bind(l.multiply(scaleFactor).multiply(1).subtract(1));
        //* scale.get()
    }
    /* Arrows outside
    protected void buildArrowHeadRight() {
        arrowHead.getPoints().addAll(
                0.0, headHeight,
                headWidth, 0.0,
                0.0, -headHeight);
    }


    protected void buildArrowHeadLeft() {
        arrowHead.getPoints().addAll(
                0.0, headHeight,
                -headWidth, 0.0,
                0.0, -headHeight);
    }

     */
    protected void buildArrowHeadRight() {
        arrowHead.getPoints().addAll(
                -headWidth, headHeight,
                0.0, 0.0,
                -headWidth, -headHeight);
    }


    protected void buildArrowHeadLeft() {
        arrowHead.getPoints().addAll(
                headWidth, headHeight,
                0.0, 0.0,
                headWidth, -headHeight);
    }


}