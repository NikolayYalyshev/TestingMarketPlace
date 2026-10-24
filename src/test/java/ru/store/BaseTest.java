package ru.store;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.WebApp;

public class BaseTest {
    protected WebDriver driver;
    
    protected WebApp objectPageNow;
    @BeforeEach
    public void setUp(){
    System.setProperty("webdriver.chrome.driver",
            "/Users/lena/Desktop/JAVA/testSuite/chromedriver-mac-x64 3/chromedriver");
    driver = new ChromeDriver();
    driver.manage().window().maximize();

    objectPageNow = new WebApp(driver);
    }

    @AfterEach
    public void tearDown(){
        if (driver != null) {
                driver.quit();
            }
    }
}
