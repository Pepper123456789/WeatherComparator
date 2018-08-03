package weather;

import static org.junit.Assert.assertTrue;

import org.junit.*;
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
    String[] arrMinAcu = new String[4];
    String[] arrMaxAcu = new String[4];
    String[] arrMinw24 = new String[4];
    String[] arrMaxw24 = new String[4];
    boolean[] resultsMin = new boolean[4];
    boolean[] resultsMax = new boolean[4];

    public void compareDay(int day)
    {
        String accuHigh = accuweather.getHigh(day);
        String newsHigh = news24.getHigh(day);

        String accuLow = accuweather.getLow(day);
        String newsLow = news24.getLow(day);

        arrMinAcu[day-1] = accuLow;
        arrMaxAcu[day-1] = accuHigh;
        arrMinw24[day-1] = newsLow;
        arrMaxw24[day-1] = newsHigh;
        resultsMin[day-1] = accuHigh.equals(newsHigh);
        resultsMin[day-1] = accuLow.equals(newsLow);

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
        try
        {
            XMLCreator.createXML(arrMinAcu, arrMaxAcu, arrMinw24, arrMaxw24, resultsMin, resultsMax);
        }
        catch (ParserConfigurationException pce)
        {
            pce.printStackTrace();
        }
        catch (TransformerException tfe)
        {
            tfe.printStackTrace();
        }
    }

    @Before
    public void setUpBeforeTest()
    {
        System.setProperty("webdriver.chrome.driver","C:/Drivers/chromedriver.exe");
        accuDriver = new ChromeDriver();
        newsDriver = new ChromeDriver();

        accuDriver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
        String weatherURL = "https://www.accuweather.com/en/za/pretoria/305449/daily-weather-forecast/305449";
        accuDriver.get(weatherURL);

        newsDriver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        String newsURL = "http://weather.news24.com/sa/pretoria";
        newsDriver.get(newsURL);

        accuweather = new AccuweatherTester(accuDriver);
        news24 = new News24Tester(newsDriver);

        accuweather.setUpRows();
    }

    @After
    public void tearDownTest()
    {
        accuDriver.close();
        newsDriver.close();
    }
}
