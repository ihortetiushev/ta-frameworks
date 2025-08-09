package com.epam.seleniumwebdriver.testscenario;

import com.epam.seleniumwebdriver.core.BasePage;
import com.epam.seleniumwebdriver.core.CartPage;
import com.epam.seleniumwebdriver.core.MainPage;
import com.epam.seleniumwebdriver.core.SortedByPhoneNamePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.epam.seleniumwebdriver.drivermanager.DriverManager.getDriver;
import static org.assertj.core.api.Assertions.assertThat;

@Test(groups = {"regression", "smoke"})
public class BuyProductByNameTestCase extends BasePage {

    private final MainPage mainPage = new MainPage(getDriver());
    private final SortedByPhoneNamePage sortedByPhoneNamePage = new SortedByPhoneNamePage(getDriver());
    private final CartPage cartPage = new CartPage(getDriver());
    public static String stringExpectedTitle;
    public static String stringActualTitle;

    @Test
    void buyProductByNamePositiveTest() throws InterruptedException {
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
        getDriver().findElement(By.xpath(sortedByPhoneNamePage.catalogProduct));
        LOGGER.debug("Go to sorted page by product name. URL " + sortedByPhoneNamePage.sortedByPhoneNamePageUrl);

        actions.moveToElement(sortedByPhoneNamePage.sortSpace).perform();
        sortedByPhoneNamePage.sortByExpensive.click();
        LOGGER.debug("Sort page by the most expensive products");

        explicitWait.until(ExpectedConditions.urlToBe(sortedByPhoneNamePage.sortedByMostExpensiveToCheapestPageUrl));
        stringExpectedTitle = sortedByPhoneNamePage.expectedPhone.getAttribute("title");
        LOGGER.debug("Expected title of the phone " + stringExpectedTitle);
        sortedByPhoneNamePage.expectedPhone.click();

        LOGGER.info("Going to phone page");
        LOGGER.info("Going to " + sortedByPhoneNamePage.mostExpensiveItemPage + " URL");

        LOGGER.info("Buying item");
        getDriver().findElement(By.xpath(sortedByPhoneNamePage.buyButtonXpath)).click();
        LOGGER.debug("Click buy button");

        sortedByPhoneNamePage.goToCart.click();

        LOGGER.info("Verify expected and actual titles");
        stringActualTitle = sortedByPhoneNamePage.actualPhone.getText();
        LOGGER.debug("Actual title " + stringActualTitle);

        assertThat(stringActualTitle)
                .as("Text '%s' is not as expected '%s'", stringActualTitle, stringExpectedTitle)
                .isEqualTo(stringExpectedTitle);
        LOGGER.info("Verify was successful");

        cartPage.closeButton.click();
        sortedByPhoneNamePage.searchBar.click();

        LOGGER.info("Searching second product");
        searchProduct(sortedByPhoneNamePage.searchBar, "Pixel 30W charger");
        LOGGER.debug("Entering second product: \"Pixel 30W charger\"");
        sortedByPhoneNamePage.searchBar.sendKeys(Keys.ENTER);

        actions.moveToElement(sortedByPhoneNamePage.sortSpace).perform();
        sortedByPhoneNamePage.sortByCheap.click();

        LOGGER.info("Sorting items by the cheapest products");
        stringExpectedTitle = sortedByPhoneNamePage.expectedCharger.getAttribute("title");
        LOGGER.debug("Expected product title" + stringExpectedTitle);

        explicitWait.until(ExpectedConditions.urlToBe(sortedByPhoneNamePage.sortedByChargerPhonePageUrl));
        sortedByPhoneNamePage.expectedCharger.click();

        LOGGER.info("Go to selected charger page");
        LOGGER.debug("Go to " + sortedByPhoneNamePage.expectedChargerUrl + " URL");

        LOGGER.info("Buying product");
        getDriver().findElement(By.xpath(sortedByPhoneNamePage.buyButtonXpath)).click();

        LOGGER.info("Verify expected and actual titles");
        stringActualTitle = sortedByPhoneNamePage.actualTitleCharger.getText();
        LOGGER.debug("Actual title " + stringActualTitle);

        assertThat(stringActualTitle)
                .as("Text '%s' is not as expected '%s'", stringActualTitle, stringExpectedTitle)
                .isEqualTo(stringExpectedTitle);

        LOGGER.info("Verify was successful");

        cartPage.deleteProduct2.click();
        Thread.sleep(1000);
        cartPage.deleteProduct1.click();
    }
}
