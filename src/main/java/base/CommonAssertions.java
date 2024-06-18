package base;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import java.util.NoSuchElementException;


import driver.AppDriver;

public class CommonAssertions {

    public static void assertElementOnTop(String elementText, String text) {
        Assert.assertEquals(elementText, text, "Text of the element at the 1st index is not 'Nigeria'");
    }

    public static void assertElementNotInList(List < WebElement > allElements) {
        // Assertion using loop and contains
        boolean afghanistanExists = false;
        for (WebElement element: allElements) {

            if (element.getText().equals("Afghanistan")) {

                afghanistanExists = true;
                break;
            }
        }
        Assert.assertFalse(afghanistanExists, "Afghanistan should not exist in the list");
    }

    public static void assertDeletedElementsNotInList(List<WebElement> updatedList ,List<String> deletedElementTexts) {
        //List<WebElement> updatedList = getListOfCheckBoxes();
        for (String text : deletedElementTexts) {
            for (WebElement element : updatedList) {
                if (element.getText().equals(text)) {
                    // Assertion fails if the deleted element is found in the updated list
                    throw new AssertionError("Deleted element '" + text + "' is still present in the list.");
                }
            }
        }
        System.out.println("All deleted elements are not in the list.");
    }
    public static void assertSecondAppOpened() throws InterruptedException {
        try {
            Thread.sleep(2000);

            WebElement someElementInSecondApp = AppDriver.getCurrentDriver().findElement(By.id("io.selendroid.testapp:id/buttonStartWebview"));
            Assert.assertTrue(someElementInSecondApp.isDisplayed(), "Second application did not open successfully.");
        } catch (NoSuchElementException e) {
            Assert.fail("Second application did not open successfully.", e);
        }
    }
}