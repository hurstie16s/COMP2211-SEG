package comp2211.seg.ProcessDataModel;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;

public class Runway {


    private SimpleDoubleProperty clearwayRightWidth = new SimpleDoubleProperty(500);
    private SimpleDoubleProperty clearwayRightHeight = new SimpleDoubleProperty(150);
    private SimpleDoubleProperty clearwayLeftWidth = new SimpleDoubleProperty(500);
    private SimpleDoubleProperty clearwayLeftHeight = new SimpleDoubleProperty(150);
    private SimpleDoubleProperty stopwayRight = new SimpleDoubleProperty(150);
    private SimpleDoubleProperty stopwayLeft = new SimpleDoubleProperty(150);
    private SimpleDoubleProperty stripEndRight = new SimpleDoubleProperty(60);
    private SimpleDoubleProperty stripEndLeft = new SimpleDoubleProperty(60);
    private SimpleDoubleProperty RESARightWidth = new SimpleDoubleProperty(240);
    private SimpleDoubleProperty RESARightHeight = new SimpleDoubleProperty(90);
    private SimpleDoubleProperty RESALeftWidth = new SimpleDoubleProperty(240);
    private SimpleDoubleProperty RESALeftHeight = new SimpleDoubleProperty(90);




    //Inputs

    /*
    A runway designator consists of a two-digit number,
    which is the whole number nearest to one tenth of the magnetic North
    when viewed from the direction of approach. For example,
    if the azimuth of the centre-line is 153 then the runway designator will be 15
     */
    private final SimpleStringProperty runwayDesignator = new SimpleStringProperty("36");
    private final SimpleDoubleProperty tora = new SimpleDoubleProperty(1000);
    private final SimpleDoubleProperty toda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty asda = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty lda = new SimpleDoubleProperty(800);
    private final SimpleDoubleProperty dispThreshold = new SimpleDoubleProperty(0);

    private final ArrayList<Obstacle> runwayObstacles = new ArrayList<>();

    private final SimpleBooleanProperty landing = new SimpleBooleanProperty(true);


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


    private SimpleDoubleProperty runwayLength = new SimpleDoubleProperty(1000);


    private SimpleDoubleProperty runwayWidth = new SimpleDoubleProperty(60);


    public Runway() {
        for (Property prop: new Property[] {
                runwayDesignator,
                tora,
                toda,
                asda,
                lda,
                dispThreshold,
                landing,
                direction
        }) {
            prop.addListener((observableValue, o, t1) -> recalculate());
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
        if (landing.get()){
            runwayLength.set(lda.get());
            if (direction.get()){
                calculateLandOver();

            } else {
                calculateLandTowards();

            }
        } else {
            runwayLength.set(tora.get());
            if (direction.get()){
                calculateTakeOffAway();

            } else {
                calculateTakeOffToward();

            }
        }
    }

    public void calculateLandOver() {
        output1.set(tora.get());
    }

    public void calculateLandTowards() {
        output1.set(toda.get());
    }

    public void calculateTakeOffToward() {
        output1.set(Double.parseDouble(runwayDesignator.get()));}

    public void calculateTakeOffAway() {
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

    public boolean getLanding() {
        return landing.get();
    }

    public SimpleBooleanProperty landingProperty() {
        return landing;
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

    public double getRunwayLength() {
        return runwayLength.get();
    }

    public SimpleDoubleProperty runwayLengthProperty() {
        return runwayLength;
    }

    public double getRunwayWidth() {
        return runwayWidth.get();
    }

    public SimpleDoubleProperty runwayWidthProperty() {
        return runwayWidth;
    }

    public double getClearwayRightWidth() {
        return clearwayRightWidth.get();
    }

    public SimpleDoubleProperty clearwayRightWidthProperty() {
        return clearwayRightWidth;
    }

    public double getClearwayRightHeight() {
        return clearwayRightHeight.get();
    }

    public SimpleDoubleProperty clearwayRightHeightProperty() {
        return clearwayRightHeight;
    }

    public double getClearwayLeftWidth() {
        return clearwayLeftWidth.get();
    }

    public SimpleDoubleProperty clearwayLeftWidthProperty() {
        return clearwayLeftWidth;
    }

    public double getClearwayLeftHeight() {
        return clearwayLeftHeight.get();
    }

    public SimpleDoubleProperty clearwayLeftHeightProperty() {
        return clearwayLeftHeight;
    }

    public double getStopwayRight() {
        return stopwayRight.get();
    }

    public SimpleDoubleProperty stopwayRightProperty() {
        return stopwayRight;
    }

    public double getStopwayLeft() {
        return stopwayLeft.get();
    }

    public SimpleDoubleProperty stopwayLeftProperty() {
        return stopwayLeft;
    }

    public double getStripEndRight() {
        return stripEndRight.get();
    }

    public SimpleDoubleProperty stripEndRightProperty() {
        return stripEndRight;
    }

    public double getStripEndLeft() {
        return stripEndLeft.get();
    }

    public SimpleDoubleProperty stripEndLeftProperty() {
        return stripEndLeft;
    }

    public double getRESARightWidth() {
        return RESARightWidth.get();
    }

    public SimpleDoubleProperty RESARightWidthProperty() {
        return RESARightWidth;
    }

    public double getRESARightHeight() {
        return RESARightHeight.get();
    }

    public SimpleDoubleProperty RESARightHeightProperty() {
        return RESARightHeight;
    }

    public double getRESALeftWidth() {
        return RESALeftWidth.get();
    }

    public SimpleDoubleProperty RESALeftWidthProperty() {
        return RESALeftWidth;
    }

    public double getRESALeftHeight() {
        return RESALeftHeight.get();
    }

    public SimpleDoubleProperty RESALeftHeightProperty() {
        return RESALeftHeight;
    }
}
