package comp2211.seg.ProcessDataModel;

import comp2211.seg.Controller.Stage.Theme;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;



import java.util.ArrayList;

/**
 * Represents a runway object, containing various properties such as length, width, and designator, as well as methods
 * for calculating takeoff and landing distances based on these properties.
 */
public class Runway extends RunwayValues{

    /**
     * The constant logger.
     */
// logger
    private static final Logger logger = LogManager.getLogger(Runway.class);

    // Class Variables stored in runway values


    // TODO: Create header breakdowns to better explain what the numbers are

    /**
     * Instantiates a new Runway.
     *
     * @param designators the designators
     * @param leftTora    the left tora
     * @param leftToda    the left toda
     * @param leftLDA     the left lda
     * @param leftASDA    the left asda
     * @param rightTora   the right tora
     * @param rightToda   the right toda
     * @param rightLDA    the right lda
     * @param rightASDA   the right asda
     */
    public Runway(String designators, double leftTora, double leftToda, double leftLDA, double leftASDA, double rightTora, double rightToda, double rightLDA, double rightASDA){
        try {
            runwayDesignatorLeft.set(designators.split("/")[0]);
            runwayDesignatorRight.set(designators.split("/")[1]);
        } catch (Exception e) {
            runwayDesignatorLeft.set(designators);
            runwayDesignatorRight.set(calculateRunwayDesignator(designators, true));
            dualDirectionRunway.set(false);
        }
        inputLeftTora.set(leftTora);
        inputLeftToda.set(leftToda);
        inputLeftLda.set(leftLDA);
        inputLeftAsda.set(leftASDA);
        inputRightTora.set(rightTora);
        inputRightToda.set(rightToda);
        inputRightLda.set(rightLDA);
        inputRightAsda.set(rightASDA);


        for (Property prop: new Property[] {
                runwayDesignatorLeft,
                runwayLength,
                hasRunwayObstacle,
                directionLeft,
                directionRight
        }) {
            prop.addListener((observableValue, o, t1) -> recalculate());
        }
        logger.info("Created Runway object");

        logger.info("Created initial VOID runway obstacle");

        runwayLength.bind(Bindings.when(Bindings.greaterThan(inputLeftTora,inputRightTora)).then(inputLeftTora).otherwise(inputRightTora));
        runwayObstacle = new Obstacle("VOID", 5,runwayLength.get()/2);
        clearwayLeft.bind(inputRightToda.subtract(inputRightTora));
        stopwayLeft.bind(inputRightAsda.subtract(inputRightTora));
        dispThresholdRight.bind(inputRightTora.subtract(inputRightLda));
        clearwayRight.bind(inputLeftToda.subtract(inputLeftTora));
        stopwayRight.bind(inputLeftAsda.subtract(inputLeftTora));
        dispThresholdLeft.bind(inputLeftTora.subtract(inputLeftLda));
        //direction.bind(runwayObstacle.distFromThresholdProperty().greaterThan(runwayObstacle.distFromOtherThresholdProperty()));
        slopeLength.bind(
                Bindings.when(
                        runwayObstacle.heightProperty()
                                .multiply(SLOPE)
                                .subtract(
                                        runwayObstacle.lengthProperty().divide(2)
                                ).greaterThan(
                                        runwayObstacle.lengthProperty().divide(2)
                                                .add(240)
                                )
                ).then(
                        runwayObstacle.heightProperty()
                                .multiply(SLOPE)
                                .subtract(
                                        runwayObstacle.lengthProperty().divide(2)
                                )
                ).otherwise(
                        runwayObstacle.lengthProperty().divide(2)
                                .add(240)
                )
        );
        //runwayObstacle.distFromOtherThresholdProperty().bind(runwayLength.subtract(runwayObstacle.distFromThresholdProperty()));

        runwayDesignatorLeft.addListener((observableValue, s, t1) -> runwayDesignatorRight.set(calculateRunwayDesignator(runwayDesignatorLeft.get(), true)));
        runwayDesignatorRight.addListener((observableValue, s, t1) -> runwayDesignatorLeft.set(calculateRunwayDesignator(runwayDesignatorRight.get(), false)));

        runwayObstacle.distFromOtherThresholdProperty().bind(inputLeftTora.subtract(dispThresholdLeft).subtract(runwayObstacle.distFromThresholdProperty()));


        recalculate();
        validityChecks();
        logChange("Runway Initialised", Boolean.FALSE);
    }public Runway(String designator, double leftTora, double leftToda, double leftLDA, double leftASDA){

        runwayDesignatorLeft.set(designator);
        runwayDesignatorRight.set("  ");
        inputLeftTora.set(leftTora);
        inputLeftToda.set(leftToda);
        inputLeftLda.set(leftLDA);
        inputLeftAsda.set(leftASDA);
        inputRightTora.set(0);
        inputRightToda.set(0);
        inputRightLda.set(0);
        inputRightAsda.set(0);
        dualDirectionRunway.set(false);


        for (Property prop: new Property[] {
                runwayDesignatorLeft,
                runwayLength,
                hasRunwayObstacle,
                directionLeft,
                directionRight
        }) {
            prop.addListener((observableValue, o, t1) -> recalculate());
        }
        logger.info("Created Runway object");

        logger.info("Created initial VOID runway obstacle");

        runwayLength.bind(Bindings.when(Bindings.greaterThan(inputLeftTora,inputRightTora)).then(inputLeftTora).otherwise(inputRightTora));
        runwayObstacle = new Obstacle("VOID", 5,runwayLength.get()/2);
        clearwayLeft.bind(inputRightToda.subtract(inputRightTora));
        stopwayLeft.bind(inputRightAsda.subtract(inputRightTora));
        dispThresholdRight.bind(inputRightTora.subtract(inputRightLda));
        clearwayRight.bind(inputLeftToda.subtract(inputLeftTora));
        stopwayRight.bind(inputLeftAsda.subtract(inputLeftTora));
        dispThresholdLeft.bind(inputLeftTora.subtract(inputLeftLda));
        //direction.bind(runwayObstacle.distFromThresholdProperty().greaterThan(runwayObstacle.distFromOtherThresholdProperty()));
        slopeLength.bind(Bindings.when(runwayObstacle.heightProperty().multiply(SLOPE).subtract(runwayObstacle.lengthProperty().divide(2)).greaterThan(runwayObstacle.lengthProperty().divide(2).add(240))).then(runwayObstacle.heightProperty().multiply(SLOPE).subtract(runwayObstacle.lengthProperty().divide(2))).otherwise(runwayObstacle.lengthProperty().divide(2).add(240)));
        //runwayObstacle.distFromOtherThresholdProperty().bind(runwayLength.subtract(runwayObstacle.distFromThresholdProperty()));

        runwayDesignatorLeft.addListener((observableValue, s, t1) -> runwayDesignatorRight.set(calculateRunwayDesignator(runwayDesignatorLeft.get(), true)));
        runwayDesignatorRight.addListener((observableValue, s, t1) -> runwayDesignatorLeft.set(calculateRunwayDesignator(runwayDesignatorRight.get(), false)));

        runwayObstacle.distFromOtherThresholdProperty().bind(inputLeftTora.subtract(dispThresholdLeft).subtract(runwayObstacle.distFromThresholdProperty()));

        recalculate();
        validityChecks();
        logChange("Runway Initialised", Boolean.FALSE);
    }

