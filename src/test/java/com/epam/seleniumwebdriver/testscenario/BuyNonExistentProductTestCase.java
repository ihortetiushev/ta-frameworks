package com.epam.seleniumwebdriver.testscenario;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.epam.seleniumwebdriver.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.time.Duration;

import static com.epam.seleniumwebdriver.utils.DriverType.Chrome;
import static org.assertj.core.api.Assertions.assertThat;

@Test(groups = {"regression", "smoke"})
public class BuyNonExistentProductTestCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuyNonExistentProductTestCase.class);
    public static String stringExpectedMessage = ConfigReader.get("message.NoSuchProduct");
    public static String stringActualMessage;
    private final DriverType DRIVER_NAME = Chrome;
    BasePage page;

    @BeforeClass
    public void setup() {
        LOGGER.info("Setup WebDriver");
        page = new BasePage(BasePage.createDriver(DRIVER_NAME));
        LOGGER.debug("Driver is " + DRIVER_NAME);
        page.setBrowserPage(ConfigReader.get("base.url"));
        LOGGER.debug("Open " + ConfigReader.get("base.url") + " URl");
    }

    @AfterMethod
    public void takeScreenshot(ITestResult testResult) {
        if (!testResult.isSuccess()) {
            page.takeScreenshotForFailure(testResult);
            LOGGER.error("Screenshot of failure has been taken in \"screenshots\" directory");
        }
    }

    @AfterClass
    public void closeDriver() {
        page.driver.quit();
        LOGGER.info("Close WebDriver");
    }

    @Test
    void buyNonExistentProductNegativeTest() {
        MainPage mainPage = new MainPage(page.driver);

        WebDriverWait wait = new WebDriverWait(page.driver, Duration.ofSeconds(7));
        wait.until(ExpectedConditions.visibilityOf(page.locationButton));

        page.selectLocation(ConfigReader.get("location03"));
        LOGGER.debug("Location input " + ConfigReader.get("location03"));

        mainPage.searchBar.click();

        LOGGER.info("Product searching");
        page.searchProduct(mainPage.searchBar, ConfigReader.get("search.noSuchProduct"));

        LOGGER.info("Entering a product that is not in the store " + ConfigReader.get("search.noSuchProduct"));

        mainPage.searchBar.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOf(mainPage.emptyCatalogXpath));

        LOGGER.info("Having a message on the page");
        stringActualMessage = mainPage.emptyCatalogXpath.getText();
        LOGGER.debug("Actual message on the page " + mainPage.emptyCatalogXpath.getText());

        if (!stringActualMessage.equals(stringExpectedMessage)) {
            LOGGER.error("Text '{}' is not as expected '{}'", stringActualMessage, stringExpectedMessage);
        }

        assertThat(stringActualMessage)
                .as("Text '%s' is not as expected '%s'", stringActualMessage, ConfigReader.get("message.NoSuchProduct"))
                .isEqualTo(stringExpectedMessage);
        LOGGER.info("Actual message " + stringActualMessage + " Expected message " + ConfigReader.get("message.NoSuchProduct"));
    }
}