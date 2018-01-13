package cn.itcast.autotest.po.m2.login;

/**
 * 登录页面-业务操作层
 */
public class LoginProxy {

    private LoginHandle loginHandle = new LoginHandle();

    public void login(String userName, String password) throws Exception {
        loginHandle.setUserName(userName);
        loginHandle.setPassword(password);
        loginHandle.clickLoginBut();
    }
}