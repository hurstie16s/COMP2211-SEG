package comp2211.seg.ProcessDataModel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Objects;
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
        runway.addObstacle();
        runway.getRunwayObstacle().heightProperty().set(obstacle.getHeight());
        runway.getRunwayObstacle().widthProperty().set(obstacle.getWidth());
        runway.getRunwayObstacle().lengthProperty().set(obstacle.getLength());
        runway.getRunwayObstacle().distFromThresholdProperty().set(obstacle.getDistFromThreshold());
    }

    // Unit Tests

    @DisplayName("Runway Designators : Check 2nd designator is correctly calculated")
    @ParameterizedTest
    @MethodSource("generateCheckDesignatorTestData")
    void checkDesignatorTestData(Runway runway, String expectedDesignator) {

        var expectedNumber = expectedDesignator.substring(0,2);
        var expectedCharacter = expectedDesignator.substring(2);

        //assertEquals(expectedNumber, runway.getRunwayDesignatorRight().substring(0,2), "Number for runway designator incorrect");
        assertEquals(expectedCharacter, runway.getRunwayDesignatorRight().substring(2), "Character for runway designator incorrect");
        assertEquals(expectedDesignator, runway.getRunwayDesignatorRight(), "Runway designator incorrect");
    }

    //TODO: Write Test
    @DisplayName("Landing/ take-off calculations : Recalculate appropriate values")
    @ParameterizedTest
    @MethodSource("generateRecalculateTestData")
    void recalculateTest() {
        assert false;
    }

    @DisplayName("Landing calculations : Land over an obstacle")
    @ParameterizedTest
    @MethodSource("generateLandOverTestData")
    void calculateLandOverTest(Runway runway, Obstacle obstacleToAdd, String direction, double expectedLDA) {
        addObstacle(runway,obstacleToAdd);
        runway.calculateLandOver();
        if (Objects.equals(direction, "L")) {
            assertEquals(expectedLDA, runway.getLeftLda());
        } else {
            assertEquals(expectedLDA, runway.getRightLda());
        }
    }

    @DisplayName("Landing calculations : Land towards an obstacle")
    @ParameterizedTest
    @MethodSource("generateLandTowardsTestData")
    void calculateLandTowardsTest(Runway runway, Obstacle obstacleToAdd, String direction, double expectedLDA) {
        addObstacle(runway,obstacleToAdd);
        runway.calculateLandTowards();
        if (Objects.equals(direction, "L")) {
            assertEquals(expectedLDA, runway.getLeftLda());
        } else {
            assertEquals(expectedLDA, runway.getRightLda());
        }
    }

    @DisplayName("Take-off calculations : Take-off towards an obstacle")
    @ParameterizedTest
    @MethodSource("generateTakeOffTowardTestData")
    void calculateTakeOffTowardTest(
            Runway runway,
            Obstacle obstacleToAdd,
            String direction,
            double expectedTORA,
            double exceptedASDA,
            double expectedTODA
    ) {
        addObstacle(runway,obstacleToAdd);
        runway.calculateTakeOffToward();
        if (Objects.equals(direction, "L")) {
            assertEquals(expectedTORA, runway.getLeftTora());
            assertEquals(exceptedASDA, runway.getLeftAsda());
            assertEquals(expectedTODA, runway.getLeftToda());
        } else {
            assertEquals(expectedTORA, runway.getRightTora());
            assertEquals(exceptedASDA, runway.getRightAsda());
            assertEquals(expectedTODA, runway.getRightToda());
        }
    }

    @DisplayName("Take-off calculations : Take-off away from an obstacle")
    @ParameterizedTest
    @MethodSource("generateTakeOffAwayTestData")
    void calculateTakeOffAwayTest(
            Runway runway,
            Obstacle obstacleToAdd,
            String direction,
            double expectedTORA,
            double expectedASDA,
            double expectedTODA
    ) {
        addObstacle(runway,obstacleToAdd);
        runway.calculateTakeOffAway();
        if (Objects.equals(direction, "L")) {
            assertEquals(expectedTORA, runway.getLeftTora());
            assertEquals(expectedASDA, runway.getLeftAsda());
            assertEquals(expectedTODA, runway.getLeftToda());
        } else {
            assertEquals(expectedTORA, runway.getRightTora());
            assertEquals(expectedASDA, runway.getRightAsda());
            assertEquals(expectedTODA, runway.getRightToda());
        }
    }

    // Test Data Generation
    //TODO: Change generation to fit new tests
    private static Stream<Arguments> generateCheckDesignatorTestData() {
        return Stream.of(
                Arguments.of(runway1, "27L"),
                Arguments.of(runway2, "27R")
        );
    }
    //TODO: Generate test data for recalculate
    private static Stream<Arguments> generateRecalculateTestData() {
        return Stream.of();
    }
    private static Stream<Arguments> generateLandOverTestData() {
        return Stream.of(
                Arguments.of(runway1, new Obstacle("obT1", 12, -50), "L", 2985),
                Arguments.of(runway1, new Obstacle("obT4", 20, 3546), "R", 2775),
                Arguments.of(runway2, new Obstacle("obT5", 14, 80), "L", 1322)
        );
    }
    private static Stream<Arguments> generateLandTowardsTestData() {
        return Stream.of(
                Arguments.of(runway1, new Obstacle("obT1", 12, -50), "R", 3345),
                Arguments.of(runway1, new Obstacle("obT4", 20, 3546), "L", 3246),
                Arguments.of(runway2, new Obstacle("obT6", 14, 2010), "L", 1710)
        );
    }
    /*
    runway1 = 09L & 27R
    runway2 =
    runway3 = 09R & 27L
    runway4 =
     */
    private static Stream<Arguments> generateTakeOffTowardTestData() {
        return Stream.of(
                Arguments.of(runway1, new Obstacle("obT1", 12, -50),"R", 2985, 2985, 2985),
                Arguments.of(runway1, new Obstacle("obT4", 20, 3546), "L", 2793, 2793, 2793),
                Arguments.of(runway2, new Obstacle("obT6", 14, 2010), "L", 1250, 1250, 1250)
        );
    }
    //TODO: Generate test data for take-off away
    private static Stream<Arguments> generateTakeOffAwayTestData() {
        return Stream.of(
            Arguments.of(runway1, new Obstacle("obT1", 12, -50), "L", 3145, 3145, 3145),
            Arguments.of(runway1, new Obstacle("obT4", 20, 3546), "R", 3353, 3353, 3431),
            Arguments.of(runway2, new Obstacle("obT5", 14, 80), "L", 1582, 1582, 2663)
        );
    }
}