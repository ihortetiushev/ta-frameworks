package com.epam.seleniumwebdriver.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends PageFactory {

    public CartPage(WebDriver driver) {
        initElements(driver, this);
    }

    @FindBy(how = How.XPATH, using = "//*[name()='svg'][contains(@class, 'vi') and contains(@class, 'plus')]")
    public WebElement addQuantity;
    @FindBy(how = How.XPATH, using = "//*[@class='v-modal__close-btn']")
    public WebElement closeButton;

    @FindBy(how = How.XPATH, using = "//div[@class='title']/a[@target]")
    public WebElement actualElement;

    @FindBy(how = How.XPATH, using = "//*[@class='qty__count']//*[@class='input']")
    public WebElement productQuantity;
    public String buyButtonXpath = "//*[@id='product-buy-button']";

    @FindBy(how = How.XPATH, using = "(//*[@class='products__container']//*[@class='product-item__wrap']//*[@class='vi i-shared vi__close remove'])[1]")
    public WebElement deleteProduct1;
    @FindBy(how = How.XPATH, using = "(//*[@class='products__container']//*[@class='product-item__wrap']//*[@class='vi i-shared vi__close remove'])[2]")
    public WebElement deleteProduct2;
}
