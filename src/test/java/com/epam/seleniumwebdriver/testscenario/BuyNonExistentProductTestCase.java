package com.epam.seleniumwebdriver.testscenario;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.epam.seleniumwebdriver.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@Test(groups = {"regression", "smoke"})
public class BuyNonExistentProductTestCase extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuyNonExistentProductTestCase.class);
    public static String stringExpectedMessage = "Нажаль, нічого не знайдено.";
    public static String stringActualMessage;

    @Test
    void buyNonExistentProductNegativeTest() {
        MainPage mainPage = new MainPage(getDriver());

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(7));
        wait.until(ExpectedConditions.visibilityOf(locationButton));

        selectLocation("Київ");
        LOGGER.debug("Location input: \"Київ\"");

        mainPage.searchBar.click();

        LOGGER.info("Non existing product searching");
        searchProduct(mainPage.searchBar, "No kia 3310");
        LOGGER.debug("Entering a product that is not in the store: \"No kia 3310\"");

        mainPage.searchBar.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOf(mainPage.emptyCatalogXpath));

        LOGGER.info("Having a message on the page");
        stringActualMessage = mainPage.emptyCatalogXpath.getText();
        LOGGER.debug("Actual message on the page " + mainPage.emptyCatalogXpath.getText());

        if (!stringActualMessage.equals(stringExpectedMessage)) {
            LOGGER.error("Text '{}' is not as expected '{}'", stringActualMessage, stringExpectedMessage);
        }

        assertThat(stringActualMessage)
                .as("Text '%s' is not as expected '%s'", stringActualMessage, stringExpectedMessage)
                .isEqualTo(stringExpectedMessage);
        LOGGER.info("Actual message " + stringActualMessage + " Expected message " + stringExpectedMessage);
    }
}