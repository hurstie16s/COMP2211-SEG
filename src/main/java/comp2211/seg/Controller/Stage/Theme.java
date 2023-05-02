package comp2211.seg.Controller.Stage;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * The type Theme.
 */
public class Theme {

  private static final Logger logger = LogManager.getLogger(Theme.class);
  public static ArrayList<Color> themeColors;
    /**
     * The Bg.
     */
    //static public Color bg;
    /**
     * The Bg runway.
     */
    //static public Color bgRunway;
    /**
     * The Unfocused bg.
     */
    //static public Color unfocusedBG = Color.gray(0.9);
    /**
     * The Focused bg.
     */
    //static public Color focusedBG = Color.gray(0.8);
    /**
     * The Veryfocused bg.
     */
    //static public Color veryfocusedBG = Color.gray(0.75);
    /**
     * The Extremelyfocused bg.
     */
    //static public Color extremelyfocusedBG = Color.gray(0.7);
    /**
     * The Fg.
     */
    //static public Color fg = Color.gray(0.4);
    //static public Color fgBright = Color.gray(0.35);
    /**
     * The Labelfg.
     * left for sake of
     * public RunwayLabel(String name, Color color, <- this param
     * DoubleBinding xOffset, double yOffset, DoubleBinding length,
     * RunwayScene scene, boolean direction, SimpleBooleanProperty visibility)
     */
    static public Color labelfg;
    /**
     * The Grass.
     */
    static public Color grass;
    /**
     * The Obstacle.
     */
    static public Color obstacle;
    /**
     * The Slope.
     */
    static public Color slope;
    /**
     * The Cga.
     */
    public static Color cga;
    /**
     * The Clearway.
     */
    static public Color clearway;
    /**
     * The Stopway.
     */
    static public Color stopway;
    /**
     * The Physical resa.
     */
    static public Color physicalResa;
    /**
     * The Runway.
     */
    static public Color runway;
    /**
     * The Toda.
     */
    static public Color toda;
    /**
     * The Tora.
     */
    static public Color tora;
    /**
     * The Asda.
     */
    static public Color asda;
    /**
     * The Lda.
     */
    static public Color lda;
    /**
     * The Resa.
     */
    static public Color resa;
    /**
     * The Stripend.
     */
    static public Color stripend;
    /**
     * The Blastallowance.
     */
    static public Color blastallowance;
    /**
     * The Font.
     * left for sake of:
     * private Node makeOutputLabel(SimpleStringProperty string, SimpleBooleanProperty visibility, int i) {
     *         Label data = new Label();
     *         data.setFont(new Font(Theme.font.getName(),i));
     * in BaseScene line 863
     */
    //static public Font font =Font.font("Calibri", 20);
    //static public Font fontbig =Font.font("Calibri", 32);
    /**
     * The Fontsmall.
     */
    //static public Font fontsmall =Font.font("Calibri", 16);

    public Theme() {
      themeColors = new ArrayList<>();
      themeColors.add(labelfg);
      themeColors.add(runway);
      themeColors.add(grass);
      themeColors.add(obstacle);
      themeColors.add(slope);
      themeColors.add(clearway);
      themeColors.add(stopway);
      themeColors.add(lda);
      themeColors.add(tora);
      themeColors.add(asda);
      themeColors.add(toda);
      themeColors.add(resa);
      themeColors.add(cga);
      themeColors.add(stripend);
      themeColors.add(blastallowance);
      themeColors.add(physicalResa);
    }

  public void setThemeColors(ArrayList<Color> cssColors) {
    for (int i = 0; i < cssColors.size() && i < themeColors.size(); i++) {
      themeColors.set(i, cssColors.get(i));
      logger.info("Color set at index " + i + ": " + themeColors.get(i));
    }
  }

  public static Color getLabelFg() {
    return themeColors.get(0);
  }

  public static Color getRunway() {
    return themeColors.get(1);
  }

  public static Color getGrass() {
    return themeColors.get(2);
  }

  public static Color getObstacle() {
    return themeColors.get(3);
  }

  public static Color getSlope() {
    return themeColors.get(4);
  }

  public static Color getClearway() {
    return themeColors.get(5);
  }

  public static Color getStopway() {
    return themeColors.get(6);
  }

  public static Color getLda() {
    return themeColors.get(7);
  }

