package cn.itcast.autotest.testng;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * 监听测试
 */
@Listeners(TestngListener.class)
public class ListenerTest {

    @Test
    public void foo(){
        System.out.println("foo...");
        "".charAt(2);
    }
}
