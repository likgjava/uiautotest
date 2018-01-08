package cn.itcast.autotest.concurrent;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统工具类
 */
public class SystemUtil {

    /**
     * 获取未使用的端口号
     * @param startPort 开始端口
     * @param length 需要端口的个数
     * @return 未使用的端口列表
     * @throws Exception ex
     */
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

    /**
     * 判断是否为未使用的端口
     * @param port 端口
     * @return true:未使用; false:已使用
     * @throws Exception ex
     */
    public static boolean isUnusedPort(int port) throws Exception {
        List<String> resultList = execCmd("netstat -ano | findstr " + port);
        return resultList.isEmpty();
    }

    /**
     * 执行cmd命令
     * @param command 命令
     * @return 输出结果
     * @throws Exception ex
     */
    public static List<String> execCmd(String command) throws Exception {
        List<String> lines;
        Process process = Runtime.getRuntime().exec("cmd /c " + command);
        InputStream inputStream = null;
        try {
            inputStream = process.getInputStream();
            lines = IOUtils.readLines(inputStream, "GBK");

            System.out.println("error====" + IOUtils.readLines(process.getErrorStream(), "GBK"));
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return lines;
    }

    public static String getPidByPort(int port) throws Exception {
        List<String> resultList = execCmd("netstat -ano | findstr " + port);
        System.out.println(resultList);
        for (String result : resultList) {
            System.out.println(result);
            String[] split = result.split("\\s+");
            for (String s : split) {
                if (s.endsWith(":" + port)) {
                    return split[split.length - 1];
                }
            }
        }
        return null;
    }

    public static void killWithPid(String pid) throws Exception {
        String command = "cmd /c taskkill /F /pid " + pid + " /T";
        System.out.println("killWithPid command=" + command);
        List<String> result = execCmd(command);
        System.out.println("killWithPid result=" + result);
    }
}
