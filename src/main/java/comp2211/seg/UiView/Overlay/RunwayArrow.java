package comp2211.seg.UiView.Overlay;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class RunwayArrow extends Group {
    private Line arrowLine;
    private Polygon arrowHead;
    private Label arrowLabel;

    private Color color = Color.RED;
    private Color labelColor = Color.WHITE;
    private double strokeWidth = 3;
    private double headWidth = 4 * strokeWidth;
    private double headHeight = 1.75 * strokeWidth;

    public RunwayArrow(String text) {
        arrowLine = new Line();
        arrowLine.setStroke(color);
        arrowLine.setStrokeWidth(strokeWidth);

        arrowHead = new Polygon();
        // Triangle arrow head
        arrowHead.getPoints().addAll(new Double[]{0.0, headHeight, headWidth, 0.0, 0.0, -headHeight});
        arrowHead.setFill(color);
        // Move arrow head when arrow line moves
        arrowLine.endXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                arrowHead.setLayoutX(newValue.doubleValue());
                arrowLabel.setLayoutX(arrowLine.getStartX());
                arrowLabel.setLayoutY(arrowLine.getStartY() - strokeWidth - 20);
            }
        });
        arrowLine.endYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                arrowHead.setLayoutY(newValue.doubleValue());
            }
        });

        arrowLabel = new Label(text);
        arrowLabel.setTextFill(labelColor);
        getChildren().addAll(arrowLine, arrowLabel, arrowHead);
    }

    public void setXOffset(double x) {
        arrowLine.setStartX(x);
    }

    public void setLength(double l) {
        arrowLine.setEndX(arrowLine.getStartX() + l - headWidth);
    }

}