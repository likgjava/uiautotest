package com.likg.uiautotest.po.f2;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LoginPage {

    private AndroidDriver<AndroidElement> driver;

    private AndroidElement userNameElement;
    private AndroidElement passwordElement;
    private AndroidElement loginButElement;

    public LoginPage(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;

        //判断是否为登录页面
        if (driver.findElement(By.xpath("//android.widget.Button[@text='登录']")) == null) {
            throw new RuntimeException("This is not login page!");
        }

        this.userNameElement = driver.findElementById("net.csdn.csdnplus:id/editTextUserName");
        this.passwordElement = driver.findElementById("net.csdn.csdnplus:id/password");
        this.loginButElement = driver.findElementById("net.csdn.csdnplus:id/csdnsign_in_button");
    }


    public HomePage login(String userName, String password) {
        this.userNameElement.sendKeys(userName);
        this.passwordElement.sendKeys(password);
        this.loginButElement.click();

        final WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement until = wait.until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return webDriver.findElement(By.id("net.csdn.csdnplus:id/tvtitle"));
            }
        });
        Assert.assertEquals(until.getText(), "头条");

        return new HomePage(this.driver);
    }


    public WebElement invalidLogin(String userName, String password, final String errorMsg) {
        this.userNameElement.sendKeys(userName);
        this.passwordElement.sendKeys(password);
        this.loginButElement.click();

        final WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement until = wait.until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return webDriver.findElement(By.xpath(".//*[contains(@text,'" + errorMsg + "')]"));
            }
        });
        return until;
    }


}
