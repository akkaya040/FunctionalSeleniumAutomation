package com.sahibinden.test;


import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Slf4j
public class AbstractFunctions {

    protected RemoteWebDriver driver;
    protected WebDriverWait wait, waitZero, waitLoader;
    public static final int DEFAULT_WAIT = 60;
    public static final int DEFAULT_WAIT_LOADER = 60;

    public void AbstractFunctions(){

        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT));
        this.waitZero = new WebDriverWait(driver, Duration.ofSeconds(0));
        this.waitLoader = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_LOADER));// 90
    }

    protected WebElement findElement(By by, int... index) throws InterruptedException {
        // driver.manage().timeouts().implicitlyWait(GetData.DEFAULT_WAIT,
        // TimeUnit.SECONDS);

        WebElement element = null;
        untilElementAppear(by);
        try {
            if (index.length == 0)
                element = driver.findElement(by);
            else
                element = driver.findElements(by).get(index[0]);

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);arguments[0].focus();",
                    element);
            // ((JavascriptExecutor)
            // driver).executeScript("arguments[0].focus();", element);
            // wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            log.error("Error while clicking webelement : " + e);

            throw new RuntimeException(e);
        }
        return element;
    }


    /**
     * Use this method click to element
     *
     * @param by
     * @param index
     * @throws InterruptedException
     */
    protected void click(By by, int... index) throws InterruptedException {

        WebElement element;
        try {
            element = findElement(by, index);
            //String elemText = element.getText();
            element.click();
            //LogPASS("Click Button : " + elemText);

        } catch (Exception e) {
            log.error("Error while clicking webelement : " + e);
            log.info("Error while clicking webelement : " + e);

            throw new RuntimeException(e);
        }
    }


    /**
     * Wait until element appears
     *
     * @param by
     */
    protected void untilElementAppear(By by) {

        try {
            //waitLoaderBox(GetData.DEFAULT_WAIT_LOADERBOX);// , 40
            Thread.sleep(1000);
            // driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
            // wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Exception e) {
            log.error("Error while waiting until element appears : " + e);
            throw new RuntimeException(e);
        }
    }







}
