package pages.catalog;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FilterComponent {

    private WebDriver driver;

    public FilterComponent(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getCategory(String category){
        return driver.findElement(By.xpath(
                "//button[@data-filter-type='category' and .//span[text()='"+category+"']]"));
    }

    public WebElement getBrand(String brand){
        return driver.findElement(By.xpath(
        "//button[@data-filter-type='brand' and .//span[text()='"+brand+"']]"));
    }

    public WebElement getMinPrice(){
        return driver.findElement(By.cssSelector("[data-testid='price-min']"));
    }

    public WebElement getMaxPrice(){
        return driver.findElement(By.cssSelector("[data-testid='price-max']"));
    }

    public WebElement getStockOnly(){
        return driver.findElement(By.cssSelector("[data-testid='in-stock-checkbox']"));
    }

    public WebElement getSelectSorted(){
        return driver.findElement(By.cssSelector("[data-testid='catalog-sort']"));
    }

    public WebElement getAllCategory(){
        return driver.findElement(By.cssSelector("[data-testid='filter-category-all']"));
    }

    public WebElement getAllBrand(){
        return driver.findElement(By.cssSelector("[data-testid='filter-all']"));
    }
}
