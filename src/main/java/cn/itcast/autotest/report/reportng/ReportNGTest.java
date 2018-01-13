package cn.itcast.autotest.report.reportng;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.uncommons.reportng.HTMLReporter;
import org.uncommons.reportng.JUnitXMLReporter;

/**
 * ReportNG使用方式
 * 用注解的方式添加HTMLReporter和JUnitXMLReporter监听器，就可以生成html和xml格式的测试报告
 */
@Listeners({HTMLReporter.class, JUnitXMLReporter.class})
public class ReportNGTest {

    @Test
    public void foo(){
        System.out.println("foo...");
    }

    @Test
    public void test(){
        System.out.println("test...");
    }

    @Test
    public void bar(){
        System.out.println("bar...");
        "".charAt(2);
    }
}
