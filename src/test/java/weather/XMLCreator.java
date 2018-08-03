package weather;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLCreator
{

    public static void createXML(String[] arrMinAcu,String[] arrMaxAcu,String[] arrMinw24,String[] arrMaxw24, boolean[] resultsMin, boolean[] resultsMax) throws ParserConfigurationException, TransformerException {
        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("weather");
            doc.appendChild(rootElement);

            for (int x = 1;x<5;x++)
            {
                Element day = doc.createElement("day");
                rootElement.appendChild(day);

                Attr attr = doc.createAttribute("id");
                attr.setValue(""+x);
                day.setAttributeNode(attr);

                Element min = doc.createElement("min");
                day.appendChild(min);

                Element minAcu = doc.createElement("minAcu");
                minAcu.appendChild(doc.createTextNode(arrMinAcu[x-1]));
                min.appendChild(minAcu);

                Element minw24 = doc.createElement("minw24");
                minw24.appendChild(doc.createTextNode(arrMinw24[x-1]));
                min.appendChild(minw24);

                Element resultMin = doc.createElement("resultMin");
                resultMin.appendChild(doc.createTextNode(""+resultsMin[x-1]));
                min.appendChild(resultMin);

                Element max = doc.createElement("max");
                day.appendChild(max);

                Element maxAcu = doc.createElement("maxAcu");
                maxAcu.appendChild(doc.createTextNode(arrMaxAcu[x-1]));
                max.appendChild(maxAcu);

                Element maxw24 = doc.createElement("maxw24");
                maxw24.appendChild(doc.createTextNode(arrMaxw24[x-1]));
                max.appendChild(maxw24);

                Element resultMax = doc.createElement("resultMin");
                resultMax.appendChild(doc.createTextNode(""+resultsMax[x-1]));
                max.appendChild(resultMax);
            }



            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("C:\\Users\\Student01\\Desktop\\Testerfile.xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

}
