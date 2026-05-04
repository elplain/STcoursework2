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


public class A2Items1Test extends A0WebDriverSetup {

    @ParameterizedTest
    @CsvFileSource(resources = "/A2testdata.csv", numLinesToSkip = 1)
    void testItemInteraction(String username, String password, String shouldSucceed1,String shouldSucceed2 ,String shouldSucceed3,String shouldSucceed4) {
        logger.log(Level.INFO, "Starting testItemInteraction: " + shouldSucceed1 + ", " + shouldSucceed2 + ", " + shouldSucceed3 + ", " + shouldSucceed4);

        boolean Checkitem1Link = shouldSucceed1.equalsIgnoreCase("true");
        boolean Checkitem2link = shouldSucceed2.equalsIgnoreCase("true");
        boolean Checkitem3link = shouldSucceed3.equalsIgnoreCase("true");
        boolean Checkitem4link = shouldSucceed4.equalsIgnoreCase("true");

        try {
            //Login steps
            webPage.enterUsername(username);
            webPage.enterPassword(password);
            webPage.clickSubmit();
            //Item Tests page 1
            webPage.clickItemLink1();
            String url = webPage.getCurrentUrl();
            if (Checkitem1Link) {
                Assertions.assertTrue(url.contains("/inventory-item.html?id=4"));
                logger.log(Level.INFO, "Item 4 page loaded successfully");
                takeScreenshot("PASS_ITEM1_" + shouldSucceed1);
                logger.log(Level.INFO, "success");
            }
            webPage.goBack();

            //page 2
            webPage.clickItemLink2();
            String url2 = webPage.getCurrentUrl();
            if (Checkitem2link) {
                Assertions.assertTrue(url2.contains("/inventory-item.html?id=1"));
                logger.log(Level.INFO, "Item 1 page loaded successfully");
                takeScreenshot("PASS_ITEM2_" + shouldSucceed2);
                logger.log(Level.INFO, "success");
            }
            webPage.goBack();

            //page 3
            webPage.clickItemTitleLink1();
            String url3 = webPage.getCurrentUrl();
            if (Checkitem3link) {
                Assertions.assertTrue(url3.contains("/inventory-item.html?id=5"));
                logger.log(Level.INFO, "Item 5 page loaded successfully");
                takeScreenshot("PASS_ITEM3_" + shouldSucceed3);
                logger.log(Level.INFO, "success");
            }
            webPage.goBack();

            //page 4
            webPage.clickItemTitleLink2();
            String url4 = webPage.getCurrentUrl();
            if (Checkitem4link) {
                Assertions.assertTrue(url4.contains("/inventory-item.html?id=3"));
                logger.log(Level.INFO, "Item 3 page loaded successfully");
                takeScreenshot("PASS_ITEM4_" + shouldSucceed4);
                logger.log(Level.INFO, "success");
            }






        } catch (AssertionError e) {
            takeScreenshot("FAIL_ITEMS_");
            logger.log(Level.INFO, "failed");
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