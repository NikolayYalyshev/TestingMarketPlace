package pages.catalog;

import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import pages.BasePage;
import pages.cart.CartPage;

import java.util.List;
import java.util.stream.Collectors;

public class CatalogPage extends BasePage {

    private FilterComponent filterComponent;

    @FindBy(css = "[data-testid='filter-category-all']")
    private WebElement catalog;

    @FindBy(css = "[data-testid='product-card']")
    private List<WebElement> productElements;

    @FindBy(css = "[data-testid='cart-badge']")
    private WebElement cartCount;

    @FindBy(css = "[data-testid='nav-cart']")
    private WebElement cartPage;


    public CatalogPage(WebDriver driver) {
        super(driver);
        this.filterComponent = new FilterComponent(driver);
    }

    @Step("Проверяем что на странице каталог отображается элемент")
    public boolean isCatalogPageLoaded(){
        return isElementDisplayed(catalog);
    }

    @Step("Получаем первый товар из каталога")
    public ProductCardComponent getFirstProductCard(){
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(productElements)));
        if(productElements.isEmpty()){
            throw new IllegalStateException("Каталог пуст! Нет карточек товаров");
        }
        return new ProductCardComponent(productElements.get(0));
    }

    @Step("Получаем количество товара в корзине")
    public String getCartCounterText() {
        wait.until(ExpectedConditions.not(
                ExpectedConditions.textToBePresentInElement(cartCount, "0")
        ));
        return cartCount.getText();
    }

    @Step("Выбираем категорию {category}")
    public void clickCategory(String category){
        click(filterComponent.getCategory(category));
    }

    @Step("Выбираем бренд {brand}")
    public void clickBrand(String brand){
        click(filterComponent.getBrand(brand));
    }

    @Step("Устанавливаем диапазон цен от {minPrice} до {maxPrice}")
    public void setPriceRange(String minPrice, String maxPrice){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = "arguments[0].value = arguments[1]; " +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true })); " +
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));";

        js.executeScript(script, filterComponent.getMinPrice(), minPrice);
        js.executeScript(script, filterComponent.getMaxPrice(), maxPrice);
    }

    @Step("Устанавливаем признак 'Только в наличии'")
    public void clickCheckBoxStock(){
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        } catch (Exception e) {
        }
        click(filterComponent.getStockOnly());
    }

    @Step("Получаем список товаров после фильтрации")
    public List<ProductCardComponent> productsAfterFiltering() {
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(productElements)));
        List<ProductCardComponent> productCards = productElements.stream()
                .map(ProductCardComponent::new)
                .collect(Collectors.toList());
        return productCards;
    }

    @Step("Выбираем сортировку из фильтра")
    public void selectSortingByValue(String value) {
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        } catch (Exception e) {
        }
        wait.until(ExpectedConditions.elementToBeClickable(filterComponent.getSelectSorted()));
       new Select(filterComponent.getSelectSorted()).selectByValue(value);
    }

    @Step("Скролим страницу для ленивой выгрузки img")
    public void scrollPageToBottom(){
        try {
            long totalHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
            long currentScroll = 0;
            int scrollStep = 400;
            while (currentScroll < totalHeight) {
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, " + scrollStep + ");");
                currentScroll += scrollStep;
            }
            if (!productElements.isEmpty()) {
                WebElement firstProduct = productElements.get(0);
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", firstProduct
                );
                // Даем полсекунды на плавное завершение скролла к элементу
            }
        } catch (Exception e) {
        }
    }

    @Step("Переход на страницу корзины")
    public CartPage goToCartPage() {
        click(cartPage);
        return new CartPage(driver);
    }

    @Step("Выбираем Все категории")
    public void clickAllCategory(){
        click(filterComponent.getAllCategory());
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(productElements)));
    }

    @Step("Выбираем все бренды")
    public void clickAllBrand(){
        click(filterComponent.getAllBrand());
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(productElements)));
    }
}
