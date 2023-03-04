package comp2211.seg.UiView.Overlay;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;

public class RunwayArrowRight extends RunwayArrow {

    public RunwayArrowRight(String text, Color color, SimpleDoubleProperty scale, double xOffset, double yOffset, double length) {
        this(text, color, scale);
        setXOffset(xOffset);
        setYOffset(yOffset);
        setLength(length);
    }
    public RunwayArrowRight(String text, Color col, SimpleDoubleProperty scale) {
        super(text, col, scale);
        translationListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
                arrowHead.setLayoutX(arrowLine.getEndX());
                arrowHead.setLayoutY(arrowLine.getEndY());
                arrowLabel.setLayoutX(arrowLine.getStartX());
                arrowLabel.setLayoutY(arrowLine.getStartY());
            }
        };

        addTranslationListeners();
    }

    protected void buildArrowHead() {
        super.buildArrowHead();
        arrowHead.getPoints().addAll(
                0.0, headHeight,
                headWidth, 0.0,
                0.0, -headHeight);
    }

}
