package base;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import driver.AppDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import io.appium.java_client.TouchAction;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.Collections;


public class Util {
    static double SCROLL_RATIO = 0.5;
    static Duration SCROLL_DUR = Duration.ofMillis(500);
    static Duration SWIPE_DUR = Duration.ofMillis(1500);
    
    static Dimension size = AppDriver.getCurrentDriver().manage().window().getSize();
    static int screenWidth = size.getWidth();
    static int screenHeight = size.getHeight();

    public static void scrollNclick(By listItems, String attrName, String text) throws InterruptedException {
        String prevPageSource = "";
        boolean flag = false;

        while (!isEndOfPage(prevPageSource)) {
            prevPageSource = AppDriver.getCurrentDriver().getPageSource();

            for (WebElement el : AppDriver.getCurrentDriver().findElements(listItems)) {

                if (el.getAttribute(attrName).equalsIgnoreCase(text)) {
                    el.click();
                    flag = true; 
                    break;
                }
            }
            if (flag)
                break; 
            else {
                scroll(ScrollDirection.DOWN, Util.SCROLL_RATIO);
                Thread.sleep(1000);
            }

        }

    }

    public static void scrollNclick(By byEl, ScrollDirection dir) {
        String prevPageSource = "";
        boolean flag = false;

        while (!isEndOfPage(prevPageSource)) {
            prevPageSource = AppDriver.getCurrentDriver().getPageSource();

            try {
                AppDriver.getCurrentDriver().findElement(byEl).click();
            } catch (org.openqa.selenium.NoSuchElementException e) {
                scroll(dir, Util.SCROLL_RATIO);
            }
        }

    }

    public static boolean isEndOfPage(String pageSource) {
        return pageSource.equals(AppDriver.getCurrentDriver().getPageSource());
    }

    public enum ScrollDirection {
        UP, DOWN, LEFT, RIGHT
    }

    public static void scroll(ScrollDirection dir, double scrollRatio) {

        if (scrollRatio < 0 || scrollRatio > 1) {
            throw new Error("Scroll distance must be between 0 and 1");
        }
        Dimension size = AppDriver.getCurrentDriver().manage().window().getSize();
        System.out.println(size);
        Point midPoint = new Point((int) (size.width * 0.5), (int) (size.height * 0.5));
        int bottom = midPoint.y + (int) (midPoint.y * scrollRatio);
        int top = midPoint.y - (int) (midPoint.y * scrollRatio);
        //Point Start = new Point(midPoint.x, bottom );
        //Point End = new Point(midPoint.x, top );
        int left = midPoint.x - (int) (midPoint.x * scrollRatio);
        int right = midPoint.x + (int) (midPoint.x * scrollRatio);

        if (dir == ScrollDirection.UP) {
            swipe(new Point(midPoint.x, top), new Point(midPoint.x, bottom), SCROLL_DUR);
        } else if (dir == ScrollDirection.DOWN) {
            swipe(new Point(midPoint.x, bottom), new Point(midPoint.x, top), SCROLL_DUR);
        } else if (dir == ScrollDirection.LEFT) {
            swipe(new Point(left, midPoint.y), new Point(right, midPoint.y), SCROLL_DUR);
        } else {
            swipe(new Point(right, midPoint.y), new Point(left, midPoint.y), SCROLL_DUR);
        }
    }

