package com.epam.seleniumwebdriver.testscenario;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.epam.seleniumwebdriver.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@Test(groups = {"regression", "smoke"})
public class BuyProductByNameTestCase extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuyNonExistentProductTestCase.class);
    public static String stringExpectedTitle;
    public static String stringActualTitle;

    @Test
    void buyProductByNamePositiveTest() {
        MainPage mainPage = new MainPage(getDriver());
        SortedByPhoneNamePage sortedByNamePage = new SortedByPhoneNamePage(getDriver());
        CartPage cartPage = new CartPage(getDriver());

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(7));
        Actions actions = new Actions(getDriver());

        wait.until(ExpectedConditions.visibilityOf(locationButton));

        selectLocation("Харків");
        LOGGER.debug("Location input: \"Харків\"");
        mainPage.searchBar.click();

        LOGGER.info("Product searching by name");
        searchProduct(mainPage.searchBar, "Pixel 9 pro");
        LOGGER.debug("Searching product: \"Pixel 9 pro\"");

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
        searchProduct(sortedByNamePage.searchBar, "Pixel 30W charger");
        LOGGER.debug("Entering second product: \"Pixel 30W charger\"");
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
