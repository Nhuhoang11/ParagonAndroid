package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class LaunchParagon {
    public static AppiumDriver<MobileElement> getAppiumDriver(){
        AppiumDriver<MobileElement> appiumDriver = null;

        try{
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
            desiredCapabilities.setCapability(MobileCapabilityType.UDID, "2250F83B170285");
            desiredCapabilities.setCapability(MobileCapabilityTypeEx.APP_PACKAGE, "com.metasolutions.paragon");
            desiredCapabilities.setCapability(MobileCapabilityTypeEx.APP_ACTIVITY, "com.metasolutions.paragon.MainActivity");
//            desiredCapabilities.setCapability(MobileCapabilityTypeEx.NEW_COMMAND_TIMEOUT, 120);

            URL appiumServer = new URL("http://0.0.0.0:4723/wd/hub");
            appiumDriver = new AppiumDriver<>(appiumServer, desiredCapabilities);
            appiumDriver.manage().timeouts().implicitlyWait(30L, TimeUnit.SECONDS);
            MobileElement allowBtn = appiumDriver.findElementByXPath("//*[@resource-id='com.android.permissioncontroller:id/permission_allow_button']");
            allowBtn.click();
        }catch (Exception e){
            e.printStackTrace();
        }
        return appiumDriver;
    }
}
