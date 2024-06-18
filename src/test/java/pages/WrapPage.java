package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;
import base.Util;
import base.CommonAssertions;
import driver.AppDriver;

public class WrapPage extends BasePage {

    public static By nigeria = By.xpath("//android.widget.TextView[@resource-id='com.mobeta.android.demodslv:id/text' and @text='Nigeria']");
    public static By afg = By.xpath("//android.widget.TextView[@resource-id='com.mobeta.android.demodslv:id/text' and @text='Afghanistan']");
    public static By textViewLocator = By.className("android.widget.TextView");
    public static By imageViewLocator = By.className("android.widget.ImageView");
    public static By listLocator = By.id("android:id/list");
    


    public static List<WebElement> getListOfTextViews() {
        return AppDriver.getCurrentDriver().findElements(textViewLocator);
    }

    public static WebElement getList() {
        return AppDriver.getCurrentDriver().findElement(listLocator);
    }

    public void dragAndDropCountry() {
        String text = "Nigeria";
        Util.scrollToElement(text);
        staticWait(1000);

        // Reinitialize ListOfTextViews
        List<WebElement> list = getListOfTextViews();
        staticWait(1000);
        Util.pickAndDropToTop(list, text);
        staticWait(2000);

        // Retrieve the first element from the list
        WebElement firstElement = getList().findElements(textViewLocator).get(0);
        String elementText = firstElement.getText();

        CommonAssertions.assertElementOnTop(elementText, text);
        staticWait(1000);
    }

    public void swipeLeftOnCountry() {
        WebElement afgDragHandle = getList().findElements(imageViewLocator).get(1);
        Util.swipeLeftToRemove(afgDragHandle);
        staticWait(1000);

        List<WebElement> allElements = getList().findElements(textViewLocator);
        CommonAssertions.assertElementNotInList(allElements);
    }

    
}


