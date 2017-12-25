package com.likg.uiautotest.po.f1;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class LoginProxy {

    private LoginExec loginExec;


    public LoginProxy(AndroidDriver<AndroidElement> driver) {
        this.loginExec = new LoginExec(driver);
    }

    public void login(String userName, String password) {
        loginExec.setUserName(userName);
        loginExec.setPassword(password);
        loginExec.clickLoginBut();
    }

}
