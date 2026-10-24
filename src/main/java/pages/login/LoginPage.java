package pages.login;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;
import pages.catalog.CatalogPage;

public class LoginPage extends BasePage {

    @FindBy(id = "email")
    private WebElement userEmail;

    @FindBy(id = "password")
    private WebElement userPassword;

    @FindBy(id = "name")
    private WebElement userName;

    @FindBy(css = "[data-testid='auth-submit']")
    private WebElement buttonLogin;

    @FindBy(css = "[data-testid='tab-register']")
    private WebElement buttonRegister;

    @FindBy(css = "[data-testid='auth-error']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Вводим почту: {email}")
    public void enterUserEmail(String email){
        addValue(userEmail, email);
    }

    @Step("Вводим пароль: {password}")
    public void enterUserPassword (String pass){
        addValue(userPassword, pass);
    }

    @Step("Вводим имя: {name}")
    public void enterUserName(String name){
        addValue(userName, name);
    }

    public CatalogPage clickLogin(){
        String buttonText = buttonLogin.getText();
        Allure.step("Жмем кнопку '" + buttonText + "'", () -> {
            click(buttonLogin);
        });
        return new CatalogPage(driver);
    }

    @Step("Вход на сайт через почту: {email}")
    public CatalogPage authorizationSite(String email, String pass){
        enterUserEmail(email);
        enterUserPassword(pass);
        return clickLogin();
    }

    @Step("Регистрация на сайте")
    public CatalogPage registrationSite(String name, String email, String pass){
        click(buttonRegister);
        enterUserName(name);
        enterUserEmail(email);
        enterUserPassword(pass);
        return clickLogin();
    }

    @Step("Попытка входа на сайт с некорректными данными")
    public boolean authorizationSiteFail(String email, String pass){
        enterUserEmail(email);
        enterUserPassword(pass);
        click(buttonLogin);
        return isElementDisplayed(errorMessage);
    }


}
