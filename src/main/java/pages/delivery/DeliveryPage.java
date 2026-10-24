package pages.delivery;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;

import java.util.List;

public class DeliveryPage extends BasePage {

    @FindBy(css = "[data-testid='checkout-item']")
    private List<WebElement> productCards;

    @FindBy(css = "[data-testid='checkout-total']")
    private WebElement totalPriceInDelivery;

    public DeliveryPage(WebDriver driver) {
        super(driver);
    }

    @Step("Проверяем что на странице доставки отображаются товары")
    public boolean isDeliveryPageLoaded(){
        wait.until(ExpectedConditions.visibilityOfAllElements(productCards));
        return isElementDisplayed(productCards.get(0));
    }

    @Step("Получаем итоговую стоимость заказа в доставке")
    public double getTotalPriceInDelivery() {
        String priceText = totalPriceInDelivery.getText();
        return Double.parseDouble(priceText.replaceAll("[^0-9.]", ""));
    }
}
