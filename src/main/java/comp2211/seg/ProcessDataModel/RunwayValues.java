package comp2211.seg.ProcessDataModel;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public abstract class RunwayValues {

    /**
     * The Changes history.
     */
// changes history pane
    public Pane changesHistory = new Pane();
    public final ArrayList<String> changeHistory = new ArrayList<>();

    // Runway dimensions and properties
    public final SimpleDoubleProperty clearwayLeft = new SimpleDoubleProperty(500);
    public final SimpleDoubleProperty clearwayRight = new SimpleDoubleProperty(500);
    public final SimpleDoubleProperty clearwayHeight = new SimpleDoubleProperty(150);
    public final SimpleDoubleProperty stopwayLeft = new SimpleDoubleProperty(150);
    public final SimpleDoubleProperty stopwayRight = new SimpleDoubleProperty(150);
    public final SimpleDoubleProperty stripEnd = new SimpleDoubleProperty(60);
    public final SimpleDoubleProperty RESAWidth = new SimpleDoubleProperty(240);
    public final SimpleDoubleProperty RESAHeight = new SimpleDoubleProperty(90);

    //Inputs

    /*
    A runway designator consists of a two-digit number,
    which is the whole number nearest to one tenth of the magnetic North
    when viewed from the direction of approach. For example,
    if the azimuth of the centre-line is 153 then the runway designator will be 15
    followed by either L C or R to differentiate between parallel runways
     */
    public final SimpleStringProperty runwayDesignatorLeft = new SimpleStringProperty("00L");
    public final SimpleStringProperty runwayDesignatorRight = new SimpleStringProperty("18R");
    public SimpleDoubleProperty inputRightTora = new SimpleDoubleProperty(1000);
    public SimpleDoubleProperty inputRightToda = new SimpleDoubleProperty(1500);
    public SimpleDoubleProperty inputRightAsda = new SimpleDoubleProperty(1150);
    public SimpleDoubleProperty inputRightLda = new SimpleDoubleProperty(1000);
    public SimpleDoubleProperty inputLeftTora = new SimpleDoubleProperty(1000);
    public SimpleDoubleProperty inputLeftToda = new SimpleDoubleProperty(1500);
    public SimpleDoubleProperty inputLeftAsda = new SimpleDoubleProperty(1150);
    public SimpleDoubleProperty inputLeftLda = new SimpleDoubleProperty(900);
    public final SimpleDoubleProperty rightTora = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty rightToda = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty rightAsda = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty rightLda = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty leftTora = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty leftToda = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty leftAsda = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty leftLda = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty dispThresholdLeft = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty dispThresholdRight = new SimpleDoubleProperty(60);


    /**
     * The Runway obstacle.
     */
    public Obstacle runwayObstacle = new Obstacle("One", 10,0);

    public final SimpleBooleanProperty landingMode = new SimpleBooleanProperty(true);

    public final SimpleBooleanProperty directionLeft = new SimpleBooleanProperty(true);
    public final SimpleBooleanProperty directionRight = new SimpleBooleanProperty(true);
    public final SimpleBooleanProperty leftTakeOff = new SimpleBooleanProperty(false);
    public final SimpleBooleanProperty leftLand = new SimpleBooleanProperty(false);
    public final SimpleBooleanProperty rightTakeOff = new SimpleBooleanProperty(false);
    public final SimpleBooleanProperty rightLand = new SimpleBooleanProperty(false);

    // End of Inputs



    // Typical values, may become variable down the line
    // Constants
    public final SimpleDoubleProperty MINRESA = new SimpleDoubleProperty(240);
    public final SimpleDoubleProperty STRIPEND = new SimpleDoubleProperty(60);
    public final SimpleDoubleProperty BLASTZONE = new SimpleDoubleProperty(500);
    public final SimpleDoubleProperty SLOPE = new SimpleDoubleProperty(50);
    public final SimpleDoubleProperty STOPWAYMIN = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty slopeLength = new SimpleDoubleProperty(50);

    // Runway dimensions
    public final SimpleDoubleProperty runwayLength = new SimpleDoubleProperty(1000);
    public final SimpleDoubleProperty runwayWidth = new SimpleDoubleProperty(60);
    public final SimpleBooleanProperty hasRunwayObstacle = new SimpleBooleanProperty(false);
    public static final SimpleStringProperty units = new SimpleStringProperty("m");

    // Calculation Breakdowns

    // Left TORA
    public final SimpleStringProperty leftToraBreakdown = new SimpleStringProperty("N/A");
    public final SimpleStringProperty leftToraBreakdownHeader = new SimpleStringProperty("N/A");
    // Right TORA
    public final SimpleStringProperty rightToraBreakdown = new SimpleStringProperty("N/A");
    public final SimpleStringProperty rightToraBreakdownHeader = new SimpleStringProperty("N/A");
    // Left TODA
    public final SimpleStringProperty leftTodaBreakdown = new SimpleStringProperty("N/A");
    public final SimpleStringProperty leftTodaBreakdownHeader = new SimpleStringProperty("N/A");
    // Right TODA
    public final SimpleStringProperty rightTodaBreakdown = new SimpleStringProperty("N/A");
    public final SimpleStringProperty rightTodaBreakdownHeader = new SimpleStringProperty("N/A");
    // Left ASDA
    public final SimpleStringProperty leftAsdaBreakdown = new SimpleStringProperty("N/A");
    public final SimpleStringProperty leftAsdaBreakdownHeader = new SimpleStringProperty("N/A");
    // Right ASDA
    public final SimpleStringProperty rightAsdaBreakdown = new SimpleStringProperty("N/A");
    public final SimpleStringProperty rightAsdaBreakdownHeader = new SimpleStringProperty("N/A");
    // Left LDA
    public final SimpleStringProperty leftLdaBreakdown = new SimpleStringProperty("N/A");
    public final SimpleStringProperty leftLdaBreakdownHeader = new SimpleStringProperty("N/A");
    // Temp holders
    public final SimpleStringProperty leftLdaObstacleSlopeCalcBreakdown = new SimpleStringProperty();
    public final SimpleStringProperty leftLdaObstacleSlopeCalcBreakdownHeader = new SimpleStringProperty();
    public final SimpleStringProperty leftLdaSubBreakdown = new SimpleStringProperty();
    public final SimpleStringProperty leftLdaSubBreakdownHeader = new SimpleStringProperty();
    // Right LDA
    public final SimpleStringProperty rightLdaBreakdown = new SimpleStringProperty("N/A");
    public final SimpleStringProperty rightLdaBreakdownHeader = new SimpleStringProperty("N/A");
    // Temp holders
    public final SimpleStringProperty rightLdaObstacleSlopeCalcBreakdown = new SimpleStringProperty();
    public final SimpleStringProperty rightLdaObstacleSlopeCalcBreakdownHeader = new SimpleStringProperty();
    public final SimpleStringProperty rightLdaSubBreakdown = new SimpleStringProperty();
    public final SimpleStringProperty rightLdaSubBreakdownHeader = new SimpleStringProperty();

}
