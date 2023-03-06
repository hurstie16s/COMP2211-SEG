package comp2211.seg.ProcessDataModel;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * Represents a runway object, containing various properties such as length, width, and designator, as well as methods
 * for calculating takeoff and landing distances based on these properties.
 */
public class Runway {

    // logger
    private static final Logger logger = LogManager.getLogger(Runway.class);

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
    followed by either L C or R to differentiate between parallel runways
     */
    private final SimpleStringProperty runwayDesignator = new SimpleStringProperty("36C");
    private final SimpleDoubleProperty tora = new SimpleDoubleProperty(2000);
    private final SimpleDoubleProperty toda = new SimpleDoubleProperty(1800);
    private final SimpleDoubleProperty asda = new SimpleDoubleProperty(1600);
    private final SimpleDoubleProperty lda = new SimpleDoubleProperty(1400);
    private final SimpleDoubleProperty workingTora = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty workingToda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty workingAsda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty workingLda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty dispThreshold = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty stopway = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty clearway = new SimpleDoubleProperty(0);

    private Obstacle runwayObstacle = null;

    private final SimpleBooleanProperty landingMode = new SimpleBooleanProperty(true);

    private final SimpleBooleanProperty direction = new SimpleBooleanProperty(false);

    // End of Inputs



    // Typical values, may become variable down the line
    // Constants
    private static final SimpleDoubleProperty MINRESA = new SimpleDoubleProperty(240);
    private static final SimpleDoubleProperty ALSMAGNITUDE = new SimpleDoubleProperty(50);
    private static final SimpleDoubleProperty STRIPEND = new SimpleDoubleProperty(60);
    private static final SimpleDoubleProperty BLASTZONE = new SimpleDoubleProperty(500);
    private static final SimpleDoubleProperty SLOPE = new SimpleDoubleProperty(50);

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
                stopway,
                clearway,
                landingMode,
                direction
        }) {
            prop.addListener((observableValue, o, t1) -> recalculate());
        }
        logger.info("Created Runway object");
    }

    /**

     Adds an obstacle to the list of obstacles on the runway.
     @param obstacleToAdd The obstacle to add to the runway.
     */
    public void addObstacle(Obstacle obstacleToAdd) {
        this.runwayObstacle = obstacleToAdd;
        logger.info("Added Obstacle "+ runwayObstacle.getObstacleDesignator() + " to runway " + runwayDesignator.get());
        recalculate();
    }
    /**
     * Removing the obstacle from the runway
     * @param obstacleToRemove The obstacle to remove from the runway
     */
    public void removeObstacle(Obstacle obstacleToRemove) {
        if (runwayObstacle == obstacleToRemove) {
            this.runwayObstacle = null;
        }
        // set values back to original
        workingTora.set(tora.get());
        workingToda.set(toda.get());
        workingAsda.set(asda.get());
        workingLda.set(lda.get());
        logger.info("Removed Obstacle "+ runwayObstacle.getObstacleDesignator() + " from runway " + runwayDesignator.get());
        logger.info("Return runway to original state");
    }
    /**

     Recalculates the runway values based on the landing/takeoff direction and LDA/TORA values.
     If landing direction is true, runwayLength is set to LDA and either calculateLandOver() or
     calculateLandTowards() is called based on the takeoff direction. If landing direction is false,
     runwayLength is set to TORA and either calculateTakeOffAway() or calculateTakeOffToward() is called
     based on the takeoff direction.
     */
    public void recalculate(){
        if (landingMode.get()){
            runwayLength.bind(lda);
            if (direction.get()){
                calculateLandOver();
            } else {
                calculateLandTowards();

            }
        } else {
            runwayLength.bind(tora);

            if (direction.get()){
                calculateTakeOffAway();

            } else {
                calculateTakeOffToward();

            }
        }
    }

    /**
     Calculations for when a plane is landing over an obstacle
     */
    public void calculateLandOver() {
        if (runwayObstacle != null) {

            workingTora.bind(tora.subtract(BLASTZONE).subtract(runwayObstacle.distFromThresholdProperty()).subtract(dispThreshold));
            workingAsda.bind(workingTora.add(stopway));
            workingToda.bind(workingTora.add(clearway));
            workingLda.bind(lda.subtract(runwayObstacle.distFromThresholdProperty()).subtract(runwayObstacle.heightProperty().multiply(SLOPE)).subtract(STRIPEND));
        } else {
            workingTora.bind(tora);
            workingAsda.bind(asda);
            workingToda.bind(toda);
            workingLda.bind(lda);
        }
    }

    /**
     Calculations for when a plane is landing towards an obstacle
     */
    public void calculateLandTowards() {
        if (runwayObstacle != null) {
            /*
            Not really needed for landing calc
            workingTora.bind(runwayObstacle.distFromThresholdProperty().subtract(runwayObstacle.heightProperty().multiply(50)).subtract(STRIPEND.get()));
            workingAsda.bind(workingTora);
            workingToda.bind(workingTora);
             */
            workingLda.bind(runwayObstacle.distFromThresholdProperty().subtract(MINRESA).subtract(STRIPEND));
        } else {
            workingLda.bind(lda);
        }
        workingTora.bind(tora);
        workingAsda.bind(asda);
        workingToda.bind(toda);
    }

    /**
    Calculations for when a plane is taking-off towards an obstacle
     */
    public void calculateTakeOffToward() {
        if (runwayObstacle != null) {

            workingTora.bind(runwayObstacle.distFromThresholdProperty().subtract(runwayObstacle.heightProperty().multiply(50)).subtract(STRIPEND.get()));
            workingAsda.bind(workingTora);
            workingToda.bind(workingTora);
            workingLda.bind(runwayObstacle.distFromThresholdProperty().subtract(MINRESA.get()).subtract(STRIPEND.get()));
        } else {
            workingTora.bind(tora);
            workingAsda.bind(asda);
            workingToda.bind(toda);
            workingLda.bind(lda);
        }
    }

    /**
     Calculations for when a plane is taking-off away from an obstacle
     */
    public void calculateTakeOffAway() {
        if (runwayObstacle != null) {

            workingTora.bind(tora.subtract(BLASTZONE).subtract(runwayObstacle.distFromThresholdProperty()).subtract(dispThreshold.get()));
            workingAsda.bind(workingTora.add(stopway));
            workingToda.bind(workingTora.add(clearway));
            workingLda.bind(lda.subtract(runwayObstacle.distFromThresholdProperty()).subtract(runwayObstacle.heightProperty().multiply(SLOPE).subtract(STRIPEND.get())));
        } else {
            workingTora.bind(tora);
            workingAsda.bind(asda);
            workingToda.bind(toda);
            workingLda.bind(lda);
        }
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

    public Obstacle getRunwayObstacle() {
        return runwayObstacle;
    }

    public boolean isLandingMode() {
        return landingMode.get();
    }

    public SimpleBooleanProperty landingModeProperty() {
        return landingMode;
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

    public double getWorkingTora() {
        return workingTora.get();
    }

    public SimpleDoubleProperty workingToraProperty() {
        return workingTora;
    }

    public double getWorkingToda() {
        return workingToda.get();
    }

    public SimpleDoubleProperty workingTodaProperty() {
        return workingToda;
    }

    public double getWorkingAsda() {
        return workingAsda.get();
    }

    public SimpleDoubleProperty workingAsdaProperty() {
        return workingAsda;
    }

    public double getWorkingLda() {
        return workingLda.get();
    }

    public SimpleDoubleProperty workingLdaProperty() {
        return workingLda;
    }
}
