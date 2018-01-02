package com.likg.uiautotest.concurrent;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
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

    public static List<Integer> getUnusedPort(int startPort, int length) throws Exception {
        List<Integer> portList = new ArrayList<>();
        while (portList.size() < length) {
            if (isUnusedPort(startPort)) {
                portList.add(startPort);
            }
            startPort++;
        }
        return portList;
    }

    public static boolean isUnusedPort(int port) throws Exception {
        List<String> resultList = execCmd("netstat -ano | findstr " + port);
        return resultList.isEmpty();
    }

    public static void main(String[] args) throws Exception {
        //System.out.println(getDevices());

        //isUnusedPort(4723);
        //System.out.println(getUnusedPort(4723, 5));

        Process process = Runtime.getRuntime().exec("cmd /c appium -a 127.0.0.1 -p 4768 > D:/appium3.log");

        Thread.sleep(30000);


        process.destroy();

        //

    }


    public static List<String> execAdb(String command) throws Exception {
        return execCmd("adb " + command);
    }

    public static List<String> execCmd(String command) throws Exception {
        List<String> lines;
        Process process = Runtime.getRuntime().exec("cmd /c " + command);
        InputStream inputStream = null;
        try {
            inputStream = process.getInputStream();
            lines = IOUtils.readLines(inputStream);

            System.out.println("error====" + IOUtils.readLines(process.getErrorStream(), "gbk"));
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return lines;
    }
}
