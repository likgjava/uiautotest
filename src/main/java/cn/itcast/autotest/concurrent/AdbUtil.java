package cn.itcast.autotest.concurrent;

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

    }
}
