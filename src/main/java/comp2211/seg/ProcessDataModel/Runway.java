package comp2211.seg.ProcessDataModel;

import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents a runway object, containing various properties such as length, width, and designator, as well as methods
 * for calculating takeoff and landing distances based on these properties.
 */
public class Runway {

    // logger
    private static final Logger logger = LogManager.getLogger(Runway.class);

    // Runway dimensions and properties
    private final SimpleDoubleProperty clearway = new SimpleDoubleProperty(500);
    private final SimpleDoubleProperty clearwayHeight = new SimpleDoubleProperty(150);
    private final SimpleDoubleProperty stopway = new SimpleDoubleProperty(150);
    private final SimpleDoubleProperty stripEnd = new SimpleDoubleProperty(60);
    private final SimpleDoubleProperty RESAWidth = new SimpleDoubleProperty(240);
    private final SimpleDoubleProperty RESAHeight = new SimpleDoubleProperty(90);

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
    private final SimpleDoubleProperty rightTora = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty rightToda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty rightAsda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty rightLda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty leftTora = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty leftToda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty leftAsda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty leftLda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty dispThreshold = new SimpleDoubleProperty(0);

    private Obstacle runwayObstacle = null;

    private final SimpleBooleanProperty landingMode = new SimpleBooleanProperty(true);

    private final SimpleBooleanProperty direction = new SimpleBooleanProperty(false);

    // End of Inputs



    // Typical values, may become variable down the line
    // Constants
    private static final SimpleDoubleProperty MINRESA = new SimpleDoubleProperty(240);
    private static final SimpleDoubleProperty STRIPEND = new SimpleDoubleProperty(60);
    private static final SimpleDoubleProperty BLASTZONE = new SimpleDoubleProperty(500);
    private static final SimpleDoubleProperty SLOPE = new SimpleDoubleProperty(50);

    // Outputs
    private final SimpleDoubleProperty output1 = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty output2 = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty output3 = new SimpleDoubleProperty(0);

    // Runway dimensions
    private final SimpleDoubleProperty runwayLength = new SimpleDoubleProperty(1000);
    private final SimpleDoubleProperty runwayWidth = new SimpleDoubleProperty(60);
    private final SimpleBooleanProperty hasRunwayObstacle = new SimpleBooleanProperty(false);

    /**
     * Creates a new runway object and sets up change listeners on all input properties so that takeoff and landing
     * distances are automatically recalculated whenever any input value is changed.
     */
    public Runway() {
        for (Property prop: new Property[] {
                runwayDesignator,
                dispThreshold,
                direction,
                runwayLength,
                hasRunwayObstacle
        }) {
            prop.addListener((observableValue, o, t1) -> recalculate());
        }
        logger.info("Created Runway object");
        runwayObstacle = new Obstacle("One", 0,0);
        tora.bind(runwayLength);
        toda.bind(runwayLength.add(clearway));
        asda.bind(runwayLength.add(stopway));
        lda.bind(runwayLength.subtract(dispThreshold));
        recalculate();
    }

    /**

     Adds an obstacle to the list of obstacles on the runway.
     */
    public void addObstacle() {
        hasRunwayObstacle.set(true);
        logger.info("Added Obstacle "+ runwayObstacle.getObstacleDesignator() + " to runway " + runwayDesignator.get());
    }
    /**
     * Removing the obstacle from the runway
     * @param obstacleToRemove The obstacle to remove from the runway
     */
    public void removeObstacle(Obstacle obstacleToRemove) {
        hasRunwayObstacle.set(false);

        recalculate();
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
        rightTora.bind(tora);
        rightToda.bind(toda);
        rightAsda.bind(asda);
        rightLda.bind(lda);
        leftTora.bind(tora);
        leftToda.bind(toda);
        leftAsda.bind(asda);
        leftLda.bind(lda);

        if (hasRunwayObstacle.get()) {
            if (direction.get()) {
                calculateTakeOffToward();
                calculateLandTowards();
            } else {
                calculateLandOver();
                calculateTakeOffAway();
            }
        }
    }

