package com.likg.uiautotest.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.*;

public class ExcelTest {

    @Test
    public void read() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream("data/low.xls"));

        int numberOfSheets = workbook.getNumberOfSheets();
        System.out.println(numberOfSheets);

        HSSFSheet sheet = workbook.getSheet("学生列表");
        int lastRowNum = sheet.getLastRowNum();
        System.out.println("lastRowNum=" + lastRowNum);

        int firstRowNum = sheet.getFirstRowNum();
        System.out.println("firstRowNum=" + firstRowNum);

        for (int i = 1; i <= lastRowNum; i++) {
            HSSFRow row = sheet.getRow(i);
            System.out.println(row.getFirstCellNum());
            System.out.println(row.getLastCellNum());

            HSSFCell idCell = row.getCell(0);
            //String id = idCell.getStringCellValue();
            double id = idCell.getNumericCellValue();
            String username = row.getCell(1).getStringCellValue();
            System.out.println("id=" + (int) id);
            System.out.println("username=" + username);
            System.out.println();
        }
    }

    @Test
    public void readHighVersion() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("data/high.xlsx"));
        int numberOfSheets = workbook.getNumberOfSheets();
        System.out.println(numberOfSheets);
    }

    @Test
    public void getWorkbookTest() throws IOException {
        //Workbook workbook = ExcelUtil.getWorkbook(new File("data/high.xlsx"));
        Workbook workbook = ExcelUtil.getWorkbook(new File("data/low.xls"));
        int numberOfSheets = workbook.getNumberOfSheets();
        System.out.println(numberOfSheets);
    }


    @Test
    public void createExcel() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("test");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("hello");
        row.createCell(1).setCellValue(100);

        FileOutputStream outputStream = new FileOutputStream("D://test.xlsx");
        workbook.write(outputStream);

        outputStream.close();
        workbook.close();
    }

    @Test
    public void update() throws IOException {
        File file = new File("data/low.xls");
        Workbook workbook = ExcelUtil.getWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();

        Row row = sheet.createRow(lastRowNum + 1);
        row.createCell(0).setCellValue(4);
        row.createCell(1).setCellValue("appium");
        row.createCell(2).setCellValue(10);

        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);

        outputStream.close();
        workbook.close();
    }
}
