module runwaytool {
    requires java.scripting;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    exports comp2211.seg;

    opens comp2211.seg.UiView.Scene to javafx.base;
}