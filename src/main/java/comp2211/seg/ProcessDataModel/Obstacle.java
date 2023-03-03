package comp2211.seg.ProcessDataModel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Obstacle {

    private final SimpleStringProperty obstacleDesignator = new SimpleStringProperty();
    private final SimpleDoubleProperty height = new SimpleDoubleProperty();
    private final SimpleDoubleProperty distFromThreshold = new SimpleDoubleProperty();

    public Obstacle(String obstacleDesignator, double height, double distFromThreshold) {
        this.obstacleDesignator.set(obstacleDesignator);
        this.height.set(height);
        this.distFromThreshold.set(distFromThreshold);
    }

    //Getters
    public String getObstacleDesignator() {
        return this.obstacleDesignator.get();
    }

    public SimpleStringProperty obstacleDesignatorProperty() {
        return this.obstacleDesignator;
    }

    public double getHeight() {
        return this.height.get();
    }

    public SimpleDoubleProperty heightProperty() {
        return this.height;
    }

    public double getDistFromThreshold() {
        return this.distFromThreshold.get();
    }

    public SimpleDoubleProperty distFromThresholdProperty() {
        return this.distFromThreshold;
    }


}
