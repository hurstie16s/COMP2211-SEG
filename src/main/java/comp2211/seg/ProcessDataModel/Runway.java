package comp2211.seg.ProcessDataModel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Runway {
    //Inputs
    /*
    A runway designator consists of a two-digit number,
    which is the whole number nearest to one tenth of the magnetic North
    when viewed from the direction of approach. For example,
    if the azimuth of the centre-line is 153 then the runway designator will be 15
     */
    private final SimpleStringProperty RUNWAYDESIGNATOR = new SimpleStringProperty();
    private final SimpleDoubleProperty tora = new SimpleDoubleProperty();
    private final SimpleDoubleProperty toda = new SimpleDoubleProperty();
    private final SimpleDoubleProperty asda = new SimpleDoubleProperty();
    private final SimpleDoubleProperty lda = new SimpleDoubleProperty();
    private final SimpleDoubleProperty dispThreshold = new SimpleDoubleProperty();


    // Typical values, may become variable down the line
    private final SimpleDoubleProperty MINRESA = new SimpleDoubleProperty(240);
    private final SimpleDoubleProperty ALSMAGNITUDE = new SimpleDoubleProperty(50);
    private final SimpleDoubleProperty STRIPEND = new SimpleDoubleProperty(60);
    private final SimpleDoubleProperty BLASTZONE = new SimpleDoubleProperty(500);

    //Outputs
    private SimpleDoubleProperty output1 = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty output2 = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty output3 = new SimpleDoubleProperty(0);

    public Runway(String RUNWAYDESIGNATOR, double tora, double toda, double asda, double lda, double dispThreshold) {
        this.RUNWAYDESIGNATOR.set(RUNWAYDESIGNATOR);
        this.tora.set(tora);
        this.toda.set(toda);
        this.asda.set(asda);
        this.lda.set(lda);
        this.dispThreshold.set(dispThreshold);
    }
    public void calculateLandOver() {}

    public void calculateLandTowards() {}

    public void calculateTakeOffToward() {}

    public void calculateTakeOffAway() {}
}