    protected static void swipe(Point start, Point end, Duration duration) {

        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence swipe = new Sequence(input, 0);
        swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y));
        swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
    
        
        swipe.addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y));
        swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        ((AppiumDriver) AppDriver.getCurrentDriver()).perform(ImmutableList.of(swipe));
        
    }

    public static void longPress(WebElement el) {
        Point location = el.getLocation();
        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence swipe = new Sequence(input, 0);
        swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), location.x, location.y));
        swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(input.createPointerMove(Duration.ofSeconds(1), PointerInput.Origin.viewport(), location.x, location.y));
        swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        ((AppiumDriver) AppDriver.getCurrentDriver()).perform(ImmutableList.of(swipe));
    }

    public static void longPress_gesturePlugin(WebElement el) {
        ((JavascriptExecutor) AppDriver.getCurrentDriver()).executeScript("gesture: longPress", ImmutableMap.of("elementId", ((RemoteWebElement) el).getId(), "pressure", 0.5, "duration", 800));
    }

    public static void swipe_gesturePlugin(WebElement el, String direction) {
        ((JavascriptExecutor) AppDriver.getCurrentDriver()).executeScript("gesture: swipe", ImmutableMap.of("elementId", ((RemoteWebElement) el).getId(), "percentage", 50, "direction", direction));
    }

    private static Point getCenter(WebElement el) {
        Point location = el.getLocation();
        Dimension size = el.getSize();
        return new Point(location.x + size.getWidth() / 2, location.y + size.getHeight() / 2);
    }

    public static void dragNDrop(WebElement source, WebElement target) {
        Point pSourcce = getCenter(source);
        Point pTarget = getCenter(target);
        swipe(pSourcce, pTarget, SCROLL_DUR);

        swipe(pSourcce, pTarget, SCROLL_DUR);
    }

    public static void dragNDrop_gesture(WebElement source, WebElement target) {
        ((JavascriptExecutor) AppDriver.getCurrentDriver()).executeScript("gesture: dragAndDrop", ImmutableMap.of("sourceId", ((RemoteWebElement) source).getId(), "destinationId", ((RemoteWebElement) target).getId()));
    }

    public static void Drawing(WebElement drawPanel) {

        Point location = drawPanel.getLocation();
        Dimension size = drawPanel.getSize();

        Point pSource = new Point(location.x + size.getWidth() / 2
                , location.y + 10);
        Point pDest = new Point(location.x + size.getWidth() / 2
                , location.y + size.getHeight() - 10);

        //The same way, try to identify the Points for horigental drawing
        swipe(pSource, pDest, SCROLL_DUR);
    }

    public static void captureScreenShotOf(WebElement el, String fileName){
        File newImg = el.getScreenshotAs(OutputType.FILE);
        try{
            FileUtils.copyFile(newImg, new File("./screenshot/"+fileName+".jpg"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void captureFullPageShot(String fileName){
        File newImg = ((FirefoxDriver) AppDriver.getCurrentDriver()).getFullPageScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(newImg, new File("./screenshot/"+ fileName+ ".jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getScreenshot(String imagename) throws IOException, IOException {
        TakesScreenshot ts = (TakesScreenshot) AppDriver.getCurrentDriver();
        File f = ts.getScreenshotAs(OutputType.FILE);
        String filePath = "./screenshot/"+imagename+".jpg";
        FileUtils.copyFile(f, new File(filePath));
        return filePath;
    }

    public static String convertImg_Base64(String screenshotPath) throws IOException {
        byte[] file = FileUtils.readFileToByteArray(new File(screenshotPath));
        String base64Img = Base64.getEncoder().encodeToString(file);
        return  base64Img;
    }

    public static void tap(int x, int y) {
        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence tap = new Sequence(input, 1);
        tap.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        ((AppiumDriver) AppDriver.getCurrentDriver()).perform(Arrays.asList(tap));


    }


    public static void scrollToElement(String elementText){

        String uiautomatorExpression = "new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"" + elementText + "\"));";
        
        AppDriver.getCurrentDriver().findElement(MobileBy.AndroidUIAutomator(uiautomatorExpression));

    }

   public static void pickAndDropToTop(List<WebElement> list, String text) {
        int indexOfSource = -1;

        for (int index = 0; index < list.size(); index++) {
            WebElement element = list.get(index);
            if (element.getText().equals(text)) {
                indexOfSource = index;
                break;
            }
        }

        if (indexOfSource != -1) {
            final String dragHandle = "com.mobeta.android.demodslv:id/drag_handle";
            final List<WebElement> listElements = AppDriver.getCurrentDriver().findElements(By.id(dragHandle));

            if (indexOfSource > 0 && indexOfSource < listElements.size()) {
                final WebElement source = listElements.get(indexOfSource - 1);
                final WebElement target = listElements.get(0);

                Point pSource = getCenter(source);
                Point pTarget = getCenter(target);

                PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
                Sequence sequence = new Sequence(finger1, 1)
                        .addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), pSource))
                        .addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                        .addAction(new Pause(finger1, Duration.ofMillis(500)))
                        .addAction(finger1.createPointerMove(Duration.ofMillis(5000), PointerInput.Origin.viewport(), pTarget))
                        .addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                ((AppiumDriver) AppDriver.getCurrentDriver()).perform(Collections.singletonList(sequence));


            } else {
                System.out.println("Invalid index for source element");
            }
        } else {
            System.out.println("Element with text '" + text + "' not found in the list");
        }

    }
    

 public static void navigateBack()
    {
        AppDriver.getCurrentDriver().navigate().back();
        System.out.println("Navigated Back from previous Screen");
    }
    public static Point getCenterPoint(WebElement element) {
        Dimension size = element.getSize();
        int centerX = element.getLocation().getX() + (size.getWidth() / 2);
        int centerY = element.getLocation().getY() + (size.getHeight() / 2);
        return new Point(centerX, centerY);
    }

    public static void swipeLeftToRemove(WebElement element) {
        Point center = getCenterPoint(element);
        int width = element.getSize().getWidth();
    
        // Calculate target point slightly to the right for swipe initiation
        Point startPoint = new Point(304, center.getY());
    
        // Calculate target point to the left for swipe completion (adjust offset as needed)
        Point endPoint = new Point(0, center.getY());
    
        // Set swipe duration (adjust based on your app's animation speed)
        Duration duration = Duration.ofMillis(100);
    
        swipe(startPoint, endPoint, duration);
    }

    public static List<String> selectRandomElementsAndDelete(List<WebElement> checkboxesList, List<WebElement> deleteButtonsList) throws MalformedURLException, InterruptedException  {

        //List<WebElement> checkboxesList = getListOfCheckBoxes();
       
        List<String> elementTexts = new ArrayList<>();
       
        Random rand = new Random();
        List<Integer> randomIndexes = new ArrayList<>();
        while (randomIndexes.size() < 5) {
            int index = rand.nextInt(8); // Assuming the list size is 8
            if (!randomIndexes.contains(index)) {
                randomIndexes.add(index);
                String text = checkboxesList.get(index).getText();
                elementTexts.add(text);
            }
        }
        System.out.println(elementTexts);
        //return randomIndexes;
       
        //List<Integer> randomIndexes = generateRandomIndexes();
        List<String> deletedElementTexts = new ArrayList<>();

   
        Collections.sort(randomIndexes, Collections.reverseOrder());
   
        // Select the random elements
        for (int index : randomIndexes) {
            checkboxesList.get(index).click();
            deletedElementTexts.add(checkboxesList.get(index).getText()); // Store text of deleted elements


            System.out.println("selected item");
        }
      
        // Delete the selected elements starting from the end of the list
        for (int index : randomIndexes) {
            deleteButtonsList.get(index).click();
            System.out.println("deleted item");
            Thread.sleep(1000);
        }

        return deletedElementTexts;

        }
        
}