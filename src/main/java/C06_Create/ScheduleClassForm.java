package C06_Create;

import Action.ScrollDown90Percent;
import Action.SwipeXY;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.getLocationElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ScheduleClassForm {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private ScrollDown90Percent scrollDown;
    private getLocationElement getLocationElement;
    private SwipeXY swipeXY;
    By createBtnSel = MobileBy.AccessibilityId("Create");
    By titleSel = MobileBy.xpath("//android.widget.EditText[@text = 'My live stream Kevin']");
    By descSel = MobileBy.xpath("//android.widget.EditText[@text = 'Description....']");
    By categorySel = MobileBy.AccessibilityId("Yoga");
    By durationSel = MobileBy.xpath("//android.view.ViewGroup[4]/android.widget.EditText");
    By dateTimeSel = MobileBy.xpath("//android.view.ViewGroup[@content-desc]/android.view.ViewGroup/android.widget.TextView");
    By uploadThumbnailSel = MobileBy.xpath("//android.widget.TextView[@text = 'Upload thumbnail']");
    By tapOutside = MobileBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout");
    By albumTagSel = MobileBy.AccessibilityId("Album");
    By albumSel = MobileBy.xpath("//android.widget.TextView[@text = 'Máy ảnh']");
    By imageSel = MobileBy.xpath("//android.widget.FrameLayout[contains(@content-desc, 'Ảnh được chụp vào')]");
    By scaleSel = MobileBy.xpath("//*[@resource-id = 'com.metasolutions.paragon:id/scale_scroll_wheel']");
    By errorDateSel = MobileBy.xpath("//android.widget.TextView[@text = 'Please select a future time']");
    By nextMonthBtnSel = MobileBy.AccessibilityId("Tháng sau");
    By monthHeaderSel = MobileBy.xpath("//android.widget.TextView[contains(@text, 'thg')]");
    By currentMonthSel = MobileBy.xpath("//android.view.View[@content-desc]");
    By OKSel = MobileBy.xpath("//android.widget.Button[@text = 'OK']");
    By inputTimeBtnSel = MobileBy.AccessibilityId("Chuyển sang chế độ nhập văn bản để nhập thời gian.");
    By hourSel = MobileBy.id("android:id/input_hour");
    By minutesSel = MobileBy.id("android:id/input_minute");
    By doneBtnSel = MobileBy.AccessibilityId("Done");

    public ScheduleClassForm(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
        this.wait = new WebDriverWait(appiumDriver, 10);
    }

    public void enter() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tapOutside));
        appiumDriver.findElement(tapOutside).click();
    }
    public ScheduleClassForm clickOnDoneBtn() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(doneBtnSel));
        appiumDriver.findElement(doneBtnSel).click();
        return this;
    }
    public ScheduleClassForm inputTitle(String title) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(titleSel));
        appiumDriver.findElement(titleSel).sendKeys(title);
        enter();
        return this;
    }
    public ScheduleClassForm inputDesc(String desc) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(descSel));
        appiumDriver.findElement(descSel).sendKeys(desc);
        enter();
        return this;
    }
    public ScheduleClassForm inputDuration(String duration) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(durationSel));
        appiumDriver.findElement(durationSel).sendKeys(duration);
        enter();
        return this;
    }
    public ScheduleClassForm inputCategory(String category) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(categorySel));
        appiumDriver.findElement(categorySel).click();
        MobileElement categoryElement = appiumDriver.findElementByAccessibilityId(category);
        categoryElement.click();
        return this;
    }
    public ScheduleClassForm uploadThumbnail() {
        scrollDown = new ScrollDown90Percent(appiumDriver);
        scrollDown.scrollDownTo();
        wait.until(ExpectedConditions.visibilityOfElementLocated(uploadThumbnailSel));
        appiumDriver.findElement(uploadThumbnailSel).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(albumTagSel));
        appiumDriver.findElement(albumTagSel).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(albumSel));
        appiumDriver.findElement(albumSel).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(imageSel));
        appiumDriver.findElement(imageSel).click();
        // Swipe to scale image (yLocation not change)
        MobileElement swipeTool = appiumDriver.findElement(scaleSel);
        utils.getLocationElement.ElementUtils elementUtils = new getLocationElement.ElementUtils(swipeTool);
        int elementX = elementUtils.getElementXCoordinate();
        int elementY = elementUtils.getElementYCoordinate();
        System.out.println(elementX);
        System.out.println(elementY);
        swipeXY = new SwipeXY(appiumDriver);
        swipeXY.swipe((elementX + 400), elementY, elementX, elementY);
        MobileElement doneBtn = appiumDriver.findElementByAccessibilityId("Crop");
        doneBtn.click();
        return this;
    }
    public ScheduleClassForm clear() {
        appiumDriver.findElement(titleSel).clear();
        appiumDriver.findElement(descSel).clear();
        appiumDriver.findElement(durationSel).clear();
        return this;
    }
    public boolean isCreateScheduleFail() {
        try {
            boolean createBtnDisplayed = isElementDisplayed(createBtnSel);
//            boolean error1Displayed = isElementDisplayed(error1Sel);
//            boolean error2Displayed = isElementDisplayed(error2Sel);
//            boolean error3Displayed = isElementDisplayed(error3Sel);
//            return error1Displayed || error2Displayed || error3Displayed || createBtnDisplayed;
            return createBtnDisplayed;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }
    private boolean isElementDisplayed(By by) {
        try {
            MobileElement element = appiumDriver.findElement(by);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    public void isInputDateTimeFail() {}
    public ScheduleClassForm inputTime(String hh, String mm) {
        // Tap on Time field
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateTimeSel));
        List<MobileElement> dateElement = appiumDriver.findElements(dateTimeSel);
        dateElement.get(1).click();
        // Selects input mode by tap on button
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputTimeBtnSel));
        appiumDriver.findElement(inputTimeBtnSel).click();
        // Input hour and minute
        wait.until(ExpectedConditions.visibilityOfElementLocated(hourSel));
        appiumDriver.findElement(hourSel).sendKeys(hh);
        wait.until(ExpectedConditions.visibilityOfElementLocated(minutesSel));
        appiumDriver.findElement(minutesSel).sendKeys(mm);
        appiumDriver.findElement(OKSel).click();

        return this;
    }
    public String findCurrentMonthYear() {
        // Get current month
        List<MobileElement> monthCurrent = appiumDriver.findElements(currentMonthSel);
        String fullText = monthCurrent.get(1).getAttribute("content-desc");
//        String fullText = monthCurrent.get(1).getText();
        int firstSpaceIndex = fullText.indexOf(" ");
        String monthYear = fullText.substring(firstSpaceIndex + 1);
        System.out.println(monthYear);
        return monthYear;
    }
    public ScheduleClassForm inputDate (String dd, String MM) {
        By dateSel = MobileBy.xpath("//android.view.View[@text='" + dd +"']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateTimeSel));
        List<MobileElement> dateElement = appiumDriver.findElements(dateTimeSel);
        dateElement.get(0).click();
        // Tap Next month until target yyyy/MM display
        boolean isVisible = false;
        while (!isVisible) {
            try {
                String monthYearDisplaying = findCurrentMonthYear();
                System.out.println("Currently displaying: " + monthYearDisplaying);
                if (monthYearDisplaying.equals(MM)) {
                    isVisible = true;
                } else {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(nextMonthBtnSel));
                    appiumDriver.findElement(nextMonthBtnSel).click();
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(dateSel));
            appiumDriver.findElement(dateSel).click();
        }
        appiumDriver.findElement(OKSel).click();
        return this;
    }
    // Xác thực ngày được chọn hiển thị đúng trong Date field
    public void isDateSelectedDisplayCorrect(String dd, String MM) {
        // Xác thực ngày được chọn hiển thị đúng trong Date field
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateTimeSel));
        String dateDisplaying = appiumDriver.findElement(dateTimeSel).getText();
        System.out.println("Ngày hiển thị tại Date field: " + dateDisplaying);
        // Tách MM (định dạng: tháng xx 2024) thành month và year riêng biệt để đối chiếu với Date field
        String[] parts = MM.split(" ");
        String month = parts[1];
        String year = parts[2];
        String ddMMyyyy = year + "-" + month + "-" + dd;
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-M-dd");
            Date date = inputFormat.parse(ddMMyyyy);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateSelected = outputFormat.format(date);
            System.out.println("Ngày được chọn: " + dateSelected);
            if (dateSelected.equals(dateDisplaying)) {
                System.out.println("[PASS] Ngày đã chọn và ngày được hiển thị khớp nhau!");
            } else {
                System.out.println("FAIL: Ngày đã chọn và ngày được hiển thị không khớp nhau");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void isTimeSelectedDisplayCorrect(String hh, String mm) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateTimeSel));
        List<MobileElement> dateElement = appiumDriver.findElements(dateTimeSel);
        String currentDisplayingTime = dateElement.get(1).getText();
        System.out.println("Thời gian hiển thị: " + currentDisplayingTime);
        String selectedTime = hh + ":" + mm;
        System.out.println("Thời gian đã input: " + currentDisplayingTime);
        if (currentDisplayingTime.equals(selectedTime)) {
            System.out.println("[PASS] Thời gian đã input và thời gian được hiển thị khớp nhau!");
        } else {
            System.out.println("FAIL: Thời gian đã input và thời gian được hiển thị không khớp nhau");
        }
    }
}
