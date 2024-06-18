package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import java.net.MalformedURLException;
import org.openqa.selenium.WebElement;

import base.Util;



public class HomePage extends BasePage{

    
    @AndroidFindBy(xpath = "//android.widget.Button[@text='Continue']")
     private WebElement continueBtn;
    
     @AndroidFindBy(id = "android:id/button1")
     private WebElement okBtn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.mobeta.android.demodslv:id/activity_title' and @text='Warp']")
    private WebElement wrapBtn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.mobeta.android.demodslv:id/activity_title' and @text='Multiple-choice mode']")
    private WebElement multiChoiceBtn;
    
    public void clickContinue(){
        continueBtn.click();
        okBtn.click();
        staticWait(1000);
    }

    public void clickWrap(){
        wrapBtn.click();
        staticWait(1000);
    }

    public void clickMultiChoice() throws MalformedURLException {
        Util.scrollToElement("Multiple-choice mode");
        multiChoiceBtn.click();
        staticWait(1000);
       }

}

            
 
        