    /**
     Calculations for when a plane is landing over an obstacle
     */
    public void calculateLandOver() {
        /*
        Not really needed for landing calculations
        workingTora.bind(tora.subtract(BLASTZONE).subtract(runwayObstacle.distFromThresholdProperty()).subtract(dispThreshold));
        workingAsda.bind(workingTora.add(stopway));
        workingToda.bind(workingTora.add(clearway));
         */
        SimpleDoubleProperty obstacleSlopeCalculation = new SimpleDoubleProperty();
        obstacleSlopeCalculation.bind(Bindings.when(Bindings.greaterThan(runwayObstacle.heightProperty().multiply(SLOPE),MINRESA.add(runwayObstacle.widthProperty().divide(2)))).then(runwayObstacle.heightProperty().multiply(SLOPE)).otherwise(MINRESA.add(runwayObstacle.widthProperty().divide(2))));


        SimpleDoubleProperty leftLdaSubtraction = new SimpleDoubleProperty();
        SimpleDoubleProperty rightLdaSubtraction= new SimpleDoubleProperty();
        leftLdaSubtraction.bind(Bindings.when(Bindings.greaterThan(runwayObstacle.distFromThresholdProperty().add(obstacleSlopeCalculation).add(STRIPEND),BLASTZONE)).then(runwayObstacle.distFromThresholdProperty().add(obstacleSlopeCalculation).add(STRIPEND)).otherwise(BLASTZONE));
        rightLdaSubtraction.bind(Bindings.when(Bindings.greaterThan(runwayLength.subtract(runwayObstacle.distFromThresholdProperty()).add(obstacleSlopeCalculation).add(STRIPEND),BLASTZONE)).then(runwayLength.subtract(runwayObstacle.distFromThresholdProperty()).add(obstacleSlopeCalculation).add(STRIPEND)).otherwise(BLASTZONE));
        rightLda.bind(lda.subtract(leftLdaSubtraction));
        leftLda.bind(lda.subtract(rightLdaSubtraction));
    }

    /**
     Calculations for when a plane is landing towards an obstacle
     */
    public void calculateLandTowards() {
        /*
        Not really needed for landing calc
        workingTora.bind(runwayObstacle.distFromThresholdProperty().subtract(runwayObstacle.heightProperty().multiply(50)).subtract(STRIPEND.get()));
        workingAsda.bind(workingTora);
        workingToda.bind(workingTora);
         */
        leftLda.bind(runwayObstacle.distFromThresholdProperty().subtract(MINRESA).subtract(STRIPEND));
        rightLda.bind(runwayLength.subtract(runwayObstacle.distFromThresholdProperty()).subtract(MINRESA).subtract(STRIPEND));
        logger.info("Re-calculated LDA");

    }

    /**
    Calculations for when a plane is taking-off towards an obstacle
     */
    public void calculateTakeOffToward() {
        //Compare slope caused by obstacle to min RESA, so aircraft has time to stop before obstacle in case of aborted take-off
        SimpleDoubleProperty obstacleSlopeCalculation = new SimpleDoubleProperty();
        obstacleSlopeCalculation.bind(Bindings.when(Bindings.greaterThan(runwayObstacle.heightProperty().multiply(SLOPE),MINRESA.add(runwayObstacle.widthProperty().divide(2)))).then(runwayObstacle.heightProperty().multiply(SLOPE)).otherwise(MINRESA.add(runwayObstacle.widthProperty().divide(2))));
        rightTora.bind(runwayLength.subtract(runwayObstacle.distFromThresholdProperty()).subtract(obstacleSlopeCalculation).subtract(STRIPEND));
        rightAsda.bind(rightTora);
        rightToda.bind(rightTora);
        leftTora.bind(runwayObstacle.distFromThresholdProperty().subtract(obstacleSlopeCalculation).subtract(STRIPEND));
        leftAsda.bind(leftTora);
        leftToda.bind(leftTora);
        /*
        not needed for take-off
        workingLda.bind(runwayObstacle.distFromThresholdProperty().subtract(MINRESA.get()).subtract(STRIPEND.get()));
        */
    }

