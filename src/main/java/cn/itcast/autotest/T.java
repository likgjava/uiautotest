package cn.itcast.autotest;

import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class T {

    @Test
    public void t() {
        String userDir = System.getProperty("user.dir");
        System.out.println("userDir====" + userDir);

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DAY_OF_YEAR, 1);

        int i = cal.get(Calendar.DAY_OF_WEEK);
        System.out.println(i);

    }

    @Test
    public void printTime() {
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("time==" + format);

    }

}
