package cn.itcast.autotest.testng;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * 注解方式的参数化测试
 */
public class DataProviderTest {
    @DataProvider
    public Object[][] userData() {
        return new Object[][]{new Object[]{1, "tom"}, new Object[]{2, "lucy"}};
    }

    @Test(dataProvider = "userData")
    public void printUserList(int userId, String username) {
        System.out.println("userId=" + userId + "\tusername=" + username);
    }
}
