package cn.itcast.autotest.po.f1;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * 登录页面-业务操作层
 */
public class LoginProxy {

    private LoginHandle loginHandle;

    public LoginProxy(AndroidDriver<AndroidElement> driver) {
        this.loginHandle = new LoginHandle(driver);
    }

    public void login(String userName, String password) {
        loginHandle.setUserName(userName);
        loginHandle.setPassword(password);
        loginHandle.clickLoginBut();
    }
}
