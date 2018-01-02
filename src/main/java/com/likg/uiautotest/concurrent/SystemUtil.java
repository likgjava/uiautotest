package com.likg.uiautotest.concurrent;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.List;

public class SystemUtil {


    public static List<String> execAdb(String command) throws Exception {
        List<String> lines;
        Process process = Runtime.getRuntime().exec("cmd /c adb " + command);
        InputStream inputStream = null;
        try {
            inputStream = process.getInputStream();
            lines = IOUtils.readLines(inputStream);
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return lines;
    }
}
