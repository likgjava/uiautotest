package cn.itcast.autotest;

import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Listeners(TListener.class)
public class T {

    @Test
    public void t() {
        System.out.println("t......方法调试。。。123");
        String userDir = System.getProperty("user.dir");
        System.out.println("userDir====" + userDir);

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DAY_OF_YEAR, 1);

        int i = cal.get(Calendar.DAY_OF_WEEK);
        System.out.println(i);

        Reporter.log("t()方法输出的内容....");

    }

    @Test
    public void printTime() {
        System.out.println("printTime......方法调试。。。123");
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("time==" + format);

        Reporter.log("printTime()111方法输出的内容....");

        "".charAt(2);

    }

}
