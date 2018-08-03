package weather;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AccuweatherTester
{
    WebDriver driver;
    List<WebElement> list;

    public AccuweatherTester(WebDriver driver)
    {
        this.driver = driver;
    }

    public void setUpRows()
    {
        list = driver.findElements(By.xpath("//*[@id=\"panel-main\"]/div[2]/div/div/div[2]/ul/li"));

    }

    public String removeDegreeSymbol(String str) {
        return str.substring(0, str.length() - 1);
    }

    public String removeDegreeSymbolAndSlash(String str) {
        return str.substring(1, str.length() - 1);
    }

    public String getHigh(int day)
    {
        String thing = list.get(day).findElement(By.className("large-temp")).getAttribute("innerHTML");

        return removeDegreeSymbol(thing);
    }

    public String getLow(int day)
    {
        String thing = list.get(day).findElement(By.className("small-temp")).getAttribute("innerHTML");

        return removeDegreeSymbolAndSlash(thing);
    }
}
