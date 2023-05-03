package comp2211.seg.ProcessDataModel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * An Obstacle object representing an obstacle on a runway.
 */
public class Obstacle {

    /**
     * The designator of the obstacle.
     */
    private final SimpleStringProperty obstacleDesignator = new SimpleStringProperty();

    /**
     * The height of the obstacle.
     */
    private final SimpleDoubleProperty height = new SimpleDoubleProperty(10);

    /**
     * The width of the obstacle.
     */
    private final SimpleDoubleProperty length = new SimpleDoubleProperty(100);

    /**
     * The length of the obstacle.
     */
    private final SimpleDoubleProperty width = new SimpleDoubleProperty(60);

    /**
     * The distance of the obstacle from the runway threshold.
     */
    private final SimpleDoubleProperty distFromThreshold = new SimpleDoubleProperty();
    /**
     * The Dist from other threshold.
     */
    private final SimpleDoubleProperty distFromOtherThreshold = new SimpleDoubleProperty();

    /**
     * Creates a new Obstacle object with the specified designator, height, and distance from the runway threshold.
     *
     * @param obstacleDesignator the designator of the obstacle
     * @param height             the height of the obstacle
     * @param distFromThreshold  the distance of the obstacle from the runway threshold
     */
    public Obstacle(String obstacleDesignator, double height, double distFromThreshold) {
        this.obstacleDesignator.set(obstacleDesignator);
        this.height.set(height);
        this.distFromThreshold.set(distFromThreshold);
    }

    public Obstacle(String obstacleDesignator, double height, double distFromThreshold, double length, double width) {
        this(obstacleDesignator, height, distFromThreshold);
        this.length.set(length);
        this.width.set(width);

    }

    /**
     * Gets obstacle designator.
     *
     * @return the obstacle designator
     */
    public String getObstacleDesignator() {
        return obstacleDesignator.get();
    }

    /**
     * Obstacle designator property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty obstacleDesignatorProperty() {
        return obstacleDesignator;
    }

    /**
     * Returns the height of the obstacle.
     *
     * @return the height of the obstacle
     */
    public double getHeight() {
        return height.get();
    }

    /**
     * Returns the SimpleDoubleProperty representing the height of the obstacle.
     *
     * @return the SimpleDoubleProperty representing the height of the obstacle
     */
    public SimpleDoubleProperty heightProperty() {
        return height;
    }

    /**
     * Returns the width of the obstacle.
     *
     * @return the width of the obstacle
     */
    public double getLength() {
        return length.get();
    }

    /**
     * Returns the SimpleDoubleProperty representing the width of the obstacle.
     *
     * @return the SimpleDoubleProperty representing the width of the obstacle
     */
    public SimpleDoubleProperty lengthProperty() {
        return length;
    }

    /**
     * Returns the length of the obstacle.
     *
     * @return the length of the obstacle
     */
    public double getWidth() {
        return width.get();
    }

    /**
     * Returns the SimpleDoubleProperty representing the length of the obstacle.
     *
     * @return the SimpleDoubleProperty representing the length of the obstacle
     */
    public SimpleDoubleProperty widthProperty() {
        return width;
    }

    /**
     * Returns the distance of the obstacle from the runway threshold.
     *
     * @return the distance of the obstacle from the runway threshold
     */
    public double getDistFromThreshold() {
        return distFromThreshold.get();
    }

    /**
     * Returns the SimpleDoubleProperty representing the distance of the obstacle from the runway threshold.
     *
     * @return the SimpleDoubleProperty representing the distance of the obstacle from the runway threshold
     */
    public SimpleDoubleProperty distFromThresholdProperty() {
        return distFromThreshold;
    }

    /**
     * Gets dist from other threshold.
     *
     * @return the dist from other threshold
     */
    public double getDistFromOtherThreshold() {
        return distFromOtherThreshold.get();
    }

    /**
     * Dist from other threshold property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty distFromOtherThresholdProperty() {
        return distFromOtherThreshold;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return obstacleDesignator.get();
    }
}
