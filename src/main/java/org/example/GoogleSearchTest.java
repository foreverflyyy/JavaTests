package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class GoogleSearchTest {

    WebDriver wd;

    @BeforeTest()
    public void beforeTestFixture() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");

        wd = WebDriverManager
                .chromedriver()
                .capabilities(chromeOptions)
                .create();
    }

    @Test()
    public void googleSearchTest() {
        wd.get("https://www.google.com/");
        wd.manage().window().maximize();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException i) {
            i.printStackTrace(System.err);
        }

        String title = wd.getTitle();
        Assert.assertTrue(title.contains("Google"), "Title should contain 'Google'");

        WebElement searchBox = wd.findElement(By.name("q"));
        searchBox.sendKeys("Selenium WebDriver");

        WebElement searchButton = wd.findElement(By.name("btnK"));
        searchButton.submit();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String resultTitle = wd.getTitle();
        Assert.assertTrue(resultTitle.contains("Selenium WebDriver"), "Result page title should contain 'Selenium WebDriver'");

        WebElement resultStats = wd.findElement(By.id("result-stats"));
        Assert.assertTrue(resultStats.isDisplayed(), "Search results stats should be visible");

        boolean hasResults = !wd.findElements(By.cssSelector("h3")).isEmpty();
        Assert.assertTrue(hasResults, "There should be search results on the page");
    }
}