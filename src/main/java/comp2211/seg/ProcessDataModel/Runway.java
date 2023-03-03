package comp2211.seg.ProcessDataModel;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;

public class Runway {
    //Inputs

    /*
    A runway designator consists of a two-digit number,
    which is the whole number nearest to one tenth of the magnetic North
    when viewed from the direction of approach. For example,
    if the azimuth of the centre-line is 153 then the runway designator will be 15
     */
    private final SimpleStringProperty runwayDesignator = new SimpleStringProperty("36");
    private final SimpleDoubleProperty tora = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty toda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty asda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty lda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty dispThreshold = new SimpleDoubleProperty(0);

    private final ArrayList<Obstacle> runwayObstacles = new ArrayList<>();

    private final SimpleBooleanProperty landingMode = new SimpleBooleanProperty(true);


    private final SimpleBooleanProperty direction = new SimpleBooleanProperty(true);

    // End of Inputs



    // Typical values, may become variable down the line
    // Constants
    private static final SimpleDoubleProperty MINRESA = new SimpleDoubleProperty(240);
    private static final SimpleDoubleProperty ALSMAGNITUDE = new SimpleDoubleProperty(50);
    private static final SimpleDoubleProperty STRIPEND = new SimpleDoubleProperty(60);
    private static final SimpleDoubleProperty BLASTZONE = new SimpleDoubleProperty(500);

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
        runwayObstacles.add(obstacleToAdd);
        // re-calculate values?
    }

    public void removeObstacle(Obstacle obstacleToRemove) {
        runwayObstacles.remove(obstacleToRemove);
        // re-calculate values?
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

    public void calculateLandOver() {
        //tora.set(Original TORA - Blast protection - Distance from threshold - Displaced threshold);
        //asda.set((R) TORA + stopway);
        //toda.set((R) TORA + clearway);
        //lda.set(Original LDA - Distance from threshold - Slope calculation - strip end);
        output1.set(tora.get());
    }

    public void calculateLandTowards() {
        //tora.set(Distance from threshold - slope calculation - Strip end);
        //asda.set((R) TORA);
        //toda.set((R) TORA);
        //lda.set(Distance from threshold - RESA - Strip end);
        output1.set(toda.get());
    }

    public void calculateTakeOffToward() {
        //tora.set(Distance from threshold - slope calculation - Strip end);
        //asda.set((R) TORA);
        //toda.set((R) TORA);
        //lda.set(Distance from threshold - RESA - Strip end);
        output1.set(Double.parseDouble(runwayDesignator.get()));
    }

    public void calculateTakeOffAway() {
        //tora.set(Original TORA - Blast protection - Distance from threshold - Displaced threshold);
        //asda.set((R) TORA + stopway);
        //toda.set((R) TORA + clearway);
        //lda.set(Original LDA - Distance from threshold - Slope calculation - strip end);
        output1.set(asda.get());}




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

    public ArrayList<Obstacle> getRunwayObstacles() {
        return runwayObstacles;
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
