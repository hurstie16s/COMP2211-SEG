package comp2211.seg.ProcessDataModel;

import javafx.beans.property.SimpleDoubleProperty;

public class Runway {
    //Inputs
    private SimpleDoubleProperty input1 = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty input2 = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty input3 = new SimpleDoubleProperty(0);


    //Outputs
    private SimpleDoubleProperty output1 = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty output2 = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty output3 = new SimpleDoubleProperty(0);



    public void calculateRunwayParam(int firstParam,int secondParam) {
        output1.set(input1.get()+input2.get());
    }
}
