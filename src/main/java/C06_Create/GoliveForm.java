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

import javax.swing.text.Element;
import java.lang.reflect.Array;

public class GoliveForm {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private ScrollDown90Percent scrollDown;
    private getLocationElement getLocationElement;
    private SwipeXY swipeXY;
    By createBtnSel = MobileBy.AccessibilityId("Create");
    By cancelBtnSel = MobileBy.AccessibilityId("Cancel");
    By titleSel = MobileBy.xpath("//android.widget.EditText[@text = 'My live stream Kevin']");
    By descSel = MobileBy.xpath("//android.widget.EditText[@text = 'Description....']");
    By categorySel = MobileBy.AccessibilityId("Yoga");
    String[] categoryList = {"Yoga", "Cardio", "Boxing", "Stretching", "Trending", "Music"};
    By durationSel = MobileBy.xpath("//android.view.ViewGroup[4]/android.widget.EditText");
    By uploadThumbnailSel = MobileBy.xpath("//android.widget.TextView[@text = 'Upload thumbnail']");
    By tapOutside = MobileBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout");
    By error1Sel = MobileBy.xpath("//android.widget.TextView[@text = 'Please enter your title']");
    By error2Sel = MobileBy.xpath("//android.widget.TextView[@text = 'Please enter your description']");
    By error3Sel = MobileBy.xpath("//android.widget.TextView[@text = 'Required']");
    By albumTagSel = MobileBy.AccessibilityId("Album");
    By albumSel = MobileBy.xpath("//android.widget.TextView[@text = 'Máy ảnh']");
    By imageSel = MobileBy.xpath("//android.widget.FrameLayout[contains(@content-desc, 'Ảnh được chụp vào')]");
//    By scaleSel = MobileBy.xpath("//*[(@resource-id = 'com.metasolutions.paragon:id/text_view_scale') and (contains(@text, '%'))]");
    By scaleSel = MobileBy.xpath("//*[@resource-id = 'com.metasolutions.paragon:id/scale_scroll_wheel']");

    public GoliveForm(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
        this.wait = new WebDriverWait(appiumDriver, 10);
    }

    public void enter() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tapOutside));
        appiumDriver.findElement(tapOutside).click();
    }
    public void inputTitle(String title) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(titleSel));
        appiumDriver.findElement(titleSel).sendKeys(title);
        enter();
    }
    public void inputDesc(String desc) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(descSel));
        appiumDriver.findElement(descSel).sendKeys(desc);
        enter();
    }
    public void inputDuration(String duration) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(durationSel));
        appiumDriver.findElement(durationSel).sendKeys(duration);
        enter();
    }
    public void inputCategory(String category) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(categorySel));
        appiumDriver.findElement(categorySel).click();
        MobileElement categoryElement = appiumDriver.findElementByAccessibilityId(category);
        categoryElement.click();
    }
    public void uploadThumbnail() {
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
//        // Scale image
//        MobileElement percentCurrent = appiumDriver.findElement(scaleSel);
//        int percent = Integer.parseInt(percentCurrent.getText().replaceAll("[^\\d]", ""));
//        String scalePercent = Integer.toString((percent + 20)) + "%";
//        System.out.println(scalePercent);
//        appiumDriver.findElement(scaleSel).sendKeys(scalePercent);
        // Tap Done
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

    }
    public void clear() {
        appiumDriver.findElement(titleSel).clear();
        appiumDriver.findElement(descSel).clear();
        appiumDriver.findElement(durationSel).clear();
    }
    public boolean isCreateGoliveFail() {
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
}