    /**
     Calculations for when a plane is taking-off away from an obstacle
     */
    public void calculateTakeOffAway() {
        SimpleDoubleProperty toraSubtraction = new SimpleDoubleProperty(Math.max(dispThreshold.get() + BLASTZONE.get(), STRIPEND.get() + MINRESA.get()));

        rightTora.bind(tora.subtract(runwayObstacle.distFromThresholdProperty()).subtract(toraSubtraction));
        rightAsda.bind(rightTora.add(stopway));
        rightToda.bind(rightTora.add(clearway));
        leftTora.bind(tora.subtract(runwayLength.subtract(runwayObstacle.distFromThresholdProperty())).subtract(toraSubtraction));
        leftAsda.bind(leftTora);
        leftToda.bind(leftTora);

        /*
        not needed for take-off
        workingLda.bind(lda.subtract(runwayObstacle.distFromThresholdProperty()).subtract(runwayObstacle.heightProperty().multiply(SLOPE).subtract(STRIPEND.get())));
        */
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
     * Returns the value of clearwayLeftWidth.
     * @return The value of clearwayLeftWidth.
     */
    public double getClearwayWidth() {
        return clearway.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing clearwayLeftWidth.
     * @return The SimpleDoubleProperty object representing clearwayLeftWidth.
     */
    public SimpleDoubleProperty clearwayWidthProperty() {
        return clearway;
    }
    /**
     * Returns the value of clearwayLeftHeight.
     * @return The value of clearwayLeftHeight.
     */
    public double getClearwayHeight() {
        return clearwayHeight.get();
    }
    /**
     * Returns the SimpleDoubleProperty object representing clearwayLeftHeight.
     * @return The SimpleDoubleProperty object representing clearwayLeftHeight.
     */
    public SimpleDoubleProperty clearwayHeightProperty() {
        return clearwayHeight;
    }
    /**
     * Returns the value of stopwayRight.
     * @return The value of stopwayRight.
     */


    public double getRightTora() {
        return rightTora.get();
    }

    public SimpleDoubleProperty rightToraProperty() {
        return rightTora;
    }

    public double getRightToda() {
        return rightToda.get();
    }

    public SimpleDoubleProperty rightTodaProperty() {
        return rightToda;
    }

    public double getRightAsda() {
        return rightAsda.get();
    }

    public SimpleDoubleProperty rightAsdaProperty() {
        return rightAsda;
    }

    public double getRightLda() {
        return rightLda.get();
    }

    public SimpleDoubleProperty rightLdaProperty() {
        return rightLda;
    }

    public double getLeftTora() {
        return leftTora.get();
    }

    public SimpleDoubleProperty leftToraProperty() {
        return leftTora;
    }

    public double getLeftToda() {
        return leftToda.get();
    }

    public SimpleDoubleProperty leftTodaProperty() {
        return leftToda;
    }

    public double getLeftAsda() {
        return leftAsda.get();
    }

    public SimpleDoubleProperty leftAsdaProperty() {
        return leftAsda;
    }

    public double getLeftLda() {
        return leftLda.get();
    }

    public SimpleDoubleProperty leftLdaProperty() {
        return leftLda;
    }

    public double getStopway() {
        return stopway.get();
    }

    public SimpleDoubleProperty stopwayProperty() {
        return stopway;
    }

    public double getClearway() {
        return clearway.get();
    }

    public SimpleDoubleProperty clearwayProperty() {
        return clearway;
    }
    public double getStripEnd() {
        return stripEnd.get();
    }

    public SimpleDoubleProperty stripEndProperty() {
        return stripEnd;
    }

    public double getRESAWidth() {
        return RESAWidth.get();
    }

    public SimpleDoubleProperty RESAWidthProperty() {
        return RESAWidth;
    }

    public double getRESAHeight() {
        return RESAHeight.get();
    }

    public SimpleDoubleProperty RESAHeightProperty() {
        return RESAHeight;
    }

    public boolean isHasRunwayObstacle() {
        return hasRunwayObstacle.get();
    }

    public SimpleBooleanProperty hasRunwayObstacleProperty() {
        return hasRunwayObstacle;
    }
}
