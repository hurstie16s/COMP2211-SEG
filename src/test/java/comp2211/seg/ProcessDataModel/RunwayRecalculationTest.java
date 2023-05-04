package comp2211.seg.ProcessDataModel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RunwayRecalculationTest {

    @DisplayName("Runway Recalculation test: Runway 1 (09R/27L), Obstacle 2, Land Towards")
    @Test
    public void runwayRecalculationR1O2LandTowardsTest() {
        var runway = RunwayTestData.runway1;
        var obstacle = RunwayTestData.obstacle2;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(2853);
        runway.runwayObstacle.lengthProperty().set(124);
        // Land towards
        RunwayCalculations.calculateLandTowardLeft(runway);
        RunwayCalculations.calculateLandTowardRight(runway);

        assertValues(
                runway.getLeftLda(),
                runway.getRightLda(),
                2184,
                445
        );
    }
    @DisplayName("Runway Recalculation test: Runway 1 (09R/27L), Obstacle 2, Takeoff Towards")
    @Test
    public void runwayRecalculationR1O2TakeOffTowardsTest() {
        var runway = RunwayTestData.runway1;
        var obstacle = RunwayTestData.obstacle2;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(2853);
        runway.runwayObstacle.lengthProperty().set(124);
        // Takeoff towards
        RunwayCalculations.calculateTakeOffTowardLeft(runway);
        RunwayCalculations.calculateTakeOffTowardRight(runway);

        assertValues(
                runway.getLeftTora(),
                runway.getRightTora(),
                runway.getLeftToda(),
                runway.getRightToda(),
                runway.getLeftAsda(),
                runway.getRightAsda(),
                1543,
                0,
                1543,
                0,
                1543,
                0
        );
    }

    @DisplayName("Runway Recalculation test: Runway 1 (09R/27L), Obstacle 2, Land Over")
    @Test
    public void runwayRecalculationR1O2LandOverTest() {
        var runway = RunwayTestData.runway1;
        var obstacle = RunwayTestData.obstacle2;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(2853);
        runway.runwayObstacle.lengthProperty().set(124);
        // Land Over
        RunwayCalculations.calculateLandOverLeft(runway);
        RunwayCalculations.calculateLandOverRight(runway);

        assertValues(
                runway.getLeftLda(),
                runway.getRightLda(),
                0,
                1543
        );
    }

    @DisplayName("Runway Recalculation test: Runway 1 (09R/27L), Obstacle 2, Takeoff Away")
    @Test
    public void runwayRecalculationR1O2TakeoffAwayTest() {
        var runway = RunwayTestData.runway1;
        var obstacle = RunwayTestData.obstacle2;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(2853);
        runway.runwayObstacle.lengthProperty().set(124);
        // Takeoff away
        RunwayCalculations.calculateTakeOffAwayLeft(runway);
        RunwayCalculations.calculateTakeOffAwayRight(runway);

        assertValues(
                runway.getLeftTora(),
                runway.getRightTora(),
                runway.getLeftToda(),
                runway.getRightToda(),
                runway.getLeftAsda(),
                runway.getRightAsda(),
                245,
                2291,
                245,
                2291,
                245,
                2291
        );
    }

    @DisplayName("Runway Recalculation test: Runway 2 (09L/27R), Obstacle 1, Land Towards")
    @Test
    public void runwayRecalculationR2O1LandTowardsTest() {
        var runway = RunwayTestData.runway2;
        var obstacle = RunwayTestData.obstacle1;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(-50);
        runway.runwayObstacle.lengthProperty().set(100);
        // Land towards
        RunwayCalculations.calculateLandTowardLeft(runway);
        RunwayCalculations.calculateLandTowardRight(runway);

        assertValues(
                runway.getLeftLda(),
                runway.getRightLda(),
                0,
                3602
        );
    }
    @DisplayName("Runway Recalculation test: Runway 2 (09L/27R), Obstacle 1, Takeoff Towards")
    @Test
    public void runwayRecalculationR2O1TakeOffTowardsTest() {
        var runway = RunwayTestData.runway2;
        var obstacle = RunwayTestData.obstacle1;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(-50);
        runway.runwayObstacle.lengthProperty().set(100);
        // Takeoff towards
        RunwayCalculations.calculateTakeOffTowardLeft(runway);
        RunwayCalculations.calculateTakeOffTowardRight(runway);

        assertValues(
                runway.getLeftTora(),
                runway.getRightTora(),
                runway.getLeftToda(),
                runway.getRightToda(),
                runway.getLeftAsda(),
                runway.getRightAsda(),
                0,
                3292,
                0,
                3292,
                0,
                3292
        );
    }

    @DisplayName("Runway Recalculation test: Runway 2 (09L/27R), Obstacle 1, Land Over")
    @Test
    public void runwayRecalculationR2O1LandOverTest() {
        var runway = RunwayTestData.runway2;
        var obstacle = RunwayTestData.obstacle1;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(-50);
        runway.runwayObstacle.lengthProperty().set(100);
        // Land Over
        RunwayCalculations.calculateLandOverLeft(runway);
        RunwayCalculations.calculateLandOverRight(runway);

        assertValues(
                runway.getLeftLda(),
                runway.getRightLda(),
                3292,
                0
        );
    }

    @DisplayName("Runway Recalculation test: Runway 2 (09L/27R), Obstacle 1, Takeoff Away")
    @Test
    public void runwayRecalculationR2O1TakeoffAwayTest() {
        var runway = RunwayTestData.runway2;
        var obstacle = RunwayTestData.obstacle1;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(-50);
        runway.runwayObstacle.lengthProperty().set(100);
        // Takeoff away
        RunwayCalculations.calculateTakeOffAwayLeft(runway);
        RunwayCalculations.calculateTakeOffAwayRight(runway);

        assertValues(
                runway.getLeftTora(),
                runway.getRightTora(),
                runway.getLeftToda(),
                runway.getRightToda(),
                runway.getLeftAsda(),
                runway.getRightAsda(),
                3402,
                0,
                3402,
                0,
                3402,
                0
        );
    }

    @DisplayName("Runway Recalculation test: Runway 1 (09R/27L), Obstacle 3, Land Towards")
    @Test
    public void runwayRecalculationR1O3LandTowardsTest() {
        var runway = RunwayTestData.runway1;
        var obstacle = RunwayTestData.obstacle3;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(150);
        runway.runwayObstacle.lengthProperty().set(58);
        // Land towards
        RunwayCalculations.calculateLandTowardLeft(runway);
        RunwayCalculations.calculateLandTowardRight(runway);

        assertValues(
                runway.getLeftLda(),
                runway.getRightLda(),
                0,
                3181
        );
    }
    @DisplayName("Runway Recalculation test: Runway 1 (09R/27L), Obstacle 3, Takeoff Towards")
    @Test
    public void runwayRecalculationR1O3TakeOffTowardsTest() {
        var runway = RunwayTestData.runway1;
        var obstacle = RunwayTestData.obstacle3;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(150);
        runway.runwayObstacle.lengthProperty().set(58);
        // Takeoff towards
        RunwayCalculations.calculateTakeOffTowardLeft(runway);
        RunwayCalculations.calculateTakeOffTowardRight(runway);

        assertValues(
                runway.getLeftTora(),
                runway.getRightTora(),
                runway.getLeftToda(),
                runway.getRightToda(),
                runway.getLeftAsda(),
                runway.getRightAsda(),
                0,
                2700,
                0,
                2700,
                0,
                2700
        );
    }

    @DisplayName("Runway Recalculation test: Runway 1 (09R/27L), Obstacle 3, Land Over")
    @Test
    public void runwayRecalculationR1O3LandOverTest() {
        var runway = RunwayTestData.runway1;
        var obstacle = RunwayTestData.obstacle3;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(150);
        runway.runwayObstacle.lengthProperty().set(58);
        // Land Over
        RunwayCalculations.calculateLandOverLeft(runway);
        RunwayCalculations.calculateLandOverRight(runway);

        assertValues(
                runway.getLeftLda(),
                runway.getRightLda(),
                2700,
                0
        );
    }

    @DisplayName("Runway Recalculation test: Runway 1 (09R/27L), Obstacle 3, Takeoff Away")
    @Test
    public void runwayRecalculationR1O3TakeoffAwayTest() {
        var runway = RunwayTestData.runway1;
        var obstacle = RunwayTestData.obstacle3;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(150);
        runway.runwayObstacle.lengthProperty().set(58);
        // Takeoff away
        RunwayCalculations.calculateTakeOffAwayLeft(runway);
        RunwayCalculations.calculateTakeOffAwayRight(runway);

        assertValues(
                runway.getLeftTora(),
                runway.getRightTora(),
                runway.getLeftToda(),
                runway.getRightToda(),
                runway.getLeftAsda(),
                runway.getRightAsda(),
                2981,
                0,
                2981,
                0,
                2981,
                0
        );
    }

    @DisplayName("Runway Recalculation test: Runway 2 (09L/27R), Obstacle 4, Land Towards")
    @Test
    public void runwayRecalculationR2O4LandTowardsTest() {
        var runway = RunwayTestData.runway2;
        var obstacle = RunwayTestData.obstacle4;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(3546);
        runway.runwayObstacle.lengthProperty().set(64);
        // Land towards
        RunwayCalculations.calculateLandTowardLeft(runway);
        RunwayCalculations.calculateLandTowardRight(runway);

        assertValues(
                runway.getLeftLda(),
                runway.getRightLda(),
                2907,
                24
        );
    }
    @DisplayName("Runway Recalculation test: Runway 2 (09L/27R), Obstacle 4, Takeoff Towards")
    @Test
    public void runwayRecalculationR2O4TakeOffTowardsTest() {
        var runway = RunwayTestData.runway2;
        var obstacle = RunwayTestData.obstacle4;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(3546);
        runway.runwayObstacle.lengthProperty().set(64);
        // Takeoff towards
        RunwayCalculations.calculateTakeOffTowardLeft(runway);
        RunwayCalculations.calculateTakeOffTowardRight(runway);

        assertValues(
                runway.getLeftTora(),
                runway.getRightTora(),
                runway.getLeftToda(),
                runway.getRightToda(),
                runway.getLeftAsda(),
                runway.getRightAsda(),
                2486,
                0,
                2486,
                0,
                2486,
                0
        );
    }

    @DisplayName("Runway Recalculation test: Runway 2 (09L/27R), Obstacle 4, Land Over")
    @Test
    public void runwayRecalculationR2O4LandOverTest() {
        var runway = RunwayTestData.runway2;
        var obstacle = RunwayTestData.obstacle1;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(3546);
        runway.runwayObstacle.lengthProperty().set(64);
        // Land Over
        RunwayCalculations.calculateLandOverLeft(runway);
        RunwayCalculations.calculateLandOverRight(runway);

        assertValues(
                runway.getLeftLda(),
                runway.getRightLda(),
                0,
                2868
        );
    }

    @DisplayName("Runway Recalculation test: Runway 2 (09L/27R), Obstacle 4, Takeoff Away")
    @Test
    public void runwayRecalculationR2O4TakeoffAwayTest() {
        var runway = RunwayTestData.runway2;
        var obstacle = RunwayTestData.obstacle4;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(3546);
        runway.runwayObstacle.lengthProperty().set(64);
        // Takeoff away
        RunwayCalculations.calculateTakeOffAwayLeft(runway);
        RunwayCalculations.calculateTakeOffAwayRight(runway);

        assertValues(
                runway.getLeftTora(),
                runway.getRightTora(),
                runway.getLeftToda(),
                runway.getRightToda(),
                runway.getLeftAsda(),
                runway.getRightAsda(),
                0,
                3014,
                0,
                3092,
                0,
                3014
        );
    }

    @DisplayName("Runway Recalculation test: Runway 3 (07R/25L), Obstacle 5, Land Towards")
    @Test
    public void runwayRecalculationR3O5LandTowardsTest() {
        var runway = RunwayTestData.runway3;
        var obstacle = RunwayTestData.obstacle5;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(80);
        runway.runwayObstacle.lengthProperty().set(82);
        // Land towards
        RunwayCalculations.calculateLandTowardLeft(runway);
        RunwayCalculations.calculateLandTowardRight(runway);

        assertValues(
                runway.getLeftLda(),
                runway.getRightLda(),
                0,
                1659
        );
    }
    @DisplayName("Runway Recalculation test: Runway 3 (07R/25L), Obstacle 5, Takeoff Towards")
    @Test
    public void runwayRecalculationR3O5TakeOffTowardsTest() {
        var runway = RunwayTestData.runway3;
        var obstacle = RunwayTestData.obstacle5;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(80);
        runway.runwayObstacle.lengthProperty().set(82);
        // Takeoff towards
        RunwayCalculations.calculateTakeOffTowardLeft(runway);
        RunwayCalculations.calculateTakeOffTowardRight(runway);

        assertValues(
                runway.getLeftTora(),
                runway.getRightTora(),
                runway.getLeftToda(),
                runway.getRightToda(),
                runway.getLeftAsda(),
                runway.getRightAsda(),
                0,
                1322,
                0,
                1322,
                0,
                1322
        );
    }

    @DisplayName("Runway Recalculation test: Runway 3 (07R/25L), Obstacle 5, Land Over")
    @Test
    public void runwayRecalculationR3O5LandOverTest() {
        var runway = RunwayTestData.runway3;
        var obstacle = RunwayTestData.obstacle5;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(80);
        runway.runwayObstacle.lengthProperty().set(82);
        // Land Over
        RunwayCalculations.calculateLandOverLeft(runway);
        RunwayCalculations.calculateLandOverRight(runway);

        assertValues(
                runway.getLeftLda(),
                runway.getRightLda(),
                1322,
                0
        );
    }

    @DisplayName("Runway Recalculation test: Runway 3 (07R/25L), Obstacle 5, Takeoff Away")
    @Test
    public void runwayRecalculationR3O5TakeoffAwayTest() {
        var runway = RunwayTestData.runway3;
        var obstacle = RunwayTestData.obstacle5;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(80);
        runway.runwayObstacle.lengthProperty().set(82);
        // Takeoff away
        RunwayCalculations.calculateTakeOffAwayLeft(runway);
        RunwayCalculations.calculateTakeOffAwayRight(runway);

        assertValues(
                runway.getLeftTora(),
                runway.getRightTora(),
                runway.getLeftToda(),
                runway.getRightToda(),
                runway.getLeftAsda(),
                runway.getRightAsda(),
                1541,
                0,
                2622,
                620,
                1541,
                0
        );
    }

    @DisplayName("Runway Recalculation test: Runway 3 (07R/25L), Obstacle 6, Land Towards")
    @Test
    public void runwayRecalculationR3O6LandTowardsTest() {
        var runway = RunwayTestData.runway3;
        var obstacle = RunwayTestData.obstacle6;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(2285);
        runway.runwayObstacle.lengthProperty().set(42);
        // Land towards
        RunwayCalculations.calculateLandTowardLeft(runway);
        RunwayCalculations.calculateLandTowardRight(runway);

        assertValues(
                runway.getLeftLda(),
                runway.getRightLda(),
                1964,
                0
        );
    }
    @DisplayName("Runway Recalculation test: Runway 3 (07R/25L), Obstacle 6, Takeoff Towards")
    @Test
    public void runwayRecalculationR3O6TakeOffTowardsTest() {
        var runway = RunwayTestData.runway3;
        var obstacle = RunwayTestData.obstacle6;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(2285);
        runway.runwayObstacle.lengthProperty().set(42);
        // Takeoff towards
        RunwayCalculations.calculateTakeOffTowardLeft(runway);
        RunwayCalculations.calculateTakeOffTowardRight(runway);

        assertValues(
                runway.getLeftTora(),
                runway.getRightTora(),
                runway.getLeftToda(),
                runway.getRightToda(),
                runway.getLeftAsda(),
                runway.getRightAsda(),
                1675,
                0,
                1675,
                0,
                1675,
                0
        );
    }

    @DisplayName("Runway Recalculation test: Runway 3 (07R/25L), Obstacle 6, Land Over")
    @Test
    public void runwayRecalculationR3O6LandOverTest() {
        var runway = RunwayTestData.runway3;
        var obstacle = RunwayTestData.obstacle6;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(2285);
        runway.runwayObstacle.lengthProperty().set(42);
        // Land Over
        RunwayCalculations.calculateLandOverLeft(runway);
        RunwayCalculations.calculateLandOverRight(runway);

        assertValues(
                runway.getLeftLda(),
                runway.getRightLda(),
                0,
                1675
        );
    }

    @DisplayName("Runway Recalculation test: Runway 3 (07R/25L), Obstacle 6, Takeoff Away")
    @Test
    public void runwayRecalculationR3O6TakeoffAwayTest() {
        var runway = RunwayTestData.runway3;
        var obstacle = RunwayTestData.obstacle6;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(2285);
        runway.runwayObstacle.lengthProperty().set(42);
        // Takeoff away
        RunwayCalculations.calculateTakeOffAwayLeft(runway);
        RunwayCalculations.calculateTakeOffAwayRight(runway);

        assertValues(
                runway.getLeftTora(),
                runway.getRightTora(),
                runway.getLeftToda(),
                runway.getRightToda(),
                runway.getLeftAsda(),
                runway.getRightAsda(),
                0,
                1764,
                437,
                2845,
                0,
                1821
        );
    }

    @DisplayName("Runway Recalculation test: Runway 4 (16L/34R), Obstacle 7, Land Towards")
    @Test
    public void runwayRecalculationR4O7LandTowardsTest() {
        var runway = RunwayTestData.runway4;
        var obstacle = RunwayTestData.obstacle7;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(976);
        runway.runwayObstacle.lengthProperty().set(38);
        // Land towards
        RunwayCalculations.calculateLandTowardLeft(runway);
        RunwayCalculations.calculateLandTowardRight(runway);

        assertValues(
                runway.getLeftLda(),
                runway.getRightLda(),
                657,
                658
        );
    }
    @DisplayName("Runway Recalculation test: Runway 4 (16L/34R), Obstacle 7, Takeoff Towards")
    @Test
    public void runwayRecalculationR4O7TakeOffTowardsTest() {
        var runway = RunwayTestData.runway4;
        var obstacle = RunwayTestData.obstacle7;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(976);
        runway.runwayObstacle.lengthProperty().set(38);
        // Takeoff towards
        RunwayCalculations.calculateTakeOffTowardLeft(runway);
        RunwayCalculations.calculateTakeOffTowardRight(runway);

        assertValues(
                runway.getLeftTora(),
                runway.getRightTora(),
                runway.getLeftToda(),
                runway.getRightToda(),
                runway.getLeftAsda(),
                runway.getRightAsda(),
                657,
                658,
                657,
                658,
                657,
                658
        );
    }

    @DisplayName("Runway Recalculation test: Runway 4 (16L/34R), Obstacle 7, Land Over")
    @Test
    public void runwayRecalculationR4O7LandOverTest() {
        var runway = RunwayTestData.runway4;
        var obstacle = RunwayTestData.obstacle7;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(976);
        runway.runwayObstacle.lengthProperty().set(38);
        // Land Over
        RunwayCalculations.calculateLandOverLeft(runway);
        RunwayCalculations.calculateLandOverRight(runway);

        assertValues(
                runway.getLeftLda(),
                runway.getRightLda(),
                458,
                457
        );
    }

    @DisplayName("Runway Recalculation test: Runway 4 (16L/34R), Obstacle 7, Takeoff Away")
    @Test
    public void runwayRecalculationR4O7TakeoffAwayTest() {
        var runway = RunwayTestData.runway4;
        var obstacle = RunwayTestData.obstacle7;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(976);
        runway.runwayObstacle.lengthProperty().set(38);
        // Takeoff away
        RunwayCalculations.calculateTakeOffAwayLeft(runway);
        RunwayCalculations.calculateTakeOffAwayRight(runway);

        assertValues(
                runway.getLeftTora(),
                runway.getRightTora(),
                runway.getLeftToda(),
                runway.getRightToda(),
                runway.getLeftAsda(),
                runway.getRightAsda(),
                458,
                457,
                657,
                595,
                458,
                457
        );
    }

    @DisplayName("Runway Recalculation test: Runway 4 (16L/34R), Obstacle 8, Land Towards")
    @Test
    public void runwayRecalculationR4O8LandTowardsTest() {
        var runway = RunwayTestData.runway4;
        var obstacle = RunwayTestData.obstacle8;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(0);
        runway.runwayObstacle.lengthProperty().set(74);
        // Land towards
        RunwayCalculations.calculateLandTowardLeft(runway);
        RunwayCalculations.calculateLandTowardRight(runway);

        assertValues(
                runway.getLeftLda(),
                runway.getRightLda(),
                0,
                1616
        );
    }
    @DisplayName("Runway Recalculation test: Runway 4 (16L/34R), Obstacle 8, Takeoff Towards")
    @Test
    public void runwayRecalculationR4O8TakeOffTowardsTest() {
        var runway = RunwayTestData.runway4;
        var obstacle = RunwayTestData.obstacle8;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(0);
        runway.runwayObstacle.lengthProperty().set(74);
        // Takeoff towards
        RunwayCalculations.calculateTakeOffTowardLeft(runway);
        RunwayCalculations.calculateTakeOffTowardRight(runway);

        assertValues(
                runway.getLeftTora(),
                runway.getRightTora(),
                runway.getLeftToda(),
                runway.getRightToda(),
                runway.getLeftAsda(),
                runway.getRightAsda(),
                0,
                1043,
                0,
                1043,
                0,
                1043
        );
    }

    @DisplayName("Runway Recalculation test: Runway 4 (16L/34R), Obstacle 8, Land Over")
    @Test
    public void runwayRecalculationR4O8LandOverTest() {
        var runway = RunwayTestData.runway4;
        var obstacle = RunwayTestData.obstacle8;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(0);
        runway.runwayObstacle.lengthProperty().set(74);
        // Land Over
        RunwayCalculations.calculateLandOverLeft(runway);
        RunwayCalculations.calculateLandOverRight(runway);

        assertValues(
                runway.getLeftLda(),
                runway.getRightLda(),
                1043,
                0
        );
    }

    @DisplayName("Runway Recalculation test: Runway 4 (16L/34R), Obstacle 8, Takeoff Away")
    @Test
    public void runwayRecalculationR4O8TakeoffAwayTest() {
        var runway = RunwayTestData.runway4;
        var obstacle = RunwayTestData.obstacle8;

        runway.addObstacle(obstacle);
        runway.runwayObstacle.distFromThresholdProperty().set(0);
        runway.runwayObstacle.lengthProperty().set(74);
        // Takeoff away
        RunwayCalculations.calculateTakeOffAwayLeft(runway);
        RunwayCalculations.calculateTakeOffAwayRight(runway);

        assertValues(
                runway.getLeftTora(),
                runway.getRightTora(),
                runway.getLeftToda(),
                runway.getRightToda(),
                runway.getLeftAsda(),
                runway.getRightAsda(),
                1416,
                0,
                1615,
                0,
                1416,
                0
        );
    }

    private void assertValues(
            double leftLDA_actual,
            double rightLDA_actual,
            double leftLDA_expected,
            double rightLDA_expected
    ) {
        assertEquals(leftLDA_expected, leftLDA_actual, "Left LDA Incorrect");
        assertEquals(rightLDA_expected , rightLDA_actual, "Right LDA Incorrect");
    }
    private void assertValues(
            double leftTORA_actual,
            double rightTORA_actual,
            double leftTODA_actual,
            double rightTODA_actual,
            double leftASDA_actual,
            double rightASDA_actual,
            double leftTORA_expected,
            double rightTORA_expected,
            double leftTODA_expected,
            double rightTODA_expected,
            double leftASDA_expected,
            double rightASDA_expected
    ) {
        assertEquals(leftTORA_expected, leftTORA_actual, "Left TORA Incorrect");
        assertEquals(rightTORA_expected, rightTORA_actual, "Right TORA Incorrect");
        assertEquals(leftTODA_expected, leftTODA_actual, "Left TODA Incorrect");
        assertEquals(rightTODA_expected, rightTODA_actual, "Right TODA Incorrect");
        assertEquals(leftASDA_expected, leftASDA_actual, "Left ASDA Incorrect");
        assertEquals(rightASDA_expected, rightASDA_actual, "Right ASDA Incorrect");
    }

}
