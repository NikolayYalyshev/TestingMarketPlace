package pages.cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CartItemComponent {

    private final WebElement rootElement;

    public CartItemComponent(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public String getName() {
        return rootElement.findElement(By.cssSelector("[data-testid='cart-item-name']")).getText();
    }

}
