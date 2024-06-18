package driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.MalformedURLException;
import java.net.URL;

public class AppFactory {
    static AppiumDriver driver;

    private static void android_launchApp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("mulator-5554")
                .setPlatformVersion("11")
                .setAppPackage("com.mobeta.android.demodslv")
                .setAppActivity(".Launcher");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
        AppDriver.setDriver(driver);
        System.out.println("AndroidDriver is set");
    }


    public static void launchApp() throws MalformedURLException {
        android_launchApp();
        System.out.println("Android launched...");
    
    }


     public static void closeAndRealaunchApplication() {

        ((AndroidDriver)AppDriver.getCurrentDriver()).terminateApp("com.mobeta.android.demodslv");
        ((AndroidDriver)AppDriver.getCurrentDriver()).activateApp("io.selendroid.testapp");
        

    }


}
