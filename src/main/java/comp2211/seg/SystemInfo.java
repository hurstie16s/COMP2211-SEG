package comp2211.seg;

/**
 * Utility class to retrieve system information
 */
public class SystemInfo {
    /**
     * Retrieves the version of the current Java runtime environment
     *
     * @return String representation of the Java version
     */
    public static String javaVersion() {
    return System.getProperty("java.version");
  }

    /**
     * Retrieves the version of the current JavaFX runtime environment
     *
     * @return String representation of the JavaFX version
     */
    public static String javafxVersion() {
    return System.getProperty("javafx.version");
  }
}
