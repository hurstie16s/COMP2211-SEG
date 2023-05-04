package comp2211.seg.ProcessDataModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The type File handler.
 */
public abstract class FileHandler {
    /**
     * The constant logger.
     */
    private static final Logger logger = LogManager.getLogger(FileHandler.class);

    /**
     * The constant SCHEMA_FACTORY.
     */
    private static final SchemaFactory SCHEMA_FACTORY = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    /**
     * The constant airportObstacleValidator.
     */
    private static Validator airportObstacleValidator = null;
    /**
     * The constant obstacleValidator.
     */
    private static Validator obstacleValidator = null;
    /**
     * The constant airportValidator.
     */
    private static Validator airportValidator = null;
    /**
     * The constant BUILDER_FACTORY.
     */
    private static final DocumentBuilderFactory BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
    /**
     * The constant builder.
     */
    private static DocumentBuilder builder = null;

    /**
     * The constant designator.
     */
// Holding Variables
    private static String designator;
    /**
     * The constant tora.
     */
    private static double tora;
    /**
     * The constant toda.
     */
    private static double toda;
    /**
     * The constant asda.
     */
    private static double asda;
    /**
     * The constant lda.
     */
    private static double lda;
    /**
     * The constant clearway.
     */
// Check if clearway, stopway and dispThreshold even need to be brought in from XML file
    private static double clearway;
    /**
     * The constant stopway.
     */
    private static double stopway;
    /**
     * The constant dispThreshold.
     */
    private static double dispThreshold;

