package comp2211.seg.ProcessDataModel;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

/**
 * Represents a runway object, containing various properties such as length, width, and designator, as well as methods
 * for calculating takeoff and landing distances based on these properties.
 */
public class Runway {

    // Runway dimensions and properties
    private SimpleDoubleProperty clearwayRightWidth = new SimpleDoubleProperty(500);
    private SimpleDoubleProperty clearwayRightHeight = new SimpleDoubleProperty(150);
    private SimpleDoubleProperty clearwayLeftWidth = new SimpleDoubleProperty(500);
    private SimpleDoubleProperty clearwayLeftHeight = new SimpleDoubleProperty(150);
    private SimpleDoubleProperty stopwayRight = new SimpleDoubleProperty(150);
    private SimpleDoubleProperty stopwayLeft = new SimpleDoubleProperty(150);
    private SimpleDoubleProperty stripEndRight = new SimpleDoubleProperty(60);
    private SimpleDoubleProperty stripEndLeft = new SimpleDoubleProperty(60);
    private SimpleDoubleProperty RESARightWidth = new SimpleDoubleProperty(240);
    private SimpleDoubleProperty RESARightHeight = new SimpleDoubleProperty(90);
    private SimpleDoubleProperty RESALeftWidth = new SimpleDoubleProperty(240);
    private SimpleDoubleProperty RESALeftHeight = new SimpleDoubleProperty(90);

    //Inputs

    /*
    A runway designator consists of a two-digit number,
    which is the whole number nearest to one tenth of the magnetic North
    when viewed from the direction of approach. For example,
    if the azimuth of the centre-line is 153 then the runway designator will be 15
     */
    private final SimpleStringProperty runwayDesignator = new SimpleStringProperty("36");
    private final SimpleDoubleProperty tora = new SimpleDoubleProperty(10000);
    private final SimpleDoubleProperty toda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty asda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty lda = new SimpleDoubleProperty(800);
    private final SimpleDoubleProperty dispThreshold = new SimpleDoubleProperty(0);

    private final ArrayList<Obstacle> runwayObstacles = new ArrayList<>();

    private final SimpleBooleanProperty landing = new SimpleBooleanProperty(true);

    private final SimpleBooleanProperty direction = new SimpleBooleanProperty(true);

    // End of Inputs

    // Constants
    private static final SimpleDoubleProperty MINRESA = new SimpleDoubleProperty(240);
    private static final SimpleDoubleProperty ALSMAGNITUDE = new SimpleDoubleProperty(50);
    private static final SimpleDoubleProperty STRIPEND = new SimpleDoubleProperty(60);
    private static final SimpleDoubleProperty BLASTZONE = new SimpleDoubleProperty(500);

    // Outputs
    private SimpleDoubleProperty output1 = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty output2 = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty output3 = new SimpleDoubleProperty(0);

    // Runway dimensions
    private SimpleDoubleProperty runwayLength = new SimpleDoubleProperty(1000);
    private SimpleDoubleProperty runwayWidth = new SimpleDoubleProperty(60);

    /**
     * Creates a new runway object and sets up change listeners on all input properties so that takeoff and landing
     * distances are automatically recalculated whenever any input value is changed.
     */
    public Runway() {
        for (Property prop: new Property[] {
                runwayDesignator,
                tora,
                toda,
                asda,
                lda,
                dispThreshold,
                landing,
                direction
        }) {
            prop.addListener((observableValue, o, t1) -> recalculate());
        }
    }

    /**

     Adds an obstacle to the list of obstacles on the runway.
     @param obstacleToAdd The obstacle to add to the runway.
     */
    public void addObstacle(Obstacle obstacleToAdd) {
        runwayObstacles.add(obstacleToAdd);
// re-calculate values?
    }
    /**

     Removes an obstacle from the list of obstacles on the runway.
     @param obstacleToRemove The obstacle to remove from the runway.
     */
    public void removeObstacle(Obstacle obstacleToRemove) {
        runwayObstacles.remove(obstacleToRemove);
// re-calculate values?
    }
    /**

     Recalculates the runway values based on the landing/takeoff direction and LDA/TORA values.
     If landing direction is true, runwayLength is set to LDA and either calculateLandOver() or
     calculateLandTowards() is called based on the takeoff direction. If landing direction is false,
     runwayLength is set to TORA and either calculateTakeOffAway() or calculateTakeOffToward() is called
     based on the takeoff direction.
     */
    public void recalculate(){
        if (landing.get()){
            runwayLength.set(lda.get());
            if (direction.get()){
                calculateLandOver();
            } else {
                calculateLandTowards();
            }
        } else {
            runwayLength.set(tora.get());
            if (direction.get()){
                calculateTakeOffAway();
            } else {
                calculateTakeOffToward();
            }
        }
    }
    /**
     * Calculates the runway output value for landing towards.
     * Sets output1 to TODA.
     */
    public void calculateLandTowards() {
        output1.set(toda.get());
    }
    /**
     * Calculates the runway output value for landing over.
     * Sets output1 to TORA.
     */
    public void calculateLandOver() {
        output1.set(tora.get());
    }
    /**
     * Calculates the runway output value for takeoff toward.
     * Sets output1 to the runway designator value as a double.
     */
    public void calculateTakeOffToward() {
        output1.set(Double.parseDouble(runwayDesignator.get()));
    }
    /**
     * Calculates the runway output value for takeoff away.
     * Sets output1 to ASDA.
     */
    public void calculateTakeOffAway() {
        output1.set(asda.get());
    }




