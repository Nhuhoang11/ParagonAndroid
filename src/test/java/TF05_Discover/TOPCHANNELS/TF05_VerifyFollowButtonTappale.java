package TF05_Discover.TOPCHANNELS;

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

public class TF05_VerifyFollowButtonTappale {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private LoginForm loginForm;
    private VerifyFollowingList verifyFollowingList;
    By discoverSel = MobileBy.AccessibilityId("Discover");
    By channelsSel = MobileBy.xpath("//android.view.ViewGroup[@content-desc]/android.widget.TextView[1]"); // 8th -> 11th; View all -> 7th
    By buttonSel = MobileBy.xpath("//android.view.ViewGroup[@content-desc]/android.widget.TextView"); // 2nd element
    By allChannelsSel = MobileBy.xpath("//android.view.ViewGroup[contains(@content-desc, ', ')]//android.widget.TextView[1][not(contains(@text, 'Follow')) and not(contains(@text, 'Unfollow'))]");
    By followUnFollowBtnSel = MobileBy.xpath("//android.view.ViewGroup[contains(@content-desc, ', ')]//android.widget.TextView[1][(contains(@text, 'Follow')) or (contains(@text, 'Unfollow'))]");
    By backBtnSel =MobileBy.xpath("//android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.ImageView");
//    By followUnFollowBtnSel = MobileBy.xpath("//android.view.ViewGroup[contains(@content-desc, ', ')]//android.widget.TextView[@text='Follow' or @text='Unfollow']");
    By viewAllSel = MobileBy.xpath("//android.widget.TextView[@text = 'View all']");

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
    // Case 1: Follow/Unfollow account in Horizontal List
    public void TC001_VerifyFollowAccInHorizontalList() {
        // Tap on avatar
        wait.until(ExpectedConditions.visibilityOfElementLocated(channelsSel));
        List<MobileElement> channel =appiumDriver.findElements(channelsSel);
        Random random = new Random();
        int randomIndex = 7 + random.nextInt(4);
        String channelName = channel.get(randomIndex).getText();
        channel.get(randomIndex).click();

        // Check button ban đầu là Follow hay Unfollow
        wait.until(ExpectedConditions.visibilityOfElementLocated(buttonSel));
        List<MobileElement> button = appiumDriver.findElements(buttonSel);
        String buttonLabel1 = button.get(1).getText();
        if (buttonLabel1.equals("Unfollow")) {
            System.out.println("Tài khoản " + channelName + " đã được follow...");
        } else {
            System.out.println("Tài khoản " + channelName + " chưa được follow...");
        }
        // Tap on button
        button.get(1).click();
        try {
            Thread.sleep(3000);
            // State của button thay đổi -> Get text button hiện tại
            String buttonLabel2 = button.get(1).getText();
            // Verify button thay đổi sau khi tap
            if (!buttonLabel1.equals(buttonLabel2)) {
                System.out.println("[PASS] " + buttonLabel1 +" success!");
            } else {
                System.out.println("FAIL: Button not respond!");
            }

            // Verify follow/unfollow thành công bằng cách check danh sách following
            verifyFollowingList = new VerifyFollowingList(appiumDriver);
            verifyFollowingList.verifyAccInFollowingList1(channelName, buttonLabel2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    // Case 2: Follow/Unfollow account after expand Top channels List
    public void TC002_VerifyFollowAccInExpandList() {
        // Tap on View all
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewAllSel));
        List<MobileElement> channelsElements = appiumDriver.findElements(viewAllSel);
        channelsElements.get(1).click();
        // Chọn button của một tài khoản bất kỳ trên expanding list
            // Lấy ra tên của account được chọn
        Random random = new Random();
        int randomIndex = 0 + random.nextInt(6);
        wait.until(ExpectedConditions.visibilityOfElementLocated(allChannelsSel));
        List<MobileElement> channel =appiumDriver.findElements(allChannelsSel);
        String channelName = channel.get(randomIndex).getText();

            // Check button ban đầu là Follow hay Unfollow
        wait.until(ExpectedConditions.visibilityOfElementLocated(followUnFollowBtnSel));
        List<MobileElement> button = appiumDriver.findElements(followUnFollowBtnSel);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String buttonLabel1 = button.get(randomIndex).getText();
        System.out.println("Trạng thái ban đầu của button là: " + buttonLabel1);
        if (buttonLabel1.equals("Unfollow")) {
            System.out.println("Tài khoản " + channelName + " đã được follow...");
        } else {
            System.out.println("Tài khoản " + channelName + " chưa được follow...");
        }
            // Tap on button
        button.get(randomIndex).click();
//        button = appiumDriver.findElements(followUnFollowBtnSel);
        try {
            Thread.sleep(3000);
            // State của button thay đổi -> Get text button hiện tại
            String buttonLabel2 = button.get(randomIndex).getText();
            // Verify button thay đổi sau khi tap
            if (!buttonLabel1.equals(buttonLabel2)) {
                System.out.println("[PASS] " + buttonLabel1 +"success!");
            } else {
                System.out.println("FAIL: Button not respond!");
            }

            // Verify follow/unfollow thành công bằng cách check danh sách following
            verifyFollowingList = new VerifyFollowingList(appiumDriver);
            verifyFollowingList.verifyAccInExpandList(channelName, buttonLabel2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    // Case 3: Follow/Unfollow account after expand Top channels List/Detail profile
    public void TC003_VerifyFollowAccInDetailProfile() {
        // Tap on View all
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewAllSel));
        List<MobileElement> channelsElements = appiumDriver.findElements(viewAllSel);
        channelsElements.get(1).click();
        // Tap on avatar
        wait.until(ExpectedConditions.visibilityOfElementLocated(allChannelsSel));
        List<MobileElement> channel =appiumDriver.findElements(allChannelsSel);
        Random random = new Random();
        int randomIndex = 0 + random.nextInt(6);
        String channelName = channel.get(randomIndex).getText();
        channel.get(randomIndex).click();

        // Check button ban đầu là Follow hay Unfollow
        wait.until(ExpectedConditions.visibilityOfElementLocated(buttonSel));
        List<MobileElement> button = appiumDriver.findElements(buttonSel);
        String buttonLabel1 = button.get(1).getText();
        if (buttonLabel1.equals("Unfollow")) {
            System.out.println("Tài khoản " + channelName + " đã được follow...");
        } else {
            System.out.println("Tài khoản " + channelName + " chưa được follow...");
        }
        // Tap on button
        button.get(1).click();
        try {
            Thread.sleep(3000);
            // State của button thay đổi -> Get text button hiện tại
            String buttonLabel2 = button.get(1).getText();
            // Verify button thay đổi sau khi tap
            if (!buttonLabel1.equals(buttonLabel2)) {
                System.out.println("[PASS] " + buttonLabel1 +"success!");
            } else {
                System.out.println("FAIL: Button not respond!");
            }

            // Verify follow/unfollow thành công bằng cách check danh sách following
            verifyFollowingList = new VerifyFollowingList(appiumDriver);
            verifyFollowingList.verifyAccInFollowingList1(channelName, buttonLabel2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
