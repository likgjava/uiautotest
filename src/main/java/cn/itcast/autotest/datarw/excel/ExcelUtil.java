package cn.itcast.autotest.datarw.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtil {

    /**
     * 获取Workbook对象
     * @param file excel文件
     * @return Workbook
     * @throws IOException ex
     */
    public static Workbook getWorkbook(File file) throws IOException {
        Workbook workbook;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(file));
        } catch (Exception e) {
            workbook = new HSSFWorkbook(new FileInputStream(file));
        }
        return workbook;
    }
}
