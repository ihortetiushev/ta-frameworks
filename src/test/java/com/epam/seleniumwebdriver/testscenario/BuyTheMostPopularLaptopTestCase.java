package com.epam.seleniumwebdriver.testscenario;

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

import static com.epam.seleniumwebdriver.utils.DriverType.Chrome;
import static org.assertj.core.api.Assertions.*;

import java.time.Duration;

@Test(groups = "regression")
public class BuyTheMostPopularLaptopTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuyNonExistentProductTestCase.class);
    public static String stringExpectedTitle;
    public static String stringActualTitle;
    public static String expectedProductQuantity = "3";
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
    void buyTheMostPopularLaptopPositiveTest() {
        MainPage mainPage = new MainPage(page.driver);
        SortedByPopularityPage popularityPage = new SortedByPopularityPage(page.driver);
        CartPage cartPage = new CartPage(page.driver);

        Actions actions = new Actions(page.driver);

        WebDriverWait wait = new WebDriverWait(page.driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(page.locationButton));

        page.selectLocation(ConfigReader.get("location01"));
        LOGGER.debug("Location input " + ConfigReader.get("location01"));
        mainPage.catalogButton.click();

        wait.until(ExpectedConditions.visibilityOf(mainPage.laptopsPcTabletsCategory));
        mainPage.laptopsPcTabletsCategory.click();
        LOGGER.info("Searching laptops, PC and tablets category");

        wait.until(ExpectedConditions.visibilityOf(mainPage.laptopsPcTabletsCategoryAppleSelector));
        mainPage.laptopsPcTabletsCategoryAppleSelector.click();
        LOGGER.info("Searching laptops, PC and tablets category by Apple manufacture");

        actions.moveToElement(mainPage.sortButton).perform();
        mainPage.sortByPopularityButton.click();
        LOGGER.info("Sorting by the most popular");

        wait.until(ExpectedConditions.visibilityOf(popularityPage.expectedLaptop));
        stringExpectedTitle = popularityPage.expectedLaptop.getAttribute("title");
        LOGGER.debug("Expected title " + stringExpectedTitle);

        wait.until(ExpectedConditions.visibilityOf(popularityPage.sortByPopularityItem));
        popularityPage.sortByPopularityItem.click();
        LOGGER.info("Select product");

        wait.until(ExpectedConditions.urlToBe(popularityPage.expectedUrl));
        LOGGER.debug("Expected URL " + popularityPage.expectedUrl);

        popularityPage.buyButton.click();
        LOGGER.info("Buying product");

        wait.until(ExpectedConditions.visibilityOf(popularityPage.goToCartButton));
        popularityPage.goToCartButton.click();

        wait.until(ExpectedConditions.visibilityOf(cartPage.actualElement));
        stringActualTitle = cartPage.actualElement.getText();
        LOGGER.debug("Actual title " + stringActualTitle);

        LOGGER.info("Verify expected and actual titles");

        if (!stringActualTitle.equals(stringExpectedTitle)) {
            LOGGER.error("Text '{}' is not as expected '{}'", stringActualTitle, stringExpectedTitle);
        }
        assertThat(stringActualTitle)
                .as("Text '%s' is not as expected '%s'", stringActualTitle, stringExpectedTitle)
                .isEqualTo(stringExpectedTitle);

        wait.until(ExpectedConditions.elementToBeClickable(cartPage.addQuantity));
        cartPage.addQuantity.click();
        wait.until(ExpectedConditions.elementToBeClickable(cartPage.addQuantity));
        cartPage.addQuantity.click();
        LOGGER.info("Verify was successful");

        LOGGER.info("Check product quantity");
        wait.until(ExpectedConditions.attributeContains(cartPage.productQuantity, "value", "3"));

        if (!cartPage.productQuantity.getAttribute("value").equals(expectedProductQuantity)) {
            LOGGER.error("Text '{}' is not as expected '{}'", cartPage.productQuantity.getAttribute("value"), expectedProductQuantity);
        }
        assertThat(cartPage.productQuantity.getAttribute("value"))
                .as("Text '%s' is not as expected '%s'", cartPage.productQuantity.getAttribute("value"), expectedProductQuantity)
                .isEqualTo(expectedProductQuantity);
        LOGGER.info("Expected quantity equals actual");
    }
}