    /**
     * Creates a new runway object and sets up change listeners on all input properties so that takeoff and landing
     * distances are automatically recalculated whenever any input value is changed.
     */
    public Runway() {
        for (Property prop: new Property[] {
                runwayDesignatorLeft,
                runwayLength,
                hasRunwayObstacle,
                directionLeft,
                directionRight
        }) {
            prop.addListener((observableValue, o, t1) -> recalculate());
        }
        logger.info("Created Runway object");

        logger.info("Created initial VOID runway obstacle");

        runwayLength.bind(Bindings.when(Bindings.greaterThan(inputLeftTora,inputRightTora)).then(inputLeftTora).otherwise(inputRightTora));
        runwayObstacle = new Obstacle("VOID", 5,runwayLength.get()/2);
        clearwayLeft.bind(inputRightToda.subtract(inputRightTora));
        stopwayLeft.bind(inputRightAsda.subtract(inputRightTora));
        dispThresholdRight.bind(inputRightTora.subtract(inputRightLda));
        clearwayRight.bind(inputLeftToda.subtract(inputLeftTora));
        stopwayRight.bind(inputLeftAsda.subtract(inputLeftTora));
        dispThresholdLeft.bind(inputLeftTora.subtract(inputLeftLda));
        //direction.bind(runwayObstacle.distFromThresholdProperty().greaterThan(runwayObstacle.distFromOtherThresholdProperty()));
        slopeLength.bind(
                Bindings.when(
                        runwayObstacle.heightProperty()
                                .multiply(SLOPE)
                                .subtract(runwayObstacle.lengthProperty()
                                        .divide(2))
                                .greaterThan(
                                        runwayObstacle.lengthProperty()
                                                .divide(2)
                                                .add(240)))
                        .then(
                                runwayObstacle.heightProperty()
                                        .multiply(SLOPE)
                                        .subtract(runwayObstacle.lengthProperty()
                                                .divide(2)))
                        .otherwise(
                                runwayObstacle.lengthProperty()
                                        .divide(2)
                                        .add(240)
                        )
        );
        //runwayObstacle.distFromOtherThresholdProperty().bind(runwayLength.subtract(runwayObstacle.distFromThresholdProperty()));

        runwayObstacle.distFromOtherThresholdProperty().bind(inputLeftTora.subtract(dispThresholdLeft).subtract(runwayObstacle.distFromThresholdProperty()));

        runwayDesignatorLeft.addListener((observableValue, s, t1) -> runwayDesignatorRight.set(calculateRunwayDesignator(runwayDesignatorLeft.get(), true)));
        runwayDesignatorRight.addListener((observableValue, s, t1) -> runwayDesignatorLeft.set(calculateRunwayDesignator(runwayDesignatorRight.get(), false)));


        recalculate();
        validityChecks();

    }

    /**
     * To string string.
     *
     * @return the string
     */
    public String toString(){
        String designators;
        if (dualDirectionRunway.get()) {
            designators = runwayDesignatorLeft.concat("/").concat(runwayDesignatorRight).get();
        } else {
            designators = runwayDesignatorLeft.get();
        }
        return designators;
    }

    /**
     * Calculates the runway designator for the runway in the opposite direction
     *
     * @param designator The designator that has been changes, used to calculate the new designator for the runways complement direction
     * @param left       the left
     * @return the string
     */
    public String calculateRunwayDesignator(String designator, boolean left) {
        if (dualDirectionRunway.get()) {
            String newDesignator;
            try {
                var number = String.valueOf((Integer.parseInt(designator.substring(0, 2)) + 18) % 36);
                if (number.length() == 1) {
                    number = "0" + number;
                }
                var character = designator.substring(2);

                var newCharacter = "";
                switch (character) {
                    case "R":
                        newCharacter = "L";
                        break;
                    case "L":
                        newCharacter = "R";
                        break;
                    case "C":
                        newCharacter = "C";
                        break;
                    case "":
                        break;
                    default: {
                        //newCharacter = "ERROR";
                        logger.error("Incorrect initial character");
                        return "ERROR";
                    }
                }

                newDesignator = number + newCharacter;

                logger.info("Runway Designators: " + designator + ", " + newDesignator);

                if ((Integer.parseInt(number) < 18 && left) || (Integer.parseInt(number) > 18 && !left)) {
                    try {
                        // This will cascade and correct the other designator
                        return designator;
                    } finally {
                        // TODO : Swap left and right
                        swapLeftRight();
                    }
                }
            } catch (NumberFormatException e) {
                logger.warn(e.getMessage());
                newDesignator = "ERROR";
            }

            return newDesignator;
        } else if (!left){
            return runwayDesignatorLeft.get();
        } else {
            return " ";
        }
    }

    public static void main(String[] args) {
        var runway = new Runway("07", 4000, 5000, 3500, 4500, 4000, 5000, 3500, 4500);
        //System.out.println(runway.calculateRunwayDesignator("07", true));
        System.out.println(runway.toString());
        System.out.println(runway.runwayDesignatorRight);
    }

    /**
     * Swap left right.
     */
    private void swapLeftRight() {

        // Swap all inputs, call recalculate
        SimpleDoubleProperty temp;

        // TORA
        temp = inputLeftTora;
        inputLeftTora = inputRightTora;
        inputRightTora = temp;
        // TODA
        temp = inputLeftToda;
        inputLeftToda = inputRightToda;
        inputRightToda = temp;
        // ASDA
        temp = inputLeftAsda;
        inputLeftAsda = inputRightAsda;
        inputRightAsda = temp;
        // LDA
        temp = inputLeftLda;
        inputLeftLda = inputRightLda;
        inputRightLda = temp;
        //dispthreshold
        temp = dispThresholdLeft;
        dispThresholdLeft = dispThresholdRight;
        dispThresholdRight = temp;
        // clearway
        temp = clearwayLeft;
        clearwayLeft = clearwayRight;
        clearwayRight = temp;
        //stopway
        temp = stopwayLeft;
        stopwayLeft = stopwayRight;
        stopwayRight = temp;
        recalculate();
    }


    /**
     * Adds an obstacle to the list of obstacles on the runway.
     *
     * @param obstacleToAdd the obstacle to add
     */
    public void addObstacle(Obstacle obstacleToAdd) {
        runwayObstacle.obstacleDesignatorProperty().set(obstacleToAdd.getObstacleDesignator());
        runwayObstacle.heightProperty().set(obstacleToAdd.heightProperty().get());
        runwayObstacle.lengthProperty().set(obstacleToAdd.lengthProperty().get());
        runwayObstacle.widthProperty().set(obstacleToAdd.widthProperty().get());
        //runwayObstacle.distFromThresholdProperty().set(obstacleToAdd.distFromThresholdProperty().get());
        runwayObstacle.distFromThresholdProperty().set(runwayLength.get() / 2);
        //Aleks - ^ replaced with default setting which place obstacle in the middle.

        //hasRunwayObstacle.set(true); // Listener will call recalculate
        //Aleks - ^ removed as it is not needed at all here. Visibility is triggered by yes/no button.
        logger.info("Added Obstacle " + runwayObstacle.getObstacleDesignator() + " to runway " + runwayDesignatorLeft.get());
        if ((runwayObstacle.getObstacleDesignator().length() >= 2) && (runwayObstacle.getObstacleDesignator().startsWith("ob"))) {
            logChange("Added Obstacle " + runwayObstacle.getObstacleDesignator() + " to runway " + runwayDesignatorLeft.get(), Boolean.FALSE);
        } else {
            logChange("Added Obstacle " + runwayObstacle.getObstacleDesignator() + " to runway " + runwayDesignatorLeft.get(), Boolean.TRUE);
        }
        addObstacleListeners();
    }

