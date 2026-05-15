package tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

public class A8TestSortDropdown extends A0WebDriverSetup {

    @ParameterizedTest
    @CsvFileSource(resources = "/A8-9testdata.csv", numLinesToSkip = 1)
    void testSortDropdownContents(String username, String password) {
        //Login steps
        webPage.enterUsername(username);
        webPage.enterPassword(password);
        webPage.clickSubmit();

        Select dropdown = new Select(
                driver.findElement(By.className("product_sort_container"))
        );

        List<String> expected = List.of(
                "Name (A to Z)",
                "Name (Z to A)",
                "Price (low to high)",
                "Price (high to low)"
        );

        List<String> actual = dropdown.getOptions()
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