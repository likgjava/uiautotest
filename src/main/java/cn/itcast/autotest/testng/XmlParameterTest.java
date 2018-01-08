package cn.itcast.autotest.testng;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * 依赖测试
 *
 * @author likg
 */
public class XmlParameterTest {
    @Test
    @Parameters("username")
    public void test(String name) {
        System.out.println("username=" + name);
    }
}
