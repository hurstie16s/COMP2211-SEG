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

}
