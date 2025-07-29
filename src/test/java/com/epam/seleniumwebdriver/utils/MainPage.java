package com.epam.seleniumwebdriver.utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends PageFactory {

    private final WebDriver driver;

    @FindBy(how = How.XPATH, using = "//a[\n" +
            "  contains(@href, '/planshety-i-gadzhety') and\n" +
            "  contains(normalize-space(string(.)), 'Ноутбуки, ПК та планшети')\n" +
            "]\n")
    public WebElement laptopsPcTabletsCategory;
    @FindBy(how = How.XPATH, using = "//li[contains(@class, 'portal-group__item')][.//a[contains(@href, '/notebooks/')]]" +
            "//a[contains(@class, 'portal-card__link') and contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'apple')]")

    public WebElement laptopsPcTabletsCategoryAppleSelector;
    @FindBy(how = How.XPATH, using = "//span[text()='від дорогих до дешевих']")
    public WebElement sortButton;
    @FindBy(how = How.XPATH, using = "//ul[@class='sort-by__list']/li[@title='за популярністю']")
    public WebElement sortByPopularityButton;

    @FindBy(how = How.XPATH, using = "//*[contains(@class, 'v-catalog__empty') and contains(text(), 'Нажаль, нічого не знайдено')]")
    public WebElement emptyCatalogXpath;

    @FindBy(how = How.CLASS_NAME, using = "ct-button")
    public
    WebElement catalogButton;
    @FindBy(how = How.ID, using = "search-form__input")
    public
    WebElement searchBar;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        initElements(driver, this);
    }
}
