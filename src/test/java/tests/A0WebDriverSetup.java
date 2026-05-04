package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.WebPage;
import java.util.logging.Logger;

public class A0WebDriverSetup {
    protected static final Logger logger = Logger.getLogger(A0WebDriverSetup.class.getName());
    protected static WebDriver driver;
    protected static WebPage webPage;

    @BeforeEach
    void setUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        webPage = new WebPage(driver);
    }


    @AfterEach
    void tearDown() { if (driver != null) { driver.quit(); } }
}