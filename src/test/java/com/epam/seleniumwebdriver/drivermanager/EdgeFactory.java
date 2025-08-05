package com.epam.seleniumwebdriver.drivermanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeFactory extends DriverFactory<EdgeOptions> {

    @Override
    protected WebDriver createLocalDriver(EdgeOptions options) {
        return new EdgeDriver(options);
    }
    @Override
    protected EdgeOptions getOptions() {
        EdgeOptions options = new EdgeOptions();
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