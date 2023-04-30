package comp2211.seg.ProcessDataModel;

public enum SchemaType {
    AIRPORT("airport"),
    AIRPORT_OBSTACLE("airport with obstacle"),
    OBSTACLE("obstacle");

    public final String label;

    SchemaType(String label){
        this.label = label;
    }
}
