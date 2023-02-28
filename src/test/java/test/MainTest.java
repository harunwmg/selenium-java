package test;

import com.codeborne.selenide.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.Config;
import util.ExcelHandler;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class MainTest {

    String timestamp = new SimpleDateFormat("d-MMM-yy_HH-mm-ss").format(new Date());
    String[] header = {"url", "title"};
    String dir = "Report/" + timestamp;
    String filename = "output_test1.xlsx";

    public MainTest() {
        ExcelHandler.createExcelReport(dir, filename, List.of(header));
    }

    @Test (dataProvider = "excel")
    public void test1(String url) {
        WebDriver driver = getDriver(url);
        waitForPageLoad(driver);
        ArrayList<String> data = new ArrayList<>();
        data.add(url);
        data.add(driver.getTitle());

        ExcelHandler.writeExcel(dir + "/" + filename, data);
        driver.quit();
    }

    @DataProvider (name = "excel", parallel = true)
    public Object[][] excelData(ITestContext context) {

        context.getCurrentXmlTest().getSuite().setDataProviderThreadCount(Config.parallelCount);

        String inputExcel = System.getProperty("input", null);
        if (inputExcel==null) {
            String[][] returnVal = new String[2][1];
            returnVal[0][0] = "https://tompetty.com";
            returnVal[1][0] = "https://dualipa.com";
            return returnVal;
        } else return ExcelHandler.readExcel(inputExcel);
    }

    public WebDriver getDriver(String url) {
        setSelenideConfig();
        Selenide.open(url);
        return WebDriverRunner.getWebDriver();
    }

    public void setSelenideConfig() {
        Configuration.browser = Config.browser;
        Configuration.remote = Config.remote;
        Configuration.pageLoadTimeout = Config.pageLoadTimeout * 1000;
    }

    public void waitForPageLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = input -> ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(pageLoadCondition);
    }


}
