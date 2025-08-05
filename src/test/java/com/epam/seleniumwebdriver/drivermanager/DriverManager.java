package com.epam.seleniumwebdriver.drivermanager;

import com.epam.seleniumwebdriver.core.ConfigReader;
import org.openqa.selenium.WebDriver;

import java.util.Objects;

public class DriverManager {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private static WebDriver webDriver;

    private static void createDriver() {
        String driverType = ConfigReader.get("driver");
        switch (driverType) {
            case "Edge":
                DRIVER.set(new EdgeFactory().createDriver());
                break;
            case "Chrome":
                DRIVER.set(new ChromeFactory().createDriver());
                break;
            case "Firefox":
                DRIVER.set(new FirefoxFactory().createDriver());
                break;
            default:
                throw new IllegalArgumentException(String.format("Driver type %s is not supported", driverType));
        }
    }

    public static WebDriver getDriver() {
        if (Objects.isNull(DRIVER.get())) {
            createDriver();
        }
        return DRIVER.get();
    }

    public static void closeDriver() {
        if (Objects.nonNull(DRIVER.get())) {
            DRIVER.get().quit();
            DRIVER.remove();
        }
    }
}
