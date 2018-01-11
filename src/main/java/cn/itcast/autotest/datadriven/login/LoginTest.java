package cn.itcast.autotest.datadriven.login;

import cn.itcast.autotest.po.util.DriverUtil;
import com.alibaba.fastjson.JSONObject;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

/**
 * 登录流程测试
 */
public class LoginTest {
    private AndroidDriver<AndroidElement> driver;
    private LoginProxy loginProxy;
    private JSONObject testData;

    /**
     * 验证登录成功
     *
     * @throws Exception ex
     */
    @Test
    public void loginSuccess() throws Exception {
        System.out.println("loginSuccess....");
        Thread.sleep(3000);

        JSONObject jsonObject = testData.getJSONObject("loginSuccess");
        loginProxy.login(jsonObject.getString("userName"), jsonObject.getString("password"));

        AndroidElement element = driver.findElement(By.id("net.csdn.csdnplus:id/tvtitle"));
        Assert.assertEquals(element.getText(), "头条");
    }

    /**
     * 验证用户名为空
     *
     * @throws Exception ex
     */
    @Test
    public void loginUserNameIsNull() throws Exception {
        System.out.println("loginUserNameIsNull....");
        Thread.sleep(3000);

        JSONObject jsonObject = testData.getJSONObject("loginUserNameIsNull");
        loginProxy.login(jsonObject.getString("userName"), jsonObject.getString("password"));

        final String toast = "用户名密码不能为空";
        final WebDriverWait wait = new WebDriverWait(driver, 5);
        String xpath = String.format(".//*[contains(@text,'%s')]", toast);
        WebElement until = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        Assert.assertNotNull(until);

        Thread.sleep(10000);
    }

    /**
     * 验证密码错误
     *
     * @throws Exception ex
     */
    @Test
    public void loginPasswordIsError() throws Exception {
        System.out.println("loginPasswordIsError....");
        Thread.sleep(3000);

        JSONObject jsonObject = testData.getJSONObject("loginPasswordIsError");
        loginProxy.login(jsonObject.getString("userName"), jsonObject.getString("password"));

        String toast = "用户名或密码错误";
        final WebDriverWait wait = new WebDriverWait(driver, 5);
        String xpath = String.format(".//*[contains(@text,'%s')]", toast);
        WebElement toastEle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        Assert.assertNotNull(toastEle);

        Thread.sleep(10000);
    }

    @BeforeTest
    public void beforeTest() throws Exception {
        //初始化驱动
        this.driver = DriverUtil.getDriver();

        //加载测试数据
        this.testData = this.loadTestData();
        System.out.println("testData=" + testData.toJSONString());

        this.loginProxy = new LoginProxy();
    }

    private JSONObject loadTestData() throws IOException {
        File dataFile = new File(System.getProperty("user.dir") + "/data/datadriven/testData.json");
        String data = FileUtils.readFileToString(dataFile);
        return JSONObject.parseObject(data);
    }

    @AfterTest
    public void afterTest() {
        DriverUtil.quitDriver();
    }
}
