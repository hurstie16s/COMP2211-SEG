package comp2211.seg.ProcessDataModel;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Runway {
    //Inputs

    /*
    A runway designator consists of a two-digit number,
    which is the whole number nearest to one tenth of the magnetic North
    when viewed from the direction of approach. For example,
    if the azimuth of the centre-line is 153 then the runway designator will be 15
    This is followed by L, C or R to differentiate between parallel runways
     */
    private final SimpleStringProperty runwayDesignator = new SimpleStringProperty("36C");
    private final SimpleDoubleProperty tora = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty workingTora = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty toda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty workingToda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty asda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty workingAsda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty lda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty workingLda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty dispThreshold = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty stopway = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty clearway = new SimpleDoubleProperty(0);

    private Obstacle runwayObstacle = null;

    private final SimpleBooleanProperty landingMode = new SimpleBooleanProperty(true);


    private final SimpleBooleanProperty direction = new SimpleBooleanProperty(true);

    // End of Inputs



    // Typical values, may become variable down the line
    // Constants
    private static final SimpleDoubleProperty MINRESA = new SimpleDoubleProperty(240);
    private static final SimpleDoubleProperty ALSMAGNITUDE = new SimpleDoubleProperty(50);
    private static final SimpleDoubleProperty STRIPEND = new SimpleDoubleProperty(60);
    private static final SimpleDoubleProperty BLASTZONE = new SimpleDoubleProperty(500);
    private static final SimpleDoubleProperty SLOPE = new SimpleDoubleProperty(50);

    //Outputs
    private SimpleDoubleProperty output1 = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty output2 = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty output3 = new SimpleDoubleProperty(0);

    public Runway() {
        for (Property prop: new Property[] {
                runwayDesignator,
                tora,
                toda,
                asda,
                lda,
                dispThreshold,
                stopway,
                clearway,
                landingMode,
                direction
        }) {
            prop.addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                    recalculate();
                }
            });
        }
    }

    public void addObstacle(Obstacle obstacleToAdd) {
        this.runwayObstacle = obstacleToAdd;
        // re-calculate values?
    }

    /**
     * Removing the obstacle from the runway
     * @param obstacleToRemove
     */
    public void removeObstacle(Obstacle obstacleToRemove) {
        if (runwayObstacle == obstacleToRemove) {
            this.runwayObstacle = null;
        }
        // set values back to original
        workingTora.set(tora.get());
        workingToda.set(toda.get());
        workingAsda.set(asda.get());
        workingLda.set(lda.get());
    }

    public void recalculate(){
        if (landingMode.get()){
            if (direction.get()){
                calculateLandOver();

            } else {
                calculateLandTowards();

            }
        } else {

            if (direction.get()){
                calculateTakeOffAway();

            } else {
                calculateTakeOffToward();

            }
        }
    }

    /**
     Calculations for when a plane is landing over an obstacle
     */
    public void calculateLandOver() {
        if (runwayObstacle != null) {

            workingTora.set(tora.get() - BLASTZONE.get() - runwayObstacle.getDistFromThreshold() - dispThreshold.get());
            workingAsda.set(workingTora.get() + stopway.get());
            workingToda.set(workingTora.get() + clearway.get());
            workingLda.set(lda.get() - runwayObstacle.getDistFromThreshold() - (runwayObstacle.getHeight() * SLOPE.get()) - STRIPEND.get());
        }
        output1.set(workingTora.get());
    }

    /**
     Calculations for when a plane is landing towards an obstacle
     */
    public void calculateLandTowards() {
        if (runwayObstacle != null) {

            workingTora.set(runwayObstacle.getDistFromThreshold() - (50 * runwayObstacle.getHeight()) - STRIPEND.get());
            workingAsda.set(workingTora.get());
            workingToda.set(workingTora.get());
            workingLda.set(runwayObstacle.getDistFromThreshold() - MINRESA.get() - STRIPEND.get());
        }
        output1.set(workingToda.get());
    }

    /**
    Calculations for when a plane is taking-off towards an obstacle
     */
    public void calculateTakeOffToward() {
        if (runwayObstacle != null) {

            workingTora.set(runwayObstacle.getDistFromThreshold() - (50 * runwayObstacle.getHeight()) - STRIPEND.get());
            workingAsda.set(workingTora.get());
            workingToda.set(workingTora.get());
            workingLda.set(runwayObstacle.getDistFromThreshold() - MINRESA.get() - STRIPEND.get());
        }
        output1.set(Double.parseDouble(runwayDesignator.get()));
    }

    /**
     Calculations for when a plane is taking-off away from an obstacle
     */
    public void calculateTakeOffAway() {
        if (runwayObstacle != null) {

            workingTora.set(tora.get() - BLASTZONE.get() - runwayObstacle.getDistFromThreshold() - dispThreshold.get());
            workingAsda.set(workingTora.get() + stopway.get());
            workingToda.set(workingTora.get() + clearway.get());
            workingLda.set(lda.get() - runwayObstacle.getDistFromThreshold() - (runwayObstacle.getHeight() * SLOPE.get()) - STRIPEND.get());
        }
        output1.set(workingAsda.get());
    }




    // Getters
    public double getTora() {
        return tora.get();
    }

    public SimpleDoubleProperty toraProperty() {
        return tora;
    }

    public double getToda() {
        return toda.get();
    }

    public SimpleDoubleProperty todaProperty() {
        return toda;
    }

    public double getAsda() {
        return asda.get();
    }

    public SimpleDoubleProperty asdaProperty() {
        return asda;
    }

    public double getLda() {
        return lda.get();
    }

    public SimpleDoubleProperty ldaProperty() {
        return lda;
    }

    public double getDispThreshold() {
        return dispThreshold.get();
    }

    public SimpleDoubleProperty dispThresholdProperty() {
        return dispThreshold;
    }

    public Obstacle getRunwayObstacle() {
        return runwayObstacle;
    }

    public boolean isLandingMode() {
        return landingMode.get();
    }

    public SimpleBooleanProperty landingModeProperty() {
        return landingMode;
    }

    public double getOutput1() {
        return output1.get();
    }

    public SimpleDoubleProperty output1Property() {
        return output1;
    }

    public double getOutput2() {
        return output2.get();
    }

    public SimpleDoubleProperty output2Property() {
        return output2;
    }

    public double getOutput3() {
        return output3.get();
    }

    public SimpleDoubleProperty output3Property() {
        return output3;
    }

    public String getRunwayDesignator() {
        return runwayDesignator.get();
    }

    public SimpleStringProperty runwayDesignatorProperty() {
        return runwayDesignator;
    }

    public boolean isDirection() {
        return direction.get();
    }

    public SimpleBooleanProperty directionProperty() {
        return direction;
    }
}
