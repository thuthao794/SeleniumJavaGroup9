package com.railway.test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class TC09 {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    private static final String URL = "http://railwayb1.somee.com/";
    private static final String USERNAME = System.getenv("RAILWAY_USERNAME") != null
            ? System.getenv("RAILWAY_USERNAME")
            : "thuthao7904@gmail.com";
    private static final String PASSWORD = System.getenv("RAILWAY_PASSWORD") != null
            ? System.getenv("RAILWAY_PASSWORD")
            : "thuthao070904";
    private final String newPassword = "thuthao123";

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void TC09_NguoiDungCoTheThayDoiMatKhau() {
        try {
            // Step 1: Truy cập trang web
            openUrl(URL);

            // Step 2: Đăng nhập
            login(USERNAME, PASSWORD);

            // Step 3: Đổi mật khẩu
            changePassword(PASSWORD, newPassword);

            // Step 4: Kiểm tra thông báo
            verifyMessage("//p[@class='message success']", "Your password has been updated!");

        } catch (Exception e) {
            logError("Lỗi trong quá trình thực thi TC09", e);
            Assert.fail("TC09 thất bại: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            System.out.println("Đóng trình duyệt...");
            driver.quit();
        }
    }

    private void openUrl(String url) {
        driver.get(url);
        System.out.println("Truy cập URL thành công: " + url);
    }

    private void login(String username, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Login"))).click();
        System.out.println("Đi đến trang Login...");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        System.out.println("Đăng nhập thành công với: " + username);
    }

    private void changePassword(String currentPassword, String newPassword) {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Change password"))).click();
        System.out.println("Đi đến trang Change Password...");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("currentPassword"))).sendKeys(currentPassword);
        driver.findElement(By.id("newPassword")).sendKeys(newPassword);
        driver.findElement(By.id("confirmPassword")).sendKeys(newPassword);

        WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit']"));
        js.executeScript("arguments[0].scrollIntoView(true);", submitBtn);
        js.executeScript("arguments[0].click();", submitBtn);

        System.out.println("Đổi mật khẩu từ '" + currentPassword + "' sang '" + newPassword + "' thành công.");
    }

    private void verifyMessage(String xpath, String expectedMessage) {
        String actualMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).getText();
        System.out.println("Thông báo hiển thị: " + actualMessage);
        Assert.assertEquals(actualMessage, expectedMessage, "Thông báo không khớp!");
    }

    private void logError(String message, Exception e) {
        System.err.println(message);
        e.printStackTrace();
    }
}
