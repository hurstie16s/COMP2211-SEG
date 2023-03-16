package comp2211.seg.Controller.Stage;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Theme {
    static public Color bg = Color.gray(0.9);
    static public Color bgRunway = Color.gray(0.85);
    static public Color unfocusedBG = Color.gray(0.9);
    static public Color focusedBG = Color.gray(0.8);
    static public Color veryfocusedBG = Color.gray(0.75);
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
        Color res = new Color((1 - (colour.getGreen() + colour.getBlue())/2),(1 - (colour.getRed() + colour.getBlue())/2),(1 - (colour.getGreen() + colour.getRed())/2),colour.getOpacity());
        return res;
    }

    public static void makeDark(){
        bg = inverse(bg);
        bgRunway = inverse(bgRunway);
        unfocusedBG = inverse(unfocusedBG);
        focusedBG = inverse(focusedBG);
        veryfocusedBG = inverse(veryfocusedBG);
        fg = inverse(fg);
        grass = reverse(grass);
        obstacle = reverse(obstacle);
        slope = reverse(slope);
        cga = reverse(cga);
        clearway = reverse(clearway);
        stopway = reverse(stopway);
        physicalResa = reverse(physicalResa);
        runway = reverse(runway);
        toda = reverse(toda);
        tora = reverse(tora);
        asda = reverse(asda);
        lda = reverse(lda);
    }
}
