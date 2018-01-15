package cn.itcast.autotest.kw.util;

import org.apache.commons.io.IOUtils;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
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

/**
 * 操作Excel的工具类
 */
public class ExcelUtil {

    /**
     * 设置单元格内容
     *
     * @param filePath  excel文件的路径
     * @param sheetName sheet的名称
     * @param row       行
     * @param column    列
     * @param value     要设置的内容
     * @throws IOException ex
     */
    public static void setCellValue(String filePath, String sheetName, int row, int column, String value) throws IOException {
        Workbook workbook = null;
        FileOutputStream fileOutputStream = null;
        try {
            workbook = getWorkbook(filePath);
            Sheet sheet = workbook.getSheet(sheetName);
            Row rowObj = sheet.getRow(row);
            rowObj.createCell(column).setCellValue(value);

            fileOutputStream = new FileOutputStream(new File(filePath));
            workbook.write(fileOutputStream);
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
            IOUtils.closeQuietly(workbook);
        }
    }

    /**
     * 向单元格中插入图片链接
     *
     * @param filePath  excel文件的路径
     * @param sheetName sheet的名称
     * @param row       行
     * @param column    列
     * @param imgPath   图片路径
     * @throws IOException
     */
    public static void setCellValueOfLink(String filePath, String sheetName, int row, int column, String imgPath) throws IOException {
        Workbook workbook = null;
        FileOutputStream fileOutputStream = null;
        try {
            workbook = getWorkbook(filePath);
            Sheet sheet = workbook.getSheet(sheetName);
            Row rowObj = sheet.getRow(row);
            Cell cell = rowObj.createCell(column);

            CreationHelper createHelper = workbook.getCreationHelper();
            cell.setCellValue("截图");
            Hyperlink link = createHelper.createHyperlink(HyperlinkType.FILE);
            link.setAddress(imgPath);
            cell.setHyperlink(link);

            //超级链接的样式,蓝色并接默认有下划线
            CellStyle linkStyle = workbook.createCellStyle();
            Font linkFont = workbook.createFont();
            linkFont.setUnderline(Font.U_SINGLE);
            linkFont.setColor(IndexedColors.BLUE.getIndex());
            linkStyle.setFont(linkFont);
            cell.setCellStyle(linkStyle);

            fileOutputStream = new FileOutputStream(new File(filePath));
            workbook.write(fileOutputStream);
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
            IOUtils.closeQuietly(workbook);
        }
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

    public static int getRowNum(String filePath, String sheetName, int column1, String keyWord1, int column2, String keyWord2) throws IOException {
        int rowNum = -1;
        Workbook workbook = getWorkbook(filePath);
        Sheet sheet = workbook.getSheet(sheetName);
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            Cell c1 = row.getCell(column1);
            Cell c2 = row.getCell(column2);
            //System.out.println("k1="+keyWord1 + " k2="+ keyWord2 + " ck1="+c1.getStringCellValue() + " ck2="+c2.getStringCellValue());
            if (keyWord1.equals(c1.getStringCellValue()) && keyWord2.equals(c2.getStringCellValue())) {
                rowNum = i;
                break;
            }
        }
        return rowNum;
    }


    /**
     * 获取指定sheet中的全部数据
     *
     * @param filePath    excel文件路径
     * @param sheetName   sheet名称
     * @param startRowNum 指定开始行
     * @return 数据
     * @throws IOException ex
     */
    public static List<String[]> getAllData(String filePath, String sheetName, int startRowNum) throws IOException {
        List<String[]> dataList = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = getWorkbook(filePath);
            Sheet sheet = workbook.getSheet(sheetName);

            int totalColumn = sheet.getRow(0).getLastCellNum();

            for (int i = startRowNum; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                String[] rowData = new String[totalColumn];
                for (int j = 0; j < totalColumn; j++) {
                    Cell cell = row.getCell(j);
                    if (cell != null) {
                        rowData[j] = cell.getStringCellValue();
                    }
                }
                dataList.add(rowData);
            }
        } finally {
            IOUtils.closeQuietly(workbook);
        }
        return dataList;
    }

    /**
     * 获取Workbook对象
     *
     * @param filePath excel文件路径
     * @return Workbook
     * @throws IOException ex
     */
    public static Workbook getWorkbook(String filePath) throws IOException {
        Workbook workbook;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(filePath));
        } catch (Exception e) {
            workbook = new HSSFWorkbook(new FileInputStream(filePath));
        }
        return workbook;
    }
}