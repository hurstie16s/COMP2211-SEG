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
    private final SimpleDoubleProperty clearwayLeft = new SimpleDoubleProperty(500);
    private final SimpleDoubleProperty clearwayRight = new SimpleDoubleProperty(500);
    private final SimpleDoubleProperty clearwayHeight = new SimpleDoubleProperty(150);
    private final SimpleDoubleProperty stopwayLeft = new SimpleDoubleProperty(150);
    private final SimpleDoubleProperty stopwayRight = new SimpleDoubleProperty(150);
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
    private final SimpleDoubleProperty inputRightTora = new SimpleDoubleProperty(2000);
    private final SimpleDoubleProperty inputRightToda = new SimpleDoubleProperty(1800);
    private final SimpleDoubleProperty inputRightAsda = new SimpleDoubleProperty(1600);
    private final SimpleDoubleProperty inputRightLda = new SimpleDoubleProperty(1400);
    private final SimpleDoubleProperty inputLeftTora = new SimpleDoubleProperty(2000);
    private final SimpleDoubleProperty inputLeftToda = new SimpleDoubleProperty(1800);
    private final SimpleDoubleProperty inputLeftAsda = new SimpleDoubleProperty(1600);
    private final SimpleDoubleProperty inputLeftLda = new SimpleDoubleProperty(1400);
    private final SimpleDoubleProperty rightTora = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty rightToda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty rightAsda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty rightLda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty leftTora = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty leftToda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty leftAsda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty leftLda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty dispThresholdLeft = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty dispThresholdRight = new SimpleDoubleProperty(0);

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

    // Runway dimensions
    private final SimpleDoubleProperty runwayLength = new SimpleDoubleProperty(1000);
    private final SimpleDoubleProperty runwayWidth = new SimpleDoubleProperty(60);
    private final SimpleBooleanProperty hasRunwayObstacle = new SimpleBooleanProperty(false);
    private final SimpleStringProperty units = new SimpleStringProperty("m");

    /**
     * Creates a new runway object and sets up change listeners on all input properties so that takeoff and landing
     * distances are automatically recalculated whenever any input value is changed.
     */
    public Runway() {
        for (Property prop: new Property[] {
                runwayDesignator,
                direction,
                runwayLength,
                hasRunwayObstacle
        }) {
            prop.addListener((observableValue, o, t1) -> recalculate());
        }
        logger.info("Created Runway object");
        runwayObstacle = new Obstacle("One", 0,0);
        runwayLength.bind(Bindings.when(Bindings.greaterThan(inputLeftTora,inputRightTora)).then(inputLeftTora).otherwise(inputRightTora));
        clearwayRight.bind(inputRightToda.subtract(inputRightTora));
        stopwayRight.bind(inputRightAsda.subtract(inputRightTora));
        dispThresholdRight.bind(inputRightTora.subtract(inputRightLda));
        clearwayLeft.bind(inputLeftToda.subtract(inputLeftTora));
        stopwayLeft.bind(inputLeftAsda.subtract(inputLeftTora));
        dispThresholdLeft.bind(inputLeftTora.subtract(inputLeftLda));
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
        rightTora.bind(inputRightTora);
        rightToda.bind(inputRightToda);
        rightAsda.bind(inputRightAsda);
        rightLda.bind(inputRightLda);
        leftTora.bind(inputLeftTora);
        leftToda.bind(inputLeftToda);
        leftAsda.bind(inputLeftAsda);
        leftLda.bind(inputLeftLda);

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
        rightLda.bind(inputRightLda.subtract(leftLdaSubtraction));
        leftLda.bind(inputLeftLda.subtract(rightLdaSubtraction));
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
        SimpleDoubleProperty toraSubtractionLeft = new SimpleDoubleProperty(Math.max(dispThresholdLeft.get() + BLASTZONE.get(), STRIPEND.get() + MINRESA.get()));
        SimpleDoubleProperty toraSubtractionRight = new SimpleDoubleProperty(Math.max(dispThresholdRight.get() + BLASTZONE.get(), STRIPEND.get() + MINRESA.get()));

        rightTora.bind(inputRightTora.subtract(runwayObstacle.distFromThresholdProperty()).subtract(toraSubtractionRight));
        rightAsda.bind(rightTora.add(stopwayRight));
        rightToda.bind(rightTora.add(clearwayRight));
        leftTora.bind(inputLeftTora.subtract(runwayLength.subtract(runwayObstacle.distFromThresholdProperty())).subtract(toraSubtractionLeft));
        leftAsda.bind(leftTora);
        leftToda.bind(leftTora);

        /*
        not needed for take-off
        workingLda.bind(lda.subtract(runwayObstacle.distFromThresholdProperty()).subtract(runwayObstacle.heightProperty().multiply(SLOPE).subtract(STRIPEND.get())));
        */
    }




    // Getters

    public double getInputRightTora() {
        return inputRightTora.get();
    }

    public SimpleDoubleProperty inputRightToraProperty() {
        return inputRightTora;
    }

    public double getInputRightToda() {
        return inputRightToda.get();
    }

    public SimpleDoubleProperty inputRightTodaProperty() {
        return inputRightToda;
    }

    public double getInputRightAsda() {
        return inputRightAsda.get();
    }

    public SimpleDoubleProperty inputRightAsdaProperty() {
        return inputRightAsda;
    }

    public double getInputRightLda() {
        return inputRightLda.get();
    }

    public SimpleDoubleProperty inputRightLdaProperty() {
        return inputRightLda;
    }

    public double getInputLeftTora() {
        return inputLeftTora.get();
    }

    public SimpleDoubleProperty inputLeftToraProperty() {
        return inputLeftTora;
    }

    public double getInputLeftToda() {
        return inputLeftToda.get();
    }

    public SimpleDoubleProperty inputLeftTodaProperty() {
        return inputLeftToda;
    }

    public double getInputLeftAsda() {
        return inputLeftAsda.get();
    }

    public SimpleDoubleProperty inputLeftAsdaProperty() {
        return inputLeftAsda;
    }

    public double getInputLeftLda() {
        return inputLeftLda.get();
    }

    public SimpleDoubleProperty inputLeftLdaProperty() {
        return inputLeftLda;
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

    public String getUnits() {
        return units.get();
    }

    public SimpleStringProperty unitsProperty() {
        return units;
    }

    public double getClearwayLeft() {
        return clearwayLeft.get();
    }

    public SimpleDoubleProperty clearwayLeftProperty() {
        return clearwayLeft;
    }

    public double getClearwayRight() {
        return clearwayRight.get();
    }

    public SimpleDoubleProperty clearwayRightProperty() {
        return clearwayRight;
    }

    public double getStopwayLeft() {
        return stopwayLeft.get();
    }

    public SimpleDoubleProperty stopwayLeftProperty() {
        return stopwayLeft;
    }

    public double getStopwayRight() {
        return stopwayRight.get();
    }

    public SimpleDoubleProperty stopwayRightProperty() {
        return stopwayRight;
    }

    public double getDispThresholdLeft() {
        return dispThresholdLeft.get();
    }

    public SimpleDoubleProperty dispThresholdLeftProperty() {
        return dispThresholdLeft;
    }

    public double getDispThresholdRight() {
        return dispThresholdRight.get();
    }

    public SimpleDoubleProperty dispThresholdRightProperty() {
        return dispThresholdRight;
    }
}
