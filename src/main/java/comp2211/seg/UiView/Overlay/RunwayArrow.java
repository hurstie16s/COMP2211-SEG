package comp2211.seg.UiView.Overlay;

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
    protected double length;
    protected double xOffset;
    protected double yOffset;
    protected String labelText;


    protected ChangeListener<Number> translationListener;

    protected RunwayArrow(String text, Color col, SimpleDoubleProperty scale) {
        color = col;
        labelText = text;
        scaleFactor = scale;
        scaleFactor.addListener((obs, oldVal, newVal) -> setLength(length));

        buildArrowLine();
        buildArrowHead();
        buildArrowLabel();

        getChildren().addAll(arrowLine, arrowHead, arrowLabel);
    }

    protected void buildArrowLine() {
        arrowLine = new Line();
        arrowLine.setStroke(color);
        arrowLine.setStrokeWidth(strokeWidth);

    }

    protected void buildArrowHead() {
        arrowHead = new Polygon();
        arrowHead.setFill(color);
    }

    protected void buildArrowLabel() {
        arrowLabel = new Label(labelText);
        arrowLabel.setTextFill(labelColor);
    }

    protected void addTranslationListeners() {
        arrowLine.startXProperty().addListener(translationListener);
        arrowLine.endXProperty().addListener(translationListener);
        arrowLine.startYProperty().addListener(translationListener);
        arrowLine.endYProperty().addListener(translationListener);
    }


    public void setXOffset(double x) {
        xOffset = x;
        arrowLine.setStartX(xOffset * scaleFactor.get());
    }

    public void setYOffset(double y) {
        yOffset = y;
        arrowLine.setStartY(yOffset * scaleFactor.get());
        arrowLine.setEndY(yOffset * scaleFactor.get());
    }

    public void setLength(double l) {
        length = l;
        arrowLine.setEndX(arrowLine.getStartX() + length * scaleFactor.get() - headWidth);
        //* scale.get()
    }



}