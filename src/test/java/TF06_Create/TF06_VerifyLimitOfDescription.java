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
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class TF06_VerifyLimitOfDescription {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private LoginForm loginForm;
    private SwipeBack swipeBack;
    private ScheduleClassForm scheduleClassForm;
    private ScrollUp90 scrollUp90;
    String title = "Test livestream Go Live";
    String desc999Words = ScheduleClassForm.desc999Words;
    // Get random minute (duration)
    Random random = new Random();
    int randomDuration = random.nextInt(100);
    String duration = Integer.toString(randomDuration);
    // Get current Date and Time
    LocalDate localDate = LocalDate.now();
    int year = localDate.getYear();
    int month = (localDate.getMonthValue() + 1) + random.nextInt(12 - localDate.getMonthValue()); // Random từ tháng kế tiếp đến tháng 12
    int daysInMonth = YearMonth.of(year, month).lengthOfMonth(); // Get days of random month
    String validDate = String.valueOf(1 + random.nextInt(daysInMonth));
    String validMonthYear = "tháng " + month + " " + year;
    // Random hour and minutes
    String validHour = Integer.toString(random.nextInt(24));
    String validMinutes = Integer.toString(random.nextInt(60));
    // Get category random
    String[] categoryList = {"Yoga", "Cardio", "Boxing", "Stretching", "Trending", "Music"};
    int categoryIndex = random.nextInt(categoryList.length);
    String category = categoryList[categoryIndex];

    @BeforeClass
    public void setup() {
        appiumDriver = LaunchParagon.getAppiumDriver();
        wait = new WebDriverWait(appiumDriver, 10);
        swipeBack = new SwipeBack(appiumDriver);
        scrollUp90 = new ScrollUp90(appiumDriver);
        loginForm = new LoginForm(appiumDriver);
        loginForm.login(AccEx.validEmail1, AccEx.validPassword);
        scheduleClassForm = new ScheduleClassForm(appiumDriver);
        scheduleClassForm.createScheduleClassBtn();
    }
    @Test
    public void TC001_inputDesc999Word() {
        System.out.println("Characters of description: " + desc999Words.length());
        System.out.println(desc999Words);
        scheduleClassForm
                .inputTitle(title)
                .inputDesc(desc999Words)
                .inputCategory(category)
                .inputDuration(duration)
                .inputDate(validDate, validMonthYear)
                .inputTime(validHour, validMinutes)
                .uploadThumbnail();
//                .clickOnDoneBtn();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scrollUp90.scrollUpTo();
        scheduleClassForm
                .isDescriptionDisplayCorrect(desc999Words)
                .clearDescField();
    }
    @Test
    public void TC002_inputDesc1000Word() {
        scheduleClassForm
//                .inputTitle(title)
                .inputDesc(desc999Words + "a");
//                .inputCategory(category)
//                .inputDuration(duration)
//                .inputDate(validDate, validMonthYear)
//                .inputTime(validHour, validMinutes)
//                .uploadThumbnail()
//                .clickOnDoneBtn()
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scrollUp90.scrollUpTo();
        scheduleClassForm
                .isDescriptionDisplayCorrect(desc999Words + "a")
                .clearDescField();
    }
    @Test
    public void TC003_inputDesc1001Word() {
        scheduleClassForm
//                .inputTitle(title)
                .inputDesc(desc999Words + "aa");
//                .inputCategory(category)
//                .inputDuration(duration)
//                .inputDate(validDate, validMonthYear)
//                .inputTime(validHour, validMinutes)
//                .uploadThumbnail()
//                .clickOnDoneBtn();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scrollUp90.scrollUpTo();
        scheduleClassForm.isDescriptionDisplayCorrect(desc999Words + "aa");
    }
}
