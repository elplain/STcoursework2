package tests;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.logging.Level;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class A7BottomLinksTest extends A0WebDriverSetup {
    @ParameterizedTest
@CsvFileSource(resources = "/A7testdata.csv", numLinesToSkip = 1)
void testItemInteraction(String username, String password,
                         String shouldSucceed1, String shouldSucceed2, String shouldSucceed3) {

    logger.log(Level.INFO, "Starting testItemInteraction");

    boolean Check1Link = shouldSucceed1.equalsIgnoreCase("true");
    boolean Check2link = shouldSucceed2.equalsIgnoreCase("true");
    boolean Check3link = shouldSucceed3.equalsIgnoreCase("true");

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    try {
        //login
        webPage.enterUsername(username);
        webPage.enterPassword(password);
        webPage.clickSubmit();
        wait.until(ExpectedConditions.urlContains("inventory"));

        String mainWindow = driver.getWindowHandle();

        //test1 - Twitter
        final int beforeCount = driver.getWindowHandles().size();

        webPage.clickTwitter();

        wait.until(d -> d.getWindowHandles().size() > beforeCount);

        int after = driver.getWindowHandles().size();
        takeScreenshot("Success_BOTTOM_LINKS");
        logger.log(Level.INFO, "success");

if (Check1Link) {
    Assertions.assertEquals(beforeCount + 1, after);
}
        
       closeNewTabAndReturn(mainWindow);

        //test2 - Facebook
        final int beforeCount2 = driver.getWindowHandles().size();

        webPage.clickFacebook();

        wait.until(d -> d.getWindowHandles().size() > beforeCount2);

        int after2 = driver.getWindowHandles().size();
        takeScreenshot("Success_BOTTOM_LINKS");
        logger.log(Level.INFO, "success");

        if (Check2link) {
            Assertions.assertEquals(beforeCount2 + 1, after2);
}
        
        closeNewTabAndReturn(mainWindow);
        
        //test3 - Linkedin
        final int beforeCount3 = driver.getWindowHandles().size();

        webPage.clickLinkedin();

        wait.until(d -> d.getWindowHandles().size() > beforeCount3);

        int after3 = driver.getWindowHandles().size();
        takeScreenshot("Success_BOTTOM_LINKS");
        logger.log(Level.INFO, "success");

        if (Check3link) {
            Assertions.assertEquals(beforeCount3 + 1, after3);
}

        closeNewTabAndReturn(mainWindow);

    } catch (AssertionError e) {
        takeScreenshot("Success_BOTTOM_LINKS");
        logger.log(Level.INFO, "failed");
        throw e;
    }
}
    //method to close the new tab and return to the main window
    private void closeNewTabAndReturn(String mainWindow) {
    for (String window : driver.getWindowHandles()) {
        if (!window.equals(mainWindow)) {
            driver.switchTo().window(window);
            driver.close();

        break;
}
    }
            driver.switchTo().window(mainWindow);
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
