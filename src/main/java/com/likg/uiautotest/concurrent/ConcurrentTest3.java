package com.likg.uiautotest.concurrent;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConcurrentTest3 {

    @BeforeTest
    public void beforeTest() throws Exception {
        System.out.println("beforeTest...");

        //获取设备列表
        List<String> devices = AdbUtil.getDevices();

        //获取可用的端口
        List<Integer> portList = AdbUtil.getUnusedPort(4723, devices.size());



    }

    @DataProvider(parallel = true)
    public Object[][] userData() {
        System.out.println("dataProvider...");
        return new Object[][]{new Object[]{"tom", "123"},
                new Object[]{"java", "666"}};
    }

    @Test(dataProvider = "userData")
    public void test(String username, String password, String udid, int port) throws Exception {
        System.out.println("username=" + username + " port=" + port + " thread=" + Thread.currentThread().getId());
        //System.out.println("username=" + username + " port=" + port + " thread=" + Thread.currentThread().getId());
        /*AndroidDriver<WebElement> driver = this.getDriver(udid, port);

        Thread.sleep(5000);
        driver.findElementById("net.csdn.csdnplus:id/editTextUserName").sendKeys(username);
        driver.findElementById("net.csdn.csdnplus:id/password").sendKeys(password);
        driver.findElementById("net.csdn.csdnplus:id/csdnsign_in_button").click();

        Thread.sleep(5000);
        driver.quit();*/
    }

    private AndroidDriver<WebElement> getDriver(String udid, int port) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, udid);
        capabilities.setCapability(MobileCapabilityType.UDID, udid);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "net.csdn.csdnplus");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "net.csdn.csdnplus.activity.SplashActivity");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");

        String url = "http://127.0.0.1:" + port + "/wd/hub/";
        AndroidDriver<WebElement> driver = new AndroidDriver<>(new URL(url), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }
}
