package comp2211.seg.ProcessDataModel;

/**
 * The enum Schema type.
 */
public enum SchemaType {
    /**
     * Airport schema type.
     */
    AIRPORT("airport"),
    /**
     * The Airport obstacle.
     */
    AIRPORT_OBSTACLE("airport with obstacle"),
    /**
     * Obstacle schema type.
     */
    OBSTACLE("obstacle");

    /**
     * The Label.
     */
    public final String label;

    /**
     * Instantiates a new Schema type.
     *
     * @param label the label
     */
    SchemaType(String label){
        this.label = label;
    }
}
