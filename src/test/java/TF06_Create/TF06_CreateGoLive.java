package TF06_Create;

import Action.ScrollUp90;
import Action.SwipeBack;
import C02_Login.LoginForm;
import C05_Discover.VerifyFollowingList;
import C06_Create.GoliveForm;
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

import java.util.Random;

public class TF06_CreateGoLive {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private VerifyFollowingList verifyFollowingList;
    private LoginForm loginForm;
    private SwipeBack swipeBack;
    private GoliveForm goliveForm;
    private ScrollUp90 scrollUp90;
    By createSel = MobileBy.AccessibilityId("Create");
    By goLiveSel = MobileBy.AccessibilityId("Go Live");
    By scheduleClassSel = MobileBy.AccessibilityId("Schedule Class");
    By joinLiveBtnSel = MobileBy.AccessibilityId("Join Live");
    By goLiveScreenSel =MobileBy.xpath("//android.widget.TextView[@text = 'Information live']");
    By scheduleScreenSel =MobileBy.xpath("//android.widget.TextView[@text = 'Schedule a Room']");
    By createBtnSel = MobileBy.AccessibilityId("Create");
    By cancelBtnSel = MobileBy.AccessibilityId("Cancel");
    By titleSel = MobileBy.xpath("//android.widget.EditText[@text = 'My live stream Kevin']");
    By descSel = MobileBy.xpath("//android.widget.EditText[@text = 'Description']");
    By categorySel = MobileBy.AccessibilityId("Yoga");
    String[] categoryList = {"Yoga", "Cardio", "Boxing", "Stretching", "Trending", "Music"};
    By durationSel = MobileBy.xpath("//android.widget.EditText[@text = '10']");
    By uploadThumbnailSel = MobileBy.xpath("//android.widget.TextView[@text = 'Upload thumbnail']");
    String title = "Test livestream Go Live";
    String desc = "Desciptipn of Go Live";
    Random random = new Random();
    int randomMin = 0 + random.nextInt(100);
    String randomMinString = Integer.toString(randomMin);

    @BeforeClass
    public void setup() {
        appiumDriver = LaunchParagon.getAppiumDriver();
        wait = new WebDriverWait(appiumDriver, 10);
        swipeBack = new SwipeBack(appiumDriver);
        goliveForm = new GoliveForm(appiumDriver);
        scrollUp90 = new ScrollUp90(appiumDriver);
        loginForm = new LoginForm(appiumDriver);
        loginForm.inputEmail(AccEx.validEmail1);
        loginForm.inputPassword(AccEx.validPassword);
        loginForm.clickOnSignInBtn();
        wait.until(ExpectedConditions.visibilityOfElementLocated(createSel));
        appiumDriver.findElement(createSel).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(goLiveSel));
        appiumDriver.findElement(goLiveSel).click();
    }
    @Test
    public void TC001_CreateGoLive_LeftFieldEmpty() {
        // Tap on Create button
        wait.until(ExpectedConditions.visibilityOfElementLocated(createBtnSel));
        appiumDriver.findElement(createBtnSel).click();
        if (goliveForm.isCreateGoliveFail()) {
            System.out.println("[PASS] Cannot create livestream if left all fields empty!");
        } else {
            System.out.println("FAIL: Users can successfully create livestream even if left all fields empty!");
        }
        scrollUp90.scrollUpTo();
    }
    @Test
    public void TC002_CreateGoLive_LeftTitleEmpty() {
        // Left Title field empty
        goliveForm.inputDesc(desc);
        goliveForm.inputCategory("Cardio");
        goliveForm.inputDuration(randomMinString);
        goliveForm.uploadThumbnail();
        // Tap on Create button
        wait.until(ExpectedConditions.visibilityOfElementLocated(createBtnSel));
        appiumDriver.findElement(createBtnSel).click();
        if (goliveForm.isCreateGoliveFail()) {
            System.out.println("[PASS] Cannot create livestream if left title field empty!");
        } else {
            System.out.println("FAIL: Users can successfully create livestream even if left title field empty!");
        }
    }
    @Test
    public void TC003_CreateGoLive_LeftDescEmpty() {
        goliveForm.clear();
        // Input
        goliveForm.inputDesc(title);
        // Tap on Create button
        wait.until(ExpectedConditions.visibilityOfElementLocated(createBtnSel));
        appiumDriver.findElement(createBtnSel).click();
        if (goliveForm.isCreateGoliveFail()) {
            System.out.println("[PASS] Cannot create livestream if left title field empty!");
        } else {
            System.out.println("FAIL: Users can successfully create livestream even if left title field empty!");
        }
    }
}
