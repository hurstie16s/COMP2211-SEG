package comp2211.seg.ProcessDataModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RunwayTest {

    //TODO: Test Rew-calculate
    //TODO: Test Negative Distance From Threshold

    static Runway runway1 = new Runway();
    static Runway runway2 = new Runway();
    static Runway runway3 = new Runway();
    static Runway runway4 = new Runway();

    // probably will be changed to just a @Before so that we can have different runways for different tests
    @BeforeAll
    static void setUpRunways() {
        // Runways 1 & 2 are the SAME runway from different directions
        setProperties(
                "09L",
                3902,
                3902,
                3902,
                3595,
                307,
                runway1
        );
        setProperties(
                "27R",
                3884,
                3962,
                3884,
                3884,
                0,
                runway2
        );
        // Runways 3 & 4 are the SAME runway from different directions
        setProperties(
                "09R",
                3660,
                3660,
                3660,
                3353,
                307,
                runway3
        );
        setProperties(
                "27L",
                3660,
                3660,
                3660,
                3660,
                0,
                runway4
        );
    }

    static void setProperties(
            String designator,
            int tora,
            int toda,
            int asda,
            int lda,
            int dispThreshold,
            Runway runway) {
        runway.runwayDesignatorProperty().set(designator);
        runway.inputLeftToraProperty().set(tora);
        runway.inputLeftTodaProperty().set(toda);
        runway.inputLeftAsdaProperty().set(asda);
        runway.inputLeftLdaProperty().set(lda);
        runway.dispThresholdLeftProperty().set(dispThreshold);
        runway.inputRightToraProperty().set(tora);
        runway.inputRightTodaProperty().set(toda);
        runway.inputRightAsdaProperty().set(asda);
        runway.inputRightLdaProperty().set(lda);
        runway.dispThresholdRightProperty().set(dispThreshold);
    }
    void addObstacle(Runway runway, Obstacle obstacle){
        runway.addObstacle();
        runway.getRunwayObstacle().heightProperty().set(obstacle.getHeight());
        runway.getRunwayObstacle().widthProperty().set(obstacle.getWidth());
        runway.getRunwayObstacle().lengthProperty().set(obstacle.getLength());
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
    void calculateLandOverTest(Runway runway, Obstacle obstacleToAdd, double expectedLDA) {
        addObstacle(runway,obstacleToAdd);
        runway.calculateLandOver();
        assertEquals(expectedLDA, runway.getRightLda());
    }

    @DisplayName("Landing calculations : Land towards an obstacle")
    @ParameterizedTest
    @MethodSource("generateLandTowardsTestData")
    void calculateLandTowardsTest(Runway runway, Obstacle obstacleToAdd, double expectedLDA) {
        addObstacle(runway,obstacleToAdd);
        runway.calculateLandTowards();
        assertEquals(expectedLDA, runway.getRightLda());
    }

    @DisplayName("Take-off calculations : Take-off towards an obstacle")
    @ParameterizedTest
    @MethodSource("generateTakeOffTowardTestData")
    void calculateTakeOffTowardTest(
            Runway runway,
            Obstacle obstacleToAdd,
            double expectedTORA,
            double exceptedASDA,
            double expectedTODA
    ) {
        addObstacle(runway,obstacleToAdd);
        runway.calculateTakeOffToward();
        assertEquals(expectedTORA, runway.getLeftTora());
        assertEquals(exceptedASDA, runway.getLeftAsda());
        assertEquals(expectedTODA, runway.getLeftToda());
    }

    @DisplayName("Take-off calculations : Take-off away from an obstacle")
    @ParameterizedTest
    @MethodSource("generateTakeOffAwayTestData")
    void calculateTakeOffAwayTest(
            Runway runway,
            Obstacle obstacleToAdd,
            double expectedTORA,
            double expectedASDA,
            double expectedTODA
    ) {
        addObstacle(runway,obstacleToAdd);
        runway.calculateTakeOffAway();
        assertEquals(expectedTORA, runway.getLeftTora());
        assertEquals(expectedASDA, runway.getLeftAsda());
        assertEquals(expectedTODA, runway.getLeftToda());
    }

    // Test Data Generation
    //TODO: Generate test data for recalculate
    private static Stream<Arguments> generateRecalculateTestData() {return null;}
    private static Stream<Arguments> generateLandOverTestData() {
        return Stream.of(
                Arguments.of(runway1, new Obstacle("obT1", 12, -50), 2985),
                Arguments.of(runway4, new Obstacle("obT2", 25, 500), 1850),
                Arguments.of(runway3, new Obstacle("obT3", 15, 150), 2393),
                Arguments.of(runway2, new Obstacle("obT4", 20, 50), 2774)
        );
    }
    private static Stream<Arguments> generateLandTowardsTestData() {
        return Stream.of(
                Arguments.of(runway2, new Obstacle("obT1", 12, 3646), 3346),
                Arguments.of(runway3, new Obstacle("obT2", 25, 2853), 2553),
                Arguments.of(runway4, new Obstacle("obT3", 15, 3203), 2903),
                Arguments.of(runway1, new Obstacle("obT4", 20, 3546), 3246)
        );
    }
    /*
    runway1 = 09L
    runway2 = 27R
    runway3 = 09R
    runway4 = 27L
     */
    private static Stream<Arguments> generateTakeOffTowardTestData() {
        return Stream.of(
                Arguments.of(runway2, new Obstacle("obT1", 12, 3646), 2986, 2986, 2986),
                Arguments.of(runway3, new Obstacle("obT2", 25, 2853), 1850, 1850, 1850),
                Arguments.of(runway4, new Obstacle("obT3", 15, 3203), 2393, 2393, 2393),
                Arguments.of(runway1, new Obstacle("obT4", 20, 3546), 2792, 2792, 2792)
        );
    }
    //TODO: Generate test data for take-off away
    private static Stream<Arguments> generateTakeOffAwayTestData() {
        return Stream.of(
            Arguments.of(runway1, new Obstacle("obT1", 12, -50), 3146, 3146, 3146),
            Arguments.of(runway4, new Obstacle("obT2", 25, 500), 2660, 2660, 2660),
            Arguments.of(runway3, new Obstacle("obT3", 15, 150), 2703, 2703, 2703),
            Arguments.of(runway2, new Obstacle("obT4", 20, 50), 3334, 3334, 3412)
        );
    }
}