  public static Color getTora() {
    return themeColors.get(8);
  }

  public static Color getAsda() {
    return themeColors.get(9);
  }

  public static Color getToda() {
    return themeColors.get(10);
  }

  public static Color getResa() {
    return themeColors.get(11);
  }

  public static Color getCga() {
    return themeColors.get(12);
  }

  public static Color getStripEnd() {
    return themeColors.get(13);
  }

  public static Color getBlastAllowance() {
    return themeColors.get(14);
  }

  public static Color getPhysicalResa() {
    return themeColors.get(15);
  }


  /**
   * Reverse color.
   *
   * @param color the color
   * @return the color
   */
//    public static Color reverse(Color color){
//        double r = color.getRed();
//        double g = color.getGreen();
//        double b = color.getBlue();
//        double a = color.getOpacity();
//        if (Math.max(Math.max(r,g),b)>0.5){
//            return Color.color(r/2,g/2,b/2,a);
//        }else{
//            return Color.color(r*2,g*2,b*2,a);
//        }
//    }

    /**
     * Inverse color.
     *
     * @param colour the colour
     * @return the color
     */
//    public static Color inverse(Color colour) {
//        double total = (Math.exp(1 - Math.log(1+(colour.getRed()+ colour.getGreen()+colour.getBlue())*(Math.E-1)/3))-1)/(Math.E-1);
//        return makeColour(total, colour);
//    }

    /**
     * Make colour color.
     *
     * @param brightness the brightness
     * @param colour     the colour
     * @return the color
     */
//    public static Color makeColour(double brightness, Color colour){
//        double total = (colour.getRed() + colour.getGreen() + colour.getBlue())/3;
//        double red;
//        double green;
//        double blue;
//        if (total > 0){
//            red = Math.min(colour.getRed()/total,1/brightness);
//            green = Math.min(colour.getGreen()/total,1/brightness);
//            blue = Math.min(colour.getBlue()/total,1/brightness);
//        } else {
//            red = 1;
//            green = 1;
//            blue = 1;
//        }
//        return new Color(red*brightness,green*brightness,blue*brightness,1);
//
//    }

    /**
     * Make dark.
     */
//    public static void makeDark(){
//        //bg = Color.gray(0.2);
//        //bgRunway = Color.gray(0.15);
//        unfocusedBG = Color.gray(0.1);
//        focusedBG = Color.gray(0.2);
//        veryfocusedBG = Color.gray(0.25);
//        extremelyfocusedBG = Color.gray(0.3);
//        fg = Color.gray(0.6);
//        fgBright = Color.gray(0.65);
//        labelfg = Color.gray(1);
//        grass = Color.web("#014130");
//        obstacle = Color.web("#e3b7b5");
//        slope = Color.web("#ecfdec");
//        cga = Color.web("#006166");
//        clearway = Color.web("#465d52");
//        stopway = Color.web("#c1d095");
//        resa = Color.web("#698320");
//        runway = Color.web("#333432");
//        toda = Color.web("#c3d1f4");
//        tora = Color.web("#9afece");
//        asda = Color.web("#49ba26");
//        lda = Color.web("#00edfa");
//    }

    /**
     * Make dark 2.
     */
//    public static void makeDark2(){
//
//        //bg = inverse(bg);
//        bgRunway = inverse(bgRunway);
//        unfocusedBG = inverse(unfocusedBG);
//        focusedBG = inverse(focusedBG);
//        veryfocusedBG = inverse(veryfocusedBG);
//        extremelyfocusedBG = inverse(extremelyfocusedBG);
//        fg = inverse(fg);
//        fgBright = inverse(fgBright);
//        grass = inverse(grass);
//        obstacle = inverse(obstacle);
//        slope = inverse(slope);
//        cga = inverse(cga);
//        clearway = inverse(clearway);
//        stopway = inverse(stopway);
//        physicalResa = inverse(physicalResa);
//        runway = inverse(runway);
//        toda = inverse(toda);
//        tora = inverse(tora);
//        asda = inverse(asda);
//        lda  = inverse(lda );
//        resa = inverse(resa);
//        stripend = inverse(stripend);
//        blastallowance = inverse(blastallowance);
//
//
//    }

    /**
     * Retheme.
     *
     * @param scene the scene
     */
//    public static void retheme(Scene scene) {
//
//    }
}
