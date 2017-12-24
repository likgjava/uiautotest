package com.likg.uiautotest.testng;


import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * 忽略测试
 * @author likg
 */
@Listeners(TestngListener.class)
public class ListenerTest {

    @Test
    public void foo(){
        System.out.println("foo...");
        "".charAt(2);
    }

}
