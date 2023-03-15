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


    public boolean exportAirport (File file, Airport airport) {

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
            lat.appendChild(document.createTextNode(airport.getLatitude().toString()));
            rootElement.appendChild(lat);

            Element longitude = document.createElement("Longitude");
            longitude.appendChild(document.createTextNode(airport.getLongitude().toString()));
            rootElement.appendChild(longitude);

            Element runways = document.createElement("Runways");
            rootElement.appendChild(runways);

            for (Runway runwayy : airport.getRunways()) {

                Element runway = document.createElement("Runway");
                runways.appendChild(runway);

                Element designator = document.createElement("Designator");
                designator.appendChild(document.createTextNode(runwayy.getRunwayDesignator()));
                runways.appendChild(designator);

                Element right = document.createElement("Right Properties");
                runways.appendChild(right);

                Element tora = document.createElement("TORA");
                tora.appendChild(document.createTextNode(runwayy.getRightTora()));
                right.appendChild(tora);

                Element toda = document.createElement("TODA");
                toda.appendChild(document.createTextNode(runwayy.getRightToda()));
                right.appendChild(toda);

                Element asda = document.createElement("ASDA");
                asda.appendChild(document.createTextNode(runwayy.getRightAsda()));
                right.appendChild(asda);

                Element lda = document.createElement("LDA");
                lda.appendChild(document.createTextNode(runwayy.getRightLda()));
                right.appendChild(lda);

                Element clearway = document.createElement("Clearway");
                clearway.appendChild(document.createTextNode(runwayy.getClearwayRight()));
                right.appendChild(clearway);

                Element stopway = document.createElement("Stopway");
                stopway.appendChild(document.createTextNode(runwayy.getClearwayRight()));
                right.appendChild(stopway);

                Element displacementThreshold = document.createElement("Displacement Threshold");
                displacementThreshold.appendChild(document.createTextNode(runwayy.getDispThresholdRight()));
                right.appendChild(displacementThreshold);

                Element left = document.createElement("Left Properties");
                runways.appendChild(left);

                Element toraL = document.createElement("TORA");
                toraL.appendChild(document.createTextNode(runwayy.getLeftTora()));
                left.appendChild(toraL);

                Element todaL = document.createElement("TODA");
                todaL.appendChild(document.createTextNode(runwayy.getLeftToda()));
                left.appendChild(todaL);

                Element asdaL = document.createElement("ASDA");
                asdaL.appendChild(document.createTextNode(runwayy.getLeftAsda()));
                left.appendChild(asdaL);

                Element ldaL = document.createElement("LDA");
                ldaL.appendChild(document.createTextNode(runwayy.getLeftLda()));
                left.appendChild(ldaL);

                Element clearwayL = document.createElement("Clearway");
                clearwayL.appendChild(document.createTextNode(runwayy.getClearwayLeft()));
                left.appendChild(clearwayL);

                Element stopwayL = document.createElement("Stopway");
                stopwayL.appendChild(document.createTextNode(runwayy.getStopwayLeft()));
                left.appendChild(stopwayL);

                Element displacementThresholdL = document.createElement("Displacement Threshold");
                displacementThresholdL.appendChild(document.createTextNode(runwayy.getDispThresholdLeft()));
                left.appendChild(displacementThresholdL);


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


}
