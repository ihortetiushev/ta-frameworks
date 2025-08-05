package com.epam.seleniumwebdriver.testscenario;

import com.epam.seleniumwebdriver.core.BasePage;
import com.epam.seleniumwebdriver.core.MainPage;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.epam.seleniumwebdriver.drivermanager.DriverManager.getDriver;
import static org.assertj.core.api.Assertions.assertThat;

@Test(groups = {"regression", "smoke"})
public class BuyNonExistentProductTestCase extends BasePage {
    private final MainPage MAIN_PAGE = new MainPage(getDriver());

    private static final Logger LOGGER = LoggerFactory.getLogger(BuyNonExistentProductTestCase.class);
    public static String stringExpectedMessage = "Нажаль, нічого не знайдено.";
    public static String stringActualMessage;

    @Test
    void buyNonExistentProductNegativeTest() {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(7));

        selectLocation("Київ");
        LOGGER.debug("Location input: \"Київ\"");
        MAIN_PAGE.searchBar.click();

        LOGGER.info("Non existing product searching");

        searchProduct(MAIN_PAGE.searchBar, "No kia 3310");
        LOGGER.debug("Entering a product that is not in the store: \"No kia 3310\"");

        MAIN_PAGE.searchBar.sendKeys(Keys.ENTER);

        LOGGER.info("Having a message on the page");
        stringActualMessage = MAIN_PAGE.emptyCatalogXpath.getText();
        LOGGER.debug("Actual message on the page " + MAIN_PAGE.emptyCatalogXpath.getText());

        if (!stringActualMessage.equals(stringExpectedMessage)) {
            LOGGER.error("Text '{}' is not as expected '{}'", stringActualMessage, stringExpectedMessage);
        }

        assertThat(stringActualMessage)
                .as("Text '%s' is not as expected '%s'", stringActualMessage, stringExpectedMessage)
                .isEqualTo(stringExpectedMessage);
        LOGGER.info("Actual message " + stringActualMessage + " Expected message " + stringExpectedMessage);
    }
}