package com.epam.seleniumwebdriver.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class SortedByPopularityPage extends PageFactory {

    private final WebDriver driver;

    public static String expectedLaptopXpath = "//*[contains(@class, 'product-card__content')]//a[text()='Ноутбук Apple MacBook Air 13 M1 (MGN63) Space Grey']";
    @FindBy(how = How.XPATH, using = "//*[@class='products-layout__container products-layout--grid']/div[1]")
    public WebElement sortByPopularityItem;
    public String expectedUrl = "https://allo.ua/ua/products/notebooks/apple-macbook-air-13-m1-mgn63-space-grey.html";
    @FindBy(how = How.ID, using = "product-buy-button")
    public WebElement buyButton;
    @FindBy(how = How.XPATH, using = "//*[@class='related-products__button a-button a-button--outline a-button--primary']")
    public WebElement goToCartButton;
    @FindBy(how = How.XPATH, using = "//*[contains(@class, 'product-card__content')]//a[text()='Ноутбук Apple MacBook Air 13 M1 (MGN63) Space Grey']")
    public WebElement expectedLaptop;

    public SortedByPopularityPage(WebDriver driver) {
        this.driver = driver;
        initElements(driver, this);
    }
}
