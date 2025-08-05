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

    private final MainPage MAIN_PAGE = new MainPage(getDriver());
    private final SortedByPhoneNamePage SORTED_BY_NAME_PAGE = new SortedByPhoneNamePage(getDriver());
    private final CartPage CART_PAGE = new CartPage(getDriver());

    private static final Logger LOGGER = LoggerFactory.getLogger(BuyNonExistentProductTestCase.class);
    public static String stringExpectedTitle;
    public static String stringActualTitle;

    @Test
    void buyProductByNamePositiveTest() {
        WebDriverWait explicitWait = new WebDriverWait(getDriver(), Duration.ofSeconds(7));
        Actions actions = new Actions(getDriver());

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        selectLocation("Харків");
        LOGGER.debug("Location input: \"Харків\"");
        MAIN_PAGE.searchBar.click();

        LOGGER.info("Product searching by name");
        searchProduct(MAIN_PAGE.searchBar, "Pixel 9 pro");
        LOGGER.debug("Searching product: \"Pixel 9 pro\"");

        MAIN_PAGE.searchBar.sendKeys(Keys.ENTER);

        LOGGER.info("Sorted page by product name");
        explicitWait.until(ExpectedConditions.urlToBe(SORTED_BY_NAME_PAGE.sortedByPhoneNamePageUrl));
        LOGGER.debug("Go to sorted page by product name. URL " + SORTED_BY_NAME_PAGE.sortedByPhoneNamePageUrl);

        actions.moveToElement(SORTED_BY_NAME_PAGE.sortSpace).perform();
        SORTED_BY_NAME_PAGE.sortByExpensive.click();
        LOGGER.debug("Sort page by the most expensive products");

        explicitWait.until(ExpectedConditions.urlToBe(SORTED_BY_NAME_PAGE.sortedByMostExpensiveToCheapestPageUrl));

        stringExpectedTitle = SORTED_BY_NAME_PAGE.expectedPhone.getAttribute("title");
        LOGGER.debug("Expected title of the phone " + stringExpectedTitle);
        SORTED_BY_NAME_PAGE.expectedPhone.click();

        LOGGER.info("Going to phone page");
        explicitWait.until(ExpectedConditions.urlToBe(SORTED_BY_NAME_PAGE.mostExpensiveItemPage));
        LOGGER.info("Going to " + SORTED_BY_NAME_PAGE.mostExpensiveItemPage + " URL");

        LOGGER.info("Buying item");
        SORTED_BY_NAME_PAGE.buyButton.click();
        LOGGER.debug("Click buy button");

        SORTED_BY_NAME_PAGE.goToCart.click();

        LOGGER.info("Verify expected and actual titles");
        stringActualTitle = SORTED_BY_NAME_PAGE.actualPhone.getText();
        LOGGER.debug("Actual title " + stringActualTitle);

        if (!stringActualTitle.equals(stringExpectedTitle)) {
            LOGGER.error("Text '{}' is not as expected '{}'", stringActualTitle, stringExpectedTitle);
        }
        assertThat(stringActualTitle)
                .as("Text '%s' is not as expected '%s'", stringActualTitle, stringExpectedTitle)
                .isEqualTo(stringExpectedTitle);
        LOGGER.info("Verify was successful");

        CART_PAGE.closeButton.click();
        SORTED_BY_NAME_PAGE.searchBar.click();

        LOGGER.info("Searching second product");
        searchProduct(SORTED_BY_NAME_PAGE.searchBar, "Pixel 30W charger");
        LOGGER.debug("Entering second product: \"Pixel 30W charger\"");
        SORTED_BY_NAME_PAGE.searchBar.sendKeys(Keys.ENTER);

        actions.moveToElement(SORTED_BY_NAME_PAGE.sortSpace).perform();
        SORTED_BY_NAME_PAGE.sortByCheap.click();

        LOGGER.info("Sorting items by the cheapest products");
        stringExpectedTitle = SORTED_BY_NAME_PAGE.expectedCharger.getAttribute("title");
        LOGGER.debug("Expected product title" + stringExpectedTitle);

        explicitWait.until(ExpectedConditions.urlToBe(SORTED_BY_NAME_PAGE.sortedByChargerPhonePageUrl));
        SORTED_BY_NAME_PAGE.expectedCharger.click();

        LOGGER.info("Go to selected charger page");
        explicitWait.until(ExpectedConditions.urlToBe(SORTED_BY_NAME_PAGE.expectedChargerUrl));
        LOGGER.debug("Go to " + SORTED_BY_NAME_PAGE.expectedChargerUrl + " URL");

        LOGGER.info("Buying product");
        SORTED_BY_NAME_PAGE.buyButton.click();

        LOGGER.info("Verify expected and actual titles");
        stringActualTitle = SORTED_BY_NAME_PAGE.actualTitleCharger.getText();
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
