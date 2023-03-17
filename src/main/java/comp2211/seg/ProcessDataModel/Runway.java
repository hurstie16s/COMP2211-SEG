package comp2211.seg.ProcessDataModel;

import comp2211.seg.Controller.Interfaces.GlobalVariables;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
    private final SimpleStringProperty runwayDesignatorLeft = new SimpleStringProperty("VOID");
    private final SimpleStringProperty runwayDesignatorRight = new SimpleStringProperty("VOID");
    private final SimpleDoubleProperty inputRightTora = new SimpleDoubleProperty(1000);
    private final SimpleDoubleProperty inputRightToda = new SimpleDoubleProperty(1500);
    private final SimpleDoubleProperty inputRightAsda = new SimpleDoubleProperty(1150);
    private final SimpleDoubleProperty inputRightLda = new SimpleDoubleProperty(1000);
    private final SimpleDoubleProperty inputLeftTora = new SimpleDoubleProperty(1000);
    private final SimpleDoubleProperty inputLeftToda = new SimpleDoubleProperty(1500);
    private final SimpleDoubleProperty inputLeftAsda = new SimpleDoubleProperty(1150);
    private final SimpleDoubleProperty inputLeftLda = new SimpleDoubleProperty(900);
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
    /**
     * The Changes history.
     */
    public TextFlow changesHistory = new TextFlow();

    private Obstacle runwayObstacle;

    private final SimpleBooleanProperty landingMode = new SimpleBooleanProperty(true);

    private final SimpleBooleanProperty direction = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty leftTakeOff = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty leftLand = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty rightTakeOff = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty rightLand = new SimpleBooleanProperty(false);

    // End of Inputs



    // Typical values, may become variable down the line
    // Constants
    private static final SimpleDoubleProperty MINRESA = new SimpleDoubleProperty(240);
    private static final SimpleDoubleProperty STRIPEND = new SimpleDoubleProperty(60);
    private static final SimpleDoubleProperty BLASTZONE = new SimpleDoubleProperty(500);
    private static final SimpleDoubleProperty SLOPE = new SimpleDoubleProperty(50);
    private static final SimpleDoubleProperty STOPWAYMIN = new SimpleDoubleProperty(0);

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
                runwayDesignatorLeft,
                runwayLength,
                hasRunwayObstacle
        }) {
            prop.addListener((observableValue, o, t1) -> recalculate());
        }
        logger.info("Created Runway object");

        logger.info("Created initial VOID runway obstacle");
        runwayObstacle = new Obstacle("VOID", 0,0, 0);

        runwayLength.bind(Bindings.when(Bindings.greaterThan(inputLeftTora,inputRightTora)).then(inputLeftTora).otherwise(inputRightTora));
        clearwayLeft.bind(inputRightToda.subtract(inputRightTora));
        stopwayLeft.bind(inputRightAsda.subtract(inputRightTora));
        dispThresholdRight.bind(inputRightTora.subtract(inputRightLda));
        clearwayRight.bind(inputLeftToda.subtract(inputLeftTora));
        stopwayRight.bind(inputLeftAsda.subtract(inputLeftTora));
        dispThresholdLeft.bind(inputLeftTora.subtract(inputLeftLda));

        recalculate();
        validityChecks();

        runwayDesignatorLeft.addListener((observableValue, s, t1) -> runwayDesignatorRight.set(calculateRunwayDesignator(runwayDesignatorLeft.get())));
        runwayDesignatorRight.addListener((observableValue, s, t1) -> runwayDesignatorLeft.set(calculateRunwayDesignator(runwayDesignatorRight.get())));
    }

    /**
     * Calculates the runway designator for the runway in the opposite direction
     * @param designator The designator that has been changes, used to calculate the new designator for the runways complement direction
     */
    private String calculateRunwayDesignator(String designator) {
        var number = String.valueOf((Integer.parseInt(designator.substring(0,2)) + 18) % 36);
        if (number.length() == 1) {
            number = "0"+number;
        }
        var character = designator.substring(2);

        var newCharacter = "";
        switch (character) {
            case "R" -> newCharacter = "L";
            case "L" -> newCharacter = "R";
            case "C" -> newCharacter = "C";
            default -> {
                newCharacter = "ERROR";
                logger.error("Incorrect initial character");
            }
        }

        var newDesignator = number + newCharacter;

        logger.info("Runway Designators: "+designator+", "+newDesignator);

        return newDesignator;
    }

    /**
     * Adds an obstacle to the list of obstacles on the runway.
     */
    public void addObstacle(Obstacle obstacleToAdd) {
        runwayObstacle = obstacleToAdd;
        hasRunwayObstacle.set(true); // Listener will call recalculate
        logger.info("Added Obstacle "+ runwayObstacle.getObstacleDesignator() + " to runway " + runwayDesignatorLeft.get());
    }

    /**
     * Removing the obstacle from the runway
     */
    public void removeObstacle() {
        runwayObstacle = null;
        hasRunwayObstacle.set(false);
        logger.info("Removed Obstacle from runway " + runwayDesignatorLeft.get());
        logger.info("Return runway to original state");
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
            if (runwayObstacle.getDistFromThreshold() *  2 > runwayLength.get()) {
                logger.info("Calculate take-off towards for left");
                logger.info("Calculate land towards for right");
                calculateTakeOffToward();
                calculateLandTowards();
            } else {
                logger.info("Calculate take-off away for left");
                logger.info("Calculate land over for right");
                calculateLandOver();
                calculateTakeOffAway();
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
        label.setFont(GlobalVariables.font);
        Text label2 = new Text();
        label2.textProperty().bind(new SimpleStringProperty("Top TODA (").concat(inputLeftToda.asString()).concat(") < Top ASDA (").concat( inputLeftAsda.asString()).concat(")"));
        label2.visibleProperty().bind(Bindings.greaterThanOrEqual(inputLeftToda,inputLeftAsda).not());
        label2.setFill(Color.RED);
        label2.setFont(GlobalVariables.font);
        Text label3 = new Text();
        label3.textProperty().bind(new SimpleStringProperty("Top LDA (").concat(inputLeftLda.asString()).concat(") < 0"));
        label3.visibleProperty().bind(Bindings.greaterThanOrEqual(inputLeftLda,0).not());
        label3.setFill(Color.RED);
        label3.setFont(GlobalVariables.font);
        Text label4 = new Text();
        label4.textProperty().bind(new SimpleStringProperty("Top TORA (").concat(inputLeftTora.asString()).concat(") < Top LDA (").concat( inputLeftLda.asString()).concat(")"));
        label4.visibleProperty().bind(Bindings.lessThanOrEqual(inputLeftLda,inputLeftTora).not());
        label4.setFill(Color.RED);
        label4.setFont(GlobalVariables.font);
        Text label5= new Text();
        label5.textProperty().bind(new SimpleStringProperty("Bottom ASDA (").concat(inputRightAsda.asString()).concat(") < Bottom TORA (").concat( inputRightTora.asString()).concat(")"));
        label5.visibleProperty().bind(Bindings.greaterThanOrEqual(inputRightAsda,inputRightTora).not());
        label5.setFill(Color.RED);
        label5.setFont(GlobalVariables.font);
        Text label6= new Text();
        label6.textProperty().bind(new SimpleStringProperty("Bottom TODA (").concat(inputRightToda.asString()).concat(") < Bottom ASDA (").concat( inputRightAsda.asString()).concat(")"));
        label6.visibleProperty().bind(Bindings.greaterThanOrEqual(inputRightToda,inputRightAsda).not());
        label6.setFill(Color.RED);
        label6.setFont(GlobalVariables.font);
        Text label7= new Text();
        label7.textProperty().bind(new SimpleStringProperty("Bottom LDA (").concat(inputRightLda.asString()).concat(") < 0"));
        label7.visibleProperty().bind(Bindings.greaterThanOrEqual(inputRightLda,0).not());
        label7.setFill(Color.RED);
        label7.setFont(GlobalVariables.font);
        Text label8 = new Text();
        label8.textProperty().bind(new SimpleStringProperty("Bottom TORA (").concat(inputRightTora.asString()).concat(") < Bottom LDA (").concat( inputRightLda.asString()).concat(")"));
        label8.visibleProperty().bind(Bindings.lessThanOrEqual(inputRightLda,inputRightTora).not());
        label8.setFill(Color.RED);
        label8.setFont(GlobalVariables.font);
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
     * Calculations for when a plane is landing over an obstacle
     */
// TODO: Check working
    public void calculateLandOver() {

        // Calculate Land Over for Left

        var ldaSubtraction = getLdaSubtraction(runwayObstacle.distFromThresholdProperty());

        leftLda.bind(inputLeftLda.subtract(ldaSubtraction));

        logger.info("New LDA calculated for landing over an obstacle for runway "+runwayDesignatorLeft.get());

        // Calculate Land Towards for Right

        rightLda.bind(runwayObstacle.distFromOtherThresholdProperty().subtract(MINRESA).subtract(STRIPEND));

        logger.info("New LDA calculated for landing towards and obstacle for runway "+runwayDesignatorRight.get());
    }

    /**
     * Calculations for when a plane is landing towards an obstacle
     */
    public void calculateLandTowards() {
        /*
        Not really needed for landing calc
        workingTora.bind(runwayObstacle.distFromThresholdProperty().subtract(runwayObstacle.heightProperty().multiply(50)).subtract(STRIPEND.get()));
        workingAsda.bind(workingTora);
        workingToda.bind(workingTora);
         */

        // Calculate Land Towards for Left

        leftLda.bind(runwayObstacle.distFromThresholdProperty().subtract(MINRESA).subtract(STRIPEND));

        logger.info("New LDA calculated for landing towards and obstacle for runway "+runwayDesignatorLeft.get());

        // Calculate Land Over for Right

        var ldaSubtraction = getLdaSubtraction(runwayObstacle.distFromOtherThresholdProperty());

        rightLda.bind(inputRightLda.subtract(ldaSubtraction));

        logger.info("New LDA calculated for landing over an obstacle for runway "+runwayDesignatorRight.get());

    }

    /**
     * Calculating the LDA subtraction
     * @param distFromThreshold The correct distance from threshold for the obstacle for the direction
     * @return The calculated subtraction from the LDA
     */
    private SimpleDoubleProperty getLdaSubtraction(SimpleDoubleProperty distFromThreshold) {

        var obstacleSlopeCalculation = getObstacleSlopeCalculation();

        var ldaSubtraction = new SimpleDoubleProperty();
        ldaSubtraction.bind(
                Bindings.when(
                        Bindings.greaterThan(
                                distFromThreshold
                                        .add(obstacleSlopeCalculation)
                                        .add(STRIPEND),BLASTZONE)).then(distFromThreshold
                        .add(obstacleSlopeCalculation).add(STRIPEND)).otherwise(BLASTZONE));

        logger.info("LDA Subtraction: "+ldaSubtraction.get());

        return ldaSubtraction;
    }

    /**
     * Calculate the slope value for an obstacle
     * @return The appropriate slope over an obstacle
     */
    private SimpleDoubleProperty getObstacleSlopeCalculation() {
        var obstacleSlopeCalculation = new SimpleDoubleProperty();
        obstacleSlopeCalculation
                .bind(Bindings
                        .when(Bindings
                                .greaterThan(runwayObstacle.heightProperty()
                                        .multiply(SLOPE),MINRESA
                                        .add(runwayObstacle.widthProperty()
                                                .divide(2))))
                        .then(runwayObstacle
                                .heightProperty()
                                .multiply(SLOPE))
                        .otherwise(MINRESA
                                .add(runwayObstacle
                                        .widthProperty()
                                        .divide(2))));

        logger.info("Obstacle Slop Calculation: "+obstacleSlopeCalculation.get());

        return obstacleSlopeCalculation;
    }

    /**
     * Calculations for when a plane is taking-off towards an obstacle
     */
    public void calculateTakeOffToward() {


        rightTora.bind(inputLeftTora.subtract(dispThresholdLeft).subtract(runwayObstacle.distFromThresholdProperty()).subtract(Bindings.max(runwayObstacle.heightProperty().multiply(SLOPE), MINRESA.add(runwayObstacle.widthProperty().divide(2)))).subtract(STRIPEND));
        rightAsda.bind(rightTora);
        rightToda.bind(rightTora);

        leftTora.bind(runwayObstacle.distFromThresholdProperty().add(dispThresholdLeft).subtract(Bindings.max(runwayObstacle.heightProperty().multiply(SLOPE), MINRESA.add(runwayObstacle.widthProperty().divide(2)))).subtract(STRIPEND));
        leftAsda.bind(leftTora);
        leftToda.bind(leftTora);
    }

    /**
     * Calculations for when a plane is taking-off away from an obstacle
     */
    public void calculateTakeOffAway() {

        rightTora.bind(runwayObstacle.distFromThresholdProperty().subtract(Bindings.max(BLASTZONE, STRIPEND.add(MINRESA))).add(dispThresholdLeft));
        rightAsda.bind(rightTora.add(stopwayLeft));
        rightToda.bind(rightTora.add(clearwayLeft));
        leftTora.bind(inputLeftTora.subtract(runwayObstacle.distFromThresholdProperty()).subtract(Bindings.max(BLASTZONE, STRIPEND.add(MINRESA))).subtract(dispThresholdLeft));
        leftAsda.bind(leftTora.add(stopwayRight));
        leftToda.bind(leftTora.add(clearwayRight));

        /*
        not needed for take-off
        workingLda.bind(lda.subtract(runwayObstacle.distFromThresholdProperty()).subtract(runwayObstacle.heightProperty().multiply(SLOPE).subtract(STRIPEND.get())));
        */
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

    public SimpleStringProperty runwayDesignatorRightProperty() {
        return runwayDesignatorRight;
    }

    /**
     * Returns the value of direction.
     *
     * @return The value of direction.
     */
    public boolean isDirection() {
        return direction.get();
    }

    /**
     * Returns the SimpleBooleanProperty object representing direction.
     *
     * @return The SimpleBooleanProperty object representing direction.
     */
    public SimpleBooleanProperty directionProperty() {
        return direction;
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
}
