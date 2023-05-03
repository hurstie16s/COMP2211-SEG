package comp2211.seg.ProcessDataModel;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Runway test.
 */
@Ignore
public class RunwayTestIgnoreTemp {

    // TODO: Re-do all tests properly

    private static final Logger logger = LogManager.getLogger(RunwayTestIgnoreTemp.class);

    //TODO: Test Rew-calculate
    //TODO: Re-work tests to handle left and right



    @BeforeAll
    public static void setUp() {
        RunwayTestData.setUpRunways();
    }

    // Unit Tests
    @DisplayName("Re-designator test 09R/27L: form XXR/YYL where XX < YY")
    @Test
    public void designatorXXRYYLTest() {
        String expectedOutput = "09R/27L";
        var runway = new Runway();
        runway.dualDirectionRunway.set(true);
        runway.runwayDesignatorLeft.set("09R");
        String actualOutput = runway.toString();
        assertEquals(expectedOutput, actualOutput, "Designator incorrectly calculated");
    }

    @DisplayName("Re-designator test 09L/27R: form XXL/YYR where XX < YY")
    @Test
    public void designatorXXLYYRTest() {
        String expectedOutput = "09L/27R";
        var runway = new Runway();
        runway.dualDirectionRunway.set(true);
        runway.runwayDesignatorLeft.set("09L");
        String actualOutput = runway.toString();
        assertEquals(expectedOutput, actualOutput, "Designator incorrectly calculated");
    }

    @DisplayName("Re-designator test 09C/27C: form XXC/YYC where XX < YY")
    @Test
    public void designatorXXCYYCTest() {
        String expectedOutput = "09C/27C";
        var runway = new Runway();
        runway.dualDirectionRunway.set(true);
        runway.runwayDesignatorLeft.set("09C");
        String actualOutput = runway.toString();
        assertEquals(expectedOutput, actualOutput, "Designator incorrectly calculated");
    }

    @DisplayName("Re-designator test 09/27: form XX/YY where XX < YY")
    @Test
    public void designatorXXYYTest() {
        String expectedOutput = "09/27";
        var runway = new Runway();
        runway.dualDirectionRunway.set(true);
        runway.runwayDesignatorLeft.set("09");
        String actualOutput = runway.toString();
        assertEquals(expectedOutput, actualOutput, "Designator incorrectly calculated");
    }

    @DisplayName("Re-designator test 09R/27L: form XXR/YYL where XX > YY")
    @Test
    public void designatorXXRYYLFlipTest() {
        String expectedOutput = "09R/27L";
        var runway = new Runway();
        runway.dualDirectionRunway.set(true);
        runway.runwayDesignatorLeft.set("27L");
        String actualOutput = runway.toString();
        assertEquals(expectedOutput, actualOutput, "Designator incorrectly calculated");
    }

    @DisplayName("Re-designator test 09L/27R: form XXL/YYR where XX > YY")
    @Test
    public void designatorXXLYYRFlipTest() {
        String expectedOutput = "09L/27R";
        var runway = new Runway();
        runway.dualDirectionRunway.set(true);
        runway.runwayDesignatorLeft.set("27R");
        String actualOutput = runway.toString();
        assertEquals(expectedOutput, actualOutput, "Designator incorrectly calculated");
    }

    @DisplayName("Re-designator test 09C/27C: form XXC/YYC where XX > YY")
    @Test
    public void designatorXXCYYCFlipTest() {
        String expectedOutput = "09C/27C";
        var runway = new Runway();
        runway.dualDirectionRunway.set(true);
        runway.runwayDesignatorLeft.set("27C");
        String actualOutput = runway.toString();
        assertEquals(expectedOutput, actualOutput, "Designator incorrectly calculated");
    }

    @DisplayName("Re-designator test 09/27: form XX/YY where XX > YY")
    @Test
    public void designatorXXYYFlipTest() {
        String expectedOutput = "09/27";
        var runway = new Runway();
        runway.dualDirectionRunway.set(true);
        runway.runwayDesignatorLeft.set("27");
        String actualOutput = runway.toString();
        assertEquals(expectedOutput, actualOutput, "Designator incorrectly calculated");
    }

    @DisplayName("Re-designator test 09: form XX")
    @Test
    public void designatorXXTest() {
        String expectedOutput = "09";
        var runway = new Runway();
        runway.runwayDesignatorLeft.set("09");
        runway.dualDirectionRunway.set(false);
        String actualOutput = runway.toString();
        assertEquals(expectedOutput, actualOutput, "Designator incorrectly calculated");
    }

    @DisplayName("Re-designator test, error handling, Character is not L R C or \"\"")
    @Test
    public void designatorErrorCaseTest() {
        String expectedOutput = "ERROR/ERROR";
        var runway = new Runway();
        runway.dualDirectionRunway.set(true);
        runway.runwayDesignatorLeft.set("09A");
        String actualOutput = runway.toString();
        assertEquals(expectedOutput, actualOutput, "Designator incorrectly calculated");
    }

    // TODO: Write Tests
    /*
    Tests
    R2 O1 done
    R1 O2 maths done
    R1 O3 maths done
    R2 O4 maths done
    R3 O5
    R3 O6
    R4 O7
    R4 O8
    R5 O9
     */

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
                3345
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
                2985,
                0,
                2985,
                0,
                2985
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
                3145,
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
                3095,
                0,
                3095,
                78,
                3095,
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