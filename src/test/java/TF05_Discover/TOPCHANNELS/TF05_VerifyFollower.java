package TF05_Discover.TOPCHANNELS;

import Action.SwipeBack;
import C02_Login.LoginForm;
import C05_Discover.GetEmailOfAccount;
import DB.Follow_GetFollowerAndFollowing;
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TF05_VerifyFollower {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private LoginForm loginForm;
    private SwipeBack swipeBack;
    By discoverSel = MobileBy.AccessibilityId("Discover");
    By channelsSel = MobileBy.xpath("//android.view.ViewGroup[@content-desc]/android.widget.TextView[1]"); // 8th -> 11th; View all -> 7th
    By buttonSel = MobileBy.xpath("//android.view.ViewGroup[@content-desc]/android.widget.TextView"); // 2nd element
    By allChannelsSel = MobileBy.xpath("//android.view.ViewGroup[contains(@content-desc, ', ')]//android.widget.TextView[1][not(contains(@text, 'Follow')) and not(contains(@text, 'Unfollow'))]");
    By followUnFollowBtnSel = MobileBy.xpath("//android.view.ViewGroup[contains(@content-desc, ', ')]//android.widget.TextView[1][(contains(@text, 'Follow')) or (contains(@text, 'Unfollow'))]");
    By backBtnSel =MobileBy.xpath("//android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.ImageView");
    //    By followUnFollowBtnSel = MobileBy.xpath("//android.view.ViewGroup[contains(@content-desc, ', ')]//android.widget.TextView[@text='Follow' or @text='Unfollow']");
    By viewAllSel = MobileBy.xpath("//android.widget.TextView[@text = 'View all']");
    By accInforSel = MobileBy.xpath("//android.view.ViewGroup[contains(@content-desc, ', ')]");
    By followerOnProfileSel = MobileBy.xpath("//android.widget.TextView[contains(@text, 'followers')]");

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
        loginForm.inputEmail(AccEx.validEmail1);
        loginForm.inputPassword(AccEx.validPassword);
        loginForm.clickOnSignInBtn();
        wait.until(ExpectedConditions.visibilityOfElementLocated(discoverSel));
        appiumDriver.findElement(discoverSel).click();
    }
    @Test
    public void TC001_VerifyFollowerOnDiscover() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(accInforSel));
        List<MobileElement> channelsElements = appiumDriver.findElements(accInforSel);
        for (int i = 0; i < 4; i++) {
            String text = channelsElements.get(i).getAttribute("content-desc");
//            String text = appiumDriver.findElements(accInforSel).get(i).getText();
            System.out.println(text);
            String[] parts = text.split(",");
            String nameChannel = parts[0].trim();

            String textFollowerOnDiscover = parts[1].trim();
            System.out.println(textFollowerOnDiscover);// Chỉ lấy phần số (đứng trước từ "follower")
            String numberFollowerOnDiscoverScreen = textFollowerOnDiscover.split(" ")[0].trim(); // Chỉ lấy phần số (đứng trước từ "follower")

            System.out.println("On Discover screen: " + nameChannel + " have " + numberFollowerOnDiscoverScreen + " follower");
            // Tap on account
            channelsElements.get(i).click();
            // Get number of follower on Profile screen
            String numberFollowerOnProfileScreen = appiumDriver.findElement(followerOnProfileSel).getText().split(" ")[0].trim();
            System.out.println("On Profile screen: " + nameChannel + " have " + numberFollowerOnProfileScreen + " follower");
            // Get email of Channel and Number of follower on DB:
            try {
                // Get email of Channel
                String email = Follow_GetFollowerAndFollowing.getEmailOfChannel(nameChannel);
                // Get number of follower on DB
                int followerOnDB = Follow_GetFollowerAndFollowing.countFollowerOfChannel(nameChannel);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Verify index in Discover & Profile
            if (numberFollowerOnDiscoverScreen.equals(numberFollowerOnProfileScreen)) {
                System.out.println("[PASS] Follower on Discover and Profile matched!");
            } else {
                System.out.println("FAIL: Follower on Discover and Profile not matched!");
                System.out.println("--------------------------------------------------");
            }
            // Verify index in Profile & DB
            if (numberFollowerOnProfileScreen.equals(followUnFollowBtnSel)) {
                System.out.println("[PASS] Follower on DB and Profile matched!");
            } else {
                System.out.println("FAIL: Follower on DB and Profile not matched!");
                System.out.println("--------------------------------------------------");
            }
            swipeBack = new SwipeBack(appiumDriver);
            swipeBack.swipeBack();
        }
    }
    @Test
    public void TC002_VerifyFollowerOnExpandList() {
        // Tap on View all
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewAllSel));
        List<MobileElement> viewAlls = appiumDriver.findElements(viewAllSel);
        viewAlls.get(1).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(accInforSel));
        List<MobileElement> channelsElements = appiumDriver.findElements(accInforSel);
        for (int i = 0; i < channelsElements.size(); i++) {
            String text = channelsElements.get(i).getAttribute("content-desc");
//            String text = appiumDriver.findElements(accInforSel).get(i).getText();
            System.out.println(text);
            String[] parts = text.split(",");
            String nameChannel = parts[0].trim();

            String textFollowerOnDiscover = parts[1].trim();
            System.out.println(textFollowerOnDiscover);// Chỉ lấy phần số (đứng trước từ "follower")
            String numberFollowerOnDiscoverScreen = textFollowerOnDiscover.split(" ")[0].trim(); // Chỉ lấy phần số (đứng trước từ "follower")

            System.out.println("On Top channels expand screen: " + nameChannel + " have " + numberFollowerOnDiscoverScreen + " follower");
            // Tap on account
            channelsElements.get(i).click();
            // Get number of follower on Profile screen
            String numberFollowerOnProfileScreen = appiumDriver.findElement(followerOnProfileSel).getText().split(" ")[0].trim();
            System.out.println("On Profile screen: " + nameChannel + " have " + numberFollowerOnProfileScreen + " follower");
            // Get email of Channel and Number of follower on DB:
            try {
                // Get email of Channel
                String email = Follow_GetFollowerAndFollowing.getEmailOfChannel(nameChannel);
                // Get number of follower on DB
                int followerOnDB = Follow_GetFollowerAndFollowing.countFollowerOfChannel(nameChannel);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Verify index in Discover & Profile
            if (numberFollowerOnDiscoverScreen.equals(numberFollowerOnProfileScreen)) {
                System.out.println("[PASS] Follower on Top channels expand and Profile matched!");
            } else {
                System.out.println("FAIL: Follower on Top channels expand and Profile not matched!");
                System.out.println("--------------------------------------------------");
            }
            // Verify index in Profile & DB
            if (numberFollowerOnProfileScreen.equals(followUnFollowBtnSel)) {
                System.out.println("[PASS] Follower on DB and Profile matched!");
            } else {
                System.out.println("FAIL: Follower on DB and Profile not matched!");
                System.out.println("--------------------------------------------------");
            }
            swipeBack = new SwipeBack(appiumDriver);
            swipeBack.swipeBack();
        }
    }
}
