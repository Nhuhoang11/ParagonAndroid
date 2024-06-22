package TF05_Discover.TOPCHANNELS;

import Action.SwipeBack;
import C02_Login.LoginForm;
import C05_Discover.VerifyFollowingList;
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
import java.util.Random;

public class TR05_VerifyAccountInFollowingList {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private VerifyFollowingList verifyFollowingList;
    private LoginForm loginForm;
    private SwipeBack swipeBack;
    By discoverSel = MobileBy.AccessibilityId("Discover");
    By channelsSel = MobileBy.xpath("//android.view.ViewGroup[@content-desc]/android.widget.TextView[1]"); // 8th -> 11th; View all -> 7th
    By buttonSel = MobileBy.xpath("//android.view.ViewGroup[@content-desc]/android.widget.TextView"); // 2nd element
    By allChannelsSel = MobileBy.xpath("//android.view.ViewGroup[@content-desc]/android.view.ViewGroup[1]/android.widget.ImageView");
    By nameAcc = MobileBy.xpath("//android.view.ViewGroup[3]/android.widget.TextView[1]");

    @BeforeClass
    public void setup() {
        appiumDriver = LaunchParagon.getAppiumDriver();
        wait = new WebDriverWait(appiumDriver, 10);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        loginForm = new LoginForm(appiumDriver);
        loginForm.inputEmail(AccEx.validEmail1);
        loginForm.inputPassword(AccEx.validPassword);
        loginForm.clickOnSignInBtn();
        wait.until(ExpectedConditions.visibilityOfElementLocated(discoverSel));
        appiumDriver.findElement(discoverSel).click();
    }
    @Test
    // Case 1: Follow/Unfollow account ở screen Discover/Horizontal List
    public void TC001_VerifyAnyAccInHorizontalList() {
        // Tap on avatar
        wait.until(ExpectedConditions.visibilityOfElementLocated(channelsSel));
        List<MobileElement> channel =appiumDriver.findElements(channelsSel);
        Random random = new Random();
        int randomIndex = 6 + random.nextInt(4);
        String channelName = channel.get(randomIndex).getText();
        channel.get(randomIndex).click();
        // Check button
        wait.until(ExpectedConditions.visibilityOfElementLocated(buttonSel));
        List<MobileElement> button = appiumDriver.findElements(buttonSel);
        String buttonLabel = button.get(1).getText();
        if (buttonLabel.equals("Unfollow")) {
            System.out.println("Tài khoản " + channelName + " đã được follow...");
        } else {
            System.out.println("Tài khoản " + channelName + " chưa được follow...");
        }

        // Verify
        verifyFollowingList = new VerifyFollowingList(appiumDriver);
        verifyFollowingList.verifyAccInFollowingList();

        swipeBack = new SwipeBack(appiumDriver);
        swipeBack.swipeBack();
        swipeBack.swipeBack();
    }
    @Test
    // Case 2: Follow/Unfollow account ở screen Discover/Horizontal List
    public void TC002_VerifyAllAccInHorizontalList() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(channelsSel));
        List<MobileElement> channelsElements = appiumDriver.findElements(channelsSel);
        for (int i = 6; i <= 9; i++) {
            // Tap on avatar
            String acc = channelsElements.get(i).getText();
            System.out.println(acc);
            channelsElements.get(i).click();
            // Check button
            wait.until(ExpectedConditions.visibilityOfElementLocated(buttonSel));
            List<MobileElement> button = appiumDriver.findElements(buttonSel);
            String buttonLabel = button.get(1).getText();
            if (buttonLabel.equals("Unfollow")) {
                System.out.println("Tài khoản đã được follow...");
            } else {
                System.out.println("Tài khoản chưa được follow...");
            }

            // Verify
            verifyFollowingList = new VerifyFollowingList(appiumDriver);
            verifyFollowingList.verifyAccInFollowingList();

            // Back to Discover
            swipeBack = new SwipeBack(appiumDriver);
            swipeBack.swipeBack();
            swipeBack.swipeBack();
            wait.until(ExpectedConditions.visibilityOfElementLocated(discoverSel));
            appiumDriver.findElement(discoverSel).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(channelsSel));
        }
    }
    // Case 3: Follow/Unfollow account ở screen Discover/View all account
    @Test
    public void TC003_clickOnAllCategories() throws Exception {
        // Tap on View all
        wait.until(ExpectedConditions.visibilityOfElementLocated(channelsSel));
        List<MobileElement> channelsElements = appiumDriver.findElements(channelsSel);
        channelsElements.get(5).click();

        // Tap on each avatar
        wait.until(ExpectedConditions.visibilityOfElementLocated(allChannelsSel));
        List<MobileElement> allChannelsElements = appiumDriver.findElements(allChannelsSel);
        for (int i = 0; i < allChannelsElements.size(); i++) {
            allChannelsElements.get(i).click();
            // Get name account
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(nameAcc));
                String name = appiumDriver.findElement(nameAcc).getText();
                System.out.println(name + " is clickable!");
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Check button
            wait.until(ExpectedConditions.visibilityOfElementLocated(buttonSel));
            List<MobileElement> button = appiumDriver.findElements(buttonSel);
            String buttonLabel = button.get(1).getText();
            if (buttonLabel.equals("Unfollow")) {
                System.out.println("Tài khoản đã được follow...");
            } else {
                System.out.println("Tài khoản chưa được follow...");
            }

            verifyFollowingList = new VerifyFollowingList(appiumDriver);
            verifyFollowingList.verifyAccInFollowingList();

            // Back to Discover
            swipeBack = new SwipeBack(appiumDriver);
            swipeBack.swipeBack();
            swipeBack.swipeBack();
            wait.until(ExpectedConditions.visibilityOfElementLocated(discoverSel));
            appiumDriver.findElement(discoverSel).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(channelsSel));
            channelsElements = appiumDriver.findElements(channelsSel);
            channelsElements.get(5).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(allChannelsSel));
            allChannelsElements = appiumDriver.findElements(allChannelsSel);
        }
    }
}
