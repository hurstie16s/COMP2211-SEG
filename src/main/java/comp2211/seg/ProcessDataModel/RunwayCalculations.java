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

    /**
     * The constant logger.
     */
    private static final Logger logger = LogManager.getLogger(RunwayCalculations.class);

    /**
     * Calculate left-hand runway values for taking off towards an obstacle.
     *
     * @param runway the runway
     */
    public static void calculateTakeOffTowardLeft(Runway runway) {

        var toraCalc = runway.runwayObstacle.distFromThresholdProperty()
                .add(runway.dispThresholdLeft)
                .subtract(Bindings.max(
                        runway.runwayObstacle.heightProperty().multiply(runway.SLOPE),
                        runway.MINRESA.add(runway.runwayObstacle.lengthProperty().divide(2))
                ))
                .subtract(runway.STRIPEND).subtract(runway.dispThresholdLeft);

        runway.leftTora.bind(
            Bindings.max(
                runway.runwayObstacle.distFromThresholdProperty()
                    .add(runway.dispThresholdLeft)
                        .subtract(Bindings.max(
                            runway.runwayObstacle.heightProperty().multiply(runway.SLOPE),
                            runway.MINRESA.add(runway.runwayObstacle.lengthProperty().divide(2))
                    ))
                    .subtract(runway.STRIPEND).subtract(runway.dispThresholdLeft),
                0
            )
        );

        // Ensure Declared distance isn't more than original value
        if (runway.leftTora.get() > runway.inputLeftTora.get()) {
            var distanceFromToraEnd = new SimpleDoubleProperty();
            distanceFromToraEnd.bind(runway.leftTora.subtract(runway.inputLeftTora));
            runway.leftTora.bind(runway.inputLeftTora);

            runway.leftToraBreakdown.bind(
                    new SimpleStringProperty(
                            "(Left) Calculated TORA greater than original TORA, original TORA taken as output"
                    )
            );
            runway.leftToraBreakdownHeader.bind(new SimpleStringProperty("N/A"));

            runway.leftAsda.bind(Bindings.min(runway.leftTora.add(distanceFromToraEnd), runway.leftTora.add(runway.stopwayRight)));

            runway.leftAsdaBreakdown.bind(
                    new SimpleStringProperty("Left ASDA = ")
                            .concat (runway.leftTora.intValue())
                            .concat(" + ")
                            .concat(Math.min(distanceFromToraEnd.intValue(), runway.stopwayRight.intValue()))
                            .concat(" = ")
                            .concat(runway.leftAsda.intValue())
            );
            runway.leftAsdaBreakdownHeader.bind(
                    new SimpleStringProperty("Left ASDA = Left TORA + ")
                            .concat(Bindings.when(
                                            Bindings.lessThan(
                                                    distanceFromToraEnd, runway.stopwayRight
                                            ))
                                    .then(new SimpleStringProperty("Obstacle dist from TORA end"))
                                    .otherwise(new SimpleStringProperty("Right stopway"))
                            )
            );

            runway.leftToda.bind(Bindings.min(runway.leftTora.add(distanceFromToraEnd), runway.leftTora.add(runway.clearwayRight)));

            runway.leftTodaBreakdown.bind(
                    new SimpleStringProperty("Left TODA = ")
                            .concat(runway.leftTora.intValue())
                            .concat(" + ")
                            .concat(Math.min(distanceFromToraEnd.intValue(), runway.clearwayRight.intValue()))
                            .concat(" = ")
                            .concat(runway.leftToda.intValue())
            );
            runway.leftTodaBreakdownHeader.bind(
                    new SimpleStringProperty("Left TODA = Left TORA + ")
                            .concat(Bindings.when(
                                            Bindings.lessThan(
                                                    distanceFromToraEnd, runway.clearwayRight
                                            ))
                                    .then(new SimpleStringProperty("Obstacle dist from TORA end"))
                                    .otherwise("Right clearway")
                            )
            );

        } else {

            runway.leftToraBreakdown.bind(
                    new SimpleStringProperty("Left TORA = ")
                            .concat(runway.runwayObstacle.distFromThresholdProperty().intValue())
                            .concat(" + ")
                            .concat(runway.dispThresholdLeft.intValue())
                            .concat( " - ")
                            .concat(Bindings.when(
                                            Bindings.greaterThan(
                                                    runway.runwayObstacle.heightProperty().multiply(runway.SLOPE),
                                                    runway.MINRESA.add(runway.runwayObstacle.lengthProperty().divide(2))
                                            ))
                                    .then(
                                            new SimpleStringProperty("(")
                                                    .concat(runway.runwayObstacle.heightProperty().intValue())
                                                    .concat(" x ")
                                                    .concat(runway.SLOPE)
                                    )
                                    .otherwise(
                                            new SimpleStringProperty("")
                                                    .concat(runway.MINRESA)
                                                    .concat(" + (")
                                                    .concat(runway.runwayObstacle.lengthProperty().intValue())
                                                    .concat(" / ")
                                                    .concat(2)
                                    )
                            )
                            .concat(") - ")
                            .concat(runway.STRIPEND)
                            .concat(" = ")
                            .concat(runway.leftTora.intValue())
            );

            runway.leftToraBreakdownHeader.bind(
                    new SimpleStringProperty(
                            "Left TORA = Obstacle dist from left threshold + Left displaced threshold - ")
                            .concat(Bindings.when(
                                            Bindings.greaterThan(
                                                    runway.runwayObstacle.heightProperty().multiply(runway.SLOPE),
                                                    runway.MINRESA.add(runway.runwayObstacle.lengthProperty().divide(2))
                                            ))
                                    .then(new SimpleStringProperty("(Runway height x ALS slope"))
                                    .otherwise(new SimpleStringProperty("Minimum RESA + (Runway length / 2"))
                            )
                            .concat(new SimpleStringProperty(") - Stripend"))
            );

            if (runway.leftTora.lessThan(0).get()) {
                runway.leftTora.bind(new SimpleDoubleProperty(0));
                runway.leftToraBreakdown.bind(new SimpleStringProperty("TORA cannot be less than 0m"));
            }

            runway.leftAsda.bind(runway.leftTora);
            runway.leftAsdaBreakdown.bind(
                    new SimpleStringProperty("Left ASDA = ")
                            .concat(runway.leftAsda.intValue())
            );
            runway.leftAsdaBreakdownHeader.bind(new SimpleStringProperty("Left ASDA = Left TORA"));

            runway.leftToda.bind(runway.leftTora);
            runway.leftTodaBreakdown.bind(
                    new SimpleStringProperty("Left TODA = ")
                            .concat(runway.leftToda.intValue())
            );
            runway.leftTodaBreakdownHeader.bind(new SimpleStringProperty("Left TODA = Left TORA"));
        }

    }

    /**
     * Calculate left-hand runway values for landing towards an obstacle.
     *
     * @param runway the runway
     */
    public static void calculateLandTowardLeft(Runway runway) {
        // Calculate Land Towards for Left

        runway.leftLda.bind(
                Bindings.max(
                        runway.runwayObstacle.distFromThresholdProperty()
                                .subtract(runway.MINRESA)
                                .subtract(runway.STRIPEND)
                                .subtract(runway.runwayObstacle.lengthProperty().divide(2)).subtract(runway.dispThresholdLeft),
                        0
                )
        );

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
     * Calculate left-hand runway values for taking off away from an obstacle.
     *
     * @param runway the runway
     */
    public static void calculateTakeOffAwayLeft(Runway runway) {
        // Calculate left take-off values, taking off away from the obstacle

        var toraCalc = runway.inputLeftTora
                .subtract(runway.runwayObstacle.distFromThresholdProperty())
                .subtract(Bindings.max(
                                runway.BLASTZONE, runway.STRIPEND.add(runway.MINRESA)
                        )
                )
                .subtract(runway.dispThresholdLeft)
                .subtract(
                        runway.runwayObstacle.lengthProperty().divide(2)
                ).add(runway.dispThresholdLeft);

        runway.leftTora.bind(Bindings.max(
                runway.inputLeftTora
                        .subtract(runway.runwayObstacle.distFromThresholdProperty())
                        .subtract(Bindings.max(
                                runway.BLASTZONE, runway.STRIPEND.add(runway.MINRESA)
                                )
                        )
                        .subtract(runway.dispThresholdLeft)
                        .subtract(
                                runway.runwayObstacle.lengthProperty().divide(2)
                        ).add(runway.dispThresholdLeft),
                0
        ));

        runway.leftToraBreakdown.bind(
                new SimpleStringProperty("Left TORA = ")
                        .concat(runway.inputLeftTora.intValue())
                        .concat(" - ")
                        .concat(runway.runwayObstacle.distFromThresholdProperty().intValue())
                        .concat(" - ")
                        .concat(Bindings.when(
                                        Bindings.lessThan(
                                                runway.BLASTZONE, runway.STRIPEND.add(runway.MINRESA)
                                        ))
                                .then(new SimpleStringProperty().concat(runway.BLASTZONE))
                                .otherwise(
                                        new SimpleStringProperty("(")
                                                .concat(runway.STRIPEND)
                                                .concat(" + ")
                                                .concat(runway.MINRESA)
                                                .concat(")")
                                )
                        )
                        .concat(" - ")
                        .concat(runway.dispThresholdRight.intValue())
                        .concat(" - (")
                        .concat(runway.runwayObstacle.lengthProperty().intValue())
                        .concat(" / ")
                        .concat(2)
                        .concat(") = ")
                        .concat(runway.leftTora.intValue())
        );

        runway.leftToraBreakdownHeader.bind(
                new SimpleStringProperty("Left TORA = Original left TORA - Obstacle dist from left threshold - ")
                        .concat(
                                Bindings.when(
                                                Bindings.lessThan(
                                                        runway.BLASTZONE, runway.STRIPEND.add(runway.MINRESA)
                                                ))
                                        .then(new SimpleStringProperty("Blastzone"))
                                        .otherwise("(Stripend + Minimum RESA)")
                        )
                        .concat(new SimpleStringProperty(" - Right displaced threshold - (Runway length / 2)"))
        );

        runway.leftAsda.bind(Bindings.max(
                toraCalc.add(runway.stopwayRight)
                ,0
                )
        );
        runway.leftAsdaBreakdown.bind(
                new SimpleStringProperty("Left ASDA = ")
                        .concat(runway.leftTora.intValue())
                        .concat(" + ")
                        .concat(runway.stopwayRight.intValue())
                        .concat(" = ")
                        .concat(runway.leftAsda.intValue())
        );
        runway.leftAsdaBreakdownHeader.bind(new SimpleStringProperty("Left ASDA = Left TORA + Right stopway"));

        runway.leftToda.bind(
                Bindings.max(
                        toraCalc.add(runway.clearwayRight),
                        0
                )
        );
        runway.leftTodaBreakdown.bind(
                new SimpleStringProperty("Left TODA = ")
                        .concat(runway.leftTora.intValue())
                        .concat(" + ")
                        .concat(runway.clearwayRight.intValue())
                        .concat(" = ")
                        .concat(runway.leftToda.intValue())
        );
        runway.leftTodaBreakdownHeader.bind(new SimpleStringProperty("Left TODA = Left TORA + Right clearway"));
    }

    /**
     * Calculate left-hand runway values for landing over an obstacle.
     *
     * @param runway the runway
     */
    public static void calculateLandOverLeft(Runway runway) {
        // Calculate Land Over for Left
        // check doesnt need subbing from inputLda
        runway.leftLda.bind(
                Bindings.max(
                        runway.inputLeftLda.subtract(
                                Bindings.max(
                                        runway.runwayObstacle.distFromThresholdProperty()
                                                .add(
                                                        Bindings.max(
                                                                runway.runwayObstacle.heightProperty()
                                                                        .multiply(runway.SLOPE)
                                                                        .subtract(runway.runwayObstacle.lengthProperty().divide(2)),
                                                                runway.MINRESA
                                                        )
                                                )
                                                .add(runway.STRIPEND),
                                        runway.BLASTZONE.add(runway.runwayObstacle.distFromThresholdProperty())
                                ).add(runway.runwayObstacle.lengthProperty().divide(2)).subtract(runway.dispThresholdLeft)
                        ),
                        0
                )
        );

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
                            .concat(runway.leftLda.intValue())
            );
            runway.leftLdaBreakdownHeader.bind(
                    new SimpleStringProperty("Left LDA = Original left LDA - ")
                            .concat(runway.leftLdaSubBreakdownHeader)
            );

        }

        logger.info("New LDA calculated for landing over an obstacle for runway "+runway.runwayDesignatorLeft.get());
    }

    /**
     * Calculate right-hand runway values for taking off towards an obstacle.
     *
     * @param runway the runway
     */
    public static void calculateTakeOffTowardRight(Runway runway) {
        // Calculate right take-off values, taking off towards the obstacle
        runway.rightTora.bind(Bindings.max(
                runway.runwayObstacle.distFromOtherThresholdProperty()
                        .add(runway.dispThresholdRightProperty())
                        .subtract(
                                Bindings.max(
                                        runway.runwayObstacle.heightProperty().multiply(runway.SLOPE),
                                        runway.MINRESA.add(runway.runwayObstacle.lengthProperty().divide(2))))
                        .subtract(runway.STRIPEND)
                        .add(runway.dispThresholdLeft.subtract(runway.dispThresholdRight)),
                0
        ));

        // Ensure Declared distance isn't more than original value
        if (runway.rightTora.get() > runway.inputRightTora.get()) {
            var distanceFromToraEnd = new SimpleDoubleProperty();
            distanceFromToraEnd.bind(runway.rightTora.subtract(runway.inputRightTora));
            runway.rightTora.bind(runway.inputRightTora);

            runway.rightToraBreakdown.bind(
                    new SimpleStringProperty(
                            "(Right) Calculated  TORA greater than original TORA, original TORA taken as output"
                    )
            );
            runway.rightTodaBreakdownHeader.bind(new SimpleStringProperty("N/A"));

            runway.rightAsda.bind(Bindings.min(runway.rightTora.add(distanceFromToraEnd), runway.rightTora.add(runway.stopwayLeft)));

            runway.rightAsdaBreakdown.bind(
                    new SimpleStringProperty("Right ASDA = ")
                            .concat(runway.rightTora.intValue())
                            .concat(" + ")
                            .concat(Math.min(distanceFromToraEnd.intValue(), runway.stopwayLeft.intValue()))
                            .concat(" = ")
                            .concat(runway.rightAsda.intValue())
            );
            runway.rightAsdaBreakdownHeader.bind(
                    new SimpleStringProperty("Right ASDA = Right TORA + ")
                            .concat(Bindings.when(
                                            Bindings.greaterThan(
                                                    distanceFromToraEnd, runway.stopwayLeft
                                            ))
                                    .then(new SimpleStringProperty("Obstacle dist from TORA end"))
                                    .otherwise(new SimpleStringProperty("Left stopway"))
                            )
            );

            runway.rightToda.bind(Bindings.min(runway.rightTora.add(distanceFromToraEnd), runway.rightTora.add(runway.clearwayLeft)));

            runway.rightTodaBreakdown.bind(
                    new SimpleStringProperty("Right TODA = ")
                            .concat(runway.rightTora.intValue())
                            .concat(" + ")
                            .concat(Math.min(distanceFromToraEnd.intValue(), runway.clearwayLeft.intValue()))
                            .concat(" = ")
                            .concat(runway.rightToda.intValue())
            );
            runway.rightTodaBreakdownHeader.bind(
                    new SimpleStringProperty("Right TODA = Right TORA + ")
                            .concat(Bindings.when(
                                            Bindings.greaterThan(
                                                    distanceFromToraEnd, runway.clearwayLeft
                                            ))
                                    .then(new SimpleStringProperty("Obstacle dist from TORA end"))
                                    .otherwise(new SimpleStringProperty("Left clearway"))
                            )
            );

        } else {

            runway.rightToraBreakdown.bind(
                    new SimpleStringProperty("Right TORA = ")
                            .concat(runway.runwayObstacle.distFromOtherThresholdProperty().intValue())
                            .concat(" + ")
                            .concat(runway.dispThresholdRight.intValue())
                            .concat(" - ")
                            .concat(Bindings.when(
                                            Bindings.greaterThan(
                                                    runway.runwayObstacle.heightProperty().multiply(runway.SLOPE),
                                                    runway.MINRESA.add(runway.runwayObstacle.lengthProperty().divide(2))
                                            ))
                                    .then(
                                            new SimpleStringProperty("(")
                                                    .concat(runway.runwayObstacle.heightProperty().intValue())
                                                    .concat(" x ")
                                                    .concat(runway.SLOPE)
                                    )
                                    .otherwise(
                                            new SimpleStringProperty("")
                                                    .concat(runway.MINRESA)
                                                    .concat(" + (")
                                                    .concat(runway.runwayObstacle.lengthProperty().intValue())
                                                    .concat(" / ")
                                                    .concat(2)
                                    )
                            )
                            .concat(") - ")
                            .concat(runway.STRIPEND)
                            .concat(" = ")
                            .concat(runway.rightTora.intValue())
            );
            runway.rightToraBreakdownHeader.bind(
                    new SimpleStringProperty("Right TORA = Obstacle dist from right threshold + Right displaced threshold - ")
                            .concat(Bindings.when(
                                            Bindings.greaterThan(
                                                    runway.runwayObstacle.heightProperty().multiply(runway.SLOPE),
                                                    runway.MINRESA.add(runway.runwayObstacle.lengthProperty().divide(2))
                                            ))
                                    .then(new SimpleStringProperty("(Runway height x ALS slope"))
                                    .otherwise(new SimpleStringProperty("Minimum RESA + (Runway length / 2"))
                            )
                            .concat(new SimpleStringProperty(") - Stripend"))
            );

            runway.rightAsda.bind(runway.rightTora);
            runway.rightAsdaBreakdown.bind(
                    new SimpleStringProperty("Right ASDA = ")
                            .concat(runway.rightAsda.intValue())
            );
            runway.rightAsdaBreakdownHeader.bind(new SimpleStringProperty("Right ASDA = Right TORA"));

            runway.rightToda.bind(runway.rightTora);
            runway.rightTodaBreakdown.bind(
                    new SimpleStringProperty("Right TODA = ")
                            .concat(runway.rightToda.intValue())
            );
            runway.rightTodaBreakdownHeader.bind(new SimpleStringProperty("Right TODA = Right TORA"));
        }
    }

    /**
     * Calculate land toward right.
     *
     * @param runway the runway
     */
    public static void calculateLandTowardRight(Runway runway) {
        // Calculate Land Towards for Right

        runway.rightLda.bind(
                Bindings.max(
                        runway.runwayObstacle.distFromOtherThresholdProperty()
                                .subtract(runway.MINRESA)
                                .subtract(runway.STRIPEND)
                                .subtract(runway.runwayObstacle.lengthProperty().divide(2))
                                .add(runway.dispThresholdLeft.subtract(runway.dispThresholdRight))
                        ,0
                )
        );

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
    public static void calculateTakeOffAwayRight(Runway runway) {
        // Calculate right take-off values, taking off away from the obstacle

        var toraCalc = runway.runwayObstacle.distFromThresholdProperty()
                .subtract(
                        Bindings.max(
                                runway.BLASTZONE,
                                runway.STRIPEND.add(runway.MINRESA)
                        ))
                //.add(runway.dispThresholdLeft.subtract(runway.dispThresholdRight))
                .subtract(runway.runwayObstacle.lengthProperty().divide(2));

        runway.rightTora.bind(Bindings.max(
                runway.runwayObstacle.distFromThresholdProperty()
                        .subtract(
                                Bindings.max(
                                        runway.BLASTZONE,
                                        runway.STRIPEND.add(runway.MINRESA)
                                ))
                        //.add(runway.dispThresholdLeft.subtract(runway.dispThresholdRight))
                        .subtract(runway.runwayObstacle.lengthProperty().divide(2)),
                0
        ));

        // Ensure Declared distance isn't more than original value
        if (runway.rightTora.get() > runway.inputRightTora.get()) {
            runway.rightTora.bind(runway.inputRightTora);
            runway.rightToraBreakdown.bind(
                    new SimpleStringProperty(
                            "(Right) Calculated TORA greater than original TORA, original TORA taken as output"
                    )
            );
            runway.rightToraBreakdownHeader.bind(new SimpleStringProperty("N/A"));
        } else {
            runway.rightToraBreakdown.bind(
                    new SimpleStringProperty("Right TORA = ")
                            .concat(runway.runwayObstacle.distFromThresholdProperty().intValue())
                            .concat(" - ")
                            .concat(
                                    Bindings.when(
                                            Bindings.greaterThan(
                                                    runway.BLASTZONE, runway.STRIPEND.add(runway.MINRESA)
                                            )
                                    ).then(
                                            new SimpleStringProperty("").concat(runway.BLASTZONE)
                                    ).otherwise(
                                            new SimpleStringProperty("(")
                                                    .concat(runway.STRIPEND)
                                                    .concat(" + ")
                                                    .concat(runway.MINRESA)
                                                    .concat(")")
                                    )
                            )
                            .concat(" + ")
                            .concat(runway.dispThresholdLeft.intValue())
                            .concat(" - (")
                            .concat(runway.runwayObstacle.lengthProperty().intValue())
                            .concat(" / ")
                            .concat(2)
                            .concat(") = ")
                            .concat(runway.rightTora.intValue())
            );
            runway.rightToraBreakdownHeader.bind(
                    new SimpleStringProperty("Right TORA = Obstacle dist from left threshold - ")
                            .concat(
                                    Bindings.when(
                                            Bindings.greaterThan(
                                                    runway.BLASTZONE, runway.STRIPEND.add(runway.MINRESA)
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

        runway.rightAsda.bind(
                Bindings.max(
                        toraCalc.add(runway.stopwayLeft),
                        0
                )
        );
        runway.rightAsdaBreakdown.bind(
                new SimpleStringProperty("Right ASDA = ")
                        .concat(runway.rightTora.intValue())
                        .concat(" + ")
                        .concat(runway.stopwayLeft.intValue())
                        .concat(" = ")
                        .concat(runway.rightAsda.intValue())
        );
        runway.rightAsdaBreakdownHeader.bind(
                new SimpleStringProperty("Right ASDA = Right TORA + Left stopway")
        );

        runway.rightToda.bind(
                Bindings.max(
                        toraCalc.add(runway.clearwayLeft),
                        0
                )
        );
        runway.rightTodaBreakdown.bind(
                new SimpleStringProperty("Right TODA = ")
                        .concat(runway.rightTora.intValue())
                        .concat(" + ")
                        .concat(runway.clearwayLeft.intValue())
                        .concat(" = ")
                        .concat(runway.rightToda.intValue())
        );
        runway.rightTodaBreakdownHeader.bind(
                new SimpleStringProperty("Right TODA = Right TORA + Left clearway")
        );
    }

    /**
     * Calculate land over right.
     *
     * @param runway the runway
     */
    public static void calculateLandOverRight(Runway runway) {
        // Calculate Land Over for Right
        // TODO : Issue here, fix URGENT
        runway.rightLda.bind(
                Bindings.max(
                        runway.inputRightLda.subtract(
                                Bindings.max(
                                        runway.runwayObstacle.distFromOtherThresholdProperty()
                                                .add(
                                                        Bindings.max(
                                                                (
                                                                        runway.runwayObstacle.heightProperty()
                                                                                .multiply(50)
                                                                )
                                                                        .subtract(
                                                                                runway.runwayObstacle.lengthProperty().divide(2)
                                                                        ),
                                                                runway.MINRESA
                                                        )
                                                )
                                                .add(runway.STRIPEND),
                                        runway.BLASTZONE
                                                .add(runway.runwayObstacle.distFromOtherThresholdProperty())
                                )
                        ).subtract(runway.runwayObstacle.lengthProperty().divide(2))
                                .subtract(runway.dispThresholdLeft.subtract(runway.dispThresholdRight))
                        ,0
                )
        );

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
            runway.rightLdaObstacleSlopeCalcBreakdown.bind(
                    Bindings.when(
                            Bindings.greaterThan(
                                    (
                                            runway.runwayObstacle.heightProperty()
                                                    .multiply(50)
                                    )
                                            .subtract(
                                                    runway.runwayObstacle.lengthProperty().divide(2)
                                                            .divide(2)
                                            )
                                    , runway.MINRESA
                            )
                    ).then(
                           new SimpleStringProperty("(")
                                   .concat(runway.runwayObstacle.heightProperty())
                                   .concat(" x 50 - (")
                                   .concat(runway.runwayObstacle.lengthProperty().divide(2).intValue())
                                   .concat(" / 2)")
                    ).otherwise(
                            new SimpleStringProperty("")
                                    .concat(runway.MINRESA)
                    )
            );
            runway.rightLdaBreakdown.bind(
                    new SimpleStringProperty("Right LDA = ")
                            .concat(runway.inputRightLda.intValue())
                            .concat(" - ")
                            .concat(
                                    Bindings.when(
                                            Bindings.greaterThan(
                                                    runway.runwayObstacle.distFromOtherThresholdProperty()
                                                            .add(
                                                                    Bindings.max(
                                                                            (
                                                                                    runway.runwayObstacle.heightProperty()
                                                                                            .multiply(50)
                                                                            )
                                                                                    .subtract(
                                                                                            runway.runwayLengthProperty().divide(2)
                                                                                    ),
                                                                            runway.MINRESA // always choosing this???
                                                                    )
                                                            )
                                                            .add(runway.STRIPEND),
                                                    runway.BLASTZONE
                                                            .add(runway.runwayObstacle.distFromOtherThresholdProperty())
                                            )
                                    ).then(
                                            new SimpleStringProperty("(")
                                                    .concat(runway.runwayObstacle.distFromOtherThresholdProperty().intValue())
                                                    .concat(" + ")
                                                    .concat(runway.rightLdaObstacleSlopeCalcBreakdown)
                                                    .concat(" + ")
                                                    .concat(runway.STRIPEND)
                                                    .concat(")")
                                    ).otherwise(
                                            new SimpleStringProperty("(")
                                                    .concat(runway.BLASTZONE)
                                                    .concat(" + ")
                                                    .concat(runway.runwayObstacle.distFromOtherThresholdProperty().intValue())
                                                    .concat(")")
                                    )
                            )
                            .concat(" = ")
                            .concat(runway.rightLda.intValue()));
            runway.rightLdaBreakdownHeader.bind(
                    new SimpleStringProperty("Right LDA = Original right LDA - ")
                            .concat(runway.rightLdaSubBreakdownHeader)
            );

            if (runway.rightLda.lessThan(0).get()) {
                runway.rightLda.bind(new SimpleDoubleProperty(0));
                runway.rightLdaBreakdown.bind(new SimpleStringProperty("LDA cannot be less than 0m"));
            }

        }
        logger.info("New LDA calculated for landing over an obstacle for runway "+runway.runwayDesignatorRight.get());
    }

}
