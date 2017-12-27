package com.likg.uiautotest.datadriven.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {


    public static void setCellValue(String filePath, String sheetName, int row, int column, String value) throws IOException {
        Workbook workbook = getWorkbook(filePath);
        Sheet sheet = workbook.getSheet(sheetName);
        Row rowObj = sheet.getRow(row);
        rowObj.createCell(column).setCellValue(value);

        FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();
    }

    public static int getRowNum(String filePath, String sheetName, int column, String keyWord) throws IOException {
        int rowNum = -1;
        Workbook workbook = getWorkbook(filePath);
        Sheet sheet = workbook.getSheet(sheetName);
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(column);
            if (keyWord.equals(cell.getStringCellValue())) {
                rowNum = i;
                break;
            }
        }
        return rowNum;
    }


    public static List<Object[]> getAllData(String filePath, String sheetName, int startRowNum) throws IOException {
        List<Object[]> dataList = new ArrayList<Object[]>();
        Workbook workbook = getWorkbook(filePath);
        Sheet sheet = workbook.getSheet(sheetName);

        for (int i = startRowNum; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            Object[] rowData = new Object[row.getLastCellNum()];
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                if (cell != null) {
                    rowData[j] = cell.getStringCellValue();
                }
            }
            dataList.add(rowData);
        }

        workbook.close();

        return dataList;
    }

    public static Workbook getWorkbook(String filePath) throws IOException {
        Workbook workbook;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(filePath));
        } catch (Exception e) {
            workbook = new HSSFWorkbook(new FileInputStream(filePath));
        }
        return workbook;
    }

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