    /**
     * Export airport boolean.
     *
     * @param file     the file
     * @param airport  the airport
     * @param obstacle the obstacle
     * @return the boolean
     */
    public static boolean exportAirportAndOb(File file, Airport airport, Obstacle obstacle) {

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();

            // These are the root elements
            Document document = documentBuilder.newDocument();
            Element rootElement = document.createElement("airport");
            document.appendChild(rootElement);

            Element name = document.createElement("name");
            name.appendChild(document.createTextNode(airport.toString()));
            rootElement.appendChild(document.createTextNode("\n"));
            rootElement.appendChild(name);

            Element lat = document.createElement("Latitude");
            lat.appendChild(document.createTextNode(Double.toString(airport.getLatitude())));
            rootElement.appendChild(document.createTextNode("\n"));
            rootElement.appendChild(lat);

            Element longitude = document.createElement("Longitude");
            longitude.appendChild(document.createTextNode(Double.toString(airport.getLongitude())));
            rootElement.appendChild(document.createTextNode("\n"));
            rootElement.appendChild(longitude);


            Element runways = document.createElement("Runways");
            rootElement.appendChild(document.createTextNode("\n"));
            rootElement.appendChild(runways);

            for (Runway runway : airport.getRunways()) {
                Element runwayElement = document.createElement("Runway");
                runways.appendChild(runwayElement);
//
                appendElementWithNewline(runwayElement, "Resa_Height", Double.toString(runway.getRESAHeight()), document);
                appendElementWithNewline(runwayElement, "Resa_Width", Double.toString(runway.getRESAWidth()), document);
                appendElementWithNewline(runwayElement, "Dual_Direction", Boolean.toString(runway.dualDirectionRunway.get()), document);
//
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
//
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
//
                runwayElement.appendChild(document.createTextNode("\n"));

            }

            Element runwayObs = document.createElement("Runway_Obstacles");
            rootElement.appendChild(runwayObs);

            Element runwayOb = document.createElement("Runway_Obstacle");
            runwayObs.appendChild(runwayOb);

            appendElementWithNewline(runwayOb,"Obstacle_Designator", (obstacle.getObstacleDesignator()), document);
            appendElementWithNewline(runwayOb,"Obstacle_Height", Double.toString(obstacle.getHeight()), document);
            appendElementWithNewline(runwayOb,"Obstacle_Width", Double.toString(obstacle.getWidth()), document);
            appendElementWithNewline(runwayOb,"Obstacle_Length", Double.toString(obstacle.getLength()), document);
            appendElementWithNewline(runwayOb,"Distance_From_Threshold", Double.toString(obstacle.getDistFromThreshold()), document);
            appendElementWithNewline(runwayOb,"Distance_From_Other_Threshold", Double.toString(obstacle.getDistFromOtherThreshold()), document);

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

    /**
     * Export airport boolean.
     *
     * @param file     the file
     * @param airport  the airport
     * @param obstacle the obstacle
     * @return the boolean
     */
    public static boolean exportAirport(File file, Airport airport) {

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();

            // These are the root elements
            Document document = documentBuilder.newDocument();
            Element rootElement = document.createElement("airport");
            document.appendChild(rootElement);

            Element name = document.createElement("name");
            name.appendChild(document.createTextNode(airport.toString()));
            rootElement.appendChild(document.createTextNode("\n"));
            rootElement.appendChild(name);

            Element lat = document.createElement("Latitude");
            lat.appendChild(document.createTextNode(Double.toString(airport.getLatitude())));
            rootElement.appendChild(document.createTextNode("\n"));
            rootElement.appendChild(lat);

            Element longitude = document.createElement("Longitude");
            longitude.appendChild(document.createTextNode(Double.toString(airport.getLongitude())));
            rootElement.appendChild(document.createTextNode("\n"));
            rootElement.appendChild(longitude);


            Element runways = document.createElement("Runways");
            rootElement.appendChild(document.createTextNode("\n"));
            rootElement.appendChild(runways);

            for (Runway runway : airport.getRunways()) {
                Element runwayElement = document.createElement("Runway");
                runways.appendChild(runwayElement);
//
                appendElementWithNewline(runwayElement, "Resa_Height", Double.toString(runway.getRESAHeight()), document);
                appendElementWithNewline(runwayElement, "Resa_Width", Double.toString(runway.getRESAWidth()), document);
                appendElementWithNewline(runwayElement, "Dual_Direction", Boolean.toString(runway.dualDirectionRunway.get()), document);
//
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
//
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
//
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


    /**
     * Append element with newline.
     *
     * @param parent      the parent
     * @param elementName the element name
     * @param textContent the text content
     * @param document    the document
     */
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

            appendElementWithNewline(rootElement,"Obstacle_Designator", (obstacle.getObstacleDesignator()), document);
            appendElementWithNewline(rootElement,"Height", Double.toString(obstacle.getHeight()), document);
            appendElementWithNewline(rootElement,"Length", Double.toString(obstacle.getLength()), document);
            appendElementWithNewline(rootElement,"Width", Double.toString(obstacle.getWidth()), document);
            appendElementWithNewline(rootElement,"Distance_From_Threshold", Double.toString(obstacle.getDistFromThreshold()), document);



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

    /**
     * Import obstacle.
     *
     * @param inputFile the file containing data to be imported
     * @return the imported obstacle
     */
    public static Obstacle importObstacle(File inputFile) throws Exception {

        Obstacle obstacle = null;

        // Check given file conforms to the appropriate schema
        // Possibly throw custom exception?
        if (fileFormatFailed(inputFile, SchemaType.OBSTACLE)) throw new SchemaFailedException(inputFile.getName(), SchemaType.OBSTACLE);
        logger.info("File Accepted by schema");

        if (builder == null) createBuilder();

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

        return obstacle;
    }

    /**
     * Import airport airport.
     *
     * @param inputFile the input file
     * @return the airport
     */
    public static Airport importAirport(File inputFile) throws Exception {
        Airport airport = null;

        // Check given file conforms to the appropriate schema
        if (fileFormatFailed(inputFile, SchemaType.AIRPORT)) throw new SchemaFailedException(inputFile.getName(), SchemaType.AIRPORT);
        logger.info("File Accepted by schema");

        if (builder == null) createBuilder();

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
        NodeList runways = document.getElementsByTagName("Runway");
        logger.info("Number of Runways: "+runways.getLength());
        for (int i = 0; i < runways.getLength(); i++) {
            Runway runway = new Runway();

            Element runwayToParse = (Element) runways.item(i);

            // Get general Runway properties
            double resaHeight = Double.parseDouble(runwayToParse.getElementsByTagName("Resa_Height").item(0).getTextContent());
            runway.setRESAHeight(resaHeight);
            logger.info("Set RESA Height to "+resaHeight+"m");
            double resaWidth = Double.parseDouble(runwayToParse.getElementsByTagName("Resa_Width").item(0).getTextContent());
            runway.setRESAWidth(resaWidth);
            logger.info("Set RESA width to "+resaWidth+"m");

            boolean dualDirection = Boolean.parseBoolean(
                    runwayToParse.getElementsByTagName("Dual_Direction").item(0).getTextContent()
            );
            runway.dualDirectionRunway.set(dualDirection);

            if (dualDirection) {
                // Get Right-hand runway properties
                logger.info("Importing right-hand runway properties");
                Element rightProperties = (Element) runwayToParse.getElementsByTagName("Right_Properties").item(0);

                getProperties(rightProperties);

                runway.setRunwayDesignatorRight(designator);
                runway.setInputRightTora(tora);
                runway.setInputRightToda(toda);
                runway.setInputRightAsda(asda);
                runway.setInputRightLda(lda);
                //runway.setClearwayRight(clearway);
                //runway.setStopwayRight(stopway);
                //runway.setDispThresholdRight(dispThreshold);
            }

            // Get Left-hand runway properties
            logger.info("Importing left-hand runway properties");
            Element leftProperties = (Element) runwayToParse.getElementsByTagName("Left_Properties").item(0);

            getProperties(leftProperties);

            runway.setRunwayDesignatorLeft(designator);
            runway.setInputLeftTora(tora);
            runway.setInputLeftToda(toda);
            runway.setInputLeftAsda(asda);
            runway.setInputLeftLda(lda);

            airport.addRunway(runway);
        }

        return airport;
    }

    /**
     * Import airport with obstacles.
     *
     * @param inputFile the file containing data to be imported
     * @return the airport
     */
    public static Airport importAirportWithObstacles(File inputFile) throws Exception {

        Airport airport = null;

        // Check given file conforms to the appropriate schema
        if (fileFormatFailed(inputFile, SchemaType.AIRPORT_OBSTACLE)) throw new SchemaFailedException(inputFile.getName(), SchemaType.AIRPORT_OBSTACLE);
        logger.info("File Accepted by schema");

        if (builder == null) createBuilder();

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
        NodeList runways = document.getElementsByTagName("Runway");
        for (int i = 0; i < runways.getLength(); i++) {
            Runway runway = new Runway();

            Element runwayToParse = (Element) runways.item(i);

            // Get general Runway properties
            double resaHeight = Double.parseDouble(runwayToParse.getElementsByTagName("Resa_Height").item(0).getTextContent());
            runway.setRESAHeight(resaHeight);
            logger.info("Set RESA Height to "+resaHeight+"m");
            double resaWidth = Double.parseDouble(runwayToParse.getElementsByTagName("Resa_Width").item(0).getTextContent());
            runway.setRESAWidth(resaWidth);
            logger.info("Set RESA width to "+resaWidth+"m");

            boolean dualDirection = Boolean.parseBoolean(
                    runwayToParse.getElementsByTagName("Dual_Direction").item(0).getTextContent()
            );
            runway.dualDirectionRunway.set(dualDirection);

            if (dualDirection) {
                // Get Right-hand runway properties
                logger.info("Importing right-hand runway properties");
                Element rightProperties = (Element) runwayToParse.getElementsByTagName("Right_Properties").item(0);

                getProperties(rightProperties);

                runway.setRunwayDesignatorRight(designator);
                runway.setInputRightTora(tora);
                runway.setInputRightToda(toda);
                runway.setInputRightAsda(asda);
                runway.setInputRightLda(lda);
                //runway.setClearwayRight(clearway);
                //runway.setStopwayRight(stopway);
                //runway.setDispThresholdRight(dispThreshold);
            }

            // Get Left-hand runway properties
            logger.info("Importing left-hand runway properties");
            Element leftProperties = (Element) runwayToParse.getElementsByTagName("Left_Properties").item(0);

            getProperties(leftProperties);

            runway.setRunwayDesignatorLeft(designator);
            runway.setInputLeftTora(tora);
            runway.setInputLeftToda(toda);
            runway.setInputLeftAsda(asda);
            runway.setInputLeftLda(lda);

            airport.addRunway(runway);
        }

        // Get runway obstacles
        logger.info("Importing runway obstacles");
        NodeList obstacles = document.getElementsByTagName("Runway_Obstacles");
        for (int i = 0; i < obstacles.getLength(); i++) {
            logger.info("Importing obstacle for runway "
                    +airport.getRunways().get(i).getRunwayDesignatorLeft()
                    +"/ "
                    +airport.getRunways().get(i).getRunwayDesignatorRight()
            );
            Element obstacleToParse = (Element) obstacles.item(0);

            String obstacleDesignator = obstacleToParse
                    .getElementsByTagName("Obstacle_Designator")
                    .item(0)
                    .getTextContent();
            logger.info("Obstacle designator = "+obstacleDesignator);
            double obstacleHeight = Double.parseDouble(
                    obstacleToParse
                            .getElementsByTagName("Obstacle_Height")
                            .item(0)
                            .getTextContent()
            );
            logger.info("Obstacle height = "+obstacleHeight);
            double obstacleWidth = Double.parseDouble(
                    obstacleToParse
                            .getElementsByTagName("Obstacle_Width")
                            .item(0)
                            .getTextContent()
            );
            logger.info("Obstacle width = "+obstacleWidth);
            double obstacleLength = Double.parseDouble(
                    obstacleToParse
                            .getElementsByTagName("Obstacle_Length")
                            .item(0)
                            .getTextContent()
            );
            logger.info("Obstacle length = "+obstacleLength);
            double distFromThreshold = Double.parseDouble(
                    obstacleToParse
                            .getElementsByTagName("Distance_From_Threshold")
                            .item(0)
                            .getTextContent()
            );
            logger.info("Obstacle distance from threshold = "+distFromThreshold);
            airport.getRunways().get(i).runwayObstacle.obstacleDesignatorProperty().set(obstacleDesignator);
            airport.getRunways().get(i).runwayObstacle.heightProperty().set(obstacleHeight);
            airport.getRunways().get(i).runwayObstacle.widthProperty().set(obstacleWidth);
            airport.getRunways().get(i).runwayObstacle.lengthProperty().set(obstacleLength);
            airport.getRunways().get(i).runwayObstacle.distFromThresholdProperty().set(distFromThreshold);
            logger.info("Obstacle added to runway");

        }


        return airport;
    }

    /**
     * Gets properties.
     *
     * @param element the element
     */
    private static void getProperties(Element element) {

        designator = element.getElementsByTagName("Designator").item(0).getTextContent();
        logger.info("Designator = "+designator);

        tora = Double.parseDouble(element.getElementsByTagName("TORA").item(0).getTextContent());
        logger.info("TORA = "+ tora +"m");

        toda = Double.parseDouble(element.getElementsByTagName("TODA").item(0).getTextContent());
        logger.info("TODA = "+ toda +"m");

        asda = Double.parseDouble(element.getElementsByTagName("ASDA").item(0).getTextContent());
        logger.info("ASDA = "+ asda +"m");

        lda = Double.parseDouble(element.getElementsByTagName("LDA").item(0).getTextContent());
        logger.info("LDA = "+ lda +"m");

        // May not need importing
        clearway = Double.parseDouble(element.getElementsByTagName("Clearway").item(0).getTextContent());
        //logger.info("Clearway = "+rightClearway+"m");

        stopway = Double.parseDouble(element.getElementsByTagName("Stopway").item(0).getTextContent());
        //logger.info("Stopway = "+rightStopway+"m");

        dispThreshold = Double.parseDouble(element.getElementsByTagName("Displacement_Threshold").item(0).getTextContent());
        //logger.info("Displaced threshold = "+dispThreshold);
    }

    /**
     * File format failed boolean.
     *
     * @param file       the file
     * @param schemaType the schema type
     * @return the boolean
     */
    private static boolean fileFormatFailed(File file, SchemaType schemaType) {
        if (schemaType.equals(SchemaType.AIRPORT_OBSTACLE) && airportObstacleValidator == null) {
            airportObstacleValidator = createSchemaValidator("src/main/resources/XML/AirportOb.xsd");
            logger.info("Schema validator created for airport");
        } else if (schemaType.equals(SchemaType.OBSTACLE) && obstacleValidator == null) {
            obstacleValidator = createSchemaValidator("src/main/resources/XML/Obstacle.xsd");
            logger.info("Schema validator created for obstacle");
        } else if (schemaType.equals(SchemaType.AIRPORT) && airportValidator == null) {
            airportValidator = createSchemaValidator("src/main/resources/XML/Airport.xsd");
            logger.info("Schema validator created for airport");
        }
        // File paths are only hard coded for now to make ease of testing
        try {
            Source testFileSource = new StreamSource(file);
            switch (schemaType) {
                case AIRPORT_OBSTACLE -> airportObstacleValidator.validate(testFileSource);
                case OBSTACLE -> obstacleValidator.validate(testFileSource);
                case AIRPORT -> airportValidator.validate(testFileSource);
            }
        } catch (IOException | SAXException e) {
            logger.error(e.getMessage());
            logger.warn("Handle Error - file does not fit schema or file could not be read");
            // TODO: Handle Error
            return true;
        }
        logger.info(
                "File validated for "
                        + schemaType.label
                        + " import");
        return false;
    }

    /**
     * Create schema validator validator.
     *
     * @param schemaFilePathFromResources the schema file path from resources
     * @return the validator
     */
    public static Validator createSchemaValidator(String schemaFilePathFromResources) {
        Validator validator;
        File schemaFile = new File(schemaFilePathFromResources);
        try {
            Schema schema = SCHEMA_FACTORY.newSchema(schemaFile);
            validator = schema.newValidator();
        } catch (SAXException e) {
            logger.error(e.getMessage());
            logger.warn("Handle Error - issue with schema file");
            // TODO: Handle Error
            throw new RuntimeException(e);
        }
        return validator;
    }

    /**
     * Create builder.
     */
    private static void createBuilder() {
        try {
            builder = BUILDER_FACTORY.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("Failed to create parser: "+e.getMessage());
            logger.warn("Handle Error - issue with parser config");
            // TODO: Handle Error
            throw new RuntimeException(e);
        }
    }

    private static boolean exportBreakdownToTxt (
            String leftToraHeader,
            String leftTora,
            String rightToraHeader,
            String rightTora,
            String leftTodaHeader,
            String leftToda,
            String rightTodaHeader,
            String rightToda,
            String leftAsdaHeader,
            String leftAsda,
            String rightAsdaHeader,
            String rightAsda,
            String leftLdaHeader,
            String leftLda,
            String rightLdaHeader,
            String rightLda,
            File fileToExportTo
    ) {
        //TODO: update
        String[] dataComponents = new String[] {
                leftToraHeader,
                leftTora,
                rightToraHeader,
                rightTora,
                leftTodaHeader,
                leftToda,
                rightTodaHeader,
                rightToda,
                leftAsdaHeader,
                leftAsda,
                rightAsdaHeader,
                rightAsda,
                leftLdaHeader,
                leftLda,
                rightLdaHeader,
                rightLda
        };

        String data = "Calculation Breakdowns:\n" + String.join("\n", dataComponents)+"\nEnd of Calculation Breakdowns";

        boolean success = true;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileToExportTo));
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            success = false;
        }
        return success;
    }
}
