package com.sahibinden.test.Pages;

import com.sahibinden.test.AbstractFunctions;
import com.sahibinden.test.ObjectRepos.ObjectRepo;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Page extends AbstractFunctions{

    private WebDriver driver;
    private AbstractFunctions af = new AbstractFunctions();

    public Page runChrome()   {

        String driverPath =  System.getProperty("user.dir");
        System.out.println("Project path : " + driverPath);
        //System.setProperty("webdriver.chrome.driver", "/Users/akkaya/Projects/Selenium/chromedriver86");
        //driver = new ChromeDriver();
        driver = WebDriverManager.chromedriver().create();

        // tum elementler icin maksimum 15 bekleyeck
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().window().maximize();

        return this;
    }

    public Page runFirefox()   {

        String driverPath =  System.getProperty("user.dir");
        System.out.println("Project path : " + driverPath);
        System.setProperty("webdriver.firefox.driver", "/Users/optiim/seleniumSahibinden/firefoxdriver");

        driver = new FirefoxDriver();

        // tum elementler icin maksimum 15 bekleyeck
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().window().maximize();

        return this;
    }


    public Page stopDriver() {
        driver.close();
        driver.quit();


        return this;
    }

    public Page openPage() {

        System.out.println("Driver is going to Sahibinden..");
        driver.get("https://www.sahibinden.com");

        return this;
    }


    public Page clickLoginButton() {

        // if(driver.findElement(ObjectRepo.buttonLogin).getSize() != 0){
        //
        // }

        driver.findElement(ObjectRepo.buttonLogin).click();

        return this;
    }

    public Page closeAd() {
        WebElement element = (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(ObjectRepo.adScreen));
        element.click();;

        return this;
    }
}
