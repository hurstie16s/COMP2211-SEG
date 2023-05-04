package comp2211.seg.ProcessDataModel;

import javafx.beans.property.SimpleDoubleProperty;
import org.junit.jupiter.api.BeforeAll;

public class RunwayTestData {

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

    /**
     * Sets up runways.
     */
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
    static Obstacle obstacle7 = new Obstacle("ob7", 3, 976);
    /**
     * The Obstacle 8.
     */
    static Obstacle obstacle8 = new Obstacle("ob8", 17, 0);
    /**
     * The Obstacle 9.
     */

}