    /**
     * Adding listeners to Obstacle
     */
    public void addObstacleListeners() {
        runwayObstacle.distFromThresholdProperty().addListener((obs, oldDist, newDist) -> {
            logChange("Obstacle distances from thresholds updated; left: " + (newDist.intValue() - dispThresholdLeft.intValue())  + " right: " + runwayObstacle.distFromOtherThresholdProperty().intValue(), Boolean.FALSE);
        });
        runwayObstacle.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            logger.info("Obstacle height updated");
            logChange("Obstacle height updated to " + newHeight, Boolean.FALSE);
        });
        runwayObstacle.lengthProperty().addListener((obs, oldLength, newLength) -> {
            logChange("Obstacle length updated to " + newLength, Boolean.FALSE);
        });
        runwayObstacle.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            logChange("Obstacle width updated to " + newWidth, Boolean.FALSE);
        });
    }

    /**
     * Removing the obstacle from the runway
     */
    public void removeObstacle() {
        hasRunwayObstacle.set(false);
        logger.info("Removed Obstacle from runway " + runwayDesignatorLeft.get());
        logger.info("Return runway to original state");
        logChange("Removed Obstacle from runway " + runwayDesignatorLeft.get(), Boolean.TRUE);
    }

    /**
     * Recalculates runway values based on objects distance from the left hand threshold
     */
    public void recalculate(){

        validityChecks();
        logger.info("Recalculating runway values");
        rightTora.bind(inputRightTora);
        rightToda.bind(inputRightToda);
        rightAsda.bind(inputRightAsda);
        rightLda.bind(inputRightLda);
        leftTora.bind(inputLeftTora);
        leftToda.bind(inputLeftToda);
        leftAsda.bind(inputLeftAsda);
        leftLda.bind(inputLeftLda);

        if (hasRunwayObstacle.get()) {
            logger.info("Runway has obstacle: calculation methods will be called");
            String change = ("Runway " + this + " recalculated; take-off/landing " + (directionLeft.get() ? "towards" : "away") + " for top, take-off/landing " + (directionRight.get() ? "away" : "towards") + " for bottom");
            logChange(change, Boolean.TRUE);
            if (directionLeft.get()) {
                logger.info("Calculate take-off towards for left");
                //logChange("Calculate take-off towards for left", Boolean.TRUE);
                RunwayCalculations.calculateTakeOffTowardLeft(this); // done
                RunwayCalculations.calculateLandTowardLeft(this); // done
            } else {
                logger.info("Calculate take-off away for left");
                //logChange("Calculate take-off away for left", Boolean.TRUE);
                RunwayCalculations.calculateTakeOffAwayLeft(this); // done
                RunwayCalculations.calculateLandOverLeft(this); // done
            }
            if (directionRight.get()) {
                logger.info("Calculate land towards for right");
                //logChange("Calculate land towards for right", Boolean.TRUE);
                RunwayCalculations.calculateTakeOffTowardRight(this); // done
                RunwayCalculations.calculateTakeOffAwayRight(this); // done
                RunwayCalculations.calculateLandOverRight(this); // done
            } else {
                logger.info("Calculate land over for right");
                //logChange("Calculate land over for right", Boolean.TRUE);
                RunwayCalculations.calculateTakeOffTowardRight(this); // done
                RunwayCalculations.calculateLandTowardRight(this); // done
            }
        } else {
            logger.info("Runway has no obstacle: runway returned to original state");
            logChange("Runway obstacle inactive", Boolean.FALSE);
        }
    }

    /**
     * Validity checks.
     */
    public void validityChecks(){
        // Bindings.greaterThan(greater,lesser) = true;
        leftTakeOff.bind(Bindings.and(Bindings.greaterThanOrEqual(inputLeftAsda,inputLeftTora),Bindings.greaterThanOrEqual(inputLeftToda,leftAsda)));
        leftLand.bind(Bindings.and(Bindings.greaterThanOrEqual(inputLeftLda,0),
                Bindings.lessThanOrEqual(inputLeftLda,inputLeftTora))
        );
        rightTakeOff.bind(Bindings.and(Bindings.and(Bindings.greaterThanOrEqual(inputRightAsda,inputRightTora),Bindings.greaterThanOrEqual(inputRightToda,inputRightAsda)),dualDirectionRunway));
        rightLand.bind(Bindings.and(Bindings.and(Bindings.greaterThanOrEqual(inputRightLda,0),
                Bindings.lessThanOrEqual(inputRightLda,inputRightTora)),dualDirectionRunway)
        );
    }

    /**
     * Make error scene border pane.
     *
     * @param width  the width
     * @param height the height
     * @return the border pane
     */
    public BorderPane makeErrorScene(NumberBinding width, NumberBinding height){
        BorderPane errorPane = new BorderPane();
        errorPane.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
        errorPane.visibleProperty().bind(
                Bindings.and(
                        Bindings.and(leftTakeOff,
                                rightTakeOff
                        ),
                        Bindings.and(leftLand,
                                rightLand
                        )
                ).not()
        );

        Text label = new Text();
        label.textProperty().bind(new SimpleStringProperty("Top ASDA (").concat(inputLeftAsda.asString()).concat(") < Top TORA (").concat( inputLeftTora.asString()).concat(")"));
        label.visibleProperty().bind(Bindings.greaterThanOrEqual(inputLeftAsda,inputLeftTora).not());
        label.setFill(Color.RED);
        //label.setFont(Theme.font);
        label.getStyleClass().add("font");
        Text label2 = new Text();
        label2.textProperty().bind(new SimpleStringProperty("Top TODA (").concat(inputLeftToda.asString()).concat(") < Top ASDA (").concat( inputLeftAsda.asString()).concat(")"));
        label2.visibleProperty().bind(Bindings.greaterThanOrEqual(inputLeftToda,inputLeftAsda).not());
        label2.setFill(Color.RED);
        //label2.setFont(Theme.font);
        label2.getStyleClass().add("font");
        Text label3 = new Text();
        label3.textProperty().bind(new SimpleStringProperty("Top LDA (").concat(inputLeftLda.asString()).concat(") < 0"));
        label3.visibleProperty().bind(Bindings.greaterThanOrEqual(inputLeftLda,0).not());
        label3.setFill(Color.RED);
        //label3.setFont(Theme.font);
        label3.getStyleClass().add("font");
        Text label4 = new Text();
        label4.textProperty().bind(new SimpleStringProperty("Top TORA (").concat(inputLeftTora.asString()).concat(") < Top LDA (").concat( inputLeftLda.asString()).concat(")"));
        label4.visibleProperty().bind(Bindings.lessThanOrEqual(inputLeftLda,inputLeftTora).not());
        label4.setFill(Color.RED);
        //label4.setFont(Theme.font);
        label4.getStyleClass().add("font");
        Text label5= new Text();
        label5.textProperty().bind(new SimpleStringProperty("Bottom ASDA (").concat(inputRightAsda.asString()).concat(") < Bottom TORA (").concat( inputRightTora.asString()).concat(")"));
        label5.visibleProperty().bind(Bindings.greaterThanOrEqual(inputRightAsda,inputRightTora).not());
        label5.setFill(Color.RED);
        //label5.setFont(Theme.font);
        label5.getStyleClass().add("font");
        Text label6= new Text();
        label6.textProperty().bind(new SimpleStringProperty("Bottom TODA (").concat(inputRightToda.asString()).concat(") < Bottom ASDA (").concat( inputRightAsda.asString()).concat(")"));
        label6.visibleProperty().bind(Bindings.greaterThanOrEqual(inputRightToda,inputRightAsda).not());
        label6.setFill(Color.RED);
        //label6.setFont(Theme.font);
        label6.getStyleClass().add("font");
        Text label7= new Text();
        label7.textProperty().bind(new SimpleStringProperty("Bottom LDA (").concat(inputRightLda.asString()).concat(") < 0"));
        label7.visibleProperty().bind(Bindings.greaterThanOrEqual(inputRightLda,0).not());
        label7.setFill(Color.RED);
        //label7.setFont(Theme.font);
        label7.getStyleClass().add("font");
        Text label8 = new Text();
        label8.textProperty().bind(new SimpleStringProperty("Bottom TORA (").concat(inputRightTora.asString()).concat(") < Bottom LDA (").concat( inputRightLda.asString()).concat(")"));
        label8.visibleProperty().bind(Bindings.lessThanOrEqual(inputRightLda,inputRightTora).not());
        label8.setFill(Color.RED);
        //label8.setFont(Theme.font);
        label8.getStyleClass().add("font");
        VBox labels = new VBox(label,label2,label3,label4,label5,label6,label7,label8);
        labels.setAlignment(Pos.CENTER);
        errorPane.setCenter(labels);
        width.addListener((observableValue, number, t1) -> {
            labels.setMaxWidth((Double) t1);
            labels.setMinWidth((Double) t1);
            errorPane.setMaxWidth((Double) t1);
            errorPane.setMinWidth((Double) t1);
        });
        height.addListener((observableValue, number, t1) -> {
            labels.setMaxHeight((Double) t1);
            labels.setMinHeight((Double) t1);
            errorPane.setMaxHeight((Double) t1);
            errorPane.setMinHeight((Double) t1);
        });

        return errorPane;
    }

    /**
     * Calculating the LDA subtraction
     *
     * @param distFromThreshold The correct distance from threshold for the obstacle for the direction
     * @param left              the left
     * @return The calculated subtraction from the LDA
     */
    public SimpleDoubleProperty getLdaSubtraction(SimpleDoubleProperty distFromThreshold, boolean left) {

        var obstacleSlopeCalculation = getObstacleSlopeCalculation(left);

        var ldaSubtraction = new SimpleDoubleProperty();
        ldaSubtraction.bind(
                Bindings.when(
                        Bindings.greaterThan(
                                distFromThreshold
                                        .add(obstacleSlopeCalculation)
                                        .add(STRIPEND),
                                BLASTZONE.add(distFromThreshold)
                        )
                )
                        .then(
                                distFromThreshold
                                        .add(obstacleSlopeCalculation)
                                        .add(STRIPEND)
                        ).otherwise(
                                BLASTZONE.add(distFromThreshold)
                        )
        );

        // TODO breakdowns

        SimpleStringProperty ldaSubBreakdown = left ? leftLdaSubBreakdownProperty() : rightLdaSubBreakdownProperty();

        SimpleStringProperty ldaSubBreakdownHeader =
                left ?
                        leftLdaSubBreakdownHeaderProperty()
                        : rightLdaSubBreakdownHeaderProperty();

        SimpleStringProperty obstacleSlopCalcBreakdown =
                left ?
                        leftLdaObstacleSlopeCalcBreakdownProperty()
                        : rightLdaObstacleSlopeCalcBreakdownProperty();

        SimpleStringProperty obstacleSlopCalcBreakdownHeader =
                left ?
                        leftLdaObstacleSlopeCalcBreakdownHeaderProperty()
                        : rightLdaObstacleSlopeCalcBreakdownHeaderProperty();

        ldaSubBreakdown.bind(
                Bindings.when(
                        Bindings.greaterThan(
                                distFromThreshold.add(obstacleSlopeCalculation).add(STRIPEND),
                                BLASTZONE.add(distFromThreshold)
                        )
                ).then(
                        new SimpleStringProperty("(")
                                .concat(distFromThreshold.intValue())
                                .concat(" + ")
                                .concat(obstacleSlopCalcBreakdown)
                                .concat(" + ")
                                .concat(STRIPEND)
                                .concat(")")
                ).otherwise(
                        new SimpleStringProperty("(")
                                .concat(distFromThreshold.intValue())
                                .concat(" + ")
                                .concat(BLASTZONE)
                                .concat(")")
                )
        );

        ldaSubBreakdownHeader.bind(
                Bindings.when(
                        Bindings.greaterThan(
                                distFromThreshold.add(obstacleSlopeCalculation.add(STRIPEND)),
                                BLASTZONE.add(distFromThreshold)

                        )
                ).then(
                        new SimpleStringProperty("(Distance from threshold + ")
                                .concat(obstacleSlopCalcBreakdownHeader)
                                .concat(" + Stripend)")
                ).otherwise(
                        new SimpleStringProperty("(Distance from threshold + Blastzone)")
                )
        );

        logger.info("LDA Subtraction: "+ldaSubtraction.get());

        return ldaSubtraction;
    }

    /**
     * Calculate the slope value for an obstacle
     *
     * @param left the left
     * @return The appropriate slope over an obstacle
     */
    public SimpleDoubleProperty getObstacleSlopeCalculation(boolean left) {
        var obstacleSlopeCalculation = new SimpleDoubleProperty();
        obstacleSlopeCalculation
                .bind(
                        Bindings.max(
                                runwayObstacle.heightProperty()
                                        .multiply(SLOPE)
                                        .subtract(
                                                runwayLengthProperty().divide(2)
                                        ),
                                MINRESA
                        )
                );

        logger.info("Obstacle Slop Calculation: "+obstacleSlopeCalculation.get());

        SimpleStringProperty obstacleSlopeCalcBreakdown =
                left ?
                        leftLdaObstacleSlopeCalcBreakdownProperty()
                        : rightLdaObstacleSlopeCalcBreakdownProperty();

        SimpleStringProperty obstacleSlopeCalcBreakdownHeader =
                left ?
                        leftLdaObstacleSlopeCalcBreakdownHeaderProperty()
                        : rightLdaObstacleSlopeCalcBreakdownHeaderProperty();

        obstacleSlopeCalcBreakdown.bind(
                Bindings.when(
                        Bindings.greaterThan(
                                runwayObstacle.heightProperty().multiply(SLOPE),
                                MINRESA.add(runwayObstacle.lengthProperty().divide(2))
                        )
                )
                        .then(
                                new SimpleStringProperty("(")
                                        .concat(runwayObstacle.heightProperty().intValue())
                                        .concat(" x ")
                                        .concat(SLOPE)
                                        .concat(")")
                        )
                        .otherwise(
                                new SimpleStringProperty("(")
                                        .concat(MINRESA)
                                        .concat("+(")
                                        .concat(runwayObstacle.lengthProperty().intValue())
                                        .concat(" / ")
                                        .concat(2)
                                        .concat("))")
                        )
        );

        obstacleSlopeCalcBreakdownHeader.bind(
                Bindings.when(
                        Bindings.greaterThan(
                                runwayObstacle.heightProperty().multiply(SLOPE),
                                MINRESA.add(runwayObstacle.lengthProperty().divide(2))
                        )
                ).then(
                        new SimpleStringProperty("(Obstacle height x ALS slope)")
                ).otherwise(
                        new SimpleStringProperty("(Minimum RESA + (Obstacle length / 2))")
                )
        );

        return obstacleSlopeCalculation;
    }

    /**
     * Adds a change to changes history pane
     *
     * @param change text to be displayed in change history tab
     */
    public void logChange(String change, Boolean show) {
        if (changeHistory.isEmpty() || !change.equals(changeHistory.get(0))) {
            if (change.startsWith("Obstacle distance") && changeHistory.get(0).startsWith("Obstacle distance")) {
                changeHistory.remove(0);
            } else if (change.startsWith("Obstacle height") && changeHistory.get(0).startsWith("Obstacle height")) {
                changeHistory.remove(0);
            } else if (change.startsWith("Obstacle length") && changeHistory.get(0).startsWith("Obstacle length")) {
                changeHistory.remove(0);
            } else if (change.startsWith("Obstacle width") && changeHistory.get(0).startsWith("Obstacle width")) {
                changeHistory.remove(0);
            }
            changeHistory.add(0, change);
            if (SystemTray.isSupported() && show) {
                SystemTray tray = SystemTray.getSystemTray();
                Image image = new ImageIcon(getClass().getResource("/images/NotificationIcon.png")).getImage(); // set icon image
                TrayIcon trayIcon = new TrayIcon(image, "Runway Tool"); // create tray icon
                trayIcon.setImageAutoSize(true);
                trayIcon.setToolTip("New change: " + change);
                try {
                    tray.add(trayIcon);
                    trayIcon.displayMessage("Runway Tool: New Change", change, TrayIcon.MessageType.INFO);
                } catch (AWTException e) {
                    System.err.println(e);
                }
            }
        }
    }



    // Getters

    /**
     * Gets input right tora.
     *
     * @return the input right tora
     */
    public ObservableList<String> getChangeHistory() {
        return changeHistory;
    }

    /**
     * Gets input right tora.
     *
     * @return the input right tora
     */
    public SimpleListProperty<String> getChangeHistoryProperty(){
        return changeHistoryProperty;
    }

    /**
     * Gets input right tora.
     *
     * @return the input right tora
     */
    public double getInputRightTora() {
        return inputRightTora.get();
    }

    /**
     * Input right tora property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty inputRightToraProperty() {
        return inputRightTora;
    }

    /**
     * Gets input right toda.
     *
     * @return the input right toda
     */
    public double getInputRightToda() {
        return inputRightToda.get();
    }

    /**
     * Input right toda property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty inputRightTodaProperty() {
        return inputRightToda;
    }

    /**
     * Gets input right asda.
     *
     * @return the input right asda
     */
    public double getInputRightAsda() {
        return inputRightAsda.get();
    }

    /**
     * Input right asda property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty inputRightAsdaProperty() {
        return inputRightAsda;
    }

    /**
     * Gets input right lda.
     *
     * @return the input right lda
     */
    public double getInputRightLda() {
        return inputRightLda.get();
    }

    /**
     * Input right lda property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty inputRightLdaProperty() {
        return inputRightLda;
    }

    /**
     * Gets input left tora.
     *
     * @return the input left tora
     */
    public double getInputLeftTora() {
        return inputLeftTora.get();
    }

    /**
     * Input left tora property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty inputLeftToraProperty() {
        return inputLeftTora;
    }

    /**
     * Gets input left toda.
     *
     * @return the input left toda
     */
    public double getInputLeftToda() {
        return inputLeftToda.get();
    }

    /**
     * Input left toda property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty inputLeftTodaProperty() {
        return inputLeftToda;
    }

    /**
     * Gets input left asda.
     *
     * @return the input left asda
     */
    public double getInputLeftAsda() {
        return inputLeftAsda.get();
    }

    /**
     * Input left asda property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty inputLeftAsdaProperty() {
        return inputLeftAsda;
    }

    /**
     * Gets input left lda.
     *
     * @return the input left lda
     */
    public double getInputLeftLda() {
        return inputLeftLda.get();
    }

    /**
     * Input left lda property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty inputLeftLdaProperty() {
        return inputLeftLda;
    }


    /**
     * Gets runway obstacle.
     *
     * @return the runway obstacle
     */
    public Obstacle getRunwayObstacle() {
        return runwayObstacle;
    }

    /**
     * Is landing mode boolean.
     *
     * @return the boolean
     */
    public boolean isLandingMode() {
        return landingMode.get();
    }

    /**
     * Landing mode property simple boolean property.
     *
     * @return the simple boolean property
     */
    public SimpleBooleanProperty landingModeProperty() {
        return landingMode;
    }

    /**
     * Returns the runway designator string.
     *
     * @return The runway designator string.
     */
    public String getRunwayDesignatorLeft() {
        return runwayDesignatorLeft.get();
    }

    /**
     * Gets runway designator right.
     *
     * @return the runway designator right
     */
    public String getRunwayDesignatorRight() {
        return runwayDesignatorRight.get();
    }

    /**
     * Returns the SimpleStringProperty object representing the runway designator.
     *
     * @return The SimpleStringProperty object representing the runway designator.
     */
    public SimpleStringProperty runwayDesignatorLeftProperty() {
        return runwayDesignatorLeft;
    }

    /**
     * Runway designator right property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty runwayDesignatorRightProperty() {
        return runwayDesignatorRight;
    }

    /**
     * Returns the value of direction.
     *
     * @return The value of direction.
     */
    public boolean isDirectionLeft() {
        return directionLeft.get();
    }

    /**
     * Is direction right boolean.
     *
     * @return the boolean
     */
    public boolean isDirectionRight() {
        return directionRight.get();
    }

    /**
     * Returns the SimpleBooleanProperty object representing direction.
     *
     * @return The SimpleBooleanProperty object representing direction.
     */
    public SimpleBooleanProperty directionLeftProperty() {
        return directionLeft;
    }

    /**
     * Direction right property simple boolean property.
     *
     * @return the simple boolean property
     */
    public SimpleBooleanProperty directionRightProperty() {
        return directionRight;
    }

    /**
     * Returns the value of runwayLength.
     *
     * @return The value of runwayLength.
     */
    public double getRunwayLength() {
        return runwayLength.get();
    }

    /**
     * Returns the SimpleDoubleProperty object representing runwayLength.
     *
     * @return The SimpleDoubleProperty object representing runwayLength.
     */
    public SimpleDoubleProperty runwayLengthProperty() {
        return runwayLength;
    }

    /**
     * Returns the value of runwayWidth.
     *
     * @return The value of runwayWidth.
     */
    public double getRunwayWidth() {
        return runwayWidth.get();
    }

    /**
     * Returns the SimpleDoubleProperty object representing runwayWidth.
     *
     * @return The SimpleDoubleProperty object representing runwayWidth.
     */
    public SimpleDoubleProperty runwayWidthProperty() {
        return runwayWidth;
    }

    /**
     * Returns the value of clearwayLeftHeight.
     *
     * @return The value of clearwayLeftHeight.
     */
    public double getClearwayHeight() {
        return clearwayHeight.get();
    }

    /**
     * Returns the SimpleDoubleProperty object representing clearwayLeftHeight.
     *
     * @return The SimpleDoubleProperty object representing clearwayLeftHeight.
     */
    public SimpleDoubleProperty clearwayHeightProperty() {
        return clearwayHeight;
    }


    /**
     * Returns the value of stopwayRight.
     *
     * @return The value of stopwayRight.
     */
    public double getRightTora() {
        return rightTora.get();
    }

    /**
     * Right tora property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty rightToraProperty() {
        return rightTora;
    }

    /**
     * Gets right toda.
     *
     * @return the right toda
     */
    public double getRightToda() {
        return rightToda.get();
    }

    /**
     * Right toda property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty rightTodaProperty() {
        return rightToda;
    }

    /**
     * Gets right asda.
     *
     * @return the right asda
     */
    public double getRightAsda() {
        return rightAsda.get();
    }

    /**
     * Right asda property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty rightAsdaProperty() {
        return rightAsda;
    }

    /**
     * Gets right lda.
     *
     * @return the right lda
     */
    public double getRightLda() {
        return rightLda.get();
    }

    /**
     * Right lda property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty rightLdaProperty() {
        return rightLda;
    }

    /**
     * Gets left tora.
     *
     * @return the left tora
     */
    public double getLeftTora() {
        return leftTora.get();
    }

    /**
     * Left tora property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty leftToraProperty() {
        return leftTora;
    }

    /**
     * Gets left toda.
     *
     * @return the left toda
     */
    public double getLeftToda() {
        return leftToda.get();
    }

    /**
     * Left toda property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty leftTodaProperty() {
        return leftToda;
    }

    /**
     * Gets left asda.
     *
     * @return the left asda
     */
    public double getLeftAsda() {
        return leftAsda.get();
    }

    /**
     * Left asda property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty leftAsdaProperty() {
        return leftAsda;
    }

    /**
     * Gets left lda.
     *
     * @return the left lda
     */
    public double getLeftLda() {
        return leftLda.get();
    }

    /**
     * Left lda property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty leftLdaProperty() {
        return leftLda;
    }

    /**
     * Gets strip end.
     *
     * @return the strip end
     */
    public double getStripEnd() {
        return stripEnd.get();
    }

    /**
     * Strip end property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty stripEndProperty() {
        return stripEnd;
    }

    /**
     * Gets resa width.
     *
     * @return the resa width
     */
    public double getRESAWidth() {
        return RESAWidth.get();
    }

    /**
     * RESA width property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty RESAWidthProperty() {
        return RESAWidth;
    }

    /**
     * Gets resa height.
     *
     * @return the resa height
     */
    public double getRESAHeight() {
        return RESAHeight.get();
    }

    /**
     * RESA height property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty RESAHeightProperty() {
        return RESAHeight;
    }

    /**
     * Is has runway obstacle boolean.
     *
     * @return the boolean
     */
    public boolean isHasRunwayObstacle() {
        return hasRunwayObstacle.get();
    }

    /**
     * Has runway obstacle property simple boolean property.
     *
     * @return the simple boolean property
     */
    public SimpleBooleanProperty hasRunwayObstacleProperty() {
        return hasRunwayObstacle;
    }

    /**
     * Gets units.
     *
     * @return the units
     */
    public String getUnits() {
        return units.get();
    }

    /**
     * Units property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty unitsProperty() {
        return units;
    }

    /**
     * Gets clearway left.
     *
     * @return the clearway left
     */
    public double getClearwayLeft() {
        return clearwayLeft.get();
    }

    /**
     * Clearway left property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty clearwayLeftProperty() {
        return clearwayLeft;
    }

    /**
     * Gets clearway right.
     *
     * @return the clearway right
     */
    public double getClearwayRight() {
        return clearwayRight.get();
    }

    /**
     * Clearway right property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty clearwayRightProperty() {
        return clearwayRight;
    }

    /**
     * Gets stopway left.
     *
     * @return the stopway left
     */
    public double getStopwayLeft() {
        return stopwayLeft.get();
    }

    /**
     * Stopway left property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty stopwayLeftProperty() {
        return stopwayLeft;
    }

    /**
     * Gets stopway right.
     *
     * @return the stopway right
     */
    public double getStopwayRight() {
        return stopwayRight.get();
    }

    /**
     * Stopway right property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty stopwayRightProperty() {
        return stopwayRight;
    }

    /**
     * Gets disp threshold left.
     *
     * @return the disp threshold left
     */
    public double getDispThresholdLeft() {
        return dispThresholdLeft.get();
    }

    /**
     * Disp threshold left property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty dispThresholdLeftProperty() {
        return dispThresholdLeft;
    }

    /**
     * Gets disp threshold right.
     *
     * @return the disp threshold right
     */
    public double getDispThresholdRight() {
        return dispThresholdRight.get();
    }

    /**
     * Disp threshold right property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty dispThresholdRightProperty() {
        return dispThresholdRight;
    }

    /**
     * Is left take off boolean.
     *
     * @return the boolean
     */
    public boolean isLeftTakeOff() {
        return leftTakeOff.get();
    }

    /**
     * Left take off property simple boolean property.
     *
     * @return the simple boolean property
     */
    public SimpleBooleanProperty leftTakeOffProperty() {
        return leftTakeOff;
    }

    /**
     * Is left land boolean.
     *
     * @return the boolean
     */
    public boolean isLeftLand() {
        return leftLand.get();
    }

    /**
     * Left land property simple boolean property.
     *
     * @return the simple boolean property
     */
    public SimpleBooleanProperty leftLandProperty() {
        return leftLand;
    }

    /**
     * Is right take off boolean.
     *
     * @return the boolean
     */
    public boolean isRightTakeOff() {
        return rightTakeOff.get();
    }

    /**
     * Right take off property simple boolean property.
     *
     * @return the simple boolean property
     */
    public SimpleBooleanProperty rightTakeOffProperty() {
        return rightTakeOff;
    }

    /**
     * Is right land boolean.
     *
     * @return the boolean
     */
    public boolean isRightLand() {
        return rightLand.get();
    }

    /**
     * Right land property simple boolean property.
     *
     * @return the simple boolean property
     */
    public SimpleBooleanProperty rightLandProperty() {
        return rightLand;
    }

    /**
     * Gets minresa.
     *
     * @return the minresa
     */
    public double getMINRESA() {
        return MINRESA.get();
    }

    /**
     * Minresa property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty MINRESAProperty() {
        return MINRESA;
    }

    /**
     * Gets stripend.
     *
     * @return the stripend
     */
    public double getSTRIPEND() {
        return STRIPEND.get();
    }

    /**
     * Stripend property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty STRIPENDProperty() {
        return STRIPEND;
    }

    /**
     * Gets blastzone.
     *
     * @return the blastzone
     */
    public double getBLASTZONE() {
        return BLASTZONE.get();
    }

    /**
     * Blastzone property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty BLASTZONEProperty() {
        return BLASTZONE;
    }

    /**
     * Gets slope.
     *
     * @return the slope
     */
    public double getSLOPE() {
        return SLOPE.get();
    }

    /**
     * Slope property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty SLOPEProperty() {
        return SLOPE;
    }

    /**
     * Gets stopwaymin.
     *
     * @return the stopwaymin
     */
    public double getSTOPWAYMIN() {
        return STOPWAYMIN.get();
    }

    /**
     * Stopwaymin property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty STOPWAYMINProperty() {
        return STOPWAYMIN;
    }

    /**
     * Gets slope length.
     *
     * @return the slope length
     */
    public double getSlopeLength() {
        return slopeLength.get();
    }

    /**
     * Slope length property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty slopeLengthProperty() {
        return slopeLength;
    }

    /**
     * Left tora breakdown property simple string property.
     *
     * @return the simple string property
     */
