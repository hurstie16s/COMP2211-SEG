import comp2211.seg.ProcessDataModel.Obstacle;
import comp2211.seg.ProcessDataModel.Runway;

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

/**
 * The type Runway test.
 */
class RunwayTest {

    private static final Logger logger = LogManager.getLogger(RunwayTest.class);

    //TODO: Test Rew-calculate
    //TODO: Re-work tests to handle left and right


    /**
     * The constant runway1.
     */
// 09R and 27L
    static Runway runway1 = new Runway();
    /**
     * The constant runway2.
     */
// 09L and 27R
    static Runway runway2 = new Runway();
    /**
     * The constant runway3.
     */
// 07R and 25L
    static Runway runway3 = new Runway();
    /**
     * The constant runway4.
     */
// 16L and 34R
    static Runway runway4 = new Runway();
    /**
     * The constant runway5.
     */
// 04C and 22C
    static Runway runway5 = new Runway();

    /**
     * Sets up runways.
     */
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
        setProperties(
                "07R",
                2162,
                3243,
                2162,
                2162,
                2162,
                3243,
                2219,
                2080,
                runway3
        );
        setProperties(
                "16L",
                1953,
                2152,
                1953,
                1953,
                1953,
                2091,
                1953,
                1953,
                runway4
        );
        setProperties(
                "04C",
                968,
                968,
                968,
                968,
                968,
                968,
                968,
                968,
                runway5
        );
    }

    /**
     * Sets properties.
     *
     * @param designator the designator
     * @param leftTora   the left tora
     * @param leftToda   the left toda
     * @param leftAsda   the left asda
     * @param leftLda    the left lda
     * @param rightTora  the right tora
     * @param rightToda  the right toda
     * @param rightAsda  the right asda
     * @param rightLda   the right lda
     * @param runway     the runway
     */
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

    /**
     * Add obstacle.
     *
     * @param runway   the runway
     * @param obstacle the obstacle
     */
    void addObstacle(Runway runway, Obstacle obstacle){
        runway.removeObstacle();
        obstacle.lengthProperty().set(0);
        runway.addObstacle(obstacle);
    }

    // Unit Tests

    /**
     * Check designator test data.
     *
     * @param runway             the runway
     * @param expectedDesignator the expected designator
     */
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

    /**
     * Recalculate test.
     *
     * @param runway            the runway
     * @param obstacleToAdd     the obstacle to add
     * @param expectedTORALeft  the expected tora left
     * @param expectedASDALeft  the expected asda left
     * @param expectedTODALeft  the expected toda left
     * @param expectedLDALeft   the expected lda left
     * @param expectedTORARight the expected tora right
     * @param expectedASDARight the expected asda right
     * @param expectedTODARight the expected toda right
     * @param expectedLDARight  the expected lda right
     * @param message           the message
     */
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
        assertEquals(expectedLDARight, runway.getRightLda(), "Right LDA Incorrect");

        assertEquals(expectedTORALeft, runway.getLeftTora(), "Left TORA Incorrect");
        assertEquals(expectedTORARight, runway.getRightTora(), "Right TORA Incorrect");

        assertEquals(expectedASDALeft, runway.getLeftAsda(), "Left ASDA Incorrect");
        assertEquals(expectedASDARight, runway.getRightAsda(), "Right ASDA Incorrect");

        assertEquals(expectedTODALeft, runway.getLeftToda(), "Left TODA Incorrect");
        assertEquals(expectedTODARight, runway.getRightToda(), "Right TODA Incorrect");
    }

    // Test Data Generation

    /**
     * The constant obstacle1.
     */
// Obstacles defined in given scenario's
    static Obstacle obstacle1 = new Obstacle("ob1", 12, -50);
    /**
     * The Obstacle 2.
     */
    static Obstacle obstacle2 = new Obstacle("ob2", 25, 2853);
    /**
     * The Obstacle 3.
     */
    static Obstacle obstacle3 = new Obstacle("ob3", 15, 150);
    /**
     * The Obstacle 4.
     */
    static Obstacle obstacle4 = new Obstacle("ob4", 20, 3546);
    /**
     * The Obstacle 5.
     */
    static Obstacle obstacle5 = new Obstacle("ob5", 14, 80);
    /**
     * The Obstacle 6.
     */
    static Obstacle obstacle6 = new Obstacle("ob6", 11, 2285);
    /**
     * The Obstacle 7.
     */
    static Obstacle obstacle7 = new Obstacle("ob7", 13, 976);
    /**
     * The Obstacle 8.
     */
    static Obstacle obstacle8 = new Obstacle("ob8", 17, 0);
    /**
     * The Obstacle 9.
     */
    static Obstacle obstacle9 = new Obstacle("ob9", 8, 484);

    //TODO: Change generation to fit new tests
    private static Stream<Arguments> generateCheckDesignatorTestData() {
        return Stream.of(
                Arguments.of(runway1, "27L"),
                Arguments.of(runway2, "27R"),
                Arguments.of(runway3, "25L"),
                Arguments.of(runway4, "34R"),
                Arguments.of(runway5, "22C")
        );
    }
    //TODO: Generate test data for recalculate
    private static Stream<Arguments> generateRecalculateTestData() {
        return Stream.of(
                Arguments.of(
                        runway2,
                        obstacle1,
                        3145, //TORA left
                        3145, //ASDA left
                        3145, //TODA left
                        2985, //LDA left
                        2985, //TORA right
                        2985, //ASDA right
                        2985, //TODA right
                        3345, //LDA right
                        "Test: Given Scenario 1"
                ),
                Arguments.of(
                        runway1,
                        obstacle2,
                        1850,
                        1850,
                        1850,
                        2553,
                        2660,
                        2660,
                        2660,
                        1850,
                        "Test: Given Scenario 2"
                ),
                Arguments.of(
                        runway1,
                        obstacle3,
                        2703,
                        2703,
                        2703,
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
                        2793,
                        2793,
                        2793,
                        3246,
                        3353,
                        3353,
                        3431,
                        2775,
                        "Test: Given Scenario 4"
                ),
                Arguments.of(
                        runway3,
                        obstacle5,
                        1582,
                        1582,
                        2663,
                        1322,
                        1404,
                        1404,
                        1404,
                        1782,
                        "Test: Own Scenario 1"
                ),
                Arguments.of(
                        runway3,
                        obstacle6,
                        1675,
                        1675,
                        1675,
                        1985,
                        1785,
                        1842,
                        2866,
                        1593,
                        "Test: Own Scenario 2"
                ),
                Arguments.of(
                        runway4,
                        obstacle7,
                        477,
                        477,
                        676,
                        267,
                        267,
                        267,
                        267,
                        677,
                        "Test: Own Scenario 3"
                ),
                Arguments.of(
                        runway4,
                        obstacle8,
                        1453,
                        1453,
                        1652,
                        1043,
                        1043,
                        1043,
                        1043,
                        1653,
                        "Test: Own Scenario 4"
                ),
                Arguments.of(
                        runway5,
                        obstacle9,
                        -16,
                        -16,
                        -16,
                        -16,
                        24,
                        24,
                        24,
                        184,
                        "Test: Own Scenario 5"
                )
        );
    }
}