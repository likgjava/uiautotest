package cn.itcast.autotest.api;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 系统操作
 */
public class SystemOperation {

    private AndroidDriver<AndroidElement> driver;

    @Test
    public void operation() throws InterruptedException {
        System.out.println("operation...");
        Thread.sleep(5000);

        //获取当前activity
        String currentActivity = driver.currentActivity();
        System.out.println("currentActivity=" + currentActivity);

        //判断该应用是否已经安装
        //bundleId在android中代表的是appPackage
        boolean installed = driver.isAppInstalled("com.zhihu.android");
        System.out.println("installed=" + installed);

        //安装app
        //driver.installApp("D://apk/zhihu.apk");

        //卸载app
        //bundleId在android中代表的是appPackage
        //driver.removeApp("com.zhihu.android");

        //关闭应用
        driver.closeApp();
        System.out.println("closeApp.....");
        Thread.sleep(3000);

        //启动应用
        driver.launchApp();
        System.out.println("launchApp.....");
        Thread.sleep(3000);

        //先closeApp然后在launchApp
        driver.resetApp();

        Thread.sleep(10000);
    }

    @Test
    public void operation2() throws InterruptedException {
        //打开通知栏
        driver.openNotifications();

        Thread.sleep(3000);
        System.out.println("runAppInBackground....start");

        //让app在后台运行指定的秒数
        //与resetApp类似，区别是resetApp关闭后立即启动，而这个方法是关闭后等待seconds秒后再启动
        driver.runAppInBackground(3);
        System.out.println("runAppInBackground....end");


        Thread.sleep(3000);

    }

    @Test
    public void pushFile() throws Exception {
        //将本地文件内容写到设备的指定文件中
        driver.pushFile("sdcard/Download/test.txt", new File("D://apk/test.txt"));

        //将字符数组用64位格式写到设备的指定文件中
        driver.pushFile("sdcard/Download/test2.txt", Base64.encodeBase64("abc中国人".getBytes()));

        Thread.sleep(5000);
    }

    @Test
    public void pullFile() throws Exception {
        byte[] bytes = driver.pullFile("sdcard/Download/test.txt");
        System.out.println(bytes.length);
        String pullData = new String(Base64.decodeBase64(bytes));
        System.out.println("pullData=" + pullData);

        Thread.sleep(5000);
    }

    @Test
    public void pullFolder() throws Exception {
        byte[] bytes = driver.pullFolder("sdcard/Download/");
        System.out.println(bytes.length);
        byte[] decodeBase64 = Base64.decodeBase64(bytes);
        System.out.println(decodeBase64.length);
        String pullData = new String(decodeBase64);
        System.out.println("pullData=" + pullData);

        FileUtils.writeByteArrayToFile(new File("D://pull/"), decodeBase64);

        //driver.pullFolder()

        Thread.sleep(5000);
    }

    @BeforeTest
    public void beforeTest() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "emulator");
        capabilities.setCapability("appPackage", "com.android.dialer");
        capabilities.setCapability("appActivity", ".DialtactsActivity");
        driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
