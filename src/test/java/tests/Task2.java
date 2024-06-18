package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.WrapPage;
import pages.MultiChoicePage;
import base.Util;
import java.net.MalformedURLException;
import base.CommonAssertions;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import driver.AppFactory;


public class Task2 extends BaseTest {
    

    @Test
    public void secondTask() throws MalformedURLException, InterruptedException{
        ExtentTest test = extent.createTest("PicknDrop and Delete test");
        test.log(Status.INFO, "Starting test execution.");
       
            HomePage homePage = new HomePage();
            WrapPage wrapPage = new WrapPage();
            MultiChoicePage multiChoicePage = new MultiChoicePage();

            homePage.clickContinue();
            homePage.clickWrap();
            
            wrapPage.dragAndDropCountry();
            test.log(Status.PASS, "Nigeria dropped to top successfully.");
           
            wrapPage.swipeLeftOnCountry();
            test.log(Status.PASS, "Afghanistan Deleted successfully.");

            Util.navigateBack();

            homePage.clickMultiChoice();
            multiChoicePage.selectElementsAndDelete();

            AppFactory.closeAndRealaunchApplication();
            CommonAssertions.assertSecondAppOpened(); 
            test.log(Status.PASS, "Deleted elements are not in list.");
            
    }
    
    
}
