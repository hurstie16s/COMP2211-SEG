package comp2211.seg.ProcessDataModel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Obstacle {

    private final SimpleStringProperty obstacleDesignator = new SimpleStringProperty();


    private final SimpleDoubleProperty height = new SimpleDoubleProperty();
    private final SimpleDoubleProperty width = new SimpleDoubleProperty();
    private final SimpleDoubleProperty length = new SimpleDoubleProperty();

    public double getDistFromThreshold() {
        return distFromThreshold.get();
    }

    public SimpleDoubleProperty distFromThresholdProperty() {
        return distFromThreshold;
    }

    private final SimpleDoubleProperty distFromThreshold = new SimpleDoubleProperty();

    public Obstacle(String obstacleDesignator, double height, double distFromThreshold) {
        this.obstacleDesignator.set(obstacleDesignator);
        this.height.set(height);
        this.distFromThreshold.set(distFromThreshold);
    }


    public double getHeight() {
        return height.get();
    }

    public SimpleDoubleProperty heightProperty() {
        return height;
    }

    public double getWidth() {
        return width.get();
    }

    public SimpleDoubleProperty widthProperty() {
        return width;
    }

    public double getLength() {
        return length.get();
    }

    public SimpleDoubleProperty lengthProperty() {
        return length;
    }
}
