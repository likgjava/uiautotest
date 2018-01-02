package com.likg.uiautotest.concurrent;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

//appium -a 127.0.0.1 -p 4727 -bp 4728 --chromedriver-port 9519 -U xiaomi --session-override
//appium -a 127.0.0.1 -p 4723 -bp 4724 -U 192.168.120.101:5555
//appium -a 127.0.0.1 -p 4733 -bp 4734 -U 192.168.120.102:5555
public class ConcurrentTest {

    @Test
    @Parameters({"username", "password", "udid", "port"})
    public void test(String name, String password, String udid, int port) throws MalformedURLException, InterruptedException {
        System.out.println("username=" + name + " password="+password);
        AndroidDriver<WebElement> driver = this.getDriver(udid, port);

        Thread.sleep(5000);
        System.out.println("port====="+port + " thread="+Thread.currentThread().getId());
        driver.findElementById("net.csdn.csdnplus:id/editTextUserName").sendKeys(name);
        driver.findElementById("net.csdn.csdnplus:id/password").sendKeys(password);
        driver.findElementById("net.csdn.csdnplus:id/csdnsign_in_button").click();

        Thread.sleep(5000);

        driver.quit();
    }

    public AndroidDriver<WebElement> getDriver(String udid, int port) throws MalformedURLException {
        System.out.println("getDriver...port="+port);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", udid);
        capabilities.setCapability("appPackage", "net.csdn.csdnplus");
        capabilities.setCapability("appActivity", "net.csdn.csdnplus.activity.SplashActivity");
        capabilities.setCapability("udid", udid);
        //capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Uiautomator2");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");

        AndroidDriver<WebElement> driver = new AndroidDriver<>(new URL("http://127.0.0.1:"+port+"/wd/hub/"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }


    //@Test
    public void exec() throws IOException {

        Process process = Runtime.getRuntime().exec("cmd /c java -version");

        List<String> strings = IOUtils.readLines(process.getInputStream());
        System.out.println("1111111111111111");
        System.out.println(strings);

        System.out.println("222222222222");
        strings = IOUtils.readLines(process.getErrorStream());
        System.out.println(strings);


//        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        System.out.println("111111");
//
//
//        String msg = null;
//        while ((msg = reader.readLine()) != null) {
//            System.out.println("line==="+msg);
//        }
//
//        reader.close();


    }
}
