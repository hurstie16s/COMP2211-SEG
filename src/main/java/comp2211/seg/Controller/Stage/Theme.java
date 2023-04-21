package comp2211.seg.Controller.Stage;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * The type Theme.
 */
public class Theme {
    /**
     * The Bg.
     */
    static public Color bg = Color.gray(0.8);
    /**
     * The Bg runway.
     */
    static public Color bgRunway = Color.gray(0.85);
    /**
     * The Unfocused bg.
     */
    static public Color unfocusedBG = Color.gray(0.9);
    /**
     * The Focused bg.
     */
    static public Color focusedBG = Color.gray(0.8);
    /**
     * The Veryfocused bg.
     */
    static public Color veryfocusedBG = Color.gray(0.75);
    /**
     * The Extremelyfocused bg.
     */
    static public Color extremelyfocusedBG = Color.gray(0.7);
    /**
     * The Fg.
     */
    static public Color fg = Color.gray(0.4);
    static public Color fgBright = Color.gray(0.35);
    /**
     * The Labelfg.
     */
    static public Color labelfg = Color.gray(0);
    /**
     * The Grass.
     */
    static public Color grass = Color.web("#93cd81");
    /**
     * The Obstacle.
     */
    static public Color obstacle = Color.web("#ff0000");
    /**
     * The Slope.
     */
    static public Color slope = Color.web("#008080");
    /**
     * The Cga.
     */
    static public Color cga = Color.web("#008080");
    /**
     * The Clearway.
     */
    static public Color clearway = Color.web("#db0056");
    /**
     * The Stopway.
     */
    static public Color stopway = Color.web("#faa40a");
    /**
     * The Physical resa.
     */
    static public Color physicalResa = Color.web("#fed9b4");
    /**
     * The Runway.
     */
    static public Color runway = Color.web("#a5a5a5");
    /**
     * The Toda.
     */
    static public Color toda = Color.web("#ff00ff");
    /**
     * The Tora.
     */
    static public Color tora = Color.web("#00ffff");
    /**
     * The Asda.
     */
    static public Color asda = Color.web("#ffff00");
    /**
     * The Lda.
     */
    static public Color lda = Color.web("#0000ff");;
    /**
     * The Resa.
     */
    static public Color resa = Color.web("#d06800");
    /**
     * The Stripend.
     */
    static public Color stripend = Color.web("#00ff00");
    /**
     * The Blastallowance.
     */
    static public Color blastallowance = Color.web("#cccc00");
    /**
     * The Font.
     */
    static public Font font =Font.font("Calibri", 20);
    static public Font fontbig =Font.font("Calibri", 32);
    /**
     * The Fontsmall.
     */
    static public Font fontsmall =Font.font("Calibri", 16);

    /**
     * Reverse color.
     *
     * @param color the color
     * @return the color
     */
    public static Color reverse(Color color){
        double r = color.getRed();
        double g = color.getGreen();
        double b = color.getBlue();
        double a = color.getOpacity();
        if (Math.max(Math.max(r,g),b)>0.5){
            return Color.color(r/2,g/2,b/2,a);
        }else{
            return Color.color(r*2,g*2,b*2,a);
        }
    }

    /**
     * Inverse color.
     *
     * @param colour the colour
     * @return the color
     */
    public static Color inverse(Color colour) {
        double total = (Math.exp(1 - Math.log(1+(colour.getRed()+ colour.getGreen()+colour.getBlue())*(Math.E-1)/3))-1)/(Math.E-1);
        return makeColour(total, colour);

    }

    /**
     * Make colour color.
     *
     * @param brightness the brightness
     * @param colour     the colour
     * @return the color
     */
    public static Color makeColour(double brightness, Color colour){
        double total = (colour.getRed() + colour.getGreen() + colour.getBlue())/3;
        double red;
        double green;
        double blue;
        if (total > 0){
            red = Math.min(colour.getRed()/total,1/brightness);
            green = Math.min(colour.getGreen()/total,1/brightness);
            blue = Math.min(colour.getBlue()/total,1/brightness);
        } else {
            red = 1;
            green = 1;
            blue = 1;
        }
        return new Color(red*brightness,green*brightness,blue*brightness,1);

    }

    /**
     * Make dark.
     */
    public static void makeDark(){
        bg = Color.gray(0.2);
        bgRunway = Color.gray(0.15);
        unfocusedBG = Color.gray(0.1);
        focusedBG = Color.gray(0.2);
        veryfocusedBG = Color.gray(0.25);
        extremelyfocusedBG = Color.gray(0.3);
        fg = Color.gray(0.6);
        fgBright = Color.gray(0.65);
        labelfg = Color.gray(1);
        grass = Color.web("#014130");
        obstacle = Color.web("#e3b7b5");
        slope = Color.web("#ecfdec");
        cga = Color.web("#006166");
        clearway = Color.web("#465d52");
        stopway = Color.web("#c1d095");
        resa = Color.web("#698320");
        runway = Color.web("#333432");
        toda = Color.web("#c3d1f4");
        tora = Color.web("#9afece");
        asda = Color.web("#49ba26");
        lda = Color.web("#00edfa");
    }

    /**
     * Make dark 2.
     */
    public static void makeDark2(){

        bg = inverse(bg);
        bgRunway = inverse(bgRunway);
        unfocusedBG = inverse(unfocusedBG);
        focusedBG = inverse(focusedBG);
        veryfocusedBG = inverse(veryfocusedBG);
        extremelyfocusedBG = inverse(extremelyfocusedBG);
        fg = inverse(fg);
        fgBright = inverse(fgBright);
        grass = inverse(grass);
        obstacle = inverse(obstacle);
        slope = inverse(slope);
        cga = inverse(cga);
        clearway = inverse(clearway);
        stopway = inverse(stopway);
        physicalResa = inverse(physicalResa);
        runway = inverse(runway);
        toda = inverse(toda);
        tora = inverse(tora);
        asda = inverse(asda);
        lda  = inverse(lda );
        resa = inverse(resa);
        stripend = inverse(stripend);
        blastallowance = inverse(blastallowance);


    }

    /**
     * Retheme.
     *
     * @param scene the scene
     */
    public static void retheme(Scene scene) {

    }
}
