package cn.itcast.autotest.po.f2;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * 登录页面
 */
public class LoginPage {

    private AndroidDriver<AndroidElement> driver;

    private AndroidElement userNameElement;
    private AndroidElement passwordElement;
    private AndroidElement loginButElement;

    public LoginPage(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;

        this.userNameElement = driver.findElementById("net.csdn.csdnplus:id/editTextUserName");
        this.passwordElement = driver.findElementById("net.csdn.csdnplus:id/password");
        this.loginButElement = driver.findElementById("net.csdn.csdnplus:id/csdnsign_in_button");
    }

    /**
     * 正常登录流程
     * @param userName 用户名
     * @param password 密码
     * @return 主页对象
     */
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

    /**
     * 无效登录流程
     * @param userName 用户名
     * @param password 密码
     * @param errorMsg 错误提示信息
     * @return 错误提示框组件
     */
    public WebElement invalidLogin(String userName, String password, final String errorMsg) {
        this.userNameElement.sendKeys(userName);
        this.passwordElement.sendKeys(password);
        this.loginButElement.click();

        final WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement element = wait.until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return webDriver.findElement(By.xpath(".//*[contains(@text,'" + errorMsg + "')]"));
            }
        });
        return element;
    }

}
