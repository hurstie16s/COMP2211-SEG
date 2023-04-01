package comp2211.seg.ProcessDataModel;

import java.util.ArrayList;

/**
 * An Airport object representing an airport with runways.
 */
public class Airport {
    /** The list of runways at the airport. */
    private ArrayList<Runway> runways;
    /** The name of the airport. */
    public String name;
    public Double latitude = 0.0;
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
    public Airport(String name, Runway[] runways) {
        this.name = name;

    }
    public String toString(){
        return name;
    }

    /**
     * Creates a new runway and adds it to the list of runways at the airport.
     */
    public void makeRunway() {
        runways.add(new Runway());
    }
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

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


}
