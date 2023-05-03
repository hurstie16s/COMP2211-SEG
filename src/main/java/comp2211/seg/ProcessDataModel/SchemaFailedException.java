package comp2211.seg.ProcessDataModel;

public class SchemaFailedException extends Exception{
    public SchemaFailedException(String fileFailed, SchemaType schemaType) {
        super("File "+fileFailed+" does not fit the schema file "+schemaType.schemaFile);
    }
}
