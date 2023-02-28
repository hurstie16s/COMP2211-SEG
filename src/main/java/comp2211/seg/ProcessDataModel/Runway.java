package comp2211.seg.ProcessDataModel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Runway {
    //Inputs

    /*
    A runway designator consists of a two-digit number,
    which is the whole number nearest to one tenth of the magnetic North
    when viewed from the direction of approach. For example,
    if the azimuth of the centre-line is 153 then the runway designator will be 15
     */
    private final SimpleStringProperty runwayDesignator = new SimpleStringProperty();
    private final SimpleDoubleProperty tora = new SimpleDoubleProperty();
    private final SimpleDoubleProperty toda = new SimpleDoubleProperty();
    private final SimpleDoubleProperty asda = new SimpleDoubleProperty();
    private final SimpleDoubleProperty lda = new SimpleDoubleProperty();
    private final SimpleDoubleProperty dispThreshold = new SimpleDoubleProperty();

    private final ArrayList<Obstacle> runwayObstacles = new ArrayList<>();

    // End of Inputs

    // Typical values, may become variable down the line
    // Constants
    private static final SimpleDoubleProperty MINRESA = new SimpleDoubleProperty(240);
    private static final SimpleDoubleProperty ALSMAGNITUDE = new SimpleDoubleProperty(50);
    private static final SimpleDoubleProperty STRIPEND = new SimpleDoubleProperty(60);
    private static final SimpleDoubleProperty BLASTZONE = new SimpleDoubleProperty(500);

    //Outputs
    private SimpleDoubleProperty output1 = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty output2 = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty output3 = new SimpleDoubleProperty(0);

    public Runway(String runwayDesignator, double tora, double toda, double asda, double lda, double dispThreshold) {
        this.runwayDesignator.set(runwayDesignator);
        this.tora.set(tora);
        this.toda.set(toda);
        this.asda.set(asda);
        this.lda.set(lda);
        this.dispThreshold.set(dispThreshold);
    }

    public void addObstacle(Obstacle obstacleToAdd) {
        runwayObstacles.add(obstacleToAdd);
        // re-calculate values?
    }

    public void removeObstacle(Obstacle obstacleToRemove) {
        runwayObstacles.remove(obstacleToRemove);
        // re-calculate values?
    }

    public void calculateLandOver() {}

    public void calculateLandTowards() {}

    public void calculateTakeOffToward() {}

    public void calculateTakeOffAway() {}

    // Setters
    // Getters
}
