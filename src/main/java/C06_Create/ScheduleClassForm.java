package C06_Create;

import Action.ScrollDown90Percent;
import Action.ScrollUp90;
import Action.SwipeXY;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.getLocationElement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ScheduleClassForm {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private ScrollDown90Percent scrollDown;
    private ScrollUp90 scrollUp90;
    private getLocationElement getLocationElement;
    private SwipeXY swipeXY;

    By fieldsSel = MobileBy.xpath("//android.widget.EditText[@text]");
    By categorySel = MobileBy.xpath("//android.view.ViewGroup[@content-desc]/android.widget.TextView");
    By dateTimeSel = MobileBy.xpath("//android.view.ViewGroup[@content-desc]/android.view.ViewGroup/android.widget.TextView");
    By uploadThumbnailSel = MobileBy.xpath("//android.widget.TextView[@text = 'Upload thumbnail']");
    By tapOutside = MobileBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout");
    By albumTagSel = MobileBy.AccessibilityId("Album");
    By albumSel = MobileBy.xpath("//android.widget.TextView[@text = 'Máy ảnh']");
    By imageSel = MobileBy.xpath("//android.widget.FrameLayout[contains(@content-desc, 'Ảnh được chụp vào')]");
    By delThumbnailBtnSel = MobileBy.xpath("//android.view.ViewGroup[4]/android.view.ViewGroup/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.ImageView");
    By scaleSel = MobileBy.xpath("//*[@resource-id = 'com.metasolutions.paragon:id/scale_scroll_wheel']");
    By errorDateTimeSel = MobileBy.xpath("//android.widget.TextView[@text = 'Please select a future time']");
    By nextMonthBtnSel = MobileBy.AccessibilityId("Tháng sau");
    By monthHeaderSel = MobileBy.xpath("//android.widget.TextView[contains(@text, 'thg')]");
    By currentMonthSel = MobileBy.xpath("//android.view.View[@content-desc]");
    By OKSel = MobileBy.xpath("//android.widget.Button[@text = 'OK']");
    By inputTimeBtnSel = MobileBy.AccessibilityId("Chuyển sang chế độ nhập văn bản để nhập thời gian.");
    By hourSel = MobileBy.id("android:id/input_hour");
    By minutesSel = MobileBy.id("android:id/input_minute");
    By createSel = MobileBy.AccessibilityId("Create");
    By scheduleClassSel = MobileBy.AccessibilityId("Schedule Class");
    By doneBtnSel = MobileBy.AccessibilityId("Done");
    By cancelBtnSel = MobileBy.AccessibilityId("Cancel");

    public ScheduleClassForm(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
        this.wait = new WebDriverWait(appiumDriver, 10);
    }

    public void enter() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tapOutside));
        appiumDriver.findElement(tapOutside).click();
    }
    public ScheduleClassForm createScheduleClassBtn() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createSel));
        appiumDriver.findElement(createSel).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(scheduleClassSel));
        appiumDriver.findElement(scheduleClassSel).click();
        return this;
    }
    public ScheduleClassForm clickOnDoneBtn() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(doneBtnSel));
        appiumDriver.findElement(doneBtnSel).click();
        return this;
    }
    public ScheduleClassForm clickOnCancelBtn() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cancelBtnSel));
        appiumDriver.findElement(cancelBtnSel).click();
        return this;
    }
    public ScheduleClassForm inputTitle(String title) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(fieldsSel));
        List<MobileElement> fields = appiumDriver.findElements(fieldsSel);
        fields.get(0).sendKeys(title);
        enter();
        return this;
    }
    public ScheduleClassForm inputDesc(String desc) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(fieldsSel));
        List<MobileElement> fields = appiumDriver.findElements(fieldsSel);
        fields.get(1).sendKeys(desc);
        enter();
        return this;
    }
    public ScheduleClassForm inputDuration(String duration) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(fieldsSel));
        List<MobileElement> fields = appiumDriver.findElements(fieldsSel);
        fields.get(2).sendKeys(duration);
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
        swipeXY = new SwipeXY(appiumDriver);
        swipeXY.swipe((elementX + 400), elementY, elementX, elementY);
        MobileElement doneBtn = appiumDriver.findElementByAccessibilityId("Crop");
        doneBtn.click();
        return this;
    }
    public ScheduleClassForm clear() {
        scrollUp90 = new ScrollUp90(appiumDriver);
        scrollUp90.scrollUpTo();

        List<MobileElement> fieldElements = appiumDriver.findElements(By.xpath("//android.widget.EditText[@text]"));
        for (MobileElement element: fieldElements) {
            element.clear();
        }

        scrollDown = new ScrollDown90Percent(appiumDriver);
        scrollDown.scrollDownTo();
        try {
            boolean delBtn = appiumDriver.findElement(delThumbnailBtnSel).isDisplayed();
            if (delBtn) {
                appiumDriver.findElement(delThumbnailBtnSel).click();
            }
        } catch (NoSuchElementException e) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(uploadThumbnailSel));
            System.out.println("Thumbnail field empty");
        }

        scrollUp90 = new ScrollUp90(appiumDriver);
        scrollUp90.scrollUpTo();
        return this;
    }
    public boolean isCreateScheduleFail() {
        try {
            Thread.sleep(3000);
            boolean doneBtnDisplayed = isElementDisplayed(doneBtnSel);
//            boolean error1Displayed = isElementDisplayed(error1Sel);
//            boolean error2Displayed = isElementDisplayed(error2Sel);
//            boolean error3Displayed = isElementDisplayed(error3Sel);
//            return error1Displayed || error2Displayed || error3Displayed || createBtnDisplayed;
            return doneBtnDisplayed;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
//                System.out.println("Currently displaying: " + monthYearDisplaying);
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
    public ScheduleClassForm isDateSelectedDisplayCorrect(String dd, String MM) {
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
        return this;
    }
    public ScheduleClassForm isTimeSelectedDisplayCorrect(String hh, String mm) {
        try {
            String time = hh + ":" + mm;
            DateFormat inputFormat = new SimpleDateFormat("H:mm");
            DateFormat outputFormat = new SimpleDateFormat("HH:mm");
            // Không cho phép chuyển đổi linh hoạt
            inputFormat.setLenient(false); // tránh các trường hợp như "0:48" được hiểu là 12:48 AM (12 giờ 48 phút sáng).

            Date date = inputFormat.parse(time);
            String selectedTime = outputFormat.format(date);
            System.out.println("Thời gian đã input: " + selectedTime);

            wait.until(ExpectedConditions.visibilityOfElementLocated(dateTimeSel));
            List<MobileElement> dateElement = appiumDriver.findElements(dateTimeSel);
            String currentDisplayingTime = dateElement.get(1).getText().trim();
            System.out.println("Thời gian hiển thị: " + currentDisplayingTime);
            if (currentDisplayingTime.equals(selectedTime)) {
                System.out.println("[PASS] Thời gian đã input và thời gian được hiển thị khớp nhau!");
            } else {
                System.out.println("FAIL: Thời gian đã input và thời gian được hiển thị không khớp nhau");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }
    public ScheduleClassForm isInputDateTimeFail() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorDateTimeSel));
        MobileElement error = appiumDriver.findElement(errorDateTimeSel);
        System.out.println("[PASS]: Users received a error: " + error.getText());
        wait.until(ExpectedConditions.visibilityOfElementLocated(OKSel));
        appiumDriver.findElement(OKSel).click();
        return this;
    }
    public final static String desc999Words= "Ngày 16 tháng 9 năm 1963, Malaya, Singapore, Bắc Borneo và Sarawak chính thức hợp nhất và Malaysia hình thành.[34] Chính phủ của Đảng Hành động Nhân dân cảm thấy sẽ khó khăn để Singapore tồn tại như một quốc gia. Singapore thiếu tài nguyên tự nhiên và đối diện với suy giảm mậu dịch trung chuyển cùng dân số tăng trưởng vốn đòi hỏi công việc. Do đó, Singapore cảm thấy rằng hợp nhất có lợi cho kinh tế khi tạo ra một thị trường tự do chung, loại bỏ thuế quan mậu dịch, giải quyết khủng hoảng thất nghiệp và trợ giúp các ngành công nghiệp mới. Chính phủ Anh Quốc miễn cưỡng trong việc trao quyền độc lập hoàn toàn cho Singapore vì họ cho rằng điều này sẽ cung cấp một nơi ẩn náu cho cộng sản. Liên minh không vững vàng ngay từ đầu, trong tuyển cử cấp bang tại Singapore năm 1963, một chi bộ địa phương của Tổ chức dân tộc Mã Lai thống nhất tham gia tranh cử bất chấp thỏa thuận rằng đảng này sẽ không tham gia chính trị của bang trong những năm đầu khi Malaysia hình thành. Mặc dù Tổ chức dân tộc Mã ";
    public ScheduleClassForm clearDescField() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scrollUp90 = new ScrollUp90(appiumDriver);
        scrollUp90.scrollUpTo();

        wait.until(ExpectedConditions.visibilityOfElementLocated(fieldsSel));
        List<MobileElement> fieldElements = appiumDriver.findElements(fieldsSel);
        fieldElements.get(1).clear(); // Clear description field
        return this;
    }
    public ScheduleClassForm isDescriptionDisplayCorrect(String inputDesc) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(fieldsSel));
        List<MobileElement> fieldElements = appiumDriver.findElements(fieldsSel);
        String descDisplaying = fieldElements.get(1).getText();
        System.out.println(descDisplaying);
        if (descDisplaying.equals(inputDesc)) {
            System.out.println("[PASS] Nội dung description hiển thị đúng và đủ");
        } else {
            System.out.println("FAIL: Nội dung description hiển thị không đúng");
        }
        return this;
    }
}
