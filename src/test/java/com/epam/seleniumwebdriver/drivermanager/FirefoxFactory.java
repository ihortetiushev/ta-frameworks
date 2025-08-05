package com.epam.seleniumwebdriver.drivermanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxFactory extends DriverFactory<FirefoxOptions> {
    @Override
    protected WebDriver createLocalDriver(FirefoxOptions options) {
        return new FirefoxDriver(options);
    }
    @Override
    protected FirefoxOptions getOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("test-type");
        options.addArguments("no-sandbox");
        options.addArguments("ignore-certificate-errors");
        options.addArguments("disable-default-apps");
        options.addArguments("disable-extensions-file-access-check");
        options.addArguments("disable-search-engine-choice-screen");
        options.addArguments("incognito");
        options.setAcceptInsecureCerts(true);
        return options;
    }
}
