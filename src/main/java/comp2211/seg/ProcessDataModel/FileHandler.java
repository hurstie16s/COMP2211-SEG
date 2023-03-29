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

public class FileHandler {
    private static final Logger logger = LogManager.getLogger(FileHandler.class);

    private static final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    private static Validator airportValidator = null;
    private static Validator obstacleValidator = null;

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

    public static Obstacle importObstacle(File inputFile){

        // Check given file conforms to the appropriate schema
        checkFileFormat(inputFile, false);

        return null;
    }
    public static Airport importAirport(File inputFile){

        Airport airport;

        // Check given file conforms to the appropriate schema
        checkFileFormat(inputFile, true);

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

        try {

            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            Document document = builder.parse(inputFile);

            document.getDocumentElement().normalize();

            // Get Airport Name
            String airportName = document.getElementsByTagName("name").item(0).getTextContent();

            // Create Airport Object
            airport = new Airport(airportName);

            // Get Latitude
            Double latitude = Double.parseDouble(document.getElementsByTagName("Latitude").item(0).getTextContent());
            airport.setLatitude(latitude);

            // Get Longitude
            Double longitude = Double.parseDouble(document.getElementsByTagName("Longitude").item(0).getTextContent());
            airport.setLongitude(longitude);

            // Get Runways
            NodeList runways = document.getElementsByTagName("Runway");
            for (int i = 0; i < runways.getLength(); i++) {
                Runway runway = new Runway();

                Node runwayToParse = runways.item(i);
            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            logger.error(e.getMessage());
            logger.warn("Handle Error");
            // TODO: Handle Error
        }

        return null;
    }
    private static boolean checkFileFormat(File file, boolean validateAirport) {
        if (validateAirport && airportValidator == null) {
            airportValidator = createSchemaValidator("src/main/resources/XML/Airport.xsd");
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
            throw new RuntimeException(e.getMessage());
        }
        return validator;
    }

    // For testing as go
    public static void main(String[] args) {
        File testFile = new File("src/main/resources/XML/AirportTest.xml");
        var fileOK = checkFileFormat(testFile, true);
        logger.info(fileOK);
    }

}
