package com.likg.uiautotest.concurrent;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConcurrentTest3 {

    private List<String> devices;
    private List<Integer> portList;
    private List<User> userList;

    @BeforeTest
    public void beforeTest() throws Exception {
        System.out.println("beforeTest...");

        //获取设备列表
        devices = AdbUtil.getDevices();
        System.out.println("devices=" + devices);

        //获取可用的端口
        portList = SystemUtil.getUnusedPort(4723, devices.size());
        System.out.println("portList=" + portList);

        //获取用户数据
        userList = getUserList(devices.size());

        //启动appium服务
        for (int i = 0; i < devices.size(); i++) {
            AppiumServer.startServer(portList.get(i));
        }
        Thread.sleep(10000);
    }

    @DataProvider(parallel = true)
    public Object[][] userData() {
        System.out.println("dataProvider...");
        Object[][] dataList = new Object[this.devices.size()][4];
        for (int i = 0; i < devices.size(); i++) {
            Object[] data = new Object[4];
            User user = this.userList.get(i);
            data[0] = user.getUserName();
            data[1] = user.getPassword();
            data[2] = this.devices.get(i);
            data[3] = this.portList.get(i);
            dataList[i] = data;
        }
        return dataList;
    }

    @Test(dataProvider = "userData")
    public void test(String username, String password, String udid, int port) throws Exception {
        System.out.println("username=" + username + " udid=" + udid + " port=" + port + " thread=" + Thread.currentThread().getId());
        AndroidDriver<WebElement> driver = this.getDriver(udid, port);

        Thread.sleep(5000);
        driver.findElementById("net.csdn.csdnplus:id/editTextUserName").sendKeys(username);
        driver.findElementById("net.csdn.csdnplus:id/password").sendKeys(password);
        driver.findElementById("net.csdn.csdnplus:id/csdnsign_in_button").click();

        Thread.sleep(5000);
        driver.quit();
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

    private List<User> getUserList(int size) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            userList.add(new User("user" + i, "p" + i));
        }
        return userList;
    }

    @AfterTest
    public void afterTest() {
        for (Integer port : this.portList) {
            try {
                System.out.println("stopServer...port=" + port);
                AppiumServer.stopServer(port);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
