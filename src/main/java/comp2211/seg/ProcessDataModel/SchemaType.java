package comp2211.seg.ProcessDataModel;

/**
 * The enum Schema type.
 */
public enum SchemaType {
    /**
     * Airport schema type.
     */
    AIRPORT("airport", "Airport.xsd"),
    /**
     * The Airport obstacle.
     */
    AIRPORT_OBSTACLE("airport with obstacle", "AirportOb.xsd"),
    /**
     * Obstacle schema type.
     */
    OBSTACLE("obstacle", "Obstacle.xsd");

    /**
     * The Label.
     */
    public final String label;
    public final String schemaFile;

    /**
     * Instantiates a new Schema type.
     *
     * @param label the label
     */
    SchemaType(String label, String schemaFile){
        this.label = label;
        this.schemaFile = schemaFile;
    }
}
