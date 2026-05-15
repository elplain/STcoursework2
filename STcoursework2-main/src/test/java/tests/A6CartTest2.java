package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.logging.Level;

public class A6CartTest2 extends A0WebDriverSetup {

    @ParameterizedTest
    @CsvFileSource(resources = "/A5-6testdata.csv", numLinesToSkip = 1)
    void testLogin(String username, String password, String shouldSucceed, String fn, String ln, String postccode) {
        logger.log(Level.INFO, "Starting testLogin: " + shouldSucceed);

        boolean isSuccess = shouldSucceed.equalsIgnoreCase("true");

        try {
            //Login steps
            webPage.enterUsername(username);
            webPage.enterPassword(password);
            webPage.clickSubmit();
            //Add to cart steps in item pages
            webPage.clickAddToCart1();
            webPage.clickAddToCart2();
            //Add to cart through Item windows
            webPage.clickMoveToCart();
            //print cart contents
            webPage.printCartContents();
            //back shopping button
            webPage.clickBackShopping();
            //move back to cart
            webPage.clickMoveToCart();
            //checkout steps
            webPage.clickCheckout();
            webPage.clickCancelButton();
            webPage.clickCheckout();

            webPage.enterFirstName(fn);
            webPage.enterLastName(ln);
            webPage.enterPostalCode(postccode);
            webPage.clickContinue();
            webPage.clickCancelButton();
            //checkout process again
            webPage.clickMoveToCart();
            webPage.clickCheckout();
            webPage.clickContinue();
            webPage.enterFirstName(fn);
            webPage.enterLastName(ln);
            webPage.enterPostalCode(postccode);
            webPage.clickContinue();
            webPage.printTotalPrice();
            webPage.clickFinish();
            //print total price

            //finish shopping
            webPage.clickBackHome();
            //get url

            String url = webPage.getCurrentUrl();

            if (isSuccess) {
                Assertions.assertTrue(url.contains("/inventory.html"));
                logger.log(Level.INFO, "Items bought successfully");
            } else {
                logger.log(Level.INFO, "failed to buy items");
            }
            takeScreenshot("PASS_" + shouldSucceed);
            logger.log(Level.INFO, "success: shouldSucceed={0}", shouldSucceed);

        } catch (AssertionError e) {
            takeScreenshot("FAIL_" + shouldSucceed);
            logger.log(Level.INFO, "failed: shouldSucceed={0}", shouldSucceed);
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
