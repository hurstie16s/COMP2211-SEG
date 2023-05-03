package comp2211.seg.ProcessDataModel;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * The type Runway values.
 */
public abstract class RunwayValues {

    /**
     * The Changes history.
     */
// changes history pane
    public Pane changesHistory = new Pane();
    /**
     * The Change history.
     */
    public ObservableList<String> changeHistory = FXCollections.observableArrayList();
    public final SimpleListProperty<String> changeHistoryProperty = new SimpleListProperty<>(changeHistory);

    /**
     * The Clearway left.
     */
// Runway dimensions and properties
    public SimpleDoubleProperty clearwayLeft = new SimpleDoubleProperty(500);
    /**
     * The Clearway right.
     */
    public SimpleDoubleProperty clearwayRight = new SimpleDoubleProperty(500);
    /**
     * The Clearway height.
     */
    public final SimpleDoubleProperty clearwayHeight = new SimpleDoubleProperty(150);
    /**
     * The Stopway left.
     */
    public SimpleDoubleProperty stopwayLeft = new SimpleDoubleProperty(150);
    /**
     * The Stopway right.
     */
    public SimpleDoubleProperty stopwayRight = new SimpleDoubleProperty(150);
    /**
     * The Strip end.
     */
    public final SimpleDoubleProperty stripEnd = new SimpleDoubleProperty(60);
    /**
     * The Resa width.
     */
    public final SimpleDoubleProperty RESAWidth = new SimpleDoubleProperty(240);
    /**
     * The Resa height.
     */
    public final SimpleDoubleProperty RESAHeight = new SimpleDoubleProperty(90);

    //Inputs

    /**
     * The Runway designator left.
     */
/*
    A runway designator consists of a two-digit number,
    which is the whole number nearest to one tenth of the magnetic North
    when viewed from the direction of approach. For example,
    if the azimuth of the centre-line is 153 then the runway designator will be 15
    followed by either L C or R to differentiate between parallel runways
     */
    public final SimpleStringProperty runwayDesignatorLeft = new SimpleStringProperty("00L");
    /**
     * The Runway designator right.
     */
    public final SimpleStringProperty runwayDesignatorRight = new SimpleStringProperty("18R");
    /**
     * The Input right tora.
     */
    public SimpleDoubleProperty inputRightTora = new SimpleDoubleProperty(1000);
    /**
     * The Input right toda.
     */
    public SimpleDoubleProperty inputRightToda = new SimpleDoubleProperty(1500);
    /**
     * The Input right asda.
     */
    public SimpleDoubleProperty inputRightAsda = new SimpleDoubleProperty(1150);
    /**
     * The Input right lda.
     */
    public SimpleDoubleProperty inputRightLda = new SimpleDoubleProperty(1000);
    /**
     * The Input left tora.
     */
    public SimpleDoubleProperty inputLeftTora = new SimpleDoubleProperty(1000);
    /**
     * The Input left toda.
     */
    public SimpleDoubleProperty inputLeftToda = new SimpleDoubleProperty(1500);
    /**
     * The Input left asda.
     */
    public SimpleDoubleProperty inputLeftAsda = new SimpleDoubleProperty(1150);
    /**
     * The Input left lda.
     */
    public SimpleDoubleProperty inputLeftLda = new SimpleDoubleProperty(900);
    /**
     * The Right tora.
     */
    public final SimpleDoubleProperty rightTora = new SimpleDoubleProperty(0);
    /**
     * The Right toda.
     */
    public final SimpleDoubleProperty rightToda = new SimpleDoubleProperty(0);
    /**
     * The Right asda.
     */
    public final SimpleDoubleProperty rightAsda = new SimpleDoubleProperty(0);
    /**
     * The Right lda.
     */
    public final SimpleDoubleProperty rightLda = new SimpleDoubleProperty(0);
    /**
     * The Left tora.
     */
    public final SimpleDoubleProperty leftTora = new SimpleDoubleProperty(0);
    /**
     * The Left toda.
     */
    public final SimpleDoubleProperty leftToda = new SimpleDoubleProperty(0);
    /**
     * The Left asda.
     */
    public final SimpleDoubleProperty leftAsda = new SimpleDoubleProperty(0);
    /**
     * The Left lda.
     */
    public final SimpleDoubleProperty leftLda = new SimpleDoubleProperty(0);
    /**
     * The Disp threshold left.
     */
    public SimpleDoubleProperty dispThresholdLeft = new SimpleDoubleProperty(0);
    /**
     * The Disp threshold right.
     */
    public SimpleDoubleProperty dispThresholdRight = new SimpleDoubleProperty(60);


    /**
     * The Runway obstacle.
     */
    public Obstacle runwayObstacle = new Obstacle("One", 10,0);

    /**
     * The Landing mode.
     */
    public final SimpleBooleanProperty landingMode = new SimpleBooleanProperty(true);

    /**
     * The Direction left.
     */
    public final SimpleBooleanProperty directionLeft = new SimpleBooleanProperty(true);
    /**
     * The Direction right.
     */
    public final SimpleBooleanProperty directionRight = new SimpleBooleanProperty(true);
    /**
     * The Left take off.
     */
    public final SimpleBooleanProperty leftTakeOff = new SimpleBooleanProperty(false);
    /**
     * The Left land.
     */
    public final SimpleBooleanProperty leftLand = new SimpleBooleanProperty(false);
    /**
     * The Right take off.
     */
    public final SimpleBooleanProperty rightTakeOff = new SimpleBooleanProperty(false);
    /**
     * The Right land.
     */
    public final SimpleBooleanProperty rightLand = new SimpleBooleanProperty(false);

