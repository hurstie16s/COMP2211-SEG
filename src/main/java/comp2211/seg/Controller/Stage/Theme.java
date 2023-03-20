package comp2211.seg.Controller.Stage;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Theme {
    static public Color bg = Color.gray(0.8);
    static public Color bgRunway = Color.gray(0.85);
    static public Color unfocusedBG = Color.gray(0.9);
    static public Color focusedBG = Color.gray(0.8);
    static public Color veryfocusedBG = Color.gray(0.75);
    static public Color extremelyfocusedBG = Color.gray(0.7);
    static public Color fg = Color.gray(0.4);
    static public Color grass = Color.web("#93cd81");
    static public Color obstacle = Color.web("#ff0000");
    static public Color slope = Color.web("#008080");
    static public Color cga = Color.web("#008080");
    static public Color clearway = Color.web("#db0056");
    static public Color stopway = Color.web("#faa40a");
    static public Color physicalResa = Color.web("#fed9b4");
    static public Color runway = Color.web("#a5a5a5");
    static public Color toda = Color.web("#ff00ff");
    static public Color tora = Color.web("#00ffff");
    static public Color asda = Color.web("#ffff00");
    static public Color lda = Color.web("#0000ff");;
    static public Color resa = Color.web("#d06800");
    static public Color stripend = Color.web("#00ff00");
    static public Color blastallowance = Color.web("#cccc00");
    static public Font font =Font.font("Calibri", 20);

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

    public static Color inverse(Color colour) {
        double total = (Math.exp(1 - Math.log(1+(colour.getRed()+ colour.getGreen()+colour.getBlue())*(Math.E-1)/3))-1)/(Math.E-1);
        return makeColour(total, colour);

    }
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

    public static void makeDark(){
        bg = Color.gray(0.2);
        bgRunway = Color.gray(0.15);
        unfocusedBG = Color.gray(0.1);
        focusedBG = Color.gray(0.2);
        veryfocusedBG = Color.gray(0.25);
        extremelyfocusedBG = Color.gray(0.3);
        fg = Color.gray(0.6);
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
    public static void makeDark2(){

        bg = inverse(bg);
        bgRunway = inverse(bgRunway);
        unfocusedBG = inverse(unfocusedBG);
        focusedBG = inverse(focusedBG);
        veryfocusedBG = inverse(veryfocusedBG);
        extremelyfocusedBG = inverse(extremelyfocusedBG);
        fg = inverse(fg);
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
}
