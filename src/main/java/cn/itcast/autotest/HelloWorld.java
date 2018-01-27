package cn.itcast.autotest;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * appium入门示例
 */
public class HelloWorld {

    private AndroidDriver<WebElement> driver;

    @Test
    public void swipe() throws InterruptedException {
        System.out.println("swipe...");
        Thread.sleep(5000);

        //获取屏幕的宽度和高度
        int width = driver.manage().window().getSize().getWidth();
        int height = driver.manage().window().getSize().getHeight();
        System.out.println("width=" + width);
        System.out.println("height=" + height);

        //左滑
        driver.swipe(width * 9 / 10, height / 2, width / 10, height / 2, 500);
        Thread.sleep(5000);
        //右滑
        driver.swipe(width / 10, height / 2, width * 9 / 10, height / 2, 500);
        Thread.sleep(5000);
    }

    @BeforeTest
    public void beforeTest() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "emulator");
        capabilities.setCapability("app", "D:/apk/zhihu.apk");

        URL url = new URL("http://127.0.0.1:4723/wd/hub/");
        driver = new AndroidDriver<>(url, capabilities);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}