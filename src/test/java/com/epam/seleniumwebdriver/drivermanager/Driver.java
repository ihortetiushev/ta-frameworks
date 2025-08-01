package com.epam.seleniumwebdriver.drivermanager;

import com.epam.seleniumwebdriver.core.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;

public class Driver {
    private WebDriver webDriver;


    public WebDriver createDriver(DriverType driverType) {
        WebDriver webDriver;
        switch (driverType) {
            case Edge:
                WebDriverManager.edgedriver().setup();
                webDriver = WebDriverManager.edgedriver().create();
                break;

            case Chrome:
                WebDriverManager.chromedriver().setup();
                webDriver = WebDriverManager.chromedriver().create();
                break;

            case Firefox:
                WebDriverManager.firefoxdriver().setup();
                webDriver = WebDriverManager.chromedriver().create();
                break;

            default:
                throw new IllegalArgumentException("Driver type " + driverType + " is not supported");
        }
        return webDriver;
    }

    public WebDriver getDriver() {
        if (webDriver == null) {
            String config = ConfigReader.get("driver");
            if(config == null) {
                config = DriverType.Chrome.name();
            }
            DriverType driverType = DriverType.valueOf(config);
            webDriver = createDriver(driverType);
            webDriver.manage().window().minimize();
        }
        return webDriver;
    }

    public void closeDriver() {
        if (webDriver != null) {
            try {
                webDriver.quit();
            } catch (NoSuchSessionException ignored) {}
            webDriver = null;
        }
    }
}
