package util;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelHandler {
    public static synchronized void createExcelReport(String filepath, List<String> header) {
        File file = new File(filepath);
        if (!file.exists()) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("sheet1");
            XSSFRow row = sheet.createRow(0);

            for(int i=0; i<header.size();i++){
                XSSFCell cell = row.createCell(i);
                cell.setCellValue(header.get(i));
            }

            try {
                FileOutputStream out = new FileOutputStream(file);
                workbook.write(out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static synchronized void writeExcel(String filepath, ArrayList<String> data) {
        File file = new File(filepath);
        if (!file.exists()) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();

            XSSFRow row = sheet.createRow(lastRowNum+1);
            for(int i=0; i<data.size();i++) {
                XSSFCell cell = row.createCell(i);
                cell.setCellValue(data.get(i));
            }

            try {
                FileOutputStream out = new FileOutputStream(file);
                workbook.write(out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
