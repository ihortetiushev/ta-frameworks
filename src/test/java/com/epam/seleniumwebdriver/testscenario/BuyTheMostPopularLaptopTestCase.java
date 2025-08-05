package com.epam.seleniumwebdriver.testscenario;

import com.epam.seleniumwebdriver.core.BasePage;
import com.epam.seleniumwebdriver.core.CartPage;
import com.epam.seleniumwebdriver.core.MainPage;
import com.epam.seleniumwebdriver.core.SortedByPopularityPage;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.epam.seleniumwebdriver.drivermanager.DriverManager.getDriver;
import static org.assertj.core.api.Assertions.assertThat;

@Test(groups = "regression")
public class BuyTheMostPopularLaptopTestCase extends BasePage {

    private final MainPage MAIN_PAGE = new MainPage(getDriver());
    private final SortedByPopularityPage POPULARITY_PAGE = new SortedByPopularityPage(getDriver());
    private final CartPage CART_PAGE = new CartPage(getDriver());

    private static final Logger LOGGER = LoggerFactory.getLogger(BuyNonExistentProductTestCase.class);
    public static String stringExpectedTitle;
    public static String stringActualTitle;
    public static String expectedProductQuantity = "3";
    @Test
    void buyTheMostPopularLaptopPositiveTest() {
        Actions actions = new Actions(getDriver());
        WebDriverWait explicitWait = new WebDriverWait(getDriver(), Duration.ofSeconds(7));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        selectLocation("Одеса");
        LOGGER.debug("Location input: \"Одеса\"");
        MAIN_PAGE.catalogButton.click();

        MAIN_PAGE.laptopsPcTabletsCategory.click();
        LOGGER.info("Searching laptops, PC and tablets category");

        MAIN_PAGE.laptopsPcTabletsCategoryAppleSelector.click();
        LOGGER.debug("Searching laptops, PC and tablets category by Apple manufacture");

        actions.moveToElement(MAIN_PAGE.sortButton).perform();
        MAIN_PAGE.sortByPopularityButton.click();
        LOGGER.info("Sorting by the most popular");

        stringExpectedTitle = POPULARITY_PAGE.expectedLaptop.getAttribute("title");
        LOGGER.debug("Expected title " + stringExpectedTitle);

        POPULARITY_PAGE.sortByPopularityItem.click();
        LOGGER.info("Select product");

        explicitWait.until(ExpectedConditions.urlToBe(POPULARITY_PAGE.expectedUrl));
        LOGGER.debug("Expected URL " + POPULARITY_PAGE.expectedUrl);

        POPULARITY_PAGE.buyButton.click();
        LOGGER.info("Buying product");

        POPULARITY_PAGE.goToCartButton.click();

        stringActualTitle = CART_PAGE.actualElement.getText();
        LOGGER.debug("Actual title " + stringActualTitle);

        LOGGER.info("Verify expected and actual titles");

        if (!stringActualTitle.equals(stringExpectedTitle)) {
            LOGGER.error("Text '{}' is not as expected '{}'", stringActualTitle, stringExpectedTitle);
        }

        assertThat(stringActualTitle)
                .as("Text '%s' is not as expected '%s'", stringActualTitle, stringExpectedTitle)
                .isEqualTo(stringExpectedTitle);

        CART_PAGE.addQuantity.click();
        CART_PAGE.addQuantity.click();
        LOGGER.info("Verify was successful");

        LOGGER.info("Check product quantity");

        explicitWait.until(ExpectedConditions.attributeContains(CART_PAGE.productQuantity, "value", "3"));

        if (!CART_PAGE.productQuantity.getAttribute("value").equals(expectedProductQuantity)) {
            LOGGER.error("Text '{}' is not as expected '{}'", CART_PAGE.productQuantity.getAttribute("value"), expectedProductQuantity);
        }

        assertThat(CART_PAGE.productQuantity.getAttribute("value"))
                .as("Text '%s' is not as expected '%s'", CART_PAGE.productQuantity.getAttribute("value"), expectedProductQuantity)
                .isEqualTo(expectedProductQuantity);
        LOGGER.info("Expected quantity equals actual");
    }
}
