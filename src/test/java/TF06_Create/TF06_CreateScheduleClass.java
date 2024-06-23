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
import java.time.LocalTime;
import java.time.YearMonth;
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
    String title = "Test livestream Go Live";
    String desc = "Desciptipn of Go Live";
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
    public void TC001_CreateGoLive_LeftFieldEmpty() {
        // Tap on Create button
        scheduleClassForm.clickOnDoneBtn();
        if (scheduleClassForm.isCreateScheduleFail()) {
            System.out.println("[PASS] Cannot create livestream if left all fields empty");
        } else {
            System.out.println("FAIL: User can successfully create livestream even if left all fields empty");
        }
    }
    @Test
    public void TC002_CreateGoLive_LeftTitleEmpty() {
        // Left Title field empty
        scheduleClassForm
                .inputDesc(desc)
                .inputCategory(category)
                .inputDuration(duration)
                .inputDate(validDate, validMonthYear)
                .isDateSelectedDisplayCorrect(validDate, validMonthYear)
                .inputTime(validHour, validMinutes)
                .isTimeSelectedDisplayCorrect(validHour, validMinutes)
                .uploadThumbnail()
                .clickOnDoneBtn()
                .clear();
        // Verify create failed
        if (scheduleClassForm.isCreateScheduleFail()) {
            System.out.println("[PASS] Cannot create livestream without input Title");
        } else {
            System.out.println("FAIL: User can successfully create livestream without input Title");
        }
    }
    @Test
    public void TC003_CreateGoLive_LeftDescEmpty() {
        // Left Description field empty
        scheduleClassForm
                .inputTitle(title)
                .inputCategory(category)
                .inputDuration(duration)
                .inputDate(validDate, validMonthYear)
                .isDateSelectedDisplayCorrect(validDate, validMonthYear)
                .inputTime(validHour, validMinutes)
                .isTimeSelectedDisplayCorrect(validHour, validMinutes)
                .uploadThumbnail()
                .clickOnDoneBtn()
                .clear();
        // Verify create failed
        if (scheduleClassForm.isCreateScheduleFail()) {
            System.out.println("[PASS] Cannot create livestream without input Title");
        } else {
            System.out.println("FAIL: User can successfully create livestream without input Title");
        }
    }
    @Test
    public void TC004_CreateGoLive_LeftDurationEmpty() {
        scheduleClassForm
                .inputTitle(title)
                .inputDesc(desc)
                .inputCategory(category)
                .inputDate(validDate, validMonthYear)
                .isDateSelectedDisplayCorrect(validDate, validMonthYear)
                .inputTime(validHour, validMinutes)
                .isTimeSelectedDisplayCorrect(validHour, validMinutes)
                .uploadThumbnail()
                .clickOnDoneBtn()
                .clear();
        // Verify create failed
        if (scheduleClassForm.isCreateScheduleFail()) {
            System.out.println("[PASS] Cannot create livestream without input Duration");
        } else {
            System.out.println("FAIL: User can successfully create livestream without input Duration");
        }
    }
    @Test
    public void TC005_CreateGoLive_LeftThumnailEmpty() {
        scheduleClassForm
                .inputTitle(title)
                .inputDesc(desc)
                .inputCategory(category)
                .inputDuration(duration)
                .inputDate(validDate, validMonthYear)
                .isDateSelectedDisplayCorrect(validDate, validMonthYear)
                .inputTime(validHour, validMinutes)
                .isTimeSelectedDisplayCorrect(validHour, validMinutes)
                .clickOnDoneBtn();
        // Verify create failed
        if (scheduleClassForm.isCreateScheduleFail()) {
            System.out.println("[PASS] Cannot create livestream without upload thumbnail");
        } else {
            System.out.println("FAIL: User can successfully create livestream without upload thumbnail");
        }
        scheduleClassForm
                .clickOnCancelBtn()
                .createScheduleClassBtn();
    }
    @Test
    public void TC006_inputInvalidDateTime() {
        // Get current Date and Time
        LocalDate localDate = LocalDate.now();
        String today = String.valueOf(localDate.getDayOfMonth());
        String monthYear = "tháng " + localDate.getMonthValue() + " " + localDate.getYear();
        // Get Time
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formattedTime = DateTimeFormatter.ofPattern("HH:mm");
        String currentTime = localDateTime.format(formattedTime);
        int hour = Integer.parseInt(currentTime.substring(0,2));
        int min = Integer.parseInt(currentTime.substring(3));
        System.out.println("Now it is: " + hour + ":" + min);
        String invalidHour = Integer.toString(random.nextInt(hour + 1));
        String invalidMinute = Integer.toString(random.nextInt(min));
        System.out.println("Input time: " + invalidHour + ":" + invalidMinute);

        scheduleClassForm
                .inputTitle(title)
                .inputDesc(desc)
                .inputCategory(category)
                .inputDuration(duration)
//                .inputDate(today, monthYear) // Không cần step này vì app đã tự động input giá trị là today
                .inputTime(invalidHour, invalidMinute)
                .isInputDateTimeFail();
    }
}
