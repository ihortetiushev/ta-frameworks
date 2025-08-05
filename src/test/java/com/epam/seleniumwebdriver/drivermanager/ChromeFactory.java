package com.epam.seleniumwebdriver.drivermanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeFactory extends DriverFactory<ChromeOptions> {

    @Override
    protected WebDriver createLocalDriver(ChromeOptions options) {
        return new ChromeDriver(options);
    }

    @Override
    protected ChromeOptions getOptions() {
        ChromeOptions options = new ChromeOptions();
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
