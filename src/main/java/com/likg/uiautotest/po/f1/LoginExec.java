package com.likg.uiautotest.po.f1;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class LoginExec {

    private LoginPage loginPage;

    public LoginExec(AndroidDriver<AndroidElement> driver) {
        this.loginPage = new LoginPage(driver);
    }

    public void setUserName(String userName) {
        this.loginPage.findUserName().sendKeys(userName);
    }

    public void setPassword(String password) {
        this.loginPage.findPassword().sendKeys(password);
    }

    public void clickLoginBut() {
        this.loginPage.findLoginBut().click();
    }

}
