package com.sahibinden.test.pages;

import com.sahibinden.test.base.BasePage;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object for the Sahibinden home page (https://www.sahibinden.com).
 */
@Slf4j
public class HomePage extends BasePage {

    private static final String URL = "https://www.sahibinden.com";

    // Locators
    private static final By BUTTON_LOGIN = By.cssSelector("a[href='https://secure.sahibinden.com/giris']");
    private static final By AD_SCREEN    = By.cssSelector("[class='splash-360-home']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigates to the home page.
     */
    public HomePage open() {
        log.info("Navigating to: {}", URL);
        driver.get(URL);
        return this;
    }

    /**
     * Closes the splash/ad overlay if present.
     */
    public HomePage closeAd() {
        log.info("Closing ad screen");
        wait.until(ExpectedConditions.elementToBeClickable(AD_SCREEN)).click();
        return this;
    }

    /**
     * Clicks the login button and returns the LoginPage.
     */
    @Step("Click Login Button")
    public LoginPage clickLoginButton() {
        log.info("Clicking login button");
        click(BUTTON_LOGIN);
        return new LoginPage(driver);
    }
}
