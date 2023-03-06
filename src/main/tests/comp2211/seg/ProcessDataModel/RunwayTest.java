package comp2211.seg.ProcessDataModel;

import junitparams.Parameters;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RunwayTest {

    static Runway runway1 = new Runway();
    static Runway runway2 = new Runway();
    static Runway runway3 = new Runway();
    static Runway runway4 = new Runway();

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

    @Test
    void recalculateTest() {
    }

    @Test
    @Parameters({"runway1", "runway2", "runway3", "runway4"})
    void calculateLandOverTest(Runway runway) {

    }

    @Test
    void calculateLandTowardsTest() {
    }

    @Test
    void calculateTakeOffTowardTest() {
    }

    @Test
    void calculateTakeOffAwayTest() {
    }
}