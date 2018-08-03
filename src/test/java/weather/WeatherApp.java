package weather;

import static org.junit.Assert.assertTrue;

import org.junit.*;
import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
import java.util.concurrent.TimeUnit;


public class WeatherApp
{
    WebDriver accuDriver;
    WebDriver newsDriver;
    AccuweatherTester accuweather;
    News24Tester news24;
    DocumentBuilderFactory docFactory;
    DocumentBuilder docBuilder;
    static Document doc;
    Element rootElement;
    static Transformer transformer;
    static DOMSource source;

    public void compareDay(int day)
    {
        // city elements
        Element city = doc.createElement("city"+day);
        rootElement.appendChild(city);

        // day elements
        Element eDay = doc.createElement("city"+day);
        city.appendChild(eDay);

        String accuHigh = accuweather.getHigh(day);
        String newsHigh = news24.getHigh(day);

        String accuLow = accuweather.getLow(day);
        String newsLow = news24.getLow(day);

        // max elements
        Element max1 = doc.createElement("max1");
        max1.appendChild(doc.createTextNode(accuHigh));
        eDay.appendChild(max1);
        Element max2 = doc.createElement("max2");
        max2.appendChild(doc.createTextNode(newsHigh));
        eDay.appendChild(max2);

        // resultMax elements
        Element resultMax = doc.createElement("result");
        resultMax.appendChild(doc.createTextNode("" + accuHigh.equals(newsHigh)));
        eDay.appendChild(resultMax);

        // min elements
        Element min1 = doc.createElement("min1");
        min1.appendChild(doc.createTextNode(accuLow));
        eDay.appendChild(min1);
        Element min2 = doc.createElement("min2");
        min2.appendChild(doc.createTextNode(newsLow));
        eDay.appendChild(min2);

        // resultMin elements
        Element resultMin = doc.createElement("result");
        resultMin.appendChild(doc.createTextNode("" + accuHigh.equals(newsHigh)));
        eDay.appendChild(resultMin);

        Assert.assertTrue(accuHigh.equals(newsHigh));
        Assert.assertTrue(accuLow.equals(newsLow));
    }

    @Test
    public void compareDay1Test()
    {
        compareDay(1);
    }

    @Test
    public void compareDay2Test()
    {
        compareDay(2);
    }

    @Test
    public void compareDay3Test()
    {
        compareDay(3);
    }

    @Test
    public void compareDay4Test()
    {
        compareDay(4);
    }

    @Before
    public void setUpBeforeTest()
    {
        System.setProperty("webdriver.chrome.driver","C:/Drivers/chromedriver.exe");
        accuDriver = new ChromeDriver();

        accuDriver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
        String weatherURL = "https://www.accuweather.com/en/za/pretoria/305449/daily-weather-forecast/305449";
        accuDriver.get(weatherURL);

        accuweather = new AccuweatherTester(accuDriver);

        newsDriver = new ChromeDriver();

        newsDriver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        String newsURL = "http://weather.news24.com/sa/pretoria";
        newsDriver.get(newsURL);

        news24 = new News24Tester(newsDriver);

        accuweather.setUpRows();

        try
        {
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();

            // root elements
            doc = docBuilder.newDocument();
            rootElement = doc.createElement("Temperature");
            doc.appendChild(rootElement);
        }
        catch (ParserConfigurationException pce)
        {
            pce.printStackTrace();
        }
    }

    @After
    public void tearDownTest()
    {
        accuDriver.close();
        newsDriver.close();
    }

    @AfterAll
    public static void tearDownTests()
    {
        try
        {
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            source = new DOMSource(doc);
            StreamResult result = new StreamResult(System.out);

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");
        }
        catch (TransformerException tfe)
        {
            tfe.printStackTrace();
        }
    }
}
