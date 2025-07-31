package com.epam.seleniumwebdriver.testscenario;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.epam.seleniumwebdriver.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.epam.seleniumwebdriver.utils.DriverType.Chrome;
import static org.assertj.core.api.Assertions.assertThat;

@Test(groups = {"regression", "smoke"})
public class BuyProductByNameTestCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuyNonExistentProductTestCase.class);
    public static String stringExpectedTitle;
    public static String stringActualTitle;
    private final DriverType DRIVER_NAME = Chrome;
    BasePage page;

    @BeforeClass
    public void setup() {
        LOGGER.info("setup WebDriver");
        page = new BasePage(BasePage.createDriver(DRIVER_NAME));
        LOGGER.debug("Driver is " + DRIVER_NAME);
        page.setBrowserPage(ConfigReader.get("base.url"));
        LOGGER.debug("Open " + ConfigReader.get("base.url") + " URl");
    }

    @AfterMethod
    public void takeScreenshot(ITestResult testResult) {
        page.takeScreenshotForFailure(testResult);
        LOGGER.error("Screenshot of failure has been taken in \"screenshots\" directory");
    }

    @AfterClass
    public void closeDriver() {
        LOGGER.info("Close WebDriver");
        page.driver.quit();
    }

    @Test
    void buyProductByNamePositiveTest() {
        MainPage mainPage = new MainPage(page.driver);
        SortedByPhoneNamePage sortedByNamePage = new SortedByPhoneNamePage(page.driver);
        CartPage cartPage = new CartPage(page.driver);

        WebDriverWait wait = new WebDriverWait(page.driver, Duration.ofSeconds(7));
        Actions actions = new Actions(page.driver);

        wait.until(ExpectedConditions.visibilityOf(page.locationButton));

        page.selectLocation(ConfigReader.get("location02"));
        LOGGER.debug("Location input " + ConfigReader.get("location02"));

        mainPage.searchBar.click();

        LOGGER.info("Product searching by name");
        //mainPage.searchBar.sendKeys(ConfigReader.get("search.product01"));
        page.searchProduct(mainPage.searchBar, ConfigReader.get("search.product01"));

        LOGGER.debug("Searching product: " + ConfigReader.get("search.product01"));

        mainPage.searchBar.sendKeys(Keys.ENTER);

        LOGGER.info("Sorted page by product name");
        wait.until(ExpectedConditions.urlToBe(sortedByNamePage.sortedByPhoneNamePageUrl));
        LOGGER.debug("Go to sorted page by product name. URL " + sortedByNamePage.sortedByPhoneNamePageUrl);

        actions.moveToElement(sortedByNamePage.sortSpace).perform();
        sortedByNamePage.sortByExpensive.click();
        LOGGER.debug("Sort page by the most expensive products");

        wait.until(ExpectedConditions.urlToBe(sortedByNamePage.sortedByMostExpensiveToCheapestPageUrl));

        stringExpectedTitle = sortedByNamePage.expectedPhone.getAttribute("title");
        LOGGER.debug("Expected title of the phone " + stringExpectedTitle);
        sortedByNamePage.expectedPhone.click();

        LOGGER.info("Going to phone page");
        wait.until(ExpectedConditions.urlToBe(sortedByNamePage.mostExpensiveItemPage));
        LOGGER.info("Going to " + sortedByNamePage.mostExpensiveItemPage + " URL");

        LOGGER.info("Buying item");
        sortedByNamePage.buyButton.click();
        LOGGER.debug("Click buy button");

        wait.until(ExpectedConditions.visibilityOf(sortedByNamePage.goToCart));
        sortedByNamePage.goToCart.click();

        wait.until(ExpectedConditions.visibilityOf(sortedByNamePage.actualPhone));

        LOGGER.info("Verify expected and actual titles");
        stringActualTitle = sortedByNamePage.actualPhone.getText();
        LOGGER.debug("Actual title " + stringActualTitle);

        if (!stringActualTitle.equals(stringExpectedTitle)) {
            LOGGER.error("Text '{}' is not as expected '{}'", stringActualTitle, stringExpectedTitle);
        }
        assertThat(stringActualTitle)
                .as("Text '%s' is not as expected '%s'", stringActualTitle, stringExpectedTitle)
                .isEqualTo(stringExpectedTitle);
        LOGGER.info("Verify was successful");

        cartPage.closeButton.click();
        sortedByNamePage.searchBar.click();

        LOGGER.info("Searching second product");
        page.searchProduct(sortedByNamePage.searchBar, ConfigReader.get("search.product02"));

        LOGGER.debug("Entering second product " + ConfigReader.get("search.product02"));
        sortedByNamePage.searchBar.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOf(sortedByNamePage.sortSpace));
        actions.moveToElement(sortedByNamePage.sortSpace).perform();
        sortedByNamePage.sortByCheap.click();

        LOGGER.info("Sorting items by the cheapest products");
        wait.until(ExpectedConditions.visibilityOf(sortedByNamePage.expectedCharger));
        stringExpectedTitle = sortedByNamePage.expectedCharger.getAttribute("title");
        LOGGER.debug("Expected product title" + stringExpectedTitle);

        wait.until(ExpectedConditions.urlToBe(sortedByNamePage.sortedByChargerPhonePageUrl));
        sortedByNamePage.expectedCharger.click();

        LOGGER.info("Go to selected charger page");
        wait.until(ExpectedConditions.urlToBe(sortedByNamePage.expectedChargerUrl));
        LOGGER.debug("Go to " + sortedByNamePage.expectedChargerUrl + " URL");

        LOGGER.info("Buying product");
        sortedByNamePage.buyButton.click();

        LOGGER.info("Verify expected and actual titles");
        wait.until(ExpectedConditions.visibilityOf(sortedByNamePage.actualTitleCharger));
        stringActualTitle = sortedByNamePage.actualTitleCharger.getText();
        LOGGER.debug("Actual title " + stringActualTitle);

        if (!stringActualTitle.equals(stringExpectedTitle)) {
            LOGGER.error("Text '{}' is not as expected '{}'", stringActualTitle, stringExpectedTitle);
        }

        assertThat(stringActualTitle)
                .as("Text '%s' is not as expected '%s'", stringActualTitle, stringExpectedTitle)
                .isEqualTo(stringExpectedTitle);

        LOGGER.info("Verify was successful");
    }
}
