package comp2211.seg.UiView.Scene;

import javafx.scene.control.TextField;

public class RunwayData {
  private String column1 = null;
  private String column2 = null;
  private String column21 = null;
  private String column22 = null;
  private String column3 = null;
  private String column31 = null;
  private String column32 = null;
  private String column4 = null;
  private String column41 = null;
  private String column42 = null;
  private String column5 = null;
  private String column51 = null;
  private String column52 = null;
  private String column6 = null;
  private String column61 = null;
  private String column62 = null;
  private String column7 = null;
  private String column8 = null;
  private String column9 = null;


  public RunwayData(TextField column1,
                    TextField column21, TextField column22,
                    TextField column31, TextField column32,
                    TextField column41, TextField column42,
                    TextField column51, TextField column52,
                    TextField column61, TextField column62,
                    TextField column7,
                    TextField column8,
                    TextField column9) {

    this.column1 = column1.getText();
    this.column21 = column21.getText();
    this.column22 = column22.getText();
    this.column31 = column31.getText();
    this.column32 = column32.getText();
    this.column41 = column41.getText();
    this.column42 = column42.getText();
    this.column51 = column51.getText();
    this.column52 = column52.getText();
    this.column61 = column61.getText();
    this.column62 = column62.getText();
    this.column7 = column7.getText();
    this.column8 = column8.getText();
    this.column9 = column9.getText();

  }

  /*public RunwayData(TextField column1,
                    TextField column21, TextField column22,
                    TextField column31, TextField column32,
                    TextField column41, TextField column42,
                    TextField column51, TextField column52,
                    TextField column61, TextField column62,
                    TextField column7) {

    this.column1 = column1;
    this.column21 = column21;
    this.column22 = column22;
    this.column31 = column31;
    this.column32 = column32;
    this.column41 = column41;
    this.column42 = column42;
    this.column51 = column51;
    this.column52 = column52;
    this.column61 = column61;
    this.column62 = column62;
    this.column7 = column7;
  }*/

  public String getColumn1() {
    return column1;
  }

  public void setColumn1(String column1) {
    this.column1 = column1;
  }

  public String getColumn21() {
    return column21;
  }

  public void setColumn21(String column21) {
    this.column21 = column21;
  }

  public String getColumn22() {
    return column22;
  }

  public void setColumn22(String column22) {
    this.column22 = column22;
  }

  public String getColumn31() {
    return column31;
  }

  public void setColumn31(String column31) {
    this.column31 = column31;
  }

  public String getColumn32() {
    return column32;
  }

  public void setColumn32(String column32) {
    this.column32 = column32;
  }

  public String getColumn41() {
    return column41;
  }

  public void setColumn41(String column41) {
    this.column41 = column41;
  }

  public String getColumn42() {
    return column42;
  }

  public void setColumn42(String column42) {
    this.column42 = column42;
  }

  public String getColumn51() {
    return column51;
  }

  public void setColumn51(String column51) {
    this.column51 = column51;
  }

  public String getColumn52() {
    return column52;
  }

  public void setColumn52(String column52) {
    this.column52 = column52;
  }

  public String getColumn61() {
    return column61;
  }

  public void setColumn61(String column61) {
    this.column61 = column61;
  }

  public String getColumn62() {
    return column62;
  }

  public void setColumn62(String column62) {
    this.column62 = column62;
  }

  public String getColumn7() {
    return column7;
  }

  public void setColumn7(String column7) {
    this.column7 = column7;
  }

  public String getColumn8() {
    return column8;
  }

  public void setColumn8(String column8) {
    this.column8 = column8;
  }

  public String getColumn9() {
    return column9;
  }

  public void setColumn9(String column9) {
    this.column9 = column9;
  }

  //not sure if needed
  public String getColumn2() {
    return column2;
  }

  public void setColumn2(String column2) {
    this.column2 = column2;
  }

  public String getColumn3() {
    return column3;
  }

  public void setColumn3(String column3) {
    this.column3 = column3;
  }

  public String getColumn4() {
    return column4;
  }

  public void setColumn4(String column4) {
    this.column4 = column4;
  }

  public String getColumn5() {
    return column5;
  }

  public void setColumn5(String column5) {
    this.column5 = column5;
  }

  public String getColumn6() {
    return column6;
  }

  public void setColumn6(String column6) {
    this.column6 = column6;
  }
}
