package comp2211.seg.ProcessDataModel;

public class RunwayCalculations {

  private int calculationValue;

  public void calculateRunwayParam(int firstParam,int secondParam) {
    calculationValue = firstParam + secondParam;
  }

  public int getCalculationValue() {
    return calculationValue;
  }
}
