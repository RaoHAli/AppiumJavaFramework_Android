package pages;


import java.net.MalformedURLException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import base.CommonAssertions;

import base.Util;
import driver.AppDriver;


public class MultiChoicePage extends BasePage {


    public static By checkBoxesLocator = By.className("android.widget.CheckedTextView");
    public static By deleteBtnLocator = By.id("com.mobeta.android.demodslv:id/click_remove");
    public static List<WebElement> getListOfCheckBoxes() {
        return AppDriver.getCurrentDriver().findElements(checkBoxesLocator);
    }


    public static List<WebElement> getListOfDeleteButtons() {
        return AppDriver.getCurrentDriver().findElements(deleteBtnLocator);
    }
   

       public void selectElementsAndDelete() throws MalformedURLException, InterruptedException {
        List<WebElement> checkboxesList = getListOfCheckBoxes();
        List<WebElement> deleteButtonsList = getListOfDeleteButtons();

        //selectRandomElementsAndDelete();

        List<String> deletedElementTexts = Util.selectRandomElementsAndDelete(checkboxesList, deleteButtonsList );
        CommonAssertions.assertDeletedElementsNotInList(checkboxesList,deletedElementTexts);


       }


}
