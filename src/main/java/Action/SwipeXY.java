package Action;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;

import java.time.Duration;

public class SwipeXY {
    private AppiumDriver<MobileElement> appiumDriver;

    public SwipeXY(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    public void swipe(int xStartPoint, int yStartPoint, int xEndPoint, int yEndPoint) {
        Dimension windowSize = appiumDriver.manage().window().getSize();
        int screenWidth = windowSize.getWidth();
        int screenHeight = windowSize.getHeight();
//        int xStartPoint = 0 * screenWidth / 100;
//        int xEndPoint = 70 * screenWidth / 100;;
//        int yStartPoint = 25 * screenHeight / 100;
//        int yEndPoint = yStartPoint;

        PointOption startPoint = new PointOption<>().withCoordinates(xStartPoint, yStartPoint);
        PointOption endPoint = new PointOption<>().withCoordinates(xEndPoint, yEndPoint);
        TouchAction touchAction = new TouchAction<>(appiumDriver);
        touchAction.press(startPoint).waitAction(new WaitOptions().withDuration(Duration.ofSeconds(3))).moveTo(endPoint).release().perform();
//        MobileElement readEmailIcon = appiumDriver.findElementByXPath("//android.widget.TextView[@text = 'no-reply@verificationemail.com']");
    }
}
