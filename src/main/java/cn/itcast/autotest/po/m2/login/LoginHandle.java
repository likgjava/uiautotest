package cn.itcast.autotest.po.m2.login;

/**
 * 登录页面-操作层
 */
public class LoginHandle {

    private LoginPage loginPage = new LoginPage();

    public void setUserName(String userName) throws Exception {
        this.loginPage.findUserNameElement().sendKeys(userName);
    }

    public void setPassword(String password) throws Exception {
        this.loginPage.findPasswordElement().sendKeys(password);
    }

    public void clickLoginBut() throws Exception {
        this.loginPage.findLoginButElement().click();
    }

}
