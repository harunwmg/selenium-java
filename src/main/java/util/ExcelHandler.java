package util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelHandler {
    public static synchronized void createExcelReport(String dir, String filename, List<String> header) {
        File file = new File(dir + "/" + filename);
        if (!file.exists()) {

            File dirFile = new File(dir);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }

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
        try {
            FileInputStream fis = new FileInputStream(filepath);

            Workbook workbook = WorkbookFactory.create(fis);
            XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();

            XSSFRow row = sheet.createRow(lastRowNum+1);
            for(int i=0; i<data.size();i++) {
                XSSFCell cell = row.createCell(i);
                cell.setCellValue(data.get(i));
            }

            FileOutputStream out = new FileOutputStream(filepath);
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized String[][] readExcel(String filepath) {
        String[][] returnVal = new String[0][];
        try {
            FileInputStream file = new FileInputStream(filepath);
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum();
            int columnCount = sheet.getRow(0).getLastCellNum();
            returnVal = new String[rowCount][columnCount];

            for(int i=0; i<rowCount; i++) {
                Row row = sheet.getRow(i);
                for(int j=0; j<columnCount; j++) {
                    Cell cell = row.getCell(j);
                    returnVal[i][j] = cell.getStringCellValue();
                }
            }

            file.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            return returnVal;
        }
    }
}
