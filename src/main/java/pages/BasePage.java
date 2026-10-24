package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;

    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        PageFactory.initElements(driver, this);
    }

    protected void click(WebElement element){
        wait.until(ExpectedConditions.elementToBeClickable(element));
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});",
                    element);
        } catch (Exception e) {
            // Игнорируем, если JS-скролл по какой-то причине не сработал, чтобы не валить тест
        }
        element.click();
    }

    protected void addValue(WebElement element, String text){
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    protected boolean isElementDisplayed(WebElement element){
        try{
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        }catch(Exception e){
            return false;
        }
    }
}
