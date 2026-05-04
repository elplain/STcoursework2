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


public class A1LoginTest extends A0WebDriverSetup {

    @ParameterizedTest
    @CsvFileSource(resources = "/A1testdata.csv", numLinesToSkip = 1)
    void testLogin(String username, String password, String shouldSucceed) {
        logger.log(Level.INFO, "Starting testLogin: " + username);

        boolean isSuccess = shouldSucceed.equalsIgnoreCase("true");

        try {
            //Login steps
            webPage.enterUsername(username);
            webPage.enterPassword(password);
            webPage.clickSubmit();
            //get the current URL after loggin attempt to verify if login was successful
            String url = webPage.getCurrentUrl();

            if (isSuccess) {
                Assertions.assertTrue(url.contains("/inventory.html"));
                logger.log(Level.INFO, "logged in successfully");
            } else {
                Assertions.assertFalse(url.contains("logged-in-successfully"));
                logger.log(Level.INFO, "login failed");
            }
            takeScreenshot("PASS_" + username);
            logger.log(Level.INFO, "success: username={0}", username);

        } catch (AssertionError e) {
            takeScreenshot("FAIL_" + username);
            logger.log(Level.INFO, "failed: username={0}", username);
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
