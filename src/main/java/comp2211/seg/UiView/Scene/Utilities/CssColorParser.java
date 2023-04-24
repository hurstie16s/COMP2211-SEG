package comp2211.seg.UiView.Scene.Utilities;

import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.util.List;

import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;
import com.steadystate.css.parser.CSSOMParser;

public class CssColorParser {

  public static Color getColorFromCssClass(Scene scene, String cssClass, String cssPrefix, int length) throws Exception {
    System.out.println("Parser is on :" + scene + "/"+ cssClass + "/" + cssPrefix);
    // Get the last stylesheet URL in the list of stylesheets applied to the scene
    List<String> stylesheetUrls = scene.getStylesheets();
    String lastStylesheetUrl = stylesheetUrls.get(stylesheetUrls.size() - 1);

    // Load the last stylesheet as a CSSStyleSheet object
    InputSource url = new InputSource(lastStylesheetUrl);
    CSSOMParser parser = new CSSOMParser();
    CSSStyleSheet stylesheet = parser.parseStyleSheet(url, null, null);
    //System.out.println(stylesheet);

    // Parse the color value from the .obstacle class in the last stylesheet
    CSSRuleList rules = stylesheet.getCssRules();
    for (int i = 0; i < rules.getLength(); i++) {
      CSSRule rule = rules.item(i);
      if (rule instanceof CSSStyleRule styleRule) {
        //System.out.println(rule);
        if (styleRule.getSelectorText().equals(cssClass)) {
          String cssText = styleRule.getStyle().getCssText();
          String colorValue = cssText.substring(cssText.indexOf(cssPrefix) + length);
          System.out.println(scene.getClass().getName() + " css"+ cssClass + " Color: " + colorValue);
          return Color.web(colorValue);
        }
      } else {
        System.out.println("not style rule");
      }
    }
    // The CSS class was not found in the last stylesheet
    System.out.println("Nothing");
    return null;
  }


}



