package com.sahibinden.test.pages;

import com.sahibinden.test.base.BasePage;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the Sahibinden login page.
 */
@Slf4j
public class LoginPage extends BasePage {

    // Locators
    private static final By EMAIL_INPUT    = By.id("username");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By SUBMIT_BUTTON  = By.cssSelector("button[type='submit']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Enters the email/username into the login form.
     */
    @Step("Enter Email Address: {email}")
    public LoginPage enterEmail(String email) {
        log.info("Entering email");
        findElement(EMAIL_INPUT).sendKeys(email);
        return this;
    }

    /**
     * Enters the password into the login form.
     */
    @Step("Enter Password: {password}")
    public LoginPage enterPassword(String password) {
        log.info("Entering password");
        findElement(PASSWORD_INPUT).sendKeys(password);
        return this;
    }

    /**
     * Submits the login form.
     */
    @Step("Click Submit Button")
    public LoginPage submit() {
        log.info("Submitting login form");
        click(SUBMIT_BUTTON);
        return this;
    }
}
