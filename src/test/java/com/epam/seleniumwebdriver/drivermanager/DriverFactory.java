package com.epam.seleniumwebdriver.drivermanager;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

public abstract class DriverFactory<T extends Capabilities> {

    protected abstract WebDriver createLocalDriver(T options);

    public WebDriver createDriver() {
        T options = getOptions();
        return createLocalDriver(options);
    }

    protected abstract T getOptions();
}