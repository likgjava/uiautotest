package com.likg.uiautotest.po.f1;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * 登录页面-操作层
 */
public class LoginHandle {

    private LoginPage loginPage;

    public LoginHandle(AndroidDriver<AndroidElement> driver) {
        this.loginPage = new LoginPage(driver);
    }

    public void setUserName(String userName) {
        this.loginPage.findUserNameElement().sendKeys(userName);
    }

    public void setPassword(String password) {
        this.loginPage.findPasswordElement().sendKeys(password);
    }

    public void clickLoginBut() {
        this.loginPage.findLoginButElement().click();
    }

}
