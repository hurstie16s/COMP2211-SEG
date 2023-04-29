package comp2211.seg.ProcessDataModel;

import comp2211.seg.Controller.Stage.Theme;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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

    /**
     * The Changes history.
     */
// changes history pane
    public Pane changesHistory = new Pane();
    private final ArrayList<String> changeHistory = new ArrayList<>();

    // Runway dimensions and properties
    public final SimpleDoubleProperty clearwayLeft = new SimpleDoubleProperty(500);
    public final SimpleDoubleProperty clearwayRight = new SimpleDoubleProperty(500);
    public final SimpleDoubleProperty clearwayHeight = new SimpleDoubleProperty(150);
    public final SimpleDoubleProperty stopwayLeft = new SimpleDoubleProperty(150);
    public final SimpleDoubleProperty stopwayRight = new SimpleDoubleProperty(150);
    public final SimpleDoubleProperty stripEnd = new SimpleDoubleProperty(60);
    public final SimpleDoubleProperty RESAWidth = new SimpleDoubleProperty(240);
    public final SimpleDoubleProperty RESAHeight = new SimpleDoubleProperty(90);

    //Inputs

    /*
    A runway designator consists of a two-digit number,
    which is the whole number nearest to one tenth of the magnetic North
    when viewed from the direction of approach. For example,
    if the azimuth of the centre-line is 153 then the runway designator will be 15
    followed by either L C or R to differentiate between parallel runways
     */
    public final SimpleStringProperty runwayDesignatorLeft = new SimpleStringProperty("00L");
    public final SimpleStringProperty runwayDesignatorRight = new SimpleStringProperty("18R");
    public SimpleDoubleProperty inputRightTora = new SimpleDoubleProperty(1000);
    public SimpleDoubleProperty inputRightToda = new SimpleDoubleProperty(1500);
    public SimpleDoubleProperty inputRightAsda = new SimpleDoubleProperty(1150);
    public SimpleDoubleProperty inputRightLda = new SimpleDoubleProperty(1000);
    public SimpleDoubleProperty inputLeftTora = new SimpleDoubleProperty(1000);
    public SimpleDoubleProperty inputLeftToda = new SimpleDoubleProperty(1500);
    public SimpleDoubleProperty inputLeftAsda = new SimpleDoubleProperty(1150);
    public SimpleDoubleProperty inputLeftLda = new SimpleDoubleProperty(900);
    public final SimpleDoubleProperty rightTora = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty rightToda = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty rightAsda = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty rightLda = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty leftTora = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty leftToda = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty leftAsda = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty leftLda = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty dispThresholdLeft = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty dispThresholdRight = new SimpleDoubleProperty(60);


    /**
     * The Runway obstacle.
     */
    public Obstacle runwayObstacle = new Obstacle("One", 10,0);

    public final SimpleBooleanProperty landingMode = new SimpleBooleanProperty(true);

    public final SimpleBooleanProperty directionLeft = new SimpleBooleanProperty(true);
    public final SimpleBooleanProperty directionRight = new SimpleBooleanProperty(true);
    public final SimpleBooleanProperty leftTakeOff = new SimpleBooleanProperty(false);
    public final SimpleBooleanProperty leftLand = new SimpleBooleanProperty(false);
    public final SimpleBooleanProperty rightTakeOff = new SimpleBooleanProperty(false);
    public final SimpleBooleanProperty rightLand = new SimpleBooleanProperty(false);

    // End of Inputs



    // Typical values, may become variable down the line
    // Constants
    public final SimpleDoubleProperty MINRESA = new SimpleDoubleProperty(240);
    public final SimpleDoubleProperty STRIPEND = new SimpleDoubleProperty(60);
    public final SimpleDoubleProperty BLASTZONE = new SimpleDoubleProperty(500);
    public final SimpleDoubleProperty SLOPE = new SimpleDoubleProperty(50);
    public final SimpleDoubleProperty STOPWAYMIN = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty slopeLength = new SimpleDoubleProperty(50);

    // Runway dimensions
    public final SimpleDoubleProperty runwayLength = new SimpleDoubleProperty(1000);
    public final SimpleDoubleProperty runwayWidth = new SimpleDoubleProperty(60);
    public final SimpleBooleanProperty hasRunwayObstacle = new SimpleBooleanProperty(false);
    public static final SimpleStringProperty units = new SimpleStringProperty("m");

    // Calculation Breakdowns

    // Left TORA
    public final SimpleStringProperty leftToraBreakdown = new SimpleStringProperty("N/A");
    public final SimpleStringProperty leftToraBreakdownHeader = new SimpleStringProperty("N/A");
    // Right TORA
    public final SimpleStringProperty rightToraBreakdown = new SimpleStringProperty("N/A");
    public final SimpleStringProperty rightToraBreakdownHeader = new SimpleStringProperty("N/A");
    // Left TODA
    public final SimpleStringProperty leftTodaBreakdown = new SimpleStringProperty("N/A");
    public final SimpleStringProperty leftTodaBreakdownHeader = new SimpleStringProperty("N/A");
    // Right TODA
    public final SimpleStringProperty rightTodaBreakdown = new SimpleStringProperty("N/A");
    public final SimpleStringProperty rightTodaBreakdownHeader = new SimpleStringProperty("N/A");
    // Left ASDA
    public final SimpleStringProperty leftAsdaBreakdown = new SimpleStringProperty("N/A");
    public final SimpleStringProperty leftAsdaBreakdownHeader = new SimpleStringProperty("N/A");
    // Right ASDA
    public final SimpleStringProperty rightAsdaBreakdown = new SimpleStringProperty("N/A");
    public final SimpleStringProperty rightAsdaBreakdownHeader = new SimpleStringProperty("N/A");
    // Left LDA
    public final SimpleStringProperty leftLdaBreakdown = new SimpleStringProperty("N/A");
    public final SimpleStringProperty leftLdaBreakdownHeader = new SimpleStringProperty("N/A");
    // Temp holders
    public final SimpleStringProperty leftLdaObstacleSlopeCalcBreakdown = new SimpleStringProperty();
    public final SimpleStringProperty leftLdaObstacleSlopeCalcBreakdownHeader = new SimpleStringProperty();
    public final SimpleStringProperty leftLdaSubBreakdown = new SimpleStringProperty();
    public final SimpleStringProperty leftLdaSubBreakdownHeader = new SimpleStringProperty();
    // Right LDA
    public final SimpleStringProperty rightLdaBreakdown = new SimpleStringProperty("N/A");
    public final SimpleStringProperty rightLdaBreakdownHeader = new SimpleStringProperty("N/A");
    // Temp holders
    public final SimpleStringProperty rightLdaObstacleSlopeCalcBreakdown = new SimpleStringProperty();
    public final SimpleStringProperty rightLdaObstacleSlopeCalcBreakdownHeader = new SimpleStringProperty();
    public final SimpleStringProperty rightLdaSubBreakdown = new SimpleStringProperty();
    public final SimpleStringProperty rightLdaSubBreakdownHeader = new SimpleStringProperty();

    // TODO: Create header breakdowns to better explain what the numbers are

    public Runway(String designators, double leftTora, double leftToda, double leftLDA, double leftASDA, double rightTora, double rightToda, double rightLDA, double rightASDA){
        runwayDesignatorLeft.set(designators.split("/")[0]);
        runwayDesignatorRight.set(designators.split("/")[1]);
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
        slopeLength.bind(Bindings.when(runwayObstacle.heightProperty().multiply(SLOPE).subtract(runwayObstacle.lengthProperty().divide(2)).greaterThan(runwayObstacle.lengthProperty().divide(2).add(240))).then(runwayObstacle.heightProperty().multiply(SLOPE).subtract(runwayObstacle.lengthProperty().divide(2))).otherwise(runwayObstacle.lengthProperty().divide(2).add(240)));
        //runwayObstacle.distFromOtherThresholdProperty().bind(runwayLength.subtract(runwayObstacle.distFromThresholdProperty()));


        runwayObstacle.distFromOtherThresholdProperty().bind(inputLeftTora.subtract(dispThresholdLeft).subtract(runwayObstacle.distFromThresholdProperty()));


        recalculate();
        validityChecks();

        runwayDesignatorLeft.addListener((observableValue, s, t1) -> runwayDesignatorRight.set(calculateRunwayDesignator(runwayDesignatorLeft.get(), true)));
        runwayDesignatorRight.addListener((observableValue, s, t1) -> runwayDesignatorLeft.set(calculateRunwayDesignator(runwayDesignatorRight.get(), false)));
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
        slopeLength.bind(Bindings.when(runwayObstacle.heightProperty().multiply(SLOPE).subtract(runwayObstacle.lengthProperty().divide(2)).greaterThan(runwayObstacle.lengthProperty().divide(2).add(240))).then(runwayObstacle.heightProperty().multiply(SLOPE).subtract(runwayObstacle.lengthProperty().divide(2))).otherwise(runwayObstacle.lengthProperty().divide(2).add(240)));
        //runwayObstacle.distFromOtherThresholdProperty().bind(runwayLength.subtract(runwayObstacle.distFromThresholdProperty()));

        runwayObstacle.distFromOtherThresholdProperty().bind(inputLeftTora.subtract(dispThresholdLeft).subtract(runwayObstacle.distFromThresholdProperty()));



        recalculate();
        validityChecks();

        runwayDesignatorLeft.addListener((observableValue, s, t1) -> runwayDesignatorRight.set(calculateRunwayDesignator(runwayDesignatorLeft.get(), true)));
        runwayDesignatorRight.addListener((observableValue, s, t1) -> runwayDesignatorLeft.set(calculateRunwayDesignator(runwayDesignatorRight.get(), false)));
    }
    public String toString(boolean dual){
        String designators;
        if (dual) {
            designators = runwayDesignatorLeft.concat("/").concat(runwayDesignatorRight).get();
        } else {
            designators = runwayDesignatorLeft.get();
        }
        return designators;
    }

    /**
     * Calculates the runway designator for the runway in the opposite direction
     * @param designator The designator that has been changes, used to calculate the new designator for the runways complement direction
     */
    private String calculateRunwayDesignator(String designator, boolean left) {
        var number = String.valueOf((Integer.parseInt(designator.substring(0,2)) + 18) % 36);
        if (number.length() == 1) {
            number = "0"+number;
        }
        var character = designator.substring(2);

        var newCharacter = "";
        switch (character) {
            case "R":  newCharacter = "L"; break;
            case "L": newCharacter = "R"; break;
            case "C": newCharacter = "C"; break;
            case "" : break;
            default: {
                newCharacter = "ERROR";
                logger.error("Incorrect initial character"); break;
            }
        }

        var newDesignator = number + newCharacter;

        logger.info("Runway Designators: "+designator+", "+newDesignator);

        if ((Integer.parseInt(number) < 18 && left) || (Integer.parseInt(number) > 18 && !left)) {
            try {
                // This will cascade and correct the other designator
                return designator;
            } finally {
                // TODO : Swap left and right
                swapLeftRight();
            }
        }

        return newDesignator;
    }

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
        runwayObstacle.distFromThresholdProperty().set(obstacleToAdd.distFromThresholdProperty().get());
        hasRunwayObstacle.set(false); // Listener will call recalculate
        logger.info("Added Obstacle "+ runwayObstacle.getObstacleDesignator() + " to runway " + runwayDesignatorLeft.get());
        logChange("Added Obstacle "+ runwayObstacle.getObstacleDesignator() + " to runway " + runwayDesignatorLeft.get());

    }

    /**
     * Removing the obstacle from the runway
     */
    public void removeObstacle() {
        hasRunwayObstacle.set(false);
        logger.info("Removed Obstacle from runway " + runwayDesignatorLeft.get());
        logger.info("Return runway to original state");
        logChange("Removed Obstacle from runway " + runwayDesignatorLeft.get());
    }

    /**
     * Recalculates runway values based on objects distance from the left hand threshold
     */
    public void recalculate(){

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
            if (directionLeft.get()) {
                logger.info("Calculate take-off towards for left");
                calculateTakeOffToward();
                RunwayCalculations.calculateTakeOffTowardLeft(this);
                RunwayCalculations.calculateLandTowardLeft(this); // done
            } else {
                logger.info("Calculate take-off away for left");
                calculateTakeOffAway();
                RunwayCalculations.calculateTakeOffAwayLeft(this);
                RunwayCalculations.calculateLandOverLeft(this); // done
            }
            if (directionRight.get()) {
                logger.info("Calculate land towards for right");
                RunwayCalculations.calculateTakeOffTowardRight(this);
                RunwayCalculations.calculateLandTowardRight(this); // done
            } else {
                logger.info("Calculate land over for right");
                RunwayCalculations.calculateTakeOffAwayRight(this);
                RunwayCalculations.calculateLandOverRight(this); // done
            }
        } else {
            logger.info("Runway has no obstacle: runway returned to original state");
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
        rightTakeOff.bind(Bindings.and(Bindings.greaterThanOrEqual(inputRightAsda,inputRightTora),Bindings.greaterThanOrEqual(inputRightToda,inputRightAsda)));
        rightLand.bind(Bindings.and(Bindings.greaterThanOrEqual(inputRightLda,0),
                Bindings.lessThanOrEqual(inputRightLda,inputRightTora))
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
     * @param distFromThreshold The correct distance from threshold for the obstacle for the direction
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
                                        .add(STRIPEND),BLASTZONE.add(distFromThreshold))).then(distFromThreshold
                        .add(obstacleSlopeCalculation).add(STRIPEND)).otherwise(BLASTZONE.add(distFromThreshold)));

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
     * @return The appropriate slope over an obstacle
     */
    private SimpleDoubleProperty getObstacleSlopeCalculation(boolean left) {
        var obstacleSlopeCalculation = new SimpleDoubleProperty();
        obstacleSlopeCalculation
                .bind(Bindings
                        .when(Bindings
                                .greaterThan(runwayObstacle.heightProperty()
                                        .multiply(SLOPE),MINRESA
                                        .add(runwayObstacle.lengthProperty()
                                                .divide(2))))
                        .then(runwayObstacle
                                .heightProperty()
                                .multiply(SLOPE))
                        .otherwise(MINRESA
                                .add(runwayObstacle
                                        .lengthProperty()
                                        .divide(2))));

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
     * Calculations for when a plane is taking-off towards an obstacle
     */
    public void calculateTakeOffToward() {

        // Calculate right take-off values, taking off away from the obstacle
        rightTora.bind(
                runwayObstacle.distFromThresholdProperty()
                        .subtract(
                                Bindings.max(
                                        BLASTZONE,
                                        STRIPEND.add(MINRESA)
                                ))
                        .add(dispThresholdLeft)
                        .subtract(runwayObstacle.lengthProperty().divide(2))
        );

        // Ensure Declared distance isn't more than original value
        if (rightTora.get() > inputRightTora.get()) {
            rightTora.bind(inputRightTora);
            rightToraBreakdown.bind(
                    new SimpleStringProperty(
                            "(Right) Calculated TORA greater than original TORA, original TORA taken as output"
                    )
            );
            rightTodaBreakdownHeader.bind(new SimpleStringProperty("N/A"));
        } else {
            rightToraBreakdown.bind(
                    new SimpleStringProperty("Right TORA = ")
                            .concat(runwayObstacle.distFromThresholdProperty().intValue())
                            .concat(" - ")
                            .concat(
                                    Bindings.when(
                                            Bindings.greaterThan(
                                                    BLASTZONE, STRIPEND.add(MINRESA)
                                            )
                                    ).then(
                                            new SimpleStringProperty().concat(BLASTZONE)
                                    ).otherwise(
                                            new SimpleStringProperty("(")
                                                    .concat(STRIPEND)
                                                    .concat(" + ")
                                                    .concat(MINRESA)
                                                    .concat(")")
                                    )
                            )
                            .concat(" + ")
                            .concat(dispThresholdLeft.intValue())
                            .concat(" - (")
                            .concat(runwayObstacle.lengthProperty().intValue())
                            .concat(" / ")
                            .concat(2)
                            .concat(") = ")
                            .concat(rightTora.intValue())
            );
            rightTodaBreakdownHeader.bind(
                    new SimpleStringProperty("Right TORA = Obstacle dist from left threshold - ")
                            .concat(
                                    Bindings.when(
                                            Bindings.greaterThan(
                                                    BLASTZONE, STRIPEND.add(MINRESA)
                                            )
                                    ).then(
                                            new SimpleStringProperty("Blastzone")
                                    ).otherwise(
                                            new SimpleStringProperty("(Stripend + Minimum RESA)")
                                    )
                            )
                            .concat(" + Left displaced threshold - (Runway length / 2)")
            );
        }

        rightAsda.bind(rightTora.add(stopwayLeft));
        rightAsdaBreakdown.bind(
                new SimpleStringProperty("Right ASDA = ")
                        .concat(rightTora.intValue())
                        .concat(" + ")
                        .concat(stopwayLeft.intValue())
                        .concat(" = ")
                        .concat(rightAsda.intValue())
        );
        rightAsdaBreakdownHeader.bind(
                new SimpleStringProperty("Right ASDA = Right TORA + Left stopway")
        );

        rightToda.bind(rightTora.add(clearwayLeft));
        rightTodaBreakdown.bind(
                new SimpleStringProperty("Right TODA = ")
                        .concat(rightTora.intValue())
                        .concat(" + ")
                        .concat(clearwayLeft.intValue())
                        .concat(" = ")
                        .concat(rightToda.intValue())
        );
        rightTodaBreakdownHeader.bind(
                new SimpleStringProperty("Right TODA = Right TORA + Left clearway")
        );

        // Calculate left take-off values, taking off towards the obstacle
        leftTora.bind(
                runwayObstacle.distFromThresholdProperty()
                        .add(dispThresholdLeft)
                        .subtract(Bindings.max(
                                runwayObstacle.heightProperty().multiply(SLOPE),
                                MINRESA.add(runwayObstacle.lengthProperty().divide(2))
                        ))
                        .subtract(STRIPEND)
        );

        // Ensure Declared distance isn't more than original value
        if (leftTora.get() > inputLeftTora.get()) {
            var distanceFromToraEnd = new SimpleDoubleProperty();
            distanceFromToraEnd.bind(leftTora.subtract(inputLeftTora));
            leftTora.bind(inputLeftTora);

            leftToraBreakdown.bind(
                    new SimpleStringProperty(
                            "(Left) Calculated  TORA greater than original TORA, original TORA taken as output"
                    )
            );
            leftToraBreakdownHeader.bind(new SimpleStringProperty("N/A"));

            leftAsda.bind(Bindings.min(leftTora.add(distanceFromToraEnd), leftTora.add(stopwayRight)));

            leftAsdaBreakdown.bind(
                    new SimpleStringProperty("Left ASDA = ")
                            .concat(leftTora.intValue())
                            .concat(" + ")
                            .concat(Math.min(distanceFromToraEnd.intValue(), stopwayRight.intValue()))
                            .concat(" = ")
                            .concat(leftAsda.intValue())
            );
            leftAsdaBreakdownHeader.bind(
                    new SimpleStringProperty("Left ASDA = Left TORA + ")
                            .concat(Bindings.when(
                                    Bindings.lessThan(
                                            distanceFromToraEnd, stopwayRight
                                    ))
                                    .then(new SimpleStringProperty("Obstacle dist from TORA end"))
                                    .otherwise(new SimpleStringProperty("Right stopway"))
                            )
            );

            leftToda.bind(Bindings.min(leftTora.add(distanceFromToraEnd), leftTora.add(clearwayRight)));

            leftTodaBreakdown.bind(
                    new SimpleStringProperty("Left TODA = ")
                            .concat(leftTora.intValue())
                            .concat(" + ")
                            .concat(Math.min(distanceFromToraEnd.intValue(), clearwayRight.intValue()))
                            .concat(" = ")
                            .concat(leftToda.intValue())
            );
            leftTodaBreakdownHeader.bind(
                    new SimpleStringProperty("Left TODA = Left TORA + ")
                            .concat(Bindings.when(
                                    Bindings.lessThan(
                                            distanceFromToraEnd, clearwayRight
                                    ))
                                    .then(new SimpleStringProperty("Obstacle dist from TORA end"))
                                    .otherwise("Right clearway")
                            )
            );

        } else {

            leftToraBreakdown.bind(
                    new SimpleStringProperty("Left TORA = ")
                            .concat(runwayObstacle.distFromThresholdProperty().intValue())
                            .concat(" + ")
                            .concat(dispThresholdLeft.intValue())
                            .concat( " - ")
                            .concat(Bindings.when(
                                    Bindings.greaterThan(
                                            runwayObstacle.heightProperty().multiply(SLOPE),
                                            MINRESA.add(runwayObstacle.lengthProperty().divide(2))
                                    ))
                                    .then(
                                            new SimpleStringProperty("(")
                                                    .concat(runwayObstacle.heightProperty().intValue())
                                                    .concat(" x ")
                                                    .concat(SLOPE)
                                    )
                                    .otherwise(
                                            new SimpleStringProperty()
                                                    .concat(MINRESA)
                                                    .concat(" + (")
                                                    .concat(runwayObstacle.lengthProperty().intValue())
                                                    .concat(" / ")
                                                    .concat(2)
                                    )
                            )
                            .concat(") - ")
                            .concat(STRIPEND)
                            .concat(" = ")
                            .concat(leftTora.intValue())
            );

            leftToraBreakdownHeader.bind(
                    new SimpleStringProperty(
                            "Left TORA = Obstacle dist from left threshold + Left displaced threshold - ")
                            .concat(Bindings.when(
                                    Bindings.greaterThan(
                                            runwayObstacle.heightProperty().multiply(SLOPE),
                                            MINRESA.add(runwayObstacle.lengthProperty().divide(2))
                                    ))
                                    .then(new SimpleStringProperty("(Runway height x ALS slope"))
                                    .otherwise(new SimpleStringProperty("Minimum RESA + (Runway length / 2"))
                            )
                            .concat(new SimpleStringProperty(") - Stripend"))
            );

            leftAsda.bind(leftTora);
            leftAsdaBreakdown.bind(
                    new SimpleStringProperty("Left ASDA = ")
                            .concat(leftAsda.intValue())
            );
            leftAsdaBreakdownHeader.bind(new SimpleStringProperty("Left ASDA = Left TORA"));

            leftToda.bind(leftTora);
            leftTodaBreakdown.bind(
                    new SimpleStringProperty("Left TODA = ")
                            .concat(leftToda.intValue())
            );
            leftToraBreakdownHeader.bind(new SimpleStringProperty("Left TODA = Left TORA"));
        }

    }

    /**
     * Calculations for when a plane is taking-off away from an obstacle
     */
    public void calculateTakeOffAway() {

        // Calculate right take-off values, taking off towards the obstacle
        rightTora.bind(
                runwayObstacle.distFromOtherThresholdProperty()
                        .add(dispThresholdRightProperty())
                        .subtract(
                                Bindings.max(
                                        runwayObstacle.heightProperty().multiply(SLOPE),
                                        MINRESA.add(runwayObstacle.lengthProperty().divide(2))))
                        .subtract(STRIPEND));

        // Ensure Declared distance isn't more than original value
        if (rightTora.get() > inputRightTora.get()) {
            var distanceFromToraEnd = new SimpleDoubleProperty();
            distanceFromToraEnd.bind(rightTora.subtract(inputRightTora));
            rightTora.bind(inputRightTora);

            rightToraBreakdown.bind(
                    new SimpleStringProperty(
                            "(Right) Calculated  TORA greater than original TORA, original TORA taken as output"
                    )
            );
            rightTodaBreakdownHeader.bind(new SimpleStringProperty("N/A"));

            rightAsda.bind(Bindings.min(rightTora.add(distanceFromToraEnd), rightTora.add(stopwayLeft)));

            rightAsdaBreakdown.bind(
                    new SimpleStringProperty("Right ASDA = ")
                            .concat(rightTora.intValue())
                            .concat(" + ")
                            .concat(Math.min(distanceFromToraEnd.intValue(), stopwayLeft.intValue()))
                            .concat(" = ")
                            .concat(rightAsda.intValue())
            );
            rightAsdaBreakdownHeader.bind(
                    new SimpleStringProperty("Right ASDA = Right TORA + ")
                            .concat(Bindings.when(
                                    Bindings.greaterThan(
                                            distanceFromToraEnd, stopwayLeft
                                    ))
                                    .then(new SimpleStringProperty("Obstacle dist from TORA end"))
                                    .otherwise(new SimpleStringProperty("Left stopway"))
                            )
            );

            rightToda.bind(Bindings.min(rightTora.add(distanceFromToraEnd), rightTora.add(clearwayLeft)));

            rightTodaBreakdown.bind(
                    new SimpleStringProperty("Right TODA = ")
                            .concat(rightTora.intValue())
                            .concat(" + ")
                            .concat(Math.min(distanceFromToraEnd.intValue(), clearwayLeft.intValue()))
                            .concat(" = ")
                            .concat(rightToda.intValue())
            );
            rightTodaBreakdownHeader.bind(
                    new SimpleStringProperty("Right TODA = Right TORA + ")
                            .concat(Bindings.when(
                                    Bindings.greaterThan(
                                            distanceFromToraEnd, clearwayLeft
                                    ))
                                    .then(new SimpleStringProperty("Obstacle dist from TORA end"))
                                    .otherwise(new SimpleStringProperty("Left clearway"))
                            )
            );

        } else {

            rightToraBreakdown.bind(
                    new SimpleStringProperty("Right TORA = ")
                            .concat(runwayObstacle.distFromOtherThresholdProperty().intValue())
                            .concat(" + ")
                            .concat(dispThresholdRight.intValue())
                            .concat(" - ")
                            .concat(Bindings.when(
                                    Bindings.greaterThan(
                                            runwayObstacle.heightProperty().multiply(SLOPE),
                                            MINRESA.add(runwayObstacle.lengthProperty().divide(2))
                                    ))
                                    .then(
                                            new SimpleStringProperty("(")
                                                    .concat(runwayObstacle.heightProperty().intValue())
                                                    .concat(" x ")
                                                    .concat(SLOPE)
                                    )
                                    .otherwise(
                                            new SimpleStringProperty()
                                                    .concat(MINRESA)
                                                    .concat(" + (")
                                                    .concat(runwayObstacle.lengthProperty().intValue())
                                                    .concat(" / ")
                                                    .concat(2)
                                    )
                            )
                            .concat(") - ")
                            .concat(STRIPEND)
                            .concat(" = ")
                            .concat(rightTora.intValue())
            );
            rightToraBreakdownHeader.bind(
                    new SimpleStringProperty("Right TORA = Obstacle dist from right threshold + Right displaced threshold - ")
                            .concat(Bindings.when(
                                    Bindings.greaterThan(
                                            runwayObstacle.heightProperty().multiply(SLOPE),
                                            MINRESA.add(runwayObstacle.lengthProperty().divide(2))
                                    ))
                                    .then(new SimpleStringProperty("(Runway height x ALS slope"))
                                    .otherwise(new SimpleStringProperty("Minimum RESA + (Runway length / 2"))
                            )
                            .concat(new SimpleStringProperty(") - Stripend"))
            );

            rightAsda.bind(rightTora);
            rightAsdaBreakdown.bind(
                    new SimpleStringProperty("Right ASDA = ")
                            .concat(rightAsda.intValue())
            );
            rightAsdaBreakdownHeader.bind(new SimpleStringProperty("Right ASDA = Right TORA"));

            rightToda.bind(rightTora);
            rightTodaBreakdown.bind(
                    new SimpleStringProperty("Right TODA = ")
                            .concat(rightToda.intValue())
            );
            rightTodaBreakdownHeader.bind(new SimpleStringProperty("Right TODA = Right TORA"));
        }

        // Calculate left take-off values, taking off away from the obstacle
        leftTora.bind(
                inputLeftTora
                        .subtract(runwayObstacle.distFromThresholdProperty())
                        .subtract(Bindings.max(
                                BLASTZONE, STRIPEND.add(MINRESA)
                                )
                        )
                        .subtract(dispThresholdLeft)
                        .subtract(
                                runwayObstacle.lengthProperty().divide(2)
                        )
        );

        leftToraBreakdown.bind(
                new SimpleStringProperty("Left TORA = ")
                        .concat(inputLeftTora.intValue())
                        .concat(" - ")
                        .concat(runwayObstacle.distFromThresholdProperty().intValue())
                        .concat(" - ")
                        .concat(Bindings.when(
                                Bindings.lessThan(
                                        BLASTZONE, STRIPEND.add(MINRESA)
                                ))
                                .then(new SimpleStringProperty().concat(BLASTZONE))
                                .otherwise(
                                        new SimpleStringProperty("(")
                                                .concat(STRIPEND)
                                                .concat(" + ")
                                                .concat(MINRESA)
                                                .concat(")")
                                )
                        )
                        .concat(" - ")
                        .concat(dispThresholdRight.intValue())
                        .concat(" - (")
                        .concat(runwayObstacle.lengthProperty().intValue())
                        .concat(" / ")
                        .concat(2)
                        .concat(") = ")
                        .concat(leftTora.intValue())
        );

        leftToraBreakdownHeader.bind(
                new SimpleStringProperty("Left TORA = Original left TORA - Obstacle dist from left threshold - ")
                        .concat(
                                Bindings.when(
                                        Bindings.lessThan(
                                                BLASTZONE, STRIPEND.add(MINRESA)
                                        ))
                                        .then(new SimpleStringProperty("Blastzone"))
                                        .otherwise("(Stripend + Minimum RESA)")
                        )
                        .concat(new SimpleStringProperty(" - Right displaced threshold - (Runway length / 2)"))
        );

        leftAsda.bind(leftTora.add(stopwayRight));
        leftAsdaBreakdown.bind(
                new SimpleStringProperty("Left ASDA = ")
                        .concat(leftTora.intValue())
                        .concat(" + ")
                        .concat(stopwayRight.intValue())
                        .concat(" = ")
                        .concat(leftAsda.intValue())
        );
        leftAsdaBreakdownHeader.bind(new SimpleStringProperty("Left ASDA = Left TORA - Right stopway"));

        leftToda.bind(leftTora.add(clearwayRight));
        leftTodaBreakdown.bind(
                new SimpleStringProperty("Left TODA = ")
                        .concat(leftTora.intValue())
                        .concat(" + ")
                        .concat(clearwayRight.intValue())
                        .concat(" = ")
                        .concat(leftToda.intValue())
        );
        leftTodaBreakdownHeader.bind(new SimpleStringProperty("Left TODA = Left TORA + Right clearway"));
    }

    /**
     * Adds a change to changes history pane
     *
     * @param change text to be displayed in change history tab
     */
    public void logChange(String change) {
        changeHistory.add(change);
        Label changeLabel = new Label(change);
        changeLabel.setMaxWidth(changesHistory.getWidth());
        changeLabel.setMaxHeight(40);
        changeLabel.setWrapText(true);
        double newLabelHeight = changeLabel.getHeight();
        ObservableList<Node> children = changesHistory.getChildren();
        for (Node child : children) {
            double currentY = child.getLayoutY();
            child.setLayoutY(currentY + 40);
        }
        changeLabel.setLayoutY(0);
        children.add(0, changeLabel);
    }





    // Getters

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
        return leftToraBreakdownHeader;
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
        this.units.set(units);
    }


}
