package pages.catalog;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

public class ProductCardComponent {

    private WebElement rootElement;

    public ProductCardComponent(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public void clickBuyButton(){
        rootElement.findElement(By.cssSelector("[data-testid='add-to-cart']")).click();
    }

    public int getStockAmount() {
        WebElement stockElement = rootElement.findElement(By.cssSelector("[data-testid='product-stock']"));
        String stockValueRaw = stockElement.getAttribute("data-stock");
        return Integer.parseInt(stockValueRaw);
    }

    public double getPrice() {
        WebElement priceElement = rootElement.findElement(By.cssSelector("[data-testid='product-price']"));
        String priceTextRaw = priceElement.getText();

        // Очищаем строку от лишних символов (пробелы, валюта), оставляя только цифры и точку
        String cleanPrice = priceTextRaw.replaceAll("[^0-9.]", "");
        return Double.parseDouble(cleanPrice);
    }

    public String getName() {
        return rootElement.findElement(By.cssSelector("[data-testid='product-name']")).getText();
    }

    public boolean isImageValid() {
        try {
            WebElement img = rootElement.findElement(By.cssSelector("[data-testid='product-image']"));

            String src = img.getAttribute("src");
            if (src == null || src.isEmpty()) {
                return false;
            }
            Object result = ((JavascriptExecutor) ((RemoteWebElement) rootElement).getWrappedDriver())
                    .executeScript(
                            "return arguments[0].complete && " +
                                    "typeof arguments[0].naturalWidth != 'undefined' && " +
                                    "arguments[0].naturalWidth > 0",
                            img
                    );

            return (Boolean) result;
        } catch (Exception e) {
            return false;
        }
    }
}