    // Getters
    /**
     * Returns the value of TORA.
     * @return The value of TORA.
     */
    public double getTora() {
        return tora.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing TORA.
     * @return The SimpleDoubleProperty object representing TORA.
     */
    public SimpleDoubleProperty toraProperty() {
        return tora;
    }
    /**
     * Returns the value of TODA.
     * @return The value of TODA.
     */
    public double getToda() {
        return toda.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing TODA.
     * @return The SimpleDoubleProperty object representing TODA.
     */
    public SimpleDoubleProperty todaProperty() {
        return toda;
    }
    /**
     * Returns the value of ASDA.
     * @return The value of ASDA.
     */
    public double getAsda() {
        return asda.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing ASDA.
     * @return The SimpleDoubleProperty object representing ASDA.
     */
    public SimpleDoubleProperty asdaProperty() {
        return asda;
    }
    /**
     * Returns the value of LDA.
     * @return The value of LDA.
     */
    public double getLda() {
        return lda.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing LDA.
     * @return The SimpleDoubleProperty object representing LDA.
     */
    public SimpleDoubleProperty ldaProperty() {
        return lda;
    }
    /**
     * Returns the value of the displaced threshold.
     * @return The value of the displaced threshold.
     */
    public double getDispThreshold() {
        return dispThreshold.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing the displaced threshold.
     * @return The SimpleDoubleProperty object representing the displaced threshold.
     */
    public SimpleDoubleProperty dispThresholdProperty() {
        return dispThreshold;
    }
    /**
     * Returns an ArrayList of obstacles on the runway.
     * @return An ArrayList of obstacles on the runway.
     */
    public ArrayList<Obstacle> getRunwayObstacles() {
        return runwayObstacles;
    }
    /**
     * Returns the value of landing.
     * @return The value of landing.
     */
    public boolean getLanding() {
        return landing.get();
    }
    /**
     * Returns the SimpleBooleanProperty object representing landing.
     * @return The SimpleBooleanProperty object representing landing.
     */
    public SimpleBooleanProperty landingProperty() {
        return landing;
    }
    /**
     * Returns the value of output1.
     * @return The value of output1.
     */
    public double getOutput1() {
        return output1.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing output1.
     * @return The SimpleDoubleProperty object representing output1.
     */
    public SimpleDoubleProperty output1Property() {
        return output1;
    }
    /**
     * Returns the value of output2.
     * @return The value of output2.
     */
    public double getOutput2() {
        return output2.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing output2.
     * @return The SimpleDoubleProperty object representing output2.
     */
    public SimpleDoubleProperty output2Property() {
        return output2;
    }
    /**
     * Returns the value of output3.
     * @return The value of output3.
     */
    public double getOutput3() {
        return output3.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing output3.
     * @return The SimpleDoubleProperty object representing output3.
     */
    public SimpleDoubleProperty output3Property() {
        return output3;
    }
    /**
     * Returns the runway designator string.
     * @return The runway designator string.
     */
    public String getRunwayDesignator() {
        return runwayDesignator.get();
    }
    /**
     * Returns the SimpleStringProperty object representing the runway designator.
     * @return The SimpleStringProperty object representing the runway designator.
     */
    public SimpleStringProperty runwayDesignatorProperty() {
        return runwayDesignator;
    }

    /**
     * Returns the value of direction.
     * @return The value of direction.
     */
    public boolean isDirection() {
        return direction.get();
    }
    /**
     * Returns the SimpleBooleanProperty object representing direction.
     * @return The SimpleBooleanProperty object representing direction.
     */
    public SimpleBooleanProperty directionProperty() {
        return direction;
    }
    /**
     * Returns the value of runwayLength.
     * @return The value of runwayLength.
     */
    public double getRunwayLength() {
        return runwayLength.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing runwayLength.
     * @return The SimpleDoubleProperty object representing runwayLength.
     */
    public SimpleDoubleProperty runwayLengthProperty() {
        return runwayLength;
    }
    /**
     * Returns the value of runwayWidth.
     * @return The value of runwayWidth.
     */
    public double getRunwayWidth() {
        return runwayWidth.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing runwayWidth.
     * @return The SimpleDoubleProperty object representing runwayWidth.
     */
    public SimpleDoubleProperty runwayWidthProperty() {
        return runwayWidth;
    }
    /**
     * Returns the value of clearwayRightWidth.
     * @return The value of clearwayRightWidth.
     */
    public double getClearwayRightWidth() {
        return clearwayRightWidth.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing clearwayRightWidth.
     * @return The SimpleDoubleProperty object representing clearwayRightWidth.
     */
    public SimpleDoubleProperty clearwayRightWidthProperty() {
        return clearwayRightWidth;
    }
    /**
     * Returns the value of clearwayRightHeight.
     * @return The value of clearwayRightHeight.
     */
    public double getClearwayRightHeight() {
        return clearwayRightHeight.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing clearwayRightHeight.
     * @return The SimpleDoubleProperty object representing clearwayRightHeight.
     */
    public SimpleDoubleProperty clearwayRightHeightProperty() {
        return clearwayRightHeight;
    }
    /**
     * Returns the value of clearwayLeftWidth.
     * @return The value of clearwayLeftWidth.
     */
    public double getClearwayLeftWidth() {
        return clearwayLeftWidth.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing clearwayLeftWidth.
     * @return The SimpleDoubleProperty object representing clearwayLeftWidth.
     */
    public SimpleDoubleProperty clearwayLeftWidthProperty() {
        return clearwayLeftWidth;
    }
    /**
     * Returns the value of clearwayLeftHeight.
     * @return The value of clearwayLeftHeight.
     */
    public double getClearwayLeftHeight() {
        return clearwayLeftHeight.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing clearwayLeftHeight.
     * @return The SimpleDoubleProperty object representing clearwayLeftHeight.
     */
    public SimpleDoubleProperty clearwayLeftHeightProperty() {
        return clearwayLeftHeight;
    }
    /**
     * Returns the value of stopwayRight.
     * @return The value of stopwayRight.
     */
    public double getStopwayRight() {
        return stopwayRight.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing stopwayRight.
     * @return The SimpleDoubleProperty object representing stopwayRight.
     */
    public SimpleDoubleProperty stopwayRightProperty() {
        return stopwayRight;
    }
    /**
     * Returns the value of stopwayLeft.
     * @return The value of stopwayLeft.
     */
    public double getStopwayLeft() {
        return stopwayLeft.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing stopwayLeft.
     * @return The SimpleDoubleProperty object representing stopwayLeft.
     */
    public SimpleDoubleProperty stopwayLeftProperty() {
        return stopwayLeft;
    }

    /**
     * Returns the value of stripEndRight.
     * @return The value of stripEndRight.
     */
    public double getStripEndRight() {
        return stripEndRight.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing stripEndRight.
     * @return The SimpleDoubleProperty object representing stripEndRight.
     */
    public SimpleDoubleProperty stripEndRightProperty() {
        return stripEndRight;
    }
    /**
     * Returns the value of stripEndLeft.
     * @return The value of stripEndLeft.
     */
    public double getStripEndLeft() {
        return stripEndLeft.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing stripEndLeft.
     * @return The SimpleDoubleProperty object representing stripEndLeft.
     */
    public SimpleDoubleProperty stripEndLeftProperty() {
        return stripEndLeft;
    }
    /**
     * Returns the value of RESARightWidth.
     * @return The value of RESARightWidth.
     */
    public double getRESARightWidth() {
        return RESARightWidth.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing RESARightWidth.
     * @return The SimpleDoubleProperty object representing RESARightWidth.
     */
    public SimpleDoubleProperty RESARightWidthProperty() {
        return RESARightWidth;
    }
    /**
     * Returns the value of RESARightHeight.
     * @return The value of RESARightHeight.
     */
    public double getRESARightHeight() {
        return RESARightHeight.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing RESARightHeight.
     * @return The SimpleDoubleProperty object representing RESARightHeight.
     */
    public SimpleDoubleProperty RESARightHeightProperty() {
        return RESARightHeight;
    }
    /**
     * Returns the value of RESALeftWidth.
     * @return The value of RESALeftWidth.
     */
    public double getRESALeftWidth() {
        return RESALeftWidth.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing RESALeftWidth.
     * @return The SimpleDoubleProperty object representing RESALeftWidth.
     */
    public SimpleDoubleProperty RESALeftWidthProperty() {
        return RESALeftWidth;
    }
    /**
     * Returns the value of RESALeftHeight.
     * @return The value of RESALeftHeight.
     */
    public double getRESALeftHeight() {
        return RESALeftHeight.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing RESALeftHeight.
     * @return The SimpleDoubleProperty object representing RESALeftHeight.
     */
    public SimpleDoubleProperty RESALeftHeightProperty() {
        return RESALeftHeight;
    }
}
