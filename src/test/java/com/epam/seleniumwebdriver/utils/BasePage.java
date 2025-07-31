package com.epam.seleniumwebdriver.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;

public class BasePage extends PageFactory {
    public WebDriver driver;
    @FindBy(how = How.XPATH, using = "//div[@class='mh-loc']//button[@class='mh-button']")
    public WebElement locationButton;
    @FindBy(how = How.XPATH, using = "//div[contains(@class, 'geo__cities')]//*[@data-geo-select-city='Харків']")
    public WebElement locationKharkiv;
    @FindBy(how = How.XPATH, using = "//div[contains(@class, 'geo__cities')]//*[@data-geo-select-city='Київ']")
    public WebElement locationKyiv;
    @FindBy(how = How.XPATH, using = "//div[contains(@class, 'geo__cities')]//*[@data-geo-select-city='Одеса']")
    public WebElement locationOdesa;
    public BasePage(WebDriver driver) {
        this.driver = driver;
        initElements(driver, this);
    }

    public static WebDriver createDriver(DriverType driverType) {
        WebDriver webDriver;
        switch (driverType) {
            case Firefox:
                WebDriverManager.firefoxdriver().setup();
                webDriver = WebDriverManager.chromedriver().create();
                break;
            case Edge:
                WebDriverManager.edgedriver().setup();
                webDriver = WebDriverManager.edgedriver().create();
                break;

            case Chrome:
                WebDriverManager.chromedriver().setup();
                webDriver = WebDriverManager.chromedriver().create();
                break;

            default:
                throw new IllegalArgumentException("Driver type " + driverType + " is not supported");
        }
        return webDriver;
    }

    public void setBrowserPage(String browserPage) {
        driver.get(browserPage);
        driver.manage().window().maximize();
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

    public void takeScreenshotForFailure(ITestResult testResult) {
        if (ITestResult.FAILURE == testResult.getStatus()) {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File source = screenshot.getScreenshotAs(OutputType.FILE);
            File destination = new File(System.getProperty("user.dir") +
                    "/screenshots/" + testResult.getName() + ".png");
            try {
                FileHandler.copy(source, destination);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void searchProduct(WebElement webElement, String productName) {
        webElement.click();
        webElement.sendKeys(productName);
        webElement.sendKeys(Keys.ENTER);
    }
}
