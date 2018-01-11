package cn.itcast.autotest.po.m2.login;

import cn.itcast.autotest.po.m2.listener.TestngListener;
import cn.itcast.autotest.po.util.DriverUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * 登录流程测试
 */
@Listeners(TestngListener.class)
public class LoginTest {
    private AndroidDriver<AndroidElement> driver;
    private LoginProxy loginProxy;

    /**
     * 验证登录成功
     * @throws Exception ex
     */
    @Test
    public void loginSuccess() throws Exception {
        System.out.println("loginSuccess 开始执行.....");
        loginProxy.login("likg_java", "meimima");

        final WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement until = wait.until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver webDriver) {
                return webDriver.findElement(By.id("net.csdn.csdnplus:id/tvtitle"));
            }
        });
        Assert.assertEquals(until.getText(), "头条");

        Thread.sleep(10000);
    }

    /**
     * 验证用户名为空
     * @throws Exception ex
     */
    @Test
    public void loginUsernameIsNull() throws Exception {
        loginProxy.login("", "meimima");

        final String toast = "用户名密码不能为空123";
        System.out.println("toast=======" + toast);
        final WebDriverWait wait = new WebDriverWait(driver, 5);
        String xpath = String.format(".//*[contains(@text,'%s')]", toast);
        WebElement until = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        Assert.assertNotNull(until);

        Thread.sleep(10000);
    }

    /**
     * 验证密码错误
     * @throws Exception ex
     */
    @Test
    public void loginPasswordIsError() throws Exception {
        loginProxy.login("likg_java", "123");

        String toast = "用户名或密码错误";
        System.out.println("toast=======" + toast);
        final WebDriverWait wait = new WebDriverWait(driver, 5);
        String xpath = String.format(".//*[contains(@text,'%s')]", toast);
        WebElement toastEle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        Assert.assertNotNull(toastEle);

        Thread.sleep(10000);
    }

    @BeforeTest
    public void beforeTest() throws Exception {
        this.driver = DriverUtil.getDriver();

        this.loginProxy = new LoginProxy();
    }

    @AfterTest
    public void afterTest() {
        DriverUtil.quitDriver();
    }
}
