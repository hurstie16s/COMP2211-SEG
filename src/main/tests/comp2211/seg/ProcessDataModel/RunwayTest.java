package comp2211.seg.ProcessDataModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RunwayTest {

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
                306,
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
                3360,
                3360,
                3360,
                3360,
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
        runway.toraProperty().set(tora);
        runway.todaProperty().set(toda);
        runway.asdaProperty().set(asda);
        runway.ldaProperty().set(lda);
        runway.dispThresholdProperty().set(dispThreshold);
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
        runway.addObstacle(obstacleToAdd);
        runway.calculateLandOver();
        assertEquals(expectedLDA, runway.getWorkingLda());
    }

    @DisplayName("Landing calculations : Land towards an obstacle")
    @ParameterizedTest
    @MethodSource("generateLandTowardsTestData")
    void calculateLandTowardsTest(Runway runway, Obstacle obstacleToAdd, double expectedLDA) {
        runway.addObstacle(obstacleToAdd);
        runway.calculateLandTowards();
        assertEquals(expectedLDA, runway.getWorkingLda());
    }

    @DisplayName("Take-off calculations : Take-off towards an obstacle")
    @ParameterizedTest
    @MethodSource("generateTakeOffTowardTestData")
    void calculateTakeOffTowardTest(Runway runway, Obstacle obstacleToAdd, double expectedTORA) {
        assert false;
    }

    @DisplayName("Take-off calculations : Take-off away from an obstacle")
    @ParameterizedTest
    @MethodSource("generateTakeOffAwayTestData")
    void calculateTakeOffAwayTest(
            Runway runway,
            Obstacle obstacleToAdd,
            double expectedTORA,
            double expectedTODA,
            double expectedASDA
    ) {
        assert false;
    }

    // Test Data Generation
    //TODO: Generate test data for recalculate
    private static Stream<Arguments> generateRecalculateTestData() {return null;}
    private static Stream<Arguments> generateLandOverTestData() {
        return Stream.of(
                Arguments.of(runway2, new Obstacle("obT1", 25, 500), 2074.0),
                Arguments.of(runway2, new Obstacle("obT2", 12, 47), 3177),
                Arguments.of(runway2, new Obstacle("obT3", 19, 312), 2562),
                Arguments.of(runway2, new Obstacle("obT4", 8, 183), 3201)
        );
    }
    private static Stream<Arguments> generateLandTowardsTestData() {
        return Stream.of(
                Arguments.of(runway1, new Obstacle("ob1", 25, 2600), 2300.0)
        );
    }
    //TODO: Generate test data for take-off towards
    private static Stream<Arguments> generateTakeOffTowardTestData() {return null;}
    //TODO: Generate test data for take-off away
    private static Stream<Arguments> generateTakeOffAwayTestData() {return null;}
}