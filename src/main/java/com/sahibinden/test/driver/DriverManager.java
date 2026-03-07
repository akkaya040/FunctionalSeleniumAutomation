package com.sahibinden.test.driver;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

/**
 * Manages WebDriver lifecycle using ThreadLocal to support parallel test execution.
 */
@Slf4j
public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final int IMPLICIT_WAIT_SECONDS = 5;

    public enum BrowserType {
        CHROME, FIREFOX, SAFARI, EDGE
    }

    private DriverManager() {}

    /**
     * Initialises a WebDriver instance for the calling thread.
     *
     * @param browser browser to launch
     * @return the created WebDriver
     */
    public static WebDriver initDriver(BrowserType browser) {
        WebDriver driver;
        switch (browser) {
            case EDGE:
                driver = new EdgeDriver();
                break;
            case SAFARI:
                driver = new SafariDriver();
                break;
            case FIREFOX:
                driver = new FirefoxDriver();
                break;
            case CHROME:
            default:
                driver = new ChromeDriver();
                break;
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));
        driver.manage().window().maximize();
        driverThreadLocal.set(driver);
        log.info("WebDriver initialized: {}", browser);
        return driver;
    }

    /**
     * Returns the WebDriver bound to the calling thread.
     */
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    /**
     * Quits the WebDriver bound to the calling thread and removes it from the ThreadLocal.
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
            log.info("WebDriver quit successfully.");
        }
    }
}
