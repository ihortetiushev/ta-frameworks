package com.epam.seleniumwebdriver.core;

import com.epam.seleniumwebdriver.core.report.ScreenshotReport;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import static com.epam.seleniumwebdriver.drivermanager.DriverManager.closeDriver;
import static com.epam.seleniumwebdriver.drivermanager.DriverManager.getDriver;

public class BasePage extends PageFactory {

    protected static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);
    @FindBy(how = How.XPATH, using = "//div[@class='mh-loc']//button[@class='mh-button']")
    public WebElement locationButton;
    @FindBy(how = How.XPATH, using = "//div[contains(@class, 'geo__cities')]//*[@data-geo-select-city='Харків']")
    public WebElement locationKharkiv;
    @FindBy(how = How.XPATH, using = "//div[contains(@class, 'geo__cities')]//*[@data-geo-select-city='Київ']")
    public WebElement locationKyiv;
    @FindBy(how = How.XPATH, using = "//div[contains(@class, 'geo__cities')]//*[@data-geo-select-city='Одеса']")
    public WebElement locationOdesa;


    public BasePage() {
        initElements(getDriver(), this);
    }

    @BeforeClass
    public void setBrowserPage() {
        LOGGER.info("Creating WebDriver");
        WebDriver driver = getDriver();
        driver.get(ConfigReader.get("base.url"));
        LOGGER.debug("Get a URl " + ConfigReader.get("base.url"));
    }

    @AfterMethod
    public void takeScreenshotIfFailed(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            WebDriver driver = getDriver();

            if (driver != null) {
                try {
                    ScreenshotReport.takeScreenshotForFailure(driver, result);
                    LOGGER.error("Screenshot saved to screenshots directory");
                } catch (NoSuchSessionException e) {
                    LOGGER.warn("Cannot take screenshot — WebDriver session already closed");
                }
            } else {
                LOGGER.warn("Cannot take screenshot — WebDriver is null");
            }
        }
    }

    @AfterSuite
    public void driverCloser() {
        closeDriver();
        LOGGER.debug("Driver closed");
    }

    public void selectLocation(String location) {
        locationButton.click();

        switch (location) {
            case ("Харків"):
                locationKharkiv.click();
                break;

            case ("Київ"):
                locationKyiv.click();
                break;

            case ("Одеса"):
                locationOdesa.click();
                break;
            default:
                throw new IllegalArgumentException("city " + location + " is not supported");
        }
    }

    public void searchProduct(WebElement webElement, String productName) {
        webElement.click();
        webElement.sendKeys(productName);
        webElement.sendKeys(Keys.ENTER);
    }
}
