package comp2211.seg.ProcessDataModel;


import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.OutputStream;

public class FileHandler {
    private static final Logger logger = LogManager.getLogger(FileHandler.class);
//    private final String airportTag = "airport";


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
            rootElement.appendChild(name);

//            Element city = document.createElement("city");
//            city.appendChild(document.createTextNode(airport.getCity()));
//            rootElement.appendChild(city);

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
                appendElementWithNewline(runwayElement, "Obstacle", Boolean.toString(runway.isHasRunwayObstacle()), document);

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

    public static void writeXML (Document doc, OutputStream output)
        throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

//        transformer.setOutputProperties(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);
    }

    private static void appendElementWithNewline(Element parent, String elementName, String textContent, Document document) {
        Element element = document.createElement(elementName);
        element.appendChild(document.createTextNode(textContent));
        parent.appendChild(element);
        parent.appendChild(document.createTextNode("\n"));
    }


}
