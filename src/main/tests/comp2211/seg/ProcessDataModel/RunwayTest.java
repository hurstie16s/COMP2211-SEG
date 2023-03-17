package comp2211.seg.ProcessDataModel;

import javafx.beans.property.SimpleDoubleProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RunwayTest {

    private static final Logger logger = LogManager.getLogger(RunwayTest.class);

    //TODO: Test Rew-calculate
    //TODO: Re-work tests to handle left and right


    // 09R and 27L
    static Runway runway1 = new Runway();
    // 09L and 27R
    static Runway runway2 = new Runway();

    @BeforeAll
    static void setUpRunways() {
        setProperties(
                "09R",
                3660,
                3660,
                3660,
                3353,
                3660,
                3660,
                3660,
                3660,
                runway1
        );
        setProperties(
                "09L",
                3902,
                3902,
                3902,
                3595,
                3884,
                3962,
                3884,
                3884,
                runway2
        );
    }

    static void setProperties(
            String designator,
            int leftTora,
            int leftToda,
            int leftAsda,
            int leftLda,
            int rightTora,
            int rightToda,
            int rightAsda,
            int rightLda,
            Runway runway) {
        runway.runwayDesignatorLeftProperty().set(designator);
        runway.inputLeftToraProperty().bind(new SimpleDoubleProperty(leftTora));
        runway.inputLeftTodaProperty().bind(new SimpleDoubleProperty(leftToda));
        runway.inputLeftAsdaProperty().bind(new SimpleDoubleProperty(leftAsda));
        runway.inputLeftLdaProperty().bind(new SimpleDoubleProperty(leftLda));
        runway.inputRightToraProperty().bind(new SimpleDoubleProperty(rightTora));
        runway.inputRightTodaProperty().bind(new SimpleDoubleProperty(rightToda));
        runway.inputRightAsdaProperty().bind(new SimpleDoubleProperty(rightAsda));
        runway.inputRightLdaProperty().bind(new SimpleDoubleProperty(rightLda));
    }
    void addObstacle(Runway runway, Obstacle obstacle){
        runway.addObstacle(obstacle);
    }

    // Unit Tests

    @DisplayName("Runway Designators : Check 2nd designator is correctly calculated")
    @ParameterizedTest
    @MethodSource("generateCheckDesignatorTestData")
    void checkDesignatorTestData(Runway runway, String expectedDesignator) {

        var expectedNumber = expectedDesignator.substring(0,2);
        var expectedCharacter = expectedDesignator.substring(2);

        assertEquals(expectedNumber, runway.getRunwayDesignatorRight().substring(0,2), "Number for runway designator incorrect");
        assertEquals(expectedCharacter, runway.getRunwayDesignatorRight().substring(2), "Character for runway designator incorrect");
        assertEquals(expectedDesignator, runway.getRunwayDesignatorRight(), "Runway designator incorrect");
    }

    //TODO: Write Test
    @DisplayName("Landing/ take-off calculations : Recalculate appropriate values")
    @ParameterizedTest
    @MethodSource("generateRecalculateTestData")
    void recalculateTest(Runway runway,
                         Obstacle obstacleToAdd,
                         double expectedTORALeft,
                         double expectedASDALeft,
                         double expectedTODALeft,
                         double expectedLDALeft,
                         double expectedTORARight,
                         double expectedASDARight,
                         double expectedTODARight,
                         double expectedLDARight,
                         String message
    ) {
        logger.info(message);
        addObstacle(runway, obstacleToAdd);

        assertEquals(expectedLDALeft, runway.getLeftLda(), "Left LDA Incorrect");
        assertEquals(expectedTORALeft, runway.getLeftTora(), "Left TORA Incorrect");
        assertEquals(expectedASDALeft, runway.getLeftAsda(), "Left ASDA Incorrect");
        assertEquals(expectedTODALeft, runway.getLeftToda(), "Left TODA Incorrect");

        assertEquals(expectedLDARight, runway.getRightLda(), "Right LDA Incorrect");
        assertEquals(expectedTORARight, runway.getRightTora(), "Right TORA Incorrect");
        assertEquals(expectedASDARight, runway.getRightAsda(), "Right ASDA Incorrect");
        assertEquals(expectedTODARight, runway.getRightToda(), "Right TODA Incorrect");
    }

    @DisplayName("Landing calculations : Land over left : Land towards right")
    @ParameterizedTest
    @MethodSource("generateLandOverTestData")
    void calculateLandOverTest(Runway runway, Obstacle obstacleToAdd, double expectedLDALeft, double expectedLDARight, String message) {
        logger.info(message);
        addObstacle(runway,obstacleToAdd);
        runway.calculateLandOver();

        assertEquals(expectedLDALeft, runway.getLeftLda(), "Left LDA Incorrect");
        assertEquals(expectedLDARight, runway.getRightLda(), "Right LDA Incorrect");
    }

    @DisplayName("Landing calculations : Land towards left : Land over right")
    @ParameterizedTest
    @MethodSource("generateLandTowardsTestData")
    void calculateLandTowardsTest(Runway runway, Obstacle obstacleToAdd, double expectedLDALeft, double expectedLDARight, String message) {
        logger.info(message);
        addObstacle(runway,obstacleToAdd);
        runway.calculateLandTowards();

        assertEquals(expectedLDALeft, runway.getLeftLda(), "Left LDA Incorrect");
        assertEquals(expectedLDARight, runway.getRightLda(), "Right LDA Incorrect");
    }

    @DisplayName("Take-off calculations : Take-off towards left : Take-off away right")
    @ParameterizedTest
    @MethodSource("generateTakeOffTowardTestData")
    void calculateTakeOffTowardTest(
            Runway runway,
            Obstacle obstacleToAdd,
            double expectedTORALeft,
            double expectedASDALeft,
            double expectedTODALeft,
            double expectedTORARight,
            double expectedASDARight,
            double expectedTODARight,
            String message
    ) {
        logger.info(message);
        addObstacle(runway,obstacleToAdd);
        runway.calculateTakeOffToward();

        assertEquals(expectedTORALeft, runway.getLeftTora(), "Left TORA Incorrect");
        assertEquals(expectedASDALeft, runway.getLeftAsda(), "Left ASDA Incorrect");
        assertEquals(expectedTODALeft, runway.getLeftToda(), "Left TODA Incorrect");

        assertEquals(expectedTORARight, runway.getRightTora(), "Right TORA Incorrect");
        assertEquals(expectedASDARight, runway.getRightAsda(), "Right ASDA Incorrect");
        assertEquals(expectedTODARight, runway.getRightToda(), "Right TODA Incorrect");
    }

    @DisplayName("Take-off calculations : Take-off away left : Take-off towards right")
    @ParameterizedTest
    @MethodSource("generateTakeOffAwayTestData")
    void calculateTakeOffAwayTest(
            Runway runway,
            Obstacle obstacleToAdd,
            double expectedTORALeft,
            double expectedASDALeft,
            double expectedTODALeft,
            double expectedTORARight,
            double expectedASDARight,
            double expectedTODARight,
            String message
    ) {
        logger.info(message);
        addObstacle(runway,obstacleToAdd);
        runway.calculateTakeOffAway();

        assertEquals(expectedTORALeft, runway.getLeftTora(), "Left TORA Incorrect");
        assertEquals(expectedASDALeft, runway.getLeftAsda(), "Left ASDA Incorrect");
        assertEquals(expectedTODALeft, runway.getLeftToda(), "Left TODA Incorrect");

        assertEquals(expectedTORARight, runway.getRightTora(), "Right TORA Incorrect");
        assertEquals(expectedASDARight, runway.getRightAsda(), "Right ASDA Incorrect");
        assertEquals(expectedTODARight, runway.getRightToda(), "Right TODA Incorrect");
    }

    // Test Data Generation

    // Obstacles defined in given scenario's
    static Obstacle obstacle1 = new Obstacle("ob1", 12, -50, 3646);
    static Obstacle obstacle2 = new Obstacle("ob2", 25, 2853, 500);
    static Obstacle obstacle3 = new Obstacle("ob3", 15, 150, 3203);
    static Obstacle obstacle4 = new Obstacle("ob4", 20, 3546, 50);

    //TODO: Change generation to fit new tests
    private static Stream<Arguments> generateCheckDesignatorTestData() {
        return Stream.of(
                Arguments.of(runway1, "27L"),
                Arguments.of(runway2, "27R")
        );
    }
    //TODO: Generate test data for recalculate
    private static Stream<Arguments> generateRecalculateTestData() {
        return Stream.of(
                Arguments.of(
                        runway2,
                        obstacle1,
                        3346,
                        3346,
                        3346,
                        2985,
                        2986,
                        2986,
                        2986,
                        3346,
                        "Test: Given Scenario 1"
                ),
                Arguments.of(
                        runway1,
                        obstacle2,
                        1850,
                        1850,
                        1850,
                        2553,
                        2860,
                        2860,
                        2860,
                        1850,
                        "Test: Given Scenario 2"
                ),
                Arguments.of(
                        runway1,
                        obstacle3,
                        2903,
                        2903,
                        2903,
                        2393,
                        2393,
                        2393,
                        2393,
                        2903,
                        "Test: Given Scenario 3"
                ),
                Arguments.of(
                        runway2,
                        obstacle4,
                        2792,
                        2792,
                        2792,
                        3246,
                        3534,
                        3534,
                        3612,
                        2774,
                        "Test: Given Scenario 4"
                )
        );
    }
    private static Stream<Arguments> generateLandOverTestData() {
        return Stream.of();
    }
    private static Stream<Arguments> generateLandTowardsTestData() {
        return Stream.of();
    }
    private static Stream<Arguments> generateTakeOffTowardTestData() {
        return Stream.of();
    }
    //TODO: Generate test data for take-off away
    private static Stream<Arguments> generateTakeOffAwayTestData() {
        return Stream.of();
    }
}