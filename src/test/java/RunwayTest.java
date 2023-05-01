import comp2211.seg.ProcessDataModel.Obstacle;
import comp2211.seg.ProcessDataModel.Runway;

import javafx.beans.property.SimpleDoubleProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Runway test.
 */
public class RunwayTest {

    // TODO: Re-do all tests properly

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
    public static void setUpRunways() {
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
    public static void setProperties(
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
    public void addObstacle(Runway runway, Obstacle obstacle){
        runway.removeObstacle();
        obstacle.lengthProperty().set(0);
        runway.addObstacle(obstacle);
    }

    // Unit Tests
    /*
    Cases:
    where (XX<YY) {
        XXL/? -> XXL/YYR
        XXR/? -> XXR/YYL
        XXC/? -> XXC/YYC
        XX/? -> XX/YY
    }
    where (XX>YY) {
        XXL/(YY?)R -> YYR/XXL
        XXR/(YY?)L -> YYL/XXR
        XXC/(YY?)C -> YYC/XXC
        XX/(YY?) -> YY/XX
    }
    XX
     */
    @DisplayName("Re-designator test 09R/27L: form XXR/YYL where XX < YY")
    @Test
    public void designatorXXRYYLTest() {
        String expectedOutput = "09R/27L";
        var runway = new Runway();
        runway.runwayDesignatorLeft.set("09R");
        String actualOutput = runway.toString();
        assertEquals(expectedOutput, actualOutput, "Designator incorrectly calculated");
    }

    @DisplayName("Re-designator test 09L/27R: form XXR/YYL where XX < YY")
    @Test
    public void designatorXXLYYRTest() {
        String expectedOutput = "09L/27R";
        var runway = new Runway();
        runway.runwayDesignatorLeft.set("09L");
        String actualOutput = runway.toString();
        assertEquals(expectedOutput, actualOutput, "Designator incorrectly calculated");
    }

    @DisplayName("Re-designator test 09C/27C: form XXR/YYL where XX < YY")
    @Test
    public void designatorXXCYYCTest() {
        String expectedOutput = "09C/27C";
        var runway = new Runway();
        runway.runwayDesignatorLeft.set("09C");
        String actualOutput = runway.toString();
        assertEquals(expectedOutput, actualOutput, "Designator incorrectly calculated");
    }

    @DisplayName("Re-designator test 09/27: form XXR/YYL where XX < YY")
    @Test
    public void designatorXXYYTest() {
        String expectedOutput = "09/27";
        var runway = new Runway();
        runway.runwayDesignatorLeft.set("09");
        String actualOutput = runway.toString();
        assertEquals(expectedOutput, actualOutput, "Designator incorrectly calculated");
    }

    @DisplayName("Re-designator test 09R/27L: form XXR/YYL where XX > YY")
    @Test
    public void designatorXXRYYLFlipTest() {
        String expectedOutput = "09R/27L";
        var runway = new Runway();
        runway.runwayDesignatorLeft.set("27L");
        String actualOutput = runway.toString();
        assertEquals(expectedOutput, actualOutput, "Designator incorrectly calculated");
    }

    @DisplayName("Re-designator test 09L/27R: form XXR/YYL where XX > YY")
    @Test
    public void designatorXXLYYRFlipTest() {
        String expectedOutput = "09L/27R";
        var runway = new Runway();
        runway.runwayDesignatorLeft.set("27R");
        String actualOutput = runway.toString();
        assertEquals(expectedOutput, actualOutput, "Designator incorrectly calculated");
    }

    @DisplayName("Re-designator test 09C/27C: form XXR/YYL where XX > YY")
    @Test
    public void designatorXXCYYCFlipTest() {
        String expectedOutput = "09C/27C";
        var runway = new Runway();
        runway.runwayDesignatorLeft.set("27C");
        String actualOutput = runway.toString();
        assertEquals(expectedOutput, actualOutput, "Designator incorrectly calculated");
    }

    @DisplayName("Re-designator test 09/27: form XXR/YYL where XX > YY")
    @Test
    public void designatorXXYYFlipTest() {
        String expectedOutput = "09/27";
        var runway = new Runway();
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

    // TODO: Write Tests
    /*
    Tests
    R2 O1
    R1 O2
    R1 O3
    R2 O4
    R3 O5
    R3 O6
    R4 O7
    R4 O8
    R5 O9
     */

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
}