package TF06_Create;

import Action.ScrollUp90;
import Action.SwipeBack;
import C02_Login.LoginForm;
import C05_Discover.VerifyFollowingList;
import C06_Create.ScheduleClassForm;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class TF06_CreateScheduleClass {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private VerifyFollowingList verifyFollowingList;
    private LoginForm loginForm;
    private SwipeBack swipeBack;
    private ScheduleClassForm scheduleClassForm;
    private ScrollUp90 scrollUp90;
    By createSel = MobileBy.AccessibilityId("Create");
    By scheduleClassSel = MobileBy.AccessibilityId("Schedule Class");
    By doneBtnSel = MobileBy.AccessibilityId("Done");
    String title = "Test livestream Go Live";
    String desc = "Desciptipn of Go Live";
    Random random = new Random();
    int randomMin = 0 + random.nextInt(100);
    String randomMinString = Integer.toString(randomMin);
    // Get current Date and Time
    LocalDate localDate = LocalDate.now();
    int tomorrow = localDate.getDayOfMonth() + 1;
    int month = localDate.getMonthValue() + 2;
    int year = localDate.getYear();
    String validDate = String.valueOf(tomorrow);
    String validMonthYear = "tháng " + month + " " + year;
    LocalTime localTime = LocalTime.now();
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    String formattedTime = localTime.format(timeFormatter);
    String validHour = formattedTime.substring(0, 3);
    String validMinutes = formattedTime.substring(3);
    // Get category random
    String[] categoryList = {"Yoga", "Cardio", "Boxing", "Stretching", "Trending", "Music"};
    int categoryIndex = 0 + random.nextInt(categoryList.length);
    String category = categoryList[categoryIndex].toString();

    @BeforeClass
    public void setup() {
        appiumDriver = LaunchParagon.getAppiumDriver();
        wait = new WebDriverWait(appiumDriver, 10);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        swipeBack = new SwipeBack(appiumDriver);
        scheduleClassForm = new ScheduleClassForm(appiumDriver);
        scrollUp90 = new ScrollUp90(appiumDriver);
        loginForm = new LoginForm(appiumDriver);
        loginForm.inputEmail(AccEx.validEmail1);
        loginForm.inputPassword(AccEx.validPassword);
        loginForm.clickOnSignInBtn();
        wait.until(ExpectedConditions.visibilityOfElementLocated(createSel));
        appiumDriver.findElement(createSel).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(scheduleClassSel));
        appiumDriver.findElement(scheduleClassSel).click();
    }

    @Test
    public void TC001_CreateGoLive_LeftFieldEmpty() {
        // Tap on Create button

    }
    @Test
    public void TC002_CreateGoLive_LeftTitleEmpty() {
        // Left Title field empty
        scheduleClassForm.inputDesc(desc);
        scheduleClassForm.inputCategory(category);
        scheduleClassForm.inputDuration(randomMinString);
        scheduleClassForm.uploadThumbnail();
        // Tap on Create button
        wait.until(ExpectedConditions.visibilityOfElementLocated(doneBtnSel));
        appiumDriver.findElement(doneBtnSel).click();

    }
    @Test
    public void TC003_CreateGoLive_LeftDescEmpty() {
        scheduleClassForm.clear();
        // Input
        scheduleClassForm.inputDesc(title);
        // Tap on Create button
        wait.until(ExpectedConditions.visibilityOfElementLocated(doneBtnSel));
        appiumDriver.findElement(doneBtnSel).click();

    }
    @Test
    public void TC004_CreateGoLive_LeftDurationEmpty() {
        scheduleClassForm.clear()
                .inputTitle(title)
                .inputDesc(desc)
                .inputCategory(category)
                .inputDate(validDate, validMonthYear)
                .inputTime(validHour, validMinutes)
                .clickOnDoneBtn();
        if (scheduleClassForm.isCreateScheduleFail()) {
            System.out.println("[PASS] Cannot create livestream without input Duration");
        } else {
            System.out.println("FAIL: User can successfully create livestream without input Duration");
        }
    }
    @Test
    public void TC() {
        scheduleClassForm.inputDate(validDate, validMonthYear);
        scheduleClassForm.isDateSelectedDisplayCorrect(validDate, validMonthYear);
        scheduleClassForm.inputTime(validHour, validMinutes);
        scheduleClassForm.isTimeSelectedDisplayCorrect(validHour, validMinutes);
    }
}
