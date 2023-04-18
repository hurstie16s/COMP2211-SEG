package comp2211.seg.ProcessDataModel;

import comp2211.seg.UiView.Scene.SceneAbstract;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * An Airport object representing an airport with runways.
 */
public class Airport {

    private static final Logger logger = LogManager.getLogger(Airport.class);
    /** The list of runways at the airport. */
    private ArrayList<Runway> runways;
    /**
     * The name of the airport.
     */
    public String name;
    /**
     * The Latitude.
     */
    public Double latitude = 0.0;
    /**
     * The Longitude.
     */
    public Double longitude = 0.0;

    /**
     * Creates a new Airport object with the specified name and a default runway.
     *
     * @param name the name of the airport
     */
    public Airport(String name) {
        runways = new ArrayList<>();
        this.name = name;
    }


    public Airport(String name, String designators, double leftTora, double leftToda, double leftLDA, double leftASDA, double rightTora, double rightToda, double rightLDA, double rightASDA) {

        runways = new ArrayList<>();
        this.name = name;
        addRunway(new Runway(designators, leftTora, leftToda, leftLDA, leftASDA, rightTora, rightToda, rightLDA, rightASDA));
    }

    public String toString(){
        return name;
    }

    /**
     * Creates a new runway and adds it to the list of runways at the airport.
     */
    public void makeRunway() {
        logger.info("creates Runway object");
        runways.add(new Runway());
    }

    /**
     * Add runway.
     *
     * @param runway the runway
     */
    public void addRunway(Runway runway) {
        runways.add(runway);
    }

    // Getters

    /**
     * Returns the list of runways at the airport.
     *
     * @return the list of runways at the airport
     */
    public ArrayList<Runway> getRunways() {
        return runways;
    }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets latitude.
     *
     * @param latitude the latitude
     */
// Setters
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Sets longitude.
     *
     * @param longitude the longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


}