    // End of Inputs


    /**
     * The Minresa.
     */
// Typical values, may become variable down the line
    // Constants
    public final SimpleDoubleProperty MINRESA = new SimpleDoubleProperty(240);
    /**
     * The Stripend.
     */
    public final SimpleDoubleProperty STRIPEND = new SimpleDoubleProperty(60);
    /**
     * The Blastzone.
     */
    public final SimpleDoubleProperty BLASTZONE = new SimpleDoubleProperty(500);
    /**
     * The Slope.
     */
    public final SimpleDoubleProperty SLOPE = new SimpleDoubleProperty(50);
    /**
     * The Stopwaymin.
     */
    public final SimpleDoubleProperty STOPWAYMIN = new SimpleDoubleProperty(0);
    /**
     * The Slope length.
     */
    public final SimpleDoubleProperty slopeLength = new SimpleDoubleProperty(50);

    /**
     * The Runway length.
     */
// Runway dimensions
    public final SimpleDoubleProperty runwayLength = new SimpleDoubleProperty(1000);
    /**
     * The Runway width.
     */
    public final SimpleDoubleProperty runwayWidth = new SimpleDoubleProperty(60);
    /**
     * The Has runway obstacle.
     */
    public final SimpleBooleanProperty hasRunwayObstacle = new SimpleBooleanProperty(false);
    /**
     * The constant units.
     */
    public static final SimpleStringProperty units = new SimpleStringProperty("m");

    // Calculation Breakdowns

    /**
     * The Left tora breakdown.
     */
// Left TORA
    public final SimpleStringProperty leftToraBreakdown = new SimpleStringProperty("N/A");
    /**
     * The Left tora breakdown header.
     */
    public final SimpleStringProperty leftToraBreakdownHeader = new SimpleStringProperty("N/A");
    /**
     * The Right tora breakdown.
     */
// Right TORA
    public final SimpleStringProperty rightToraBreakdown = new SimpleStringProperty("N/A");
    /**
     * The Right tora breakdown header.
     */
    public final SimpleStringProperty rightToraBreakdownHeader = new SimpleStringProperty("N/A");
    /**
     * The Left toda breakdown.
     */
// Left TODA
    public final SimpleStringProperty leftTodaBreakdown = new SimpleStringProperty("N/A");
    /**
     * The Left toda breakdown header.
     */
    public final SimpleStringProperty leftTodaBreakdownHeader = new SimpleStringProperty("N/A");
    /**
     * The Right toda breakdown.
     */
// Right TODA
    public final SimpleStringProperty rightTodaBreakdown = new SimpleStringProperty("N/A");
    /**
     * The Right toda breakdown header.
     */
    public final SimpleStringProperty rightTodaBreakdownHeader = new SimpleStringProperty("N/A");
    /**
     * The Left asda breakdown.
     */
// Left ASDA
    public final SimpleStringProperty leftAsdaBreakdown = new SimpleStringProperty("N/A");
    /**
     * The Left asda breakdown header.
     */
    public final SimpleStringProperty leftAsdaBreakdownHeader = new SimpleStringProperty("N/A");
    /**
     * The Right asda breakdown.
     */
// Right ASDA
    public final SimpleStringProperty rightAsdaBreakdown = new SimpleStringProperty("N/A");
    /**
     * The Right asda breakdown header.
     */
    public final SimpleStringProperty rightAsdaBreakdownHeader = new SimpleStringProperty("N/A");
    /**
     * The Left lda breakdown.
     */
// Left LDA
    public final SimpleStringProperty leftLdaBreakdown = new SimpleStringProperty("N/A");
    /**
     * The Left lda breakdown header.
     */
    public final SimpleStringProperty leftLdaBreakdownHeader = new SimpleStringProperty("N/A");
    /**
     * The Left lda obstacle slope calc breakdown.
     */
// Temp holders
    public final SimpleStringProperty leftLdaObstacleSlopeCalcBreakdown = new SimpleStringProperty();
    /**
     * The Left lda obstacle slope calc breakdown header.
     */
    public final SimpleStringProperty leftLdaObstacleSlopeCalcBreakdownHeader = new SimpleStringProperty();
    /**
     * The Left lda sub breakdown.
     */
    public final SimpleStringProperty leftLdaSubBreakdown = new SimpleStringProperty();
    /**
     * The Left lda sub breakdown header.
     */
    public final SimpleStringProperty leftLdaSubBreakdownHeader = new SimpleStringProperty();
    /**
     * The Right lda breakdown.
     */
// Right LDA
    public final SimpleStringProperty rightLdaBreakdown = new SimpleStringProperty("N/A");
    /**
     * The Right lda breakdown header.
     */
    public final SimpleStringProperty rightLdaBreakdownHeader = new SimpleStringProperty("N/A");
    /**
     * The Right lda obstacle slope calc breakdown.
     */
// Temp holders
    public final SimpleStringProperty rightLdaObstacleSlopeCalcBreakdown = new SimpleStringProperty();
    /**
     * The Right lda obstacle slope calc breakdown header.
     */
    public final SimpleStringProperty rightLdaObstacleSlopeCalcBreakdownHeader = new SimpleStringProperty();
    /**
     * The Right lda sub breakdown.
     */
    public final SimpleStringProperty rightLdaSubBreakdown = new SimpleStringProperty();
    /**
     * The Right lda sub breakdown header.
     */
    public final SimpleStringProperty rightLdaSubBreakdownHeader = new SimpleStringProperty();


    /**
     * The Dual direction runway.
     */
    public final SimpleBooleanProperty dualDirectionRunway = new SimpleBooleanProperty(true);

}
