package cn.itcast.autotest.kw.util;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * 驱动工具类
 */
public class DriverUtil {

    private static AndroidDriver<WebElement> driver;

    /**
     * 获取驱动，第一次调用时要初始化驱动
     * @return 驱动对象
     * @throws MalformedURLException ex
     */
    public static AndroidDriver<WebElement> getDriver() throws MalformedURLException {
        System.out.println("getDriver...");
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    private static void initDriver() throws MalformedURLException {
        System.out.println("initDriver...");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "net.csdn.csdnplus");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "net.csdn.csdnplus.activity.SplashActivity");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Uiautomator2");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub/"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
