package com.epam.seleniumwebdriver.testscenario;

import com.epam.seleniumwebdriver.core.BasePage;
import com.epam.seleniumwebdriver.core.CartPage;
import com.epam.seleniumwebdriver.core.MainPage;
import com.epam.seleniumwebdriver.core.SortedByPhoneNamePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.epam.seleniumwebdriver.drivermanager.DriverManager.getDriver;
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

        WebDriverWait explicitWait = new WebDriverWait(getDriver(), Duration.ofSeconds(7));
        Actions actions = new Actions(getDriver());
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        selectLocation("Харків");
        LOGGER.debug("Location input: \"Харків\"");
        mainPage.searchBar.click();

        LOGGER.info("Product searching by name");
        searchProduct(mainPage.searchBar, "Pixel 9 pro");
        LOGGER.debug("Searching product: \"Pixel 9 pro\"");

        mainPage.searchBar.sendKeys(Keys.ENTER);

        LOGGER.info("Sorted page by product name");
        explicitWait.until(ExpectedConditions.urlToBe(sortedByNamePage.sortedByPhoneNamePageUrl));
        LOGGER.debug("Go to sorted page by product name. URL " + sortedByNamePage.sortedByPhoneNamePageUrl);

        actions.moveToElement(sortedByNamePage.sortSpace).perform();
        sortedByNamePage.sortByExpensive.click();
        LOGGER.debug("Sort page by the most expensive products");

        explicitWait.until(ExpectedConditions.urlToBe(sortedByNamePage.sortedByMostExpensiveToCheapestPageUrl));

        stringExpectedTitle = sortedByNamePage.expectedPhone.getAttribute("title");
        LOGGER.debug("Expected title of the phone " + stringExpectedTitle);
        sortedByNamePage.expectedPhone.click();

        LOGGER.info("Going to phone page");
        explicitWait.until(ExpectedConditions.urlToBe(sortedByNamePage.mostExpensiveItemPage));
        LOGGER.info("Going to " + sortedByNamePage.mostExpensiveItemPage + " URL");

        LOGGER.info("Buying item");
        sortedByNamePage.buyButton.click();
        LOGGER.debug("Click buy button");

        sortedByNamePage.goToCart.click();

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

        actions.moveToElement(sortedByNamePage.sortSpace).perform();
        sortedByNamePage.sortByCheap.click();

        LOGGER.info("Sorting items by the cheapest products");
        stringExpectedTitle = sortedByNamePage.expectedCharger.getAttribute("title");
        LOGGER.debug("Expected product title" + stringExpectedTitle);

        explicitWait.until(ExpectedConditions.urlToBe(sortedByNamePage.sortedByChargerPhonePageUrl));
        sortedByNamePage.expectedCharger.click();

        LOGGER.info("Go to selected charger page");
        explicitWait.until(ExpectedConditions.urlToBe(sortedByNamePage.expectedChargerUrl));
        LOGGER.debug("Go to " + sortedByNamePage.expectedChargerUrl + " URL");

        LOGGER.info("Buying product");
        sortedByNamePage.buyButton.click();

        LOGGER.info("Verify expected and actual titles");
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
