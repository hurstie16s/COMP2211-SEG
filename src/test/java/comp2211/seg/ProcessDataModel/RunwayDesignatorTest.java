package comp2211.seg.ProcessDataModel;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Runway test.
 */
public class RunwayDesignatorTest {

    // TODO: Re-do all tests properly

    private static final Logger logger = LogManager.getLogger(RunwayDesignatorTest.class);

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
}