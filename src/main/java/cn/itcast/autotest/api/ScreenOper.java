package cn.itcast.autotest.api;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 屏幕操作
 */
public class ScreenOper {

    private AndroidDriver<AndroidElement> driver;

    //屏幕放大缩小
    @Test
    public void zoom() throws InterruptedException {
        System.out.println("zoom...");
        Thread.sleep(5000);

        //在屏幕的元素上放大
        AndroidElement element = driver.findElementById("com.android.dialer:id/search_box_start_search");
        //TODO:有的元素好像不支持缩放
        //AndroidElement element = driver.findElementById("com.android.dialer:id/floating_action_button_container");
        driver.zoom(element);
        System.out.println("11111");
        //在屏幕的指定坐标上放大
        driver.zoom(200, 200);

        //在屏幕的元素上缩小
        driver.pinch(element);
        //在屏幕的指定坐标上缩小
        driver.pinch(200, 200);
        Thread.sleep(10000);
    }

    //横竖屏切换
    @Test
    public void rotate() throws InterruptedException {
        System.out.println("rotate...");
        Thread.sleep(5000);

        //获取屏幕朝向
        ScreenOrientation orientation = driver.getOrientation();
        System.out.println("orientation=" + orientation);

        //切换成横屏
        driver.rotate(ScreenOrientation.LANDSCAPE);
        Thread.sleep(5000);

        //切换成竖屏
        driver.rotate(ScreenOrientation.PORTRAIT);
        Thread.sleep(10000);
    }

    //截屏
    @Test
    public void screenshot() throws Exception {
        System.out.println("screenshot...");
        Thread.sleep(10000);

        File screenshot = driver.getScreenshotAs(OutputType.FILE);
        File destFile = new File("D:/screenshot/a.png");
        FileUtils.copyFile(screenshot, destFile);

        Thread.sleep(10000);
    }

    @BeforeTest
    public void beforeTest() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "emulator");
        capabilities.setCapability("appPackage", "com.android.dialer");
        capabilities.setCapability("appActivity", ".DialtactsActivity");
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
