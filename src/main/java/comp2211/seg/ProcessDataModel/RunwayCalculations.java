package comp2211.seg.ProcessDataModel;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Runway calculations.
 */
public abstract class RunwayCalculations {

    private static final Logger logger = LogManager.getLogger(RunwayCalculations.class);

    /**
     * Calculate take off toward left.
     *
     * @param runway the runway
     */
    public static void calculateTakeOffTowardLeft(Runway runway) {}

    /**
     * Calculate land toward left.
     *
     * @param runway the runway
     */
    public static void calculateLandTowardLeft(Runway runway) {
        // Calculate Land Towards for Left

        runway.leftLda.bind(runway.runwayObstacle.distFromThresholdProperty().subtract(runway.MINRESA).subtract(runway.STRIPEND));

        // Ensure Declared distance isn't more than original value
        if (runway.leftLda.get() > runway.inputLeftLda.get()) {
            runway.leftLda.bind(runway.inputLeftLda);
            runway.leftLdaBreakdown.bind(
                    new SimpleStringProperty(
                            "(Left) Calculated LDA greater than original LDA, original LDA taken as output"
                    )
            );
            runway.leftLdaBreakdownHeader.bind(new SimpleStringProperty("N/A"));
        } else {
            runway.leftLdaBreakdown.bind(
                    new SimpleStringProperty("Left LDA = ")
                            .concat(runway.runwayObstacle.distFromThresholdProperty().intValue())
                            .concat(" - ")
                            .concat(runway.MINRESA).concat(" - ")
                            .concat(runway.STRIPEND)
                            .concat(" = ")
                            .concat(runway.leftLda.intValue())
            );
            runway.leftLdaBreakdownHeader.bind(
                    new SimpleStringProperty("Left LDA = Obstacle dist from left threshold - Minimum RESA - Stripend")
            );
        }

        logger.info("New LDA calculated for landing towards and obstacle for runway "+runway.runwayDesignatorLeft.get());
    }

    /**
     * Calculate take off away left.
     *
     * @param runway the runway
     */
    public static void calculateTakeOffAwayLeft(Runway runway) {}

    /**
     * Calculate land over left.
     *
     * @param runway the runway
     */
    public static void calculateLandOverLeft(Runway runway) {
        // Calculate Land Over for Left

        var ldaSubtraction = runway.getLdaSubtraction(runway.runwayObstacle.distFromThresholdProperty(), true);

        runway.leftLda.bind(runway.inputLeftLda.subtract(ldaSubtraction));

        // Ensure Declared distance isn't more than original value
        if (runway.leftLda.get() > runway.inputLeftLda.get()) {
            runway.leftLda.bind(runway.inputLeftLda);
            runway.leftLdaBreakdown.bind(
                    new SimpleStringProperty(
                            "(Left) Calculated LDA greater than original LDA, original LDA taken as output"
                    )
            );
            runway.leftLdaBreakdownHeader.bind(new SimpleStringProperty("N/A"));
        } else {
            runway.leftLdaBreakdown.bind(
                    new SimpleStringProperty("Left LDA = ")
                            .concat(runway.inputLeftLda.intValue())
                            .concat(" - ")
                            .concat(runway.leftLdaSubBreakdown)
                            .concat(" = ")
                            .concat(runway.leftLda)
            );
            runway.leftLdaBreakdownHeader.bind(
                    new SimpleStringProperty("Left LDA = Original left LDA - ")
                            .concat(runway.leftLdaSubBreakdownHeader)
            );
        }

        logger.info("New LDA calculated for landing over an obstacle for runway "+runway.runwayDesignatorLeft.get());
    }

    /**
     * Calculate take off toward right.
     *
     * @param runway the runway
     */
    public static void calculateTakeOffTowardRight(Runway runway) {}

    /**
     * Calculate land toward right.
     *
     * @param runway the runway
     */
    public static void calculateLandTowardRight(Runway runway) {
        // Calculate Land Towards for Right

        runway.rightLda.bind(runway.runwayObstacle.distFromOtherThresholdProperty().subtract(runway.MINRESA).subtract(runway.STRIPEND));

        // Ensure Declared distance isn't more than original value
        if (runway.rightLda.get() > runway.inputRightLda.get()) {
            runway.rightLda.bind(runway.inputRightLda);
            runway.rightLdaBreakdown.bind(
                    new SimpleStringProperty(
                            "(Right) Calculated LDA greater than original LDA, original LDA taken as output"
                    )
            );
            runway.rightLdaBreakdownHeader.bind(new SimpleStringProperty("N/A"));
        } else {
            runway.rightLdaBreakdown.bind(
                    new SimpleStringProperty("Right LDA = ")
                            .concat(runway.runwayObstacle.distFromOtherThresholdProperty().intValue())
                            .concat(" - ")
                            .concat(runway.MINRESA)
                            .concat(" - ")
                            .concat(runway.STRIPEND)
                            .concat(" = ")
                            .concat(runway.rightLda.intValue())
            );
            runway.rightLdaBreakdownHeader.bind(
                    new SimpleStringProperty("Right LDA = Obstacle dist from right threshold - Minimum RESA - Stripend")
            );
        }

        logger.info("New LDA calculated for landing towards and obstacle for runway "+runway.runwayDesignatorRight.get());
    }

    /**
     * Calculate take off away right.
     *
     * @param runway the runway
     */
    public static void calculateTakeOffAwayRight(Runway runway) {}

    /**
     * Calculate land over right.
     *
     * @param runway the runway
     */
    public static void calculateLandOverRight(Runway runway) {
        // Calculate Land Over for Right

        var ldaSubtraction = runway.getLdaSubtraction(runway.runwayObstacle.distFromOtherThresholdProperty(), false);

        runway.rightLda.bind(runway.inputRightLda.subtract(ldaSubtraction));

        // Ensure Declared distance isn't more than original value
        if (runway.rightLda.get() > runway.inputRightLda.get()) {
            runway.rightLda.bind(runway.inputRightLda);
            runway.rightLdaBreakdown.bind(
                    new SimpleStringProperty(
                            "(Right) Calculated LDA greater than original LDA, original LDA taken as output"
                    )
            );
            runway.rightLdaBreakdownHeader.bind(new SimpleStringProperty("N/A"));
        } else {
            runway.rightLdaBreakdown.bind(
                    new SimpleStringProperty("Right LDA = ")
                            .concat(runway.inputRightLda.intValue())
                            .concat(" - ")
                            .concat(runway.rightLdaSubBreakdown)
                            .concat(" = ")
                            .concat(runway.rightLda.intValue()));
            runway.rightLdaBreakdownHeader.bind(
                    new SimpleStringProperty("Right LDA = Original right LDA - ")
                            .concat(runway.rightLdaSubBreakdownHeader)
            );
        }
        logger.info("New LDA calculated for landing over an obstacle for runway "+runway.runwayDesignatorRight.get());
    }

}
