module runwaytool {
    requires java.scripting;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.xml;
    requires javafx.swing;
    requires javafx.graphics;
    requires java.desktop;
  requires jdk.xml.dom;
  requires cssparser;
  requires sac;
  exports comp2211.seg;

    opens comp2211.seg.UiView.Scene to javafx.base;
    opens comp2211.seg.UiView.Scene.SceneComponents to javafx.base;
}