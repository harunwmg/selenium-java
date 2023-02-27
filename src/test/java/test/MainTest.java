package test;

import com.codeborne.selenide.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.ExcelHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class MainTest {

    String timestamp = new SimpleDateFormat("d-MMM-yy_HH-mm-ss").format(new Date());

    @Test (dataProvider = "excel")
    public void test1(String url) {
        WebDriver driver = getDriver(url);
        waitForPageLoad(driver);

        String[] header = {"url", "title"};
        String dir = "./Report/" + timestamp;
        String filepath = dir + "/output_test1.xlsx";
        ExcelHandler.createExcelReport(filepath, List.of(header));

        ArrayList<String> data = new ArrayList<>();
        data.add(url);
        data.add(driver.getTitle());

        ExcelHandler.writeExcel(filepath, data);
        driver.quit();
    }

    @DataProvider (name = "excel")
    public Object[][] readExcel() {
        String[][] returnVal = new String[2][1];
        returnVal[0][1] = "https://tompetty.com";
        returnVal[1][1] = "https://dualipa.com";
        /*try {
            FileInputStream file = new FileInputStream(new File("howtodoinjava_demo.xlsx"));
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
            return returnVal;
        }
        catch (Exception e) {
            e.printStackTrace();
        }*/
        return returnVal;
    }

    public WebDriver getDriver(String url) {
        Selenide.open(url);
        return WebDriverRunner.getWebDriver();
    }

    public void waitForPageLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
            }
        };
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(pageLoadCondition);
    }


}