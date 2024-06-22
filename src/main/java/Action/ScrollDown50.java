package Action;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;

import java.time.Duration;

public class ScrollDown50 {
    private AppiumDriver<MobileElement> appiumDriver;

    public ScrollDown50(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    public void scrollDownTo() {
        Dimension windowSize = appiumDriver.manage().window().getSize();
        int screenWidth = windowSize.getWidth();
        int screenHeight = windowSize.getHeight();
        int xStartPoint = 50 * screenWidth / 100;
        int xEndPoint = xStartPoint;
        int yStartPoint = 50 * screenHeight / 100;
        int yEndPoint = 30 * screenHeight / 100;

        PointOption startPoint = new PointOption<>().withCoordinates(xStartPoint, yStartPoint);
        PointOption endPoint = new PointOption<>().withCoordinates(xEndPoint, yEndPoint);
        TouchAction touchAction = new TouchAction<>(appiumDriver);
        touchAction.press(startPoint).waitAction(new WaitOptions().withDuration(Duration.ofSeconds(3))).moveTo(endPoint).release().perform();
//        MobileElement readEmailIcon = appiumDriver.findElementByXPath("//android.widget.TextView[@text = 'no-reply@verificationemail.com']");
    }
}
