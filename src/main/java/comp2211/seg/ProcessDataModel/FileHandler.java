package comp2211.seg.ProcessDataModel;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * The type File handler.
 */
public class FileHandler {
    private static final Logger logger = LogManager.getLogger(FileHandler.class);

    private static final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    private static Validator airportValidator = null;
    private static Validator obstacleValidator = null;
    private static final DocumentBuilderFactory BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
    private static DocumentBuilder builder = null;

    // Holding Variables
    private static String designator;
    private static double TORA;
    private static double TODA;
    private static double ASDA;
    private static double LDA;
    private static double clearway;
    private static double stopway;
    private static double dispThreshold;

    /**
     * Export airport boolean.
     *
     * @param file     the file
     * @param airport  the airport
     * @param obstacle the obstacle
     * @return the boolean
     */
    public static boolean exportAirport(File file, Airport airport, Obstacle obstacle) {

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();

            // These are the root elements
            Document document = documentBuilder.newDocument();
            Element rootElement = document.createElement("airport");
            document.appendChild(rootElement);

            Element name = document.createElement("name");
            name.appendChild(document.createTextNode(airport.toString()));
            rootElement.appendChild(name);

            Element lat = document.createElement("Latitude");
            lat.appendChild(document.createTextNode(Double.toString(airport.getLatitude())));
            rootElement.appendChild(lat);

            Element longitude = document.createElement("Longitude");
            longitude.appendChild(document.createTextNode(Double.toString(airport.getLongitude())));
            rootElement.appendChild(longitude);

            Element runways = document.createElement("Runways");
            rootElement.appendChild(runways);

            for (Runway runway : airport.getRunways()) {
                Element runwayElement = document.createElement("Runway");
                runways.appendChild(runwayElement);

                appendElementWithNewline(runwayElement, "Resa_Height", Double.toString(runway.getRESAHeight()), document);
                appendElementWithNewline(runwayElement, "Resa_Width", Double.toString(runway.getRESAWidth()), document);

                if (runway.isHasRunwayObstacle()) {

                    Element ObstacleElement = document.createElement("Runway_Obstacle");
                    runways.appendChild(ObstacleElement);

                    appendElementWithNewline(ObstacleElement,"Obstacle_Designator", runway.getRunwayObstacle().toString(), document);
                    appendElementWithNewline(ObstacleElement,"Obstacle_Height", Double.toString(runway.getRunwayObstacle().getHeight()), document);
                    appendElementWithNewline(ObstacleElement, "Obstacle_Width", Double.toString(runway.getRunwayObstacle().getLength()), document);
                    appendElementWithNewline(ObstacleElement, "Obstacle_Length", Double.toString(runway.getRunwayObstacle().getWidth()), document);
                    appendElementWithNewline(ObstacleElement, "Distance_From_Threshold", Double.toString(runway.getRunwayObstacle().getDistFromThreshold()), document);
                    appendElementWithNewline(ObstacleElement, "Distance_From_Other_Threshold", Double.toString(runway.getRunwayObstacle().getDistFromOtherThreshold()), document);
                    logger.info("Runway has Obstacles");

                } else {
                    logger.info("Runway has no Obstacles");
                }

                Element rightElement = document.createElement("Right_Properties");
                runwayElement.appendChild(rightElement);
                appendElementWithNewline(rightElement, "Designator", runway.getRunwayDesignatorRight(), document);
                appendElementWithNewline(rightElement, "TORA", Double.toString(runway.getInputRightTora()), document);
                appendElementWithNewline(rightElement, "TODA", Double.toString(runway.getInputRightToda()), document);
                appendElementWithNewline(rightElement, "ASDA", Double.toString(runway.getInputRightAsda()), document);
                appendElementWithNewline(rightElement, "LDA", Double.toString(runway.getInputRightLda()), document);
                appendElementWithNewline(rightElement, "Clearway", Double.toString(runway.getClearwayRight()), document);
                appendElementWithNewline(rightElement, "Stopway", Double.toString(runway.getStopwayRight()), document);
                appendElementWithNewline(rightElement, "Displacement_Threshold", Double.toString(runway.getDispThresholdRight()), document);

                Element leftElement = document.createElement("Left_Properties");
                runwayElement.appendChild(leftElement);
                appendElementWithNewline(leftElement, "Designator", runway.getRunwayDesignatorLeft(), document);
                appendElementWithNewline(leftElement, "TORA", Double.toString(runway.getInputLeftTora()), document);
                appendElementWithNewline(leftElement, "TODA", Double.toString(runway.getInputLeftToda()), document);
                appendElementWithNewline(leftElement, "ASDA", Double.toString(runway.getInputLeftAsda()), document);
                appendElementWithNewline(leftElement, "LDA", Double.toString(runway.getInputLeftLda()), document);
                appendElementWithNewline(leftElement, "Clearway", Double.toString(runway.getClearwayLeft()), document);
                appendElementWithNewline(leftElement, "Stopway", Double.toString(runway.getStopwayLeft()), document);
                appendElementWithNewline(leftElement, "Displacement_Threshold", Double.toString(runway.getDispThresholdLeft()), document);

                runwayElement.appendChild(document.createTextNode("\n"));
            }

            DOMSource domSource = new DOMSource(document);
            StreamResult res = new StreamResult(file);

            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.transform(domSource, res);
                return true;
            }
            catch (TransformerException e) {
                e.printStackTrace();
                return false;
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return false;
        }
    }


    private static void appendElementWithNewline(Element parent, String elementName, String textContent, Document document) {
        Element element = document.createElement(elementName);
        element.appendChild(document.createTextNode(textContent));
        parent.appendChild(element);
        parent.appendChild(document.createTextNode("\n"));
    }

    /**
     * Export obstacle boolean.
     *
     * @param file     the file
     * @param obstacle the obstacle
     * @return the boolean
     */
    public static boolean exportObstacle(File file, Obstacle obstacle) {

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();

            // These are the root elements
            Document document = documentBuilder.newDocument();
            Element rootElement = document.createElement("Obstacle");
            document.appendChild(rootElement);

            appendElementWithNewline(rootElement,"Height", Double.toString(obstacle.getHeight()), document);
            appendElementWithNewline(rootElement,"Length", Double.toString(obstacle.getLength()), document);
            appendElementWithNewline(rootElement,"Width", Double.toString(obstacle.getWidth()), document);


            DOMSource domSource = new DOMSource(document);
            StreamResult res = new StreamResult(file);

            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.transform(domSource, res);
                return true;
            }
            catch (TransformerException e) {
                e.printStackTrace();
                return false;
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return false;
        }

    }

    // TODO: Error handling - incorrect XML format - build schema to run a format check?
    // TODO: Airport + Obstacle Import
    // TODO: Obstacle Import
    /*
    Plan:
    -Take file
    -Check file in correct format - else throw exception - custom exception?
        - also provide front-end error messages
     */

    /**
     * Import obstacle.
     *
     * @param inputFile the file containing data to be imported
     * @return the imported obstacle
     */
    public static Obstacle importObstacle(File inputFile) {

        Obstacle obstacle = null;

        // Check given file conforms to the appropriate schema
        // Possibly throw custom exception?
        if (!checkFileFormat(inputFile, false)) return null;
        logger.info("File Accepted by schema");

        if (builder == null) createBuilder();

        try {

            Document document = builder.parse(inputFile);
            document.getDocumentElement().normalize();

            logger.info("Importing Obstacle");
            String obstacleDesignator = document
                    .getElementsByTagName("Obstacle_Designator")
                    .item(0)
                    .getTextContent();
            logger.info("Designator = "+obstacleDesignator);
            double height = Double.parseDouble(
                    document
                            .getElementsByTagName("Height")
                            .item(0)
                            .getTextContent());
            logger.info("Height = "+height);
            double length = Double.parseDouble(
                    document
                            .getElementsByTagName("Length")
                            .item(0)
                            .getTextContent());
            logger.info("Length = "+length);
            double width = Double.parseDouble(
                    document
                            .getElementsByTagName("Width")
                            .item(0)
                            .getTextContent());
            logger.info("Width = "+width);
            double distFromThreshold = Double.parseDouble(
                    document
                            .getElementsByTagName("Distance_From_Threshold")
                            .item(0)
                            .getTextContent());
            logger.info("Distance from threshold = "+distFromThreshold);

            obstacle = new Obstacle(obstacleDesignator, height, distFromThreshold);
            obstacle.lengthProperty().set(length);
            obstacle.widthProperty().set(width);
            logger.info("Obstacle Created");

        } catch (NullPointerException | IOException | SAXException e) {
            // TODO: Split the errors so that they can be better handled
            logger.error(e.getMessage());
            logger.warn("Handle Error");
            // TODO: Handle Error
        }

        return obstacle;
    }

    // TODO: Resolve Issues
    /**
     * Import airport.
     *
     * @param inputFile the file containing data to be imported
     * @return the airport
     */
    public static Airport importAirport(File inputFile){

        Airport airport = null;

        // Check given file conforms to the appropriate schema
        if (!checkFileFormat(inputFile, true)) return null;
        logger.info("File Accepted by schema");

        if (builder == null) createBuilder();


        try {

            Document document = builder.parse(inputFile);
            document.getDocumentElement().normalize();

            // Get Airport Name
            String airportName = document.getElementsByTagName("name").item(0).getTextContent();

            // Create Airport Object
            airport = new Airport(airportName);
            logger.info("Created new airport "+airportName);

            // Get Latitude
            double latitude = Double.parseDouble(document.getElementsByTagName("Latitude").item(0).getTextContent());
            airport.setLatitude(latitude);
            logger.info("Set latitude of "+airportName+" to "+latitude);

            // Get Longitude
            double longitude = Double.parseDouble(document.getElementsByTagName("Longitude").item(0).getTextContent());
            airport.setLongitude(longitude);
            logger.info("Set longitude of "+airportName+" to "+longitude);

            // Get Runways
            NodeList runways = document.getElementsByTagName("Runways");
            // Jumps 2 at a time to ensure that the software takes the runway child and runway obstacle child
            for (int i = 0; i < runways.getLength(); i+=2) {
                Runway runway = new Runway();

                Element runwayToParse = (Element) runways.item(i);

                // Get general Runway properties
                double resaHeight = Double.parseDouble(runwayToParse.getElementsByTagName("Resa_Height").item(0).getTextContent());
                runway.setRESAHeight(resaHeight);
                logger.info("Set RESA Height to "+resaHeight+"m");
                double resaWidth = Double.parseDouble(runwayToParse.getElementsByTagName("Resa_Width").item(0).getTextContent());
                runway.setRESAWidth(resaWidth);
                logger.info("Set RESA width to "+resaWidth+"m");

                // Get Right-hand runway properties
                // TODO: Change to set Input properties - bound values cannot be set
                // TODO: Determine if Clearway and Stopway are required for import
                logger.info("Importing right-hand runway properties");
                Element rightProperties = (Element) runwayToParse.getElementsByTagName("Right_Properties").item(0);

                getProperties(rightProperties);

                runway.setRunwayDesignatorRight(designator);
                runway.setInputRightTora(TORA);
                runway.setInputRightToda(TODA);
                runway.setInputRightAsda(ASDA);
                runway.setInputRightLda(LDA);
                //runway.setClearwayRight(clearway);
                //runway.setStopwayRight(stopway);
                //runway.setDispThresholdRight(dispThreshold);

                // Get Left-hand runway properties
                logger.info("Importing left-hand runway properties");
                Element leftProperties = (Element) runwayToParse.getElementsByTagName("Left_Properties").item(0);

                getProperties(leftProperties);

                runway.setRunwayDesignatorLeft(designator);
                runway.setInputLeftTora(TORA);
                runway.setInputLeftToda(TODA);
                runway.setInputLeftAsda(ASDA);
                runway.setInputLeftLda(LDA);

                String obstacleDesignator = runwayToParse
                        .getElementsByTagName("Obstacle_Designator")
                        .item(0)
                        .getTextContent();
                runway.runwayObstacle.obstacleDesignatorProperty().set(obstacleDesignator);
                logger.info("Obstacle designator = "+obstacleDesignator);

                double obstacleHeight = Double.parseDouble(
                        runwayToParse
                                .getElementsByTagName("Obstacle_Height")
                                .item(0)
                                .getTextContent());
                runway.runwayObstacle.heightProperty().set(obstacleHeight);
                logger.info("Obstacle height = "+obstacleHeight);
                double obstacleWidth = Double.parseDouble(
                        runwayToParse
                                .getElementsByTagName("Obstacle_Width")
                                .item(0)
                                .getTextContent());
                runway.runwayObstacle.widthProperty().set(obstacleWidth);
                logger.info("Obstacle width = "+obstacleWidth);

                double obstacleLength = Double.parseDouble(
                        runwayToParse
                                .getElementsByTagName("Obstacle_Length")
                                .item(0)
                                .getTextContent());
                runway.runwayObstacle.lengthProperty().set(obstacleLength);
                logger.info("Obstacle length = "+obstacleLength);

                double distFromThreshold = Double.parseDouble(
                        runwayToParse
                                .getElementsByTagName("Distance_From_Threshold")
                                .item(0)
                                .getTextContent());
                runway.runwayObstacle.distFromThresholdProperty().set(distFromThreshold);
                logger.info("Distance from threshold = "+distFromThreshold);

                // Add runway to airport
                airport.addRunway(runway);
            }

        } catch (NullPointerException | IOException | SAXException e) {
            // TODO: Split the errors so that they can be better handled
            logger.error(e.getMessage());
            logger.warn("Handle Error");
            // TODO: Handle Error
        }

        return airport;
    }

    private static void getProperties(Element element) {

        designator = element.getElementsByTagName("Designator").item(0).getTextContent();
        logger.info("Designator = "+designator);

        TORA = Double.parseDouble(element.getElementsByTagName("TORA").item(0).getTextContent());
        logger.info("TORA = "+TORA+"m");

        TODA = Double.parseDouble(element.getElementsByTagName("TODA").item(0).getTextContent());
        logger.info("TODA = "+TODA+"m");

        ASDA = Double.parseDouble(element.getElementsByTagName("ASDA").item(0).getTextContent());
        logger.info("ASDA = "+ASDA+"m");

        LDA = Double.parseDouble(element.getElementsByTagName("LDA").item(0).getTextContent());
        logger.info("LDA = "+LDA+"m");

        clearway = Double.parseDouble(element.getElementsByTagName("Clearway").item(0).getTextContent());
        //logger.info("Clearway = "+rightClearway+"m");

        stopway = Double.parseDouble(element.getElementsByTagName("Stopway").item(0).getTextContent());
        //logger.info("Stopway = "+rightStopway+"m");

        dispThreshold = Double.parseDouble(element.getElementsByTagName("Displacement_Threshold").item(0).getTextContent());
        //logger.info("Displaced threshold = "+dispThreshold);
    }

    private static boolean checkFileFormat(File file, boolean validateAirport) {
        if (validateAirport && airportValidator == null) {
            airportValidator = createSchemaValidator("src/main/resources/XML/AirportOb.xsd");
        } else if (!validateAirport && obstacleValidator == null) {
            obstacleValidator = createSchemaValidator("src/main/resources/XML/Obstacle.xsd");
        }
        // File paths are only hard coded for now to make ease of testing
        //InputStream airportSchemaFilePath = Objects.requireNonNull(FileHandler.class.getResourceAsStream("XML/airport.xsd")); // is coming back null
        //logger.info(airportSchemaFilePath);
        try {
            Source testFileSource = new StreamSource(file);
            if (validateAirport) {
                airportValidator.validate(testFileSource);
            } else {
                obstacleValidator.validate(testFileSource);
            }
        } catch (SAXException | IOException e) {
            logger.error(e.getMessage());
            logger.warn("Handle Error - file does not fit schema");
            // TODO: Handle Error
            return false;
        }
        return true;
    }

    /**
     * Create schema validator validator.
     *
     * @param schemaFilePathFromResources the schema file path from resources
     * @return the validator
     */
    public static Validator createSchemaValidator(String schemaFilePathFromResources) {
        Validator validator;
        //File schemaFile = new File(Objects.requireNonNull(FileHandler.class.getResource("XML/Airport.xsd")).toURI()); - is null??
        File schemaFile = new File(schemaFilePathFromResources);
        try {
            Schema schema = schemaFactory.newSchema(schemaFile);
            validator = schema.newValidator();
        } catch (SAXException e) {
            logger.error(e.getMessage());
            logger.warn("Handle Error - issue with schema file");
            // TODO: Handle Error
            throw new RuntimeException(e);
        }
        return validator;
    }

    private static void createBuilder() {
        try {
            builder = BUILDER_FACTORY.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("Failed to create parser: "+e.getMessage());
            logger.warn("Handle Error - issue with parser config");
            throw new RuntimeException(e);
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
// For testing as go
    public static void main(String[] args) {
        File testFileAirport = new File("src/main/resources/XML/AirportTest.xml");
        File testFileObstacle = new File("src/main/resources/XML/Obstacle.xml");
        //importAirport(testFileAirport);
        importObstacle(testFileObstacle);
    }

}
