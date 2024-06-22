package TF06_Create;

import Action.SwipeBack;
import C02_Login.LoginForm;
import C05_Discover.GetEmailOfAccount;
import C05_Discover.VerifyFollowingList;
import DB.Channel_GetLiveSession;
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

public class TF06_VerifyCreateScreen {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private VerifyFollowingList verifyFollowingList;
    private LoginForm loginForm;
    private SwipeBack swipeBack;
    By CreateSel = MobileBy.AccessibilityId("Create");
    By goLiveSel = MobileBy.AccessibilityId("Go Live");
    By scheduleClassSel = MobileBy.AccessibilityId("Schedule Class");
    By joinLiveBtnSel = MobileBy.AccessibilityId("Join Live");
    By goLiveScreenSel =MobileBy.xpath("//android.widget.TextView[@text = 'Information live']");
    By scheduleScreenSel =MobileBy.xpath("//android.widget.TextView[@text = 'Schedule a Room']");


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
        wait.until(ExpectedConditions.visibilityOfElementLocated(CreateSel));
        appiumDriver.findElement(CreateSel).click();
        swipeBack = new SwipeBack(appiumDriver);
    }
    @Test
    public void TC001 () {
        // Xác thực Go Live tappale
        wait.until(ExpectedConditions.visibilityOfElementLocated(goLiveSel));
        appiumDriver.findElement(goLiveSel).click();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(goLiveScreenSel));
            System.out.println("[PASS] Go Live is tappale!");
        } catch (Exception e) {
            System.out.println("FAIL: Go Live not respond!");
        }
        swipeBack.swipeBack();
    }
    @Test
    public void TC002 () {
        // Xác thực Schedule Class tappale
        wait.until(ExpectedConditions.visibilityOfElementLocated(scheduleClassSel));
        appiumDriver.findElement(scheduleClassSel).click();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(scheduleScreenSel));
            System.out.println("[PASS] Schedule Class is tappale!");
        } catch (Exception e) {
            System.out.println("FAIL: Schedule Class not respond!");
        }
        swipeBack.swipeBack();
    }
    @Test
    public void TC003 () {
        // Xác thực Thumbnail ở mục UPCOMING CLASS hiển thị nếu user có lịch hẹn livestream
        // Count scheduled livestream in DB by email
        int count = Channel_GetLiveSession.countLiveSchedule(AccEx.validEmail1);
        System.out.println("Số livestream đã được xếp lịch trước (trên DB): " + count);
        // Count live session displayed in UPCOMING CLASS
        List<MobileElement> liveElements = appiumDriver.findElements(joinLiveBtnSel);
        int countLiveInUpcomming = liveElements.size();
        System.out.println("Số livestream được hiển thị ở UPCOMING CLASS: " + countLiveInUpcomming);
        if (count == countLiveInUpcomming) {
            System.out.println("[PASS] Data displayed in UPCOMING CLASS is correct!");
        } else {
            System.out.println("FAIL: Data displayed in UPCOMING CLASS does not match!");
        }
    }
}
