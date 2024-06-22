package C06_Create;

import Action.SwipeBack;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;

public class TapOnButtonUntilVisible {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private SwipeBack swipeBack;

    public TapOnButtonUntilVisible(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    public void tapOnButtonUntilVisible(By targetSel) {
        boolean isVisible = false;
        while (!isVisible) {
            try {
                isVisible = appiumDriver.findElement(targetSel).isDisplayed();
            } catch (NoSuchElementException e) {
                swipeBack = new SwipeBack(appiumDriver);
                swipeBack.swipeBack();
            }
        }
    }
}
