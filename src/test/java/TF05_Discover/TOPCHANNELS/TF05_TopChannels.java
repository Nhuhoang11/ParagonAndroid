package TF05_Discover.TOPCHANNELS;

import Action.*;
import C02_Login.LoginForm;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.AccEx;
import utils.LaunchParagon;

import java.util.List;

public class TF05_TopChannels {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private LoginForm loginForm;
    private ScrollDownUntilEnd scrollDownUntilEnd;
    private ScrollUpUntilEnd scrollUpUntilEnd;
    private ScrollUp90 scrollUp90;
    private SwipeBack swipeBack;

    By discoverSel = MobileBy.AccessibilityId("Discover");
    By channelsSel = MobileBy.xpath("//android.view.ViewGroup[@content-desc]/android.widget.TextView[1]"); // 8th -> 11th; View all -> 7th
    By backBtnSel =MobileBy.xpath("//android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[1]/android.widget.ImageView");
    By allChannelsSel = MobileBy.xpath("//android.view.ViewGroup[@content-desc]/android.view.ViewGroup[1]/android.widget.ImageView");
    By messageBtnSel = MobileBy.AccessibilityId("Message");
    By nameAcc = MobileBy.xpath("//android.view.ViewGroup[3]/android.widget.TextView[1]");
    @BeforeClass
    public void setup() {
        appiumDriver = LaunchParagon.getAppiumDriver();
        wait = new WebDriverWait(appiumDriver, 10);
        loginForm = new LoginForm(appiumDriver);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        loginForm.inputPassword(AccEx.validPassword);
        loginForm.inputEmail(AccEx.validEmail1);
        loginForm.clickOnSignInBtn();
        wait.until(ExpectedConditions.visibilityOfElementLocated(discoverSel));
        appiumDriver.findElement(discoverSel).click();
        try {
            Thread.sleep(3000);
            appiumDriver.findElement(discoverSel).click();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void TC001_clickOnHorizontalList() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(channelsSel));
            List<MobileElement> channelsElements = appiumDriver.findElements(channelsSel);
            for (int i = 6; i <= 9; i++) {
                String acc = channelsElements.get(i).getText();
                System.out.println(acc);
                channelsElements.get(i).click();
            // Verify
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(messageBtnSel));
                System.out.println("Account" + acc + " is clickable!");
            } catch (Exception e) {
                e.printStackTrace();
            }
            swipeBack = new SwipeBack(appiumDriver);
            swipeBack.swipeBack();

            wait.until(ExpectedConditions.visibilityOfElementLocated(discoverSel));
            channelsElements = appiumDriver.findElements(channelsSel);
        }
    }
    @Test
    public void TC002_clickOnAllCategories() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(channelsSel));
        List<MobileElement> channelsElements = appiumDriver.findElements(channelsSel);
        channelsElements.get(5).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(allChannelsSel));
        List<MobileElement> allChannelsElements = appiumDriver.findElements(allChannelsSel);

        for (int i = 0; i < allChannelsElements.size(); i++) {
            allChannelsElements.get(i).click();
            // Verify
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(nameAcc));
                String name = appiumDriver.findElement(nameAcc).getText();
                System.out.println(name + " is clickable!");
            } catch (Exception e) {
                e.printStackTrace();
            }
            swipeBack = new SwipeBack(appiumDriver);
            swipeBack.swipeBack();
            allChannelsElements = appiumDriver.findElements(allChannelsSel);
        }
    }
}
