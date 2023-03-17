package comp2211.seg.ProcessDataModel;

import comp2211.seg.Controller.Interfaces.GlobalVariables;
import comp2211.seg.Controller.Stage.Theme;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private final SimpleStringProperty runwayDesignatorLeft = new SimpleStringProperty("09L");
    private final SimpleStringProperty runwayDesignatorRight = new SimpleStringProperty("");
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
    public TextFlow changesHistory = new TextFlow();

    public Obstacle runwayObstacle = new Obstacle("One", 10,0);;

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
        runwayLength.bind(Bindings.when(Bindings.greaterThan(inputLeftTora,inputRightTora)).then(inputLeftTora).otherwise(inputRightTora));
        clearwayLeft.bind(inputRightToda.subtract(inputRightTora));
        stopwayLeft.bind(inputRightAsda.subtract(inputRightTora));
        dispThresholdRight.bind(inputRightTora.subtract(inputRightLda));
        clearwayRight.bind(inputLeftToda.subtract(inputLeftTora));
        stopwayRight.bind(inputLeftAsda.subtract(inputLeftTora));
        dispThresholdLeft.bind(inputLeftTora.subtract(inputLeftLda));
        recalculate();
        validityChecks();
        calculateRunwayDesignatorLeft();
    }

    /**
     * Calculates the runway designator for the runway in the opposite direction
     */
    private void calculateRunwayDesignatorLeft() {
        var number = (Integer.parseInt(runwayDesignatorLeft.get().substring(0,2)) + 18) % 36;
        var character = runwayDesignatorLeft.get().substring(2);
        character = switch(character) {
            case "R" -> "L";
            case "L" -> "R";
            case "C" -> "C";
            default -> "error";
        };
        runwayDesignatorRight.set(number + character);
    }

    /**

     Adds an obstacle to the list of obstacles on the runway.
     */
    public void addObstacle() {
        hasRunwayObstacle.set(true);
        logger.info("Added Obstacle "+ runwayObstacle.getObstacleDesignator() + " to runway " + runwayDesignatorLeft.get());
    }
    /**
     * Removing the obstacle from the runway
     * @param obstacleToRemove The obstacle to remove from the runway
     */
    public void removeObstacle(Obstacle obstacleToRemove) {
        hasRunwayObstacle.set(false);

        recalculate();
        logger.info("Removed Obstacle "+ runwayObstacle.getObstacleDesignator() + " from runway " + runwayDesignatorLeft.get());
        logger.info("Return runway to original state");
    }

    /**
     *
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
        label.setFont(Theme.font);
        Text label2 = new Text();
        label2.textProperty().bind(new SimpleStringProperty("Top TODA (").concat(inputLeftToda.asString()).concat(") < Top ASDA (").concat( inputLeftAsda.asString()).concat(")"));
        label2.visibleProperty().bind(Bindings.greaterThanOrEqual(inputLeftToda,inputLeftAsda).not());
        label2.setFill(Color.RED);
        label2.setFont(Theme.font);
        Text label3 = new Text();
        label3.textProperty().bind(new SimpleStringProperty("Top LDA (").concat(inputLeftLda.asString()).concat(") < 0"));
        label3.visibleProperty().bind(Bindings.greaterThanOrEqual(inputLeftLda,0).not());
        label3.setFill(Color.RED);
        label3.setFont(Theme.font);
        Text label4 = new Text();
        label4.textProperty().bind(new SimpleStringProperty("Top TORA (").concat(inputLeftTora.asString()).concat(") < Top LDA (").concat( inputLeftLda.asString()).concat(")"));
        label4.visibleProperty().bind(Bindings.lessThanOrEqual(inputLeftLda,inputLeftTora).not());
        label4.setFill(Color.RED);
        label4.setFont(Theme.font);
        Text label5= new Text();
        label5.textProperty().bind(new SimpleStringProperty("Bottom ASDA (").concat(inputRightAsda.asString()).concat(") < Bottom TORA (").concat( inputRightTora.asString()).concat(")"));
        label5.visibleProperty().bind(Bindings.greaterThanOrEqual(inputRightAsda,inputRightTora).not());
        label5.setFill(Color.RED);
        label5.setFont(Theme.font);
        Text label6= new Text();
        label6.textProperty().bind(new SimpleStringProperty("Bottom TODA (").concat(inputRightToda.asString()).concat(") < Bottom ASDA (").concat( inputRightAsda.asString()).concat(")"));
        label6.visibleProperty().bind(Bindings.greaterThanOrEqual(inputRightToda,inputRightAsda).not());
        label6.setFill(Color.RED);
        label6.setFont(Theme.font);
        Text label7= new Text();
        label7.textProperty().bind(new SimpleStringProperty("Bottom LDA (").concat(inputRightLda.asString()).concat(") < 0"));
        label7.visibleProperty().bind(Bindings.greaterThanOrEqual(inputRightLda,0).not());
        label7.setFill(Color.RED);
        label7.setFont(Theme.font);
        Text label8 = new Text();
        label8.textProperty().bind(new SimpleStringProperty("Bottom TORA (").concat(inputRightTora.asString()).concat(") < Bottom LDA (").concat( inputRightLda.asString()).concat(")"));
        label8.visibleProperty().bind(Bindings.lessThanOrEqual(inputRightLda,inputRightTora).not());
        label8.setFill(Color.RED);
        label8.setFont(Theme.font);
        VBox labels = new VBox(label,label2,label3,label4,label5,label6,label7,label8);
        labels.setAlignment(Pos.CENTER);
        errorPane.setCenter(labels);
        width.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                labels.setMaxWidth((Double) t1);
                labels.setMinWidth((Double) t1);
                errorPane.setMaxWidth((Double) t1);
                errorPane.setMinWidth((Double) t1);
            }
        });
        height.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                labels.setMaxHeight((Double) t1);
                labels.setMinHeight((Double) t1);
                errorPane.setMaxHeight((Double) t1);
                errorPane.setMinHeight((Double) t1);
            }
        });

        return errorPane;
    }

    /**
     Calculations for when a plane is landing over an obstacle
     */
    // TODO: Check working
    public void calculateLandOver() {
        /*
        Not really needed for landing calculations
        workingTora.bind(tora.subtract(BLASTZONE).subtract(runwayObstacle.distFromThresholdProperty()).subtract(dispThreshold));
        workingAsda.bind(workingTora.add(stopway));
        workingToda.bind(workingTora.add(clearway));
         */
        /*


        SimpleDoubleProperty rightLdaSubtraction= new SimpleDoubleProperty();

        leftLdaSubtraction.bind(
                Bindings.when(
                        Bindings.greaterThan(
                                runwayObstacle.distFromThresholdProperty()
                                        .add(obstacleSlopeCalculation)
                                        .add(STRIPEND),BLASTZONE)).then(runwayObstacle.distFromThresholdProperty()
                        .add(obstacleSlopeCalculation).add(STRIPEND)).otherwise(BLASTZONE));

        rightLdaSubtraction.bind(
                Bindings.when(
                        Bindings.greaterThan(
                                inputLeftLda
                                        .subtract(runwayObstacle.distFromThresholdProperty())
                                        .add(obstacleSlopeCalculation)
                                        .add(STRIPEND),BLASTZONE))
                        .then(inputLeftLda
                                .subtract(runwayObstacle.distFromThresholdProperty())
                                .add(obstacleSlopeCalculation)
                                .add(STRIPEND))
                        .otherwise(BLASTZONE));

        rightLda.bind(inputRightLda.subtract(rightLdaSubtraction));
         */

        // Calculate Land Over for Left

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

        var ldaSubtraction = new SimpleDoubleProperty();
        ldaSubtraction.bind(
                Bindings.when(
                        Bindings.greaterThan(
                                runwayObstacle.distFromThresholdProperty()
                                        .add(obstacleSlopeCalculation)
                                        .add(STRIPEND),BLASTZONE)).then(runwayObstacle.distFromThresholdProperty()
                        .add(obstacleSlopeCalculation).add(STRIPEND)).otherwise(BLASTZONE));

        leftLda.bind(inputLeftLda.subtract(ldaSubtraction));

        // Calculate Land Towards for Right

        rightLda.bind(inputRightLda.subtract(runwayObstacle.distFromOtherThresholdProperty()).subtract(MINRESA).subtract(STRIPEND));
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

        // Calculate Land Towards for Left

        // Calculate Land Over for Right

        leftLda.bind(runwayObstacle.distFromThresholdProperty().subtract(MINRESA).subtract(STRIPEND));
        rightLda.bind(inputLeftLda.subtract(runwayObstacle.distFromThresholdProperty()).subtract(MINRESA).subtract(STRIPEND));
        logger.info("Re-calculated LDA");

    }

    /**
    Calculations for when a plane is taking-off towards an obstacle
     */
    public void calculateTakeOffToward() {


        rightTora.bind(inputLeftTora.subtract(dispThresholdLeft).subtract(runwayObstacle.distFromThresholdProperty()).subtract(Bindings.max(runwayObstacle.heightProperty().multiply(SLOPE), MINRESA.add(runwayObstacle.widthProperty().divide(2)))).subtract(STRIPEND));
        rightAsda.bind(rightTora);
        rightToda.bind(rightTora);

        leftTora.bind(runwayObstacle.distFromThresholdProperty().add(dispThresholdLeft).subtract(Bindings.max(runwayObstacle.heightProperty().multiply(SLOPE), MINRESA.add(runwayObstacle.widthProperty().divide(2)))).subtract(STRIPEND));
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
        return runwayDesignatorLeft.get();
    }
    /**
     * Returns the SimpleStringProperty object representing the runway designator.
     * @return The SimpleStringProperty object representing the runway designator.
     */
    public SimpleStringProperty runwayDesignatorProperty() {
        return runwayDesignatorLeft;
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


    public String getRightTora() {
        return rightTora.toString();
    }

    public SimpleDoubleProperty rightToraProperty() {
        return rightTora;
    }

    public String getRightToda() {
        return rightToda.toString();
    }

    public SimpleDoubleProperty rightTodaProperty() {
        return rightToda;
    }

    public String getRightAsda() {
        return rightAsda.toString();
    }

    public SimpleDoubleProperty rightAsdaProperty() {
        return rightAsda;
    }

    public String getRightLda() {
        return rightLda.toString();
    }

    public SimpleDoubleProperty rightLdaProperty() {
        return rightLda;
    }

    public String getLeftTora() {
        return leftTora.toString();
    }

    public SimpleDoubleProperty leftToraProperty() {
        return leftTora;
    }

    public String getLeftToda() {
        return leftToda.toString();
    }

    public SimpleDoubleProperty leftTodaProperty() {
        return leftToda;
    }

    public String getLeftAsda() {
        return leftAsda.toString();
    }

    public SimpleDoubleProperty leftAsdaProperty() {
        return leftAsda;
    }

    public String getLeftLda() {
        return leftLda.toString();
    }

    public SimpleDoubleProperty leftLdaProperty() {
        return leftLda;
    }

    public String getStripEnd() {
        return stripEnd.toString();
    }

    public SimpleDoubleProperty stripEndProperty() {
        return stripEnd;
    }

    public String getRESAWidth() {
        return RESAWidth.toString();
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

    public String getClearwayLeft() {
        return clearwayLeft.toString();
    }

    public SimpleDoubleProperty clearwayLeftProperty() {
        return clearwayLeft;
    }

    public String getClearwayRight() {
        return clearwayRight.toString();
    }

    public SimpleDoubleProperty clearwayRightProperty() {
        return clearwayRight;
    }

    public String getStopwayLeft() {
        return stopwayLeft.toString();
    }

    public SimpleDoubleProperty stopwayLeftProperty() {
        return stopwayLeft;
    }

    public String getStopwayRight() {
        return stopwayRight.toString();
    }

    public SimpleDoubleProperty stopwayRightProperty() {
        return stopwayRight;
    }

    public String getDispThresholdLeft() {
        return dispThresholdLeft.toString();
    }

    public SimpleDoubleProperty dispThresholdLeftProperty() {
        return dispThresholdLeft;
    }

    public String getDispThresholdRight() {
        return dispThresholdRight.toString();
    }

    public SimpleDoubleProperty dispThresholdRightProperty() {
        return dispThresholdRight;
    }

    public boolean isLeftTakeOff() {
        return leftTakeOff.get();
    }

    public SimpleBooleanProperty leftTakeOffProperty() {
        return leftTakeOff;
    }

    public boolean isLeftLand() {
        return leftLand.get();
    }

    public SimpleBooleanProperty leftLandProperty() {
        return leftLand;
    }

    public boolean isRightTakeOff() {
        return rightTakeOff.get();
    }

    public SimpleBooleanProperty rightTakeOffProperty() {
        return rightTakeOff;
    }

    public boolean isRightLand() {
        return rightLand.get();
    }

    public SimpleBooleanProperty rightLandProperty() {
        return rightLand;
    }
}
