package comp2211.seg.ProcessDataModel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RunwayTest {

    //TODO: Test Rew-calculate
    //TODO: Re-work tests to handle left and right


    static Runway runway1 = new Runway();
    static Runway runway2 = new Runway();
    static Runway runway3 = new Runway();
    static Runway runway4 = new Runway();

    // probably will be changed to just a @Before so that we can have different runways for different tests
    @BeforeAll
    static void setUpRunways() {
        // Runway 09L & 27R
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
                runway1
        );
        // Blank runway
        setProperties(
                "27R",
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                runway2
        );
        // Runway 09R & 27L
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
                runway3
        );
        // Blank runway
        setProperties(
                "27L",
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                runway4
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
        runway.runwayDesignatorProperty().bind(new SimpleStringProperty(designator));
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
        if (direction == "L") {
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
        if (direction == "L") {
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
        if (direction == "L") {
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
        if (direction == "L") {
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
    //TODO: Generate test data for recalculate
    private static Stream<Arguments> generateRecalculateTestData() {return null;}
    private static Stream<Arguments> generateLandOverTestData() {
        return Stream.of(
                Arguments.of(runway1, new Obstacle("obT1", 12, -50), "L", 2985),
                Arguments.of(runway3, new Obstacle("obT2", 25, 2853), "R", 1850),
                Arguments.of(runway3, new Obstacle("obT3", 15, 150), "L", 2393),
                Arguments.of(runway1, new Obstacle("obT4", 20, 3546), "R", 2775)
        );
    }
    private static Stream<Arguments> generateLandTowardsTestData() {
        return Stream.of(
                Arguments.of(runway1, new Obstacle("obT1", 12, -50), "R", 3345),
                Arguments.of(runway3, new Obstacle("obT2", 25, 2853), "L", 2553),
                Arguments.of(runway3, new Obstacle("obT3", 15, 150), "R", 2903),
                Arguments.of(runway1, new Obstacle("obT4", 20, 3546), "L", 3246)
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
                Arguments.of(runway3, new Obstacle("obT2", 25, 2853), "L", 1850, 1850, 1850),
                Arguments.of(runway3, new Obstacle("obT3", 15, 150), "R", 2393, 2393, 2393),
                Arguments.of(runway1, new Obstacle("obT4", 20, 3546), "L", 2793, 2793, 2793)
        );
    }
    //TODO: Generate test data for take-off away
    private static Stream<Arguments> generateTakeOffAwayTestData() {
        return Stream.of(
            Arguments.of(runway1, new Obstacle("obT1", 12, -50), "L", 3145, 3145, 3145),
            Arguments.of(runway3, new Obstacle("obT2", 25, 2853), "R", 2660, 2660, 2660),
            Arguments.of(runway3, new Obstacle("obT3", 15, 150), "L", 2703, 2703, 2703),
            Arguments.of(runway1, new Obstacle("obT4", 20, 3546), "R", 3353, 3353, 3431)
        );
    }
}