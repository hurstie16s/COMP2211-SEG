package comp2211.seg.UiView.Scene.Utilities;

import comp2211.seg.Controller.Stage.AppWindow;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;
import com.steadystate.css.parser.CSSOMParser;

public class CssColorParser {


  private static final Logger logger = LogManager.getLogger(CssColorParser.class);
//  public static Color getColorFromCssClass(Scene scene, String cssClass, String cssPrefix, int length) throws Exception {
//    System.out.println("Parser is on :" + scene + "/"+ cssClass + "/" + cssPrefix);
//    // Get the last stylesheet URL in the list of stylesheets applied to the scene
//    List<String> stylesheetUrls = scene.getStylesheets();
//    String lastStylesheetUrl = stylesheetUrls.get(stylesheetUrls.size() - 1);
//
//    // Load the last stylesheet as a CSSStyleSheet object
//    InputSource url = new InputSource(lastStylesheetUrl);
//    CSSOMParser parser = new CSSOMParser();
//    CSSStyleSheet stylesheet = parser.parseStyleSheet(url, null, null);
//    //System.out.println(stylesheet);
//
//    // Parse the color value from the .obstacle class in the last stylesheet
//    CSSRuleList rules = stylesheet.getCssRules();
//    for (int i = 0; i < rules.getLength(); i++) {
//      CSSRule rule = rules.item(i);
//      if (rule instanceof CSSStyleRule styleRule) {
//        //System.out.println(rule);
//        if (styleRule.getSelectorText().equals(cssClass)) {
//          String cssText = styleRule.getStyle().getCssText();
//          String colorValue = cssText.substring(cssText.indexOf(cssPrefix) + length);
//          System.out.println(scene.getClass().getName() + " css"+ cssClass + " Color: " + colorValue);
//          return Color.web(colorValue);
//        }
//      } else {
//        System.out.println("not style rule");
//      }
//    }
//    // The CSS class was not found in the last stylesheet
//    System.out.println("Nothing");
//    return null;
//  }

  public static CSSRuleList getCssRules (String stylesheet) throws IOException {
    logger.info("Getting css rules list...");
    // Get the stylesheet URL
    URL stylesheetUrl = CssColorParser.class.getResource(stylesheet);
    // Load the stylesheet as a CSSStyleSheet object
    InputSource url = new InputSource(String.valueOf(stylesheetUrl));
    CSSOMParser parser = new CSSOMParser();
    CSSStyleSheet cssStylesheet = parser.parseStyleSheet(url, null, null);
    // Parse the color value from css class in the stylesheet
    CSSRuleList rules = cssStylesheet.getCssRules();
    return rules;
  }

  public static ArrayList<Color> getCssColors(CSSRuleList rules, ArrayList<String> cssClasses, ArrayList<String> cssPrefixes) {
    ArrayList<Color> cssColors = new ArrayList<>();
    for (int i = 0; i < cssClasses.size(); i++) {
      String cssClass = cssClasses.get(i);
      String cssPrefix = cssPrefixes.get(i);
      for (int j = 0; j < rules.getLength(); j++) {
        CSSRule rule = rules.item(j);
        if (rule instanceof CSSStyleRule styleRule && styleRule.getSelectorText().equals(cssClass)) {
          String cssText = styleRule.getStyle().getCssText();
          int index = cssText.indexOf(cssPrefix);
          if (index != -1) {
            String colorValue = cssText.substring(index + cssPrefix.length()).trim();
            cssColors.add(Color.web(colorValue));
          }
        }
      }
    }
    if (cssColors.isEmpty()) {
      logger.info("Parser failed");
      return null;
    }
    return cssColors;
  }




}



