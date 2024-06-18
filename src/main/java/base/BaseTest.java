package base;

import driver.AppFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.net.MalformedURLException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class BaseTest {
    public static ExtentSparkReporter sparkReporter;
    public static ExtentReports extent;
    public static ExtentTest test;

    @BeforeMethod
    public void launchApp() throws MalformedURLException {
        System.out.println("before method");
        AppFactory.launchApp();
       
    }

    @AfterMethod
    public void closeApp(ITestResult result) throws IOException {
        if(result.getStatus() == ITestResult.FAILURE){
            Util.getScreenshot(result.getTestName());
        }
    }
        

    @BeforeSuite
    public void serverStart(){
        System.out.println("before suite");
        base.AppiumServer.start();
    }

    @AfterSuite
    public void serverStop(){
        base.AppiumServer.stop();
    }

    @BeforeSuite
    public void reportSetup() {
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/extentReport.html");
        sparkReporter.config().setDocumentTitle("AutomationReport");
        sparkReporter.config().setReportName("Automation Test Execution Report");
        sparkReporter.config().setTheme(Theme.DARK);;
        sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }

    @AfterSuite
    public void tearDown() {
        // Write the report to the file
        extent.flush();
    }
}
