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
        Color res = new Color((1 - (colour.getGreen() + colour.getBlue())/2),(1 - (colour.getRed() + colour.getBlue())/2),
            (1 - (colour.getGreen() + colour.getRed())/2),colour.getOpacity());
        return res;
    }

    public static void makeDark(){
        bg = Color.gray(0.2);
        bgRunway = Color.gray(0.15);
        unfocusedBG = Color.gray(0.1);
        focusedBG = Color.gray(0.2);
        veryfocusedBG = Color.gray(0.25);
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
}
