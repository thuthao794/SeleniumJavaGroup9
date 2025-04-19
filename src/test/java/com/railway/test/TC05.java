package com.railway.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TC05 {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void wrongPasswordSeveralTimes() throws InterruptedException {
        // Step 1: Navigate to QA Railway Website
        driver.get("http://railwayb1.somee.com");

        // Step 2-5: Perform login attempts with valid username and invalid password
        for (int i = 1; i <= 4; i++) {
            driver.findElement(By.linkText("Login")).click();
            driver.findElement(By.id("username")).clear();
            driver.findElement(By.id("username")).sendKeys("thuthao7904@gmail.com"); // Replace with a valid username
            driver.findElement(By.id("password")).clear();
            driver.findElement(By.id("password")).sendKeys("12346"); // Invalid password
            driver.findElement(By.cssSelector("input[type='submit']")).click();

            // Wait for the system to process the login attempt
            Thread.sleep(1000);

            // Step 5: Verify the error message after the 4th attempt
            if (i == 4) {
                WebElement errorMsg = driver.findElement(By.cssSelector(".message.error.LoginForm"));
                String actualText = errorMsg.getText();
                System.out.println("Error Message: " + actualText);

                Assert.assertTrue(actualText.contains("You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes."),
                        "Error message does not match the expected text.");
            }
        }
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}