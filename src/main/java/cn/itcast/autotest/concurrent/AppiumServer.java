package cn.itcast.autotest.concurrent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AppiumServer {

    private static Map<String, Process> processMap = new HashMap<>();

    public static void startServer(int port) throws IOException {
        String command = String.format("cmd /c appium -p %s > D:/appium-%s.log", port, port);
        Process process = Runtime.getRuntime().exec(command);
        processMap.put(String.valueOf(port), process);
    }

    public static void stopServer(int port) throws Exception {
        try {
            processMap.get(String.valueOf(port)).destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String pid = SystemUtil.getPidByPort(port);
        if (pid != null) {
            SystemUtil.killWithPid(pid);
        }
    }

    public static void main(String[] args) throws Exception {
        String pidByPort = SystemUtil.getPidByPort(4723);
        System.out.println("pidByPort=" + pidByPort);
    }
}
