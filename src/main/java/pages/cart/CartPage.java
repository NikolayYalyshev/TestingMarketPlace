package pages.cart;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;
import pages.delivery.DeliveryPage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    @FindBy(css = "[data-testid='cart-item']")
    private List<WebElement> cartElements;

    @FindBy(css = "[data-testid='clear-cart-button']")
    private WebElement buttonClear;

    @FindBy(css = "[data-testid='promo-input']")
    private WebElement promo;

    @FindBy(css = "[data-testid='promo-apply']")
    private WebElement buttonPromoApply;

    @FindBy(css = "[data-testid='applied-promo']")
    private WebElement appliedPromo;

    @FindBy(css = "[data-testid='checkout-button']")
    private WebElement buttonCheckout;

    @FindBy(css = "[data-testid='cart-total']")
    private WebElement totalPrice;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Проверяем что на странице корзина отображается элемент")
    public boolean isBasketPageLoaded(){
        return isElementDisplayed(buttonCheckout);
    }

    @Step("Получаем все названия товаров, которые добавили в корзину")
    public List<String> getProductNamesInCart() {
        return cartElements.stream()
                .map(CartItemComponent::new)
                .map(CartItemComponent::getName)
                .collect(Collectors.toList());
    }

    @Step("Получаем список товаров в корзине")
    public List<CartItemComponent> productsInCart() {
        if (cartElements.isEmpty()) {
            return new ArrayList<>();
        }

        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(cartElements)));
        return cartElements.stream()
                .map(CartItemComponent::new)
                .collect(Collectors.toList());
       }

    @Step("Удаляем товары через карточки товаров в корзине")
    public void removeCart() {
        List<WebElement> removeButtons = driver.findElements(By.cssSelector("[data-testid='remove-item']"));
        while (!removeButtons.isEmpty()) {
            try {
                removeButtons.get(0).click();

                int previousSize = removeButtons.size();
                wait.until(ExpectedConditions.numberOfElementsToBeLessThan(
                        By.cssSelector("[data-testid='remove-item']"), previousSize
                ));

            } catch (org.openqa.selenium.StaleElementReferenceException e) {
            }

            removeButtons = driver.findElements(By.cssSelector("[data-testid='remove-item']"));
        }
    }

    @Step("Очищаем корзину полностью")
    public void clickButtonClear(){
        click(buttonClear);
    }

    @Step("Проверяем, что корзина стала пустой")
    public boolean waitForCartToBeEmpty() {
        try {
            wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("[data-testid='cart-item']"), 0));
            return true;
        } catch ( TimeoutException e) {
            return false;
        }
    }

    @Step("Вводим промокод: {promocode}")
    public void enterPromoCode(String promocode){
        addValue(promo, promocode);
    }

    @Step("Применяем промокод")
    public void clickPromoApply(){
        click(buttonPromoApply);
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(totalPrice)));
    }

    @Step("Проверяем что промокод применился")
    public String getAttributeAppliedPromo(){
        wait.until(ExpectedConditions.visibilityOf(appliedPromo));
        return appliedPromo.getAttribute("data-promo");
    }

    @Step("Проверяем что промокод не применился")
    public boolean promoMessageInvisible(){
        try {
           return wait.until(ExpectedConditions.invisibilityOf(appliedPromo));
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Нажимаем кнопку оформления заказа")
    public DeliveryPage clickCheckoutButton(){
        click(buttonCheckout);
        return new DeliveryPage(driver);
    }

    @Step("Проверяем, активна ли кнопка оформления заказа")
    public boolean isCheckoutButtonEnabled() {
        return buttonCheckout.isEnabled();
    }

    @Step("Получаем итоговую стоимость заказа в корзине")
    public double getTotalPriceInCart() {
        String priceText = totalPrice.getText();
        return Double.parseDouble(priceText.replaceAll("[^0-9.]", ""));
    }
}
