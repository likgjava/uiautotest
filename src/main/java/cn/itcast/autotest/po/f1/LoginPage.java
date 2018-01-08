package cn.itcast.autotest.po.f1;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * 登录页面-对象库层
 */
public class LoginPage {

    private AndroidDriver<AndroidElement> driver;

    public LoginPage(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
    }

    public AndroidElement findUserNameElement() {
        return this.driver.findElementById("net.csdn.csdnplus:id/editTextUserName");
    }

    public AndroidElement findPasswordElement() {
        return this.driver.findElementById("net.csdn.csdnplus:id/password");
    }

    public AndroidElement findLoginButElement() {
        return this.driver.findElementById("net.csdn.csdnplus:id/csdnsign_in_button");
    }
}
