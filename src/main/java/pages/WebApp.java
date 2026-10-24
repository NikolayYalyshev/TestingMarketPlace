package pages;

import org.openqa.selenium.WebDriver;
import pages.cart.CartPage;
import pages.catalog.CatalogPage;
import pages.delivery.DeliveryPage;
import pages.login.LoginPage;

public class WebApp {

    private WebDriver driver;

    private LoginPage loginPage;
    private CatalogPage catalogPage;

    private CartPage cartPage;

    private DeliveryPage deliveryPage;

    public WebApp(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage getLoginPage() {
        if (loginPage == null){
            loginPage = new LoginPage(driver);
        }
        return loginPage;
    }

    public CatalogPage getCatalogPage() {
        if (catalogPage == null){
            catalogPage = new CatalogPage(driver);
        }
        return catalogPage;
    }

    public CartPage getCartPage() {
        if (cartPage == null){
            cartPage = new CartPage(driver);
        }
        return cartPage;
    }

    public DeliveryPage getDeliveryPage() {
        if (deliveryPage == null){
            deliveryPage = new DeliveryPage(driver);
        }
        return deliveryPage;
    }
}