// Breakdown properties
    public SimpleStringProperty leftToraBreakdownProperty() {
        return leftToraBreakdown;
    }

    /**
     * Left toda breakdown property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty leftTodaBreakdownProperty() {
        return leftTodaBreakdown;
    }

    /**
     * Left asda breakdown property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty leftAsdaBreakdownProperty() {
        return leftAsdaBreakdown;
    }

    /**
     * Left lda breakdown property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty leftLdaBreakdownProperty() {
        return leftLdaBreakdown;
    }

    /**
     * Right tora breakdown property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty rightToraBreakdownProperty() {
        return rightToraBreakdown;
    }

    /**
     * Right toda breakdown property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty rightTodaBreakdownProperty() {
        return rightTodaBreakdown;
    }

    /**
     * Right asda breakdown property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty rightAsdaBreakdownProperty() {
        return rightAsdaBreakdown;
    }

    /**
     * Right lda breakdown property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty rightLdaBreakdownProperty() {
        return rightLdaBreakdown;
    }

    /**
     * Right lda sub breakdown property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty rightLdaSubBreakdownProperty() {
        return rightLdaSubBreakdown;
    }

    /**
     * Right lda obstacle slope calc breakdown property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty rightLdaObstacleSlopeCalcBreakdownProperty() {
        return rightLdaObstacleSlopeCalcBreakdown;
    }

    /**
     * Left lda sub breakdown property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty leftLdaSubBreakdownProperty() {
        return leftLdaSubBreakdown;
    }

    /**
     * Left lda obstacle slope calc breakdown property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty leftLdaObstacleSlopeCalcBreakdownProperty() {
        return leftLdaObstacleSlopeCalcBreakdown;
    }

    /**
     * Gets left tora breakdown.
     *
     * @return the left tora breakdown
     */
    public String getLeftToraBreakdown() {
        return leftToraBreakdown.get();
    }

    /**
     * Gets left toda breakdown.
     *
     * @return the left toda breakdown
     */
    public String getLeftTodaBreakdown() {
        return leftTodaBreakdown.get();
    }

    /**
     * Gets left asda breakdown.
     *
     * @return the left asda breakdown
     */
    public String getLeftAsdaBreakdown() {
        return leftAsdaBreakdown.get();
    }

    /**
     * Gets left lda breakdown.
     *
     * @return the left lda breakdown
     */
    public String getLeftLdaBreakdown() {
        return leftLdaBreakdown.get();
    }

    /**
     * Gets right tora breakdown.
     *
     * @return the right tora breakdown
     */
    public String getRightToraBreakdown() {
        return rightToraBreakdown.get();
    }

    /**
     * Gets right toda breakdown.
     *
     * @return the right toda breakdown
     */
    public String getRightTodaBreakdown() {
        return rightTodaBreakdown.get();
    }

    /**
     * Gets right asda breakdown.
     *
     * @return the right asda breakdown
     */
    public String getRightAsdaBreakdown() {
        return rightAsdaBreakdown.get();
    }

    /**
     * Gets right lda breakdown.
     *
     * @return the right lda breakdown
     */
    public String getRightLdaBreakdown() {
        return rightLdaBreakdown.get();
    }

    /**
     * Gets left lda obstacle slope calc breakdown.
     *
     * @return the left lda obstacle slope calc breakdown
     */
    public String getLeftLdaObstacleSlopeCalcBreakdown() {
        return leftLdaObstacleSlopeCalcBreakdown.get();
    }

    /**
     * Gets right lda obstacle slope calc breakdown.
     *
     * @return the right lda obstacle slope calc breakdown
     */
    public String getRightLdaObstacleSlopeCalcBreakdown() {
        return rightLdaObstacleSlopeCalcBreakdown.get();
    }

    /**
     * Gets left lda sub breakdown.
     *
     * @return the left lda sub breakdown
     */
    public String getLeftLdaSubBreakdown() {
        return leftLdaSubBreakdown.get();
    }

    /**
     * Gets right lda sub breakdown.
     *
     * @return the right lda sub breakdown
     */
    public String getRightLdaSubBreakdown() {
        return rightLdaSubBreakdown.get();
    }

    /**
     * Left tora breakdown header property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty leftToraBreakdownHeaderProperty() {
        return leftToraBreakdownHeader;
    }

    /**
     * Right tora breakdown header property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty rightToraBreakdownHeaderProperty() {
        return rightToraBreakdownHeader;
    }

    /**
     * Left toda breakdown header property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty leftTodaBreakdownHeaderProperty() {
        return leftTodaBreakdownHeader;
    }

    /**
     * Right toda breakdown header property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty rightTodaBreakdownHeaderProperty() {
        return rightTodaBreakdownHeader;
    }

    /**
     * Left asda breakdown header property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty leftAsdaBreakdownHeaderProperty() {
        return leftAsdaBreakdownHeader;
    }

    /**
     * Right asda breakdown header property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty rightAsdaBreakdownHeaderProperty() {
        return rightAsdaBreakdownHeader;
    }

    /**
     * Left lda breakdown header property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty leftLdaBreakdownHeaderProperty() {
        return leftLdaBreakdownHeader;
    }

    /**
     * Right lda breakdown header property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty rightLdaBreakdownHeaderProperty() {
        return rightLdaBreakdownHeader;
    }

    /**
     * Left lda obstacle slope calc breakdown header property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty leftLdaObstacleSlopeCalcBreakdownHeaderProperty() {
        return leftLdaObstacleSlopeCalcBreakdownHeader;
    }

    /**
     * Right lda obstacle slope calc breakdown header property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty rightLdaObstacleSlopeCalcBreakdownHeaderProperty() {
        return rightLdaObstacleSlopeCalcBreakdownHeader;
    }

    /**
     * Left lda sub breakdown header property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty leftLdaSubBreakdownHeaderProperty() {
        return leftLdaSubBreakdownHeader;
    }

    /**
     * Right lda sub breakdown header property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty rightLdaSubBreakdownHeaderProperty() {
        return rightLdaSubBreakdownHeader;
    }

    /**
     * Gets left tora breakdown header.
     *
     * @return the left tora breakdown header
     */
    public String getLeftToraBreakdownHeader() {
        return leftToraBreakdownHeader.get();
    }

    /**
     * Gets right tora breakdown header.
     *
     * @return the right tora breakdown header
     */
    public String getRightToraBreakdownHeader() {
        return rightToraBreakdownHeader.get();
    }

    /**
     * Gets left toda breakdown header.
     *
     * @return the left toda breakdown header
     */
    public String getLeftTodaBreakdownHeader() {
        return leftTodaBreakdownHeader.get();
    }

    /**
     * Gets right toda breakdown header.
     *
     * @return the right toda breakdown header
     */
    public String getRightTodaBreakdownHeader() {
        return rightTodaBreakdownHeader.get();
    }

    /**
     * Gets left asda breakdown header.
     *
     * @return the left asda breakdown header
     */
    public String getLeftAsdaBreakdownHeader() {
        return leftAsdaBreakdownHeader.get();
    }

    /**
     * Gets right asda breakdown header.
     *
     * @return the right asda breakdown header
     */
    public String getRightAsdaBreakdownHeader() {
        return rightAsdaBreakdownHeader.get();
    }

    /**
     * Gets left lda breakdown header.
     *
     * @return the left lda breakdown header
     */
    public String getLeftLdaBreakdownHeader() {
        return leftLdaBreakdownHeader.get();
    }

    /**
     * Gets right lda breakdown header.
     *
     * @return the right lda breakdown header
     */
    public String getRightLdaBreakdownHeader() {
        return rightLdaBreakdownHeader.get();
    }

    /**
     * Gets left lda obstacle slope calc breakdown header.
     *
     * @return the left lda obstacle slope calc breakdown header
     */
    public String getLeftLdaObstacleSlopeCalcBreakdownHeader() {
        return leftLdaObstacleSlopeCalcBreakdownHeader.get();
    }

    /**
     * Gets right lda obstacle slope calc breakdown header.
     *
     * @return the right lda obstacle slope calc breakdown header
     */
    public String getRightLdaObstacleSlopeCalcBreakdownHeader() {
        return rightLdaObstacleSlopeCalcBreakdownHeader.get();
    }

    /**
     * Gets left lda sub breakdown header.
     *
     * @return the left lda sub breakdown header
     */
    public String getLeftLdaSubBreakdownHeader() {
        return leftLdaSubBreakdownHeader.get();
    }

    /**
     * Gets right lda sub breakdown header.
     *
     * @return the right lda sub breakdown header
     */
    public String getRightLdaSubBreakdownHeader() {
        return rightLdaSubBreakdownHeader.get();
    }

    /**
     * Sets clearway left.
     *
     * @param clearwayLeft the clearway left
     */
    public void setClearwayLeft(double clearwayLeft) {
        this.clearwayLeft.set(clearwayLeft);
    }

    /**
     * Sets clearway right.
     *
     * @param clearwayRight the clearway right
     */
    public void setClearwayRight(double clearwayRight) {
        this.clearwayRight.set(clearwayRight);
    }

    /**
     * Sets clearway height.
     *
     * @param clearwayHeight the clearway height
     */
    public void setClearwayHeight(double clearwayHeight) {
        this.clearwayHeight.set(clearwayHeight);
    }

    /**
     * Sets stopway left.
     *
     * @param stopwayLeft the stopway left
     */
    public void setStopwayLeft(double stopwayLeft) {
        this.stopwayLeft.set(stopwayLeft);
    }

    /**
     * Sets stopway right.
     *
     * @param stopwayRight the stopway right
     */
    public void setStopwayRight(double stopwayRight) {
        this.stopwayRight.set(stopwayRight);
    }

    /**
     * Sets strip end.
     *
     * @param stripEnd the strip end
     */
    public void setStripEnd(double stripEnd) {
        this.stripEnd.set(stripEnd);
    }

    /**
     * Sets resa width.
     *
     * @param RESAWidth the resa width
     */
    public void setRESAWidth(double RESAWidth) {
        this.RESAWidth.set(RESAWidth);
    }

    /**
     * Sets resa height.
     *
     * @param RESAHeight the resa height
     */
    public void setRESAHeight(double RESAHeight) {
        this.RESAHeight.set(RESAHeight);
    }

    /**
     * Sets runway designator left.
     *
     * @param runwayDesignatorLeft the runway designator left
     */
    public void setRunwayDesignatorLeft(String runwayDesignatorLeft) {
        this.runwayDesignatorLeft.set(runwayDesignatorLeft);
    }

    /**
     * Sets runway designator right.
     *
     * @param runwayDesignatorRight the runway designator right
     */
    public void setRunwayDesignatorRight(String runwayDesignatorRight) {
        this.runwayDesignatorRight.set(runwayDesignatorRight);
    }

    /**
     * Sets input right tora.
     *
     * @param inputRightTora the input right tora
     */
    public void setInputRightTora(double inputRightTora) {
        this.inputRightTora.set(inputRightTora);
    }

    /**
     * Sets input right toda.
     *
     * @param inputRightToda the input right toda
     */
    public void setInputRightToda(double inputRightToda) {
        this.inputRightToda.set(inputRightToda);
    }

    /**
     * Sets input right asda.
     *
     * @param inputRightAsda the input right asda
     */
    public void setInputRightAsda(double inputRightAsda) {
        this.inputRightAsda.set(inputRightAsda);
    }

    /**
     * Sets input right lda.
     *
     * @param inputRightLda the input right lda
     */
    public void setInputRightLda(double inputRightLda) {
        this.inputRightLda.set(inputRightLda);
    }

    /**
     * Sets input left tora.
     *
     * @param inputLeftTora the input left tora
     */
    public void setInputLeftTora(double inputLeftTora) {
        this.inputLeftTora.set(inputLeftTora);
    }

    /**
     * Sets input left toda.
     *
     * @param inputLeftToda the input left toda
     */
    public void setInputLeftToda(double inputLeftToda) {
        this.inputLeftToda.set(inputLeftToda);
    }

    /**
     * Sets input left asda.
     *
     * @param inputLeftAsda the input left asda
     */
    public void setInputLeftAsda(double inputLeftAsda) {
        this.inputLeftAsda.set(inputLeftAsda);
    }

    /**
     * Sets input left lda.
     *
     * @param inputLeftLda the input left lda
     */
    public void setInputLeftLda(double inputLeftLda) {
        this.inputLeftLda.set(inputLeftLda);
    }

    /**
     * Sets right tora.
     *
     * @param rightTora the right tora
     */
    public void setRightTora(double rightTora) {
        this.rightTora.set(rightTora);
    }

    /**
     * Sets right toda.
     *
     * @param rightToda the right toda
     */
    public void setRightToda(double rightToda) {
        this.rightToda.set(rightToda);
    }

    /**
     * Sets right asda.
     *
     * @param rightAsda the right asda
     */
    public void setRightAsda(double rightAsda) {
        this.rightAsda.set(rightAsda);
    }

    /**
     * Sets right lda.
     *
     * @param rightLda the right lda
     */
    public void setRightLda(double rightLda) {
        this.rightLda.set(rightLda);
    }

    /**
     * Sets left tora.
     *
     * @param leftTora the left tora
     */
    public void setLeftTora(double leftTora) {
        this.leftTora.set(leftTora);
    }

    /**
     * Sets left toda.
     *
     * @param leftToda the left toda
     */
    public void setLeftToda(double leftToda) {
        this.leftToda.set(leftToda);
    }

    /**
     * Sets left asda.
     *
     * @param leftAsda the left asda
     */
    public void setLeftAsda(double leftAsda) {
        this.leftAsda.set(leftAsda);
    }

    /**
     * Sets left lda.
     *
     * @param leftLda the left lda
     */
    public void setLeftLda(double leftLda) {
        this.leftLda.set(leftLda);
    }

    /**
     * Sets disp threshold left.
     *
     * @param dispThresholdLeft the disp threshold left
     */
    public void setDispThresholdLeft(double dispThresholdLeft) {
        this.dispThresholdLeft.set(dispThresholdLeft);
    }

    /**
     * Sets disp threshold right.
     *
     * @param dispThresholdRight the disp threshold right
     */
    public void setDispThresholdRight(double dispThresholdRight) {
        this.dispThresholdRight.set(dispThresholdRight);
    }

    /**
     * Sets runway obstacle.
     *
     * @param runwayObstacle the runway obstacle
     */
    public void setRunwayObstacle(Obstacle runwayObstacle) {
        this.runwayObstacle = runwayObstacle;
    }

    /**
     * Sets landing mode.
     *
     * @param landingMode the landing mode
     */
    public void setLandingMode(boolean landingMode) {
        this.landingMode.set(landingMode);
    }

    /**
     * Sets left take off.
     *
     * @param leftTakeOff the left take-off
     */
    public void setLeftTakeOff(boolean leftTakeOff) {
        this.leftTakeOff.set(leftTakeOff);
    }

    /**
     * Sets left land.
     *
     * @param leftLand the left land
     */
    public void setLeftLand(boolean leftLand) {
        this.leftLand.set(leftLand);
    }

    /**
     * Sets right take off.
     *
     * @param rightTakeOff the right take-off
     */
    public void setRightTakeOff(boolean rightTakeOff) {
        this.rightTakeOff.set(rightTakeOff);
    }

    /**
     * Sets right land.
     *
     * @param rightLand the right land
     */
    public void setRightLand(boolean rightLand) {
        this.rightLand.set(rightLand);
    }

    /**
     * Sets minresa.
     *
     * @param MINRESA the minresa
     */
    public void setMINRESA(double MINRESA) {
        this.MINRESA.set(MINRESA);
    }

    /**
     * Sets stripend.
     *
     * @param STRIPEND the stripend
     */
    public void setSTRIPEND(double STRIPEND) {
        this.STRIPEND.set(STRIPEND);
    }

    /**
     * Sets blastzone.
     *
     * @param BLASTZONE the blastzone
     */
    public void setBLASTZONE(double BLASTZONE) {
        this.BLASTZONE.set(BLASTZONE);
    }

    /**
     * Sets slope.
     *
     * @param SLOPE the slope
     */
    public void setSLOPE(double SLOPE) {
        this.SLOPE.set(SLOPE);
    }

    /**
     * Sets stopwaymin.
     *
     * @param STOPWAYMIN the stopwaymin
     */
    public void setSTOPWAYMIN(double STOPWAYMIN) {
        this.STOPWAYMIN.set(STOPWAYMIN);
    }

    /**
     * Sets slope length.
     *
     * @param slopeLength the slope length
     */
    public void setSlopeLength(double slopeLength) {
        this.slopeLength.set(slopeLength);
    }

    /**
     * Sets runway length.
     *
     * @param runwayLength the runway length
     */
    public void setRunwayLength(double runwayLength) {
        this.runwayLength.set(runwayLength);
    }

    /**
     * Sets runway width.
     *
     * @param runwayWidth the runway width
     */
    public void setRunwayWidth(double runwayWidth) {
        this.runwayWidth.set(runwayWidth);
    }

    /**
     * Sets has runway obstacle.
     *
     * @param hasRunwayObstacle the has runway obstacle
     */
    public void setHasRunwayObstacle(boolean hasRunwayObstacle) {
        this.hasRunwayObstacle.set(hasRunwayObstacle);
    }

    /**
     * Sets units.
     *
     * @param units the units
     */
    public void setUnits(String units) {
        RunwayValues.units.set(units);
    }



}
