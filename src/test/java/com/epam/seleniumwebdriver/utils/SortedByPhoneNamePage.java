package com.epam.seleniumwebdriver.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class SortedByPhoneNamePage extends PageFactory {

    private final WebDriver driver;

    @FindBy(how = How.XPATH, using = "//*[@class='sort-by__select']")
    public WebElement sortSpace;
    @FindBy(how = How.XPATH, using = "//*[@class='sort-by__list']//li[text()='від дорогих до дешевих']")
    public WebElement sortByExpensive;
    @FindBy(how = How.XPATH, using = "//*[@class='sort-by__list']//li[text()='від дешевих до дорогих']")
    public WebElement sortByCheap;
    @FindBy(how = How.XPATH, using = "//*[@class='products-layout__container products-layout--grid']/div[1]")
    public WebElement mostExpensiveItem;
    @FindBy(how = How.ID, using = "product-buy-button")
    public WebElement buyButton;
    @FindBy(how = How.XPATH, using = "//*[@class='related-products__button a-button a-button--outline a-button--primary']")
    public WebElement goToCart;
    @FindBy(how = How.ID, using = "search-form__input")
    public WebElement searchBar;
    @FindBy(how = How.XPATH, using = "//*[contains(@class, 'product-card__content')]//a[@href='https://allo.ua/ua/products/mobile/smartfon-google-pixel-9-pro-fold-16-512gb-obsidian-eu-usa-global-version-ga05799-us-51638.html' and @title]")
    public WebElement expectedPhone;
    @FindBy(how = How.XPATH, using = "//a[@href='https://allo.ua/ua/products/mobile/smartfon-google-pixel-9-pro-fold-16-512gb-obsidian-eu-usa-global-version-ga05799-us-51638.html']//*[@class='wrap']")
    public WebElement actualPhone;
    @FindBy(how = How.XPATH, using = "//*[contains(@class, 'product-card__content')]//*[@title='Зарядний пристрій Google Pixel Charger 30W']")
    public WebElement expectedCharger;
    @FindBy(how = How.XPATH, using = "//*[contains(@class, 'product-card__content')]//*[@title='Зарядний пристрій Google Pixel Charger 30W']")
    public WebElement actualCharger;
    public String sortedByPhoneNamePageUrl = "https://allo.ua/ua/catalogsearch/result/index/cat-48/?q=pixel%209%20pro";
    public String sortedByChargerPhonePageUrl = "https://allo.ua/ua/catalogsearch/result/index/dir-asc/order-price/?q=Pixel%2030W%20charger";
    public String sortedByMostExpensiveToCheapestPageUrl = "https://allo.ua/ua/catalogsearch/result/index/cat-48/dir-desc/order-price/?q=pixel%209%20pro";
    public String mostExpensiveItemPage = "https://allo.ua/ua/products/mobile/smartfon-google-pixel-9-pro-fold-16-512gb-obsidian-eu-usa-global-version-ga05799-us-51638.html";
    public String expectedTitlePhoneXpath = "//*[contains(@class, 'product-card__content')]" +
            "//a[@href='https://allo.ua/ua/products/mobile/smartfon-google-pixel-9-pro-fold-16-512gb-obsidian-eu-usa-global-version-ga05799-us-51638.html' and @title]";
    public String actualTitlePhoneXpath ="//a[@href='https://allo.ua/ua/products/mobile/" +
            "smartfon-google-pixel-9-pro-fold-16-512gb-obsidian-eu-usa-global-version-ga05799-us-51638.html']//*[@class='wrap']";
    public String actualTitleChargerXpath = "//a[@target='_self' and @href='https://allo.ua/ua/zarjadnye-ustrojstva/zarjadnoe-ustrojstvo-google-pixel-charger-30w.html']//*[@class='wrap']";
    @FindBy(how = How.XPATH, using = "//a[@target='_self' and @href='https://allo.ua/ua/zarjadnye-ustrojstva/zarjadnoe-ustrojstvo-google-pixel-charger-30w.html']//*[@class='wrap']")
    public WebElement actualTitleCharger;

    public String expectedChargerUrl = "https://allo.ua/ua/zarjadnye-ustrojstva/zarjadnoe-ustrojstvo-google-pixel-charger-30w.html";

    public SortedByPhoneNamePage(WebDriver driver) {
        this.driver = driver;
        initElements(driver, this);
    }
}
