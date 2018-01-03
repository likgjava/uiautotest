package com.likg.uiautotest.concurrent;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AdbUtil {

    public static List<String> getDevices() throws Exception {
        List<String> devices = new ArrayList<>();
        List<String> resultList = execAdb("devices");
        for (int i = 1; i < resultList.size(); i++) {
            String device = resultList.get(i);
            if (StringUtils.isNotBlank(device)) {
                devices.add(device.split("\\s")[0]);
            }
        }
        return devices;
    }

    public static List<String> execAdb(String command) throws Exception {
        return SystemUtil.execCmd("adb " + command);
    }


    public static void main(String[] args) throws Exception {
        //System.out.println(getDevices());

        //isUnusedPort(4723);
        //System.out.println(getUnusedPort(4723, 5));

        Process process = Runtime.getRuntime().exec("cmd /c appium -a 127.0.0.1 -p 4723 > D:/appium3.log");

        System.out.println("00000000000000");
        Thread.sleep(30000);


        //process.waitFor();
        System.out.println("1111111111111");
        process.destroy();
        System.out.println("222222");

        process.exitValue();

        //

    }


}
