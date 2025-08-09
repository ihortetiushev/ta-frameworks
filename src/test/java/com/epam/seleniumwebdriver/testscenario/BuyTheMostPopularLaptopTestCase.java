package com.epam.seleniumwebdriver.testscenario;

import com.epam.seleniumwebdriver.core.BasePage;
import com.epam.seleniumwebdriver.core.CartPage;
import com.epam.seleniumwebdriver.core.MainPage;
import com.epam.seleniumwebdriver.core.SortedByPopularityPage;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.epam.seleniumwebdriver.drivermanager.DriverManager.getDriver;
import static org.assertj.core.api.Assertions.assertThat;

@Test(groups = "regression")
public class BuyTheMostPopularLaptopTestCase extends BasePage {

    private final MainPage mainPage = new MainPage(getDriver());
    private final SortedByPopularityPage sortedByPopularityPage = new SortedByPopularityPage(getDriver());
    private final CartPage cartPage = new CartPage(getDriver());
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
        mainPage.catalogButton.click();

        mainPage.laptopsPcTabletsCategory.click();
        LOGGER.info("Searching laptops, PC and tablets category");

        mainPage.laptopsPcTabletsCategoryAppleSelector.click();
        LOGGER.debug("Searching laptops, PC and tablets category by Apple manufacture");

        actions.moveToElement(mainPage.sortButton).perform();
        mainPage.sortByPopularityButton.click();
        LOGGER.info("Sorting by the most popular");

        stringExpectedTitle = sortedByPopularityPage.expectedLaptop.getAttribute("title");
        LOGGER.debug("Expected title " + stringExpectedTitle);

        explicitWait.until(ExpectedConditions.urlToBe(sortedByPopularityPage.sortedByPopularityUrl));
        sortedByPopularityPage.sortByPopularityItem.click();
        LOGGER.info("Select product");

        LOGGER.debug("Expected URL " + sortedByPopularityPage.expectedUrl);

        getDriver().findElement(By.xpath(cartPage.buyButtonXpath)).click();
        LOGGER.info("Buying product");

        sortedByPopularityPage.goToCartButton.click();

        stringActualTitle = cartPage.actualElement.getText();
        LOGGER.debug("Actual title " + stringActualTitle);

        LOGGER.info("Verify expected and actual titles");

        assertThat(stringActualTitle)
                .as("Text '%s' is not as expected '%s'", stringActualTitle, stringExpectedTitle)
                .isEqualTo(stringExpectedTitle);

        cartPage.addQuantity.click();
        cartPage.addQuantity.click();
        LOGGER.info("Verify was successful");

        LOGGER.info("Check product quantity");

        explicitWait.until(ExpectedConditions.attributeContains(cartPage.productQuantity, "value", "3"));

        assertThat(cartPage.productQuantity.getAttribute("value"))
                .as("Text '%s' is not as expected '%s'", cartPage.productQuantity.getAttribute("value"), expectedProductQuantity)
                .isEqualTo(expectedProductQuantity);
        LOGGER.info("Expected quantity equals actual");
        cartPage.deleteProduct1.click();
    }
}
