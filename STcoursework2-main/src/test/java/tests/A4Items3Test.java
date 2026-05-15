package tests;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.logging.Level;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;


public class A4Items3Test extends A0WebDriverSetup {

    @ParameterizedTest
    @CsvFileSource(resources = "/A4testdata.csv", numLinesToSkip = 1)
    void testAddToCart(String username, String password, String expectedNumber) {
        logger.log(Level.INFO, "Starting testAddToCart: " + expectedNumber);

        try {
            //Login steps
            webPage.enterUsername(username);
            webPage.enterPassword(password);
            webPage.clickSubmit();
            //Add to cart steps in item pages after remove them
            webPage.clickAddToCart1();
            webPage.clickAddToCart2();
            //Add to cart through Item windows after remove them
            webPage.clickItemLink1();
            webPage.addToCartButtons();
            webPage.removeFromCart();

            webPage.goBack();

            webPage.clickItemLink2();
            webPage.addToCartButtons();
            webPage.removeFromCart();

            webPage.goBack();
            //Remove from cart through item page
            webPage.clickRemoveMainItem1();
            webPage.clickRemoveMainItem2();


            //get number of items in cart
            String actualNumber = webPage.getCartBadgeText();

            Assertions.assertEquals(expectedNumber, actualNumber, "Expected " + expectedNumber + " items in cart, but got " + actualNumber);
            takeScreenshot("PASS_" + expectedNumber);
            logger.log(Level.INFO, "success: expectedNumber={0}", expectedNumber);

        } catch (AssertionError e) {
            takeScreenshot("FAIL_" + expectedNumber);
            logger.log(Level.INFO, "failed: expectedNumber={0}", expectedNumber);
            throw e;
        }
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