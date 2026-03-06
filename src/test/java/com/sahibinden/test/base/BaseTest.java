package com.sahibinden.test.base;

import com.sahibinden.test.driver.DriverManager;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

/**
 * Base class for all test classes.
 * Handles WebDriver initialisation before each test and teardown after each test.
 */
public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    @Step("Drive Setup Steps")
    public void setUp() {
        driver = DriverManager.initDriver(DriverManager.BrowserType.CHROME);
    }

    @AfterEach
    @Step("Drive Quit Steps")
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
