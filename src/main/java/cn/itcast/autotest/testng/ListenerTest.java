package cn.itcast.autotest.testng;

import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.uncommons.reportng.HTMLReporter;
import org.uncommons.reportng.JUnitXMLReporter;

/**
 * 监听测试
 */
@Listeners({TestngListener.class, HTMLReporter.class, JUnitXMLReporter.class})
public class ListenerTest {

    @Test
    public void foo(){
        System.out.println("foo...");
        Reporter.log("出现异常了......error.....");
        "".charAt(2);
    }

    @Test
    public void bar(){
        System.out.println("bar...");
    }
}
