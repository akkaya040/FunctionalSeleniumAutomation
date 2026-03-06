package com.sahibinden.test.base;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Abstract base class for all Page Objects.
 * Provides common Selenium interaction helpers.
 */
@Slf4j
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    public static final int DEFAULT_WAIT_SECONDS = 15;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_SECONDS));
    }

    /**
     * Waits for the element to be present, scrolls it into view, and waits until it is clickable.
     *
     * @param by    locator strategy
     * @param index optional index when multiple elements match
     * @return the located WebElement
     */
    protected WebElement findElement(By by, int... index) {
        untilElementAppear(by);
        try {
            WebElement element = (index.length == 0)
                    ? driver.findElement(by)
                    : driver.findElements(by).get(index[0]);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView(false); arguments[0].focus();", element);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return element;
        } catch (Exception e) {
            log.error("Could not find element [{}]: {}", by, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds an element and clicks it.
     *
     * @param by    locator strategy
     * @param index optional index when multiple elements match
     */
    protected void click(By by, int... index) {
        try {
            findElement(by, index).click();
        } catch (Exception e) {
            log.error("Could not click element [{}]: {}", by, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Waits until at least one element matching the locator is present in the DOM.
     *
     * @param by locator strategy
     */
    protected void untilElementAppear(By by) {
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
        } catch (Exception e) {
            log.error("Element did not appear [{}]: {}", by, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
