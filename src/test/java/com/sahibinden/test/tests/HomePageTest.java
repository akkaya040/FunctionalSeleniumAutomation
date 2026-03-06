package com.sahibinden.test.tests;

import com.sahibinden.test.base.BaseTest;
import com.sahibinden.test.pages.HomePage;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Sahibinden home page.
 * WebDriver lifecycle is handled by BaseTest (@BeforeEach / @AfterEach).
 */
public class HomePageTest extends BaseTest {

    @Test
    public void testOpenHomePage() {
        new HomePage(driver)
                .open();
    }

    @Test
    public void testClickLoginButton() {
        new HomePage(driver)
                .open()
                .clickLoginButton();
    }

    @Test
    public void testCloseAdAndClickLogin() {
        new HomePage(driver)
                .open()
                .closeAd()
                .clickLoginButton();
    }
}
