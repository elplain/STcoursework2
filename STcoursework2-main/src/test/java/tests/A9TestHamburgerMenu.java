package tests;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

public class A9TestHamburgerMenu extends A0WebDriverSetup {

    @ParameterizedTest
    @CsvFileSource(resources = "/A8-9testdata.csv", numLinesToSkip = 1)
    void A9testSortDropdownContents(String username, String password) {
        //Login steps
        webPage.enterUsername(username);
        webPage.enterPassword(password);
        webPage.clickSubmit();

        driver.findElement(By.id("react-burger-menu-btn")).click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_sidebar_link")));

        List<String> expected = List.of(
                "All Items",
                "About",
                "Logout",
                "Reset App State"
        );

        List<String> actual = driver.findElements(By.className("bm-item"))
                .stream()
                .map(WebElement::getText)
                .toList();

        assertEquals(expected, actual);
        takeScreenshot("PASS");
        logger.log(Level.INFO, "success");
    }
    protected void takeScreenshot(String testName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);
            Path dest = Paths.get("src/test/resources/screenshots/" +
                    testName + "_" + LocalDateTime.now().toString().replace(':', '_') + ".png");
            Files.copy(src.toPath(), dest);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Screenshot failed", e);
        }
    }
}