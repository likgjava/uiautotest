package com.likg.uiautotest.po.f1;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class LoginPage {

    private AndroidDriver<AndroidElement> driver;

    public LoginPage(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
    }

    public AndroidElement findUserName() {
        return this.driver.findElementById("net.csdn.csdnplus:id/editTextUserName");
    }

    public AndroidElement findPassword() {
        return this.driver.findElementById("net.csdn.csdnplus:id/password");
    }

    public AndroidElement findLoginBut() {
        return this.driver.findElementById("net.csdn.csdnplus:id/csdnsign_in_button");
    }

}
