package weather;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class News24Tester
{
    WebDriver driver;

    public News24Tester(WebDriver driver)
    {
        this.driver = driver;
    }

    public String getHigh(int day)
    {
        day+=2;
        WebElement element = driver.findElement(By.xpath("//*[@id='div7DayForecast']/div/div/div["+day+"]"));

        String thing = element.getText();
        thing =  thing.substring(thing.lastIndexOf('-') + 2, thing.length()-2);
        return thing;
    }

    public String getLow(int day)
    {
        day+=2;
        WebElement element = driver.findElement(By.xpath("//*[@id='div7DayForecast']/div/div/div["+day+"]"));

        String thing = element.getText();
        thing = thing.substring(thing.lastIndexOf('y') + 3, thing.indexOf('C')-1);
        return thing;
    }
}
