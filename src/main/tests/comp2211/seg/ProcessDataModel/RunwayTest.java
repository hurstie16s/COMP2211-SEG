package comp2211.seg.ProcessDataModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    static Obstacle obstacle1 = new Obstacle("ob1", 25, 2600);
    static Obstacle obstacle2 = new Obstacle("ob2", 25, 500);

    // probably will be changed to just an @Before so that we can have different runways for different tests
    @BeforeAll
    static void setUpRunways() {
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

    @ParameterizedTest
    @MethodSource("generateRecalculateTestData")
    void recalculateTest() {
        assert false;
    }

    @DisplayName("Test calculations for landing over an obstacle")
    @ParameterizedTest
    @MethodSource("generateLandOverTestData")
    void calculateLandOverTest(Runway runway, Obstacle obstacleToAdd, double expectedLDA) {
        runway.addObstacle(obstacleToAdd);
        runway.calculateLandOver();
        assertEquals(expectedLDA, runway.getWorkingLda());
    }

    @ParameterizedTest
    @MethodSource("generateLandTowardsTestData")
    void calculateLandTowardsTest() {
        assert false;
    }

    @ParameterizedTest
    @MethodSource("generateTakeOffTowardTestData")
    void calculateTakeOffTowardTest() {
        assert false;
    }

    @ParameterizedTest
    @MethodSource("generateTakeOffAwayTestData")
    void calculateTakeOffAwayTest() {
        assert false;
    }

    // Test Data Generation
    private static Stream<Arguments> generateRecalculateTestData() {return null;}
    private static Stream<Arguments> generateLandOverTestData() {
        return Stream.of(
                Arguments.of(runway2, obstacle2, 2074.0)
        );
    }
    private static Stream<Arguments> generateLandTowardsTestData() {return null;}
    private static Stream<Arguments> generateTakeOffTowardTestData() {return null;}
    private static Stream<Arguments> generateTakeOffAwayTestData() {return null;}
}