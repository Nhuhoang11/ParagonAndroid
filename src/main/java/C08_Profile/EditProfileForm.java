package C08_Profile;

import Action.SwipeBack;
import Action.SwipeXY;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.getLocationElement;

public class EditProfileForm {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private SwipeXY swipeXY;
    private SwipeBack swipeBack;
    By profileNavSel = MobileBy.AccessibilityId("Profile");
    By usernameSel = MobileBy.xpath("//android.view.ViewGroup[@content-desc]/android.widget.TextView"); // 1st element
    By usernameOnProfileSel = MobileBy.xpath("//android.view.ViewGroup[3]/android.widget.TextView");
    By editProfileBtnSel = MobileBy.AccessibilityId("Edit profile");
    By changeAvatarSel = MobileBy.xpath("//*[contains(@content-desc, 'Profile Picture')]");
    By albumTagSel = MobileBy.AccessibilityId("Album");
    By albumSel = MobileBy.xpath("//android.widget.TextView[@text = 'Máy ảnh']");
    By imageSel = MobileBy.xpath("//android.widget.FrameLayout[contains(@content-desc, 'Ảnh được chụp vào')]");
    By scaleSel = MobileBy.xpath("//*[@resource-id = 'com.metasolutions.paragon:id/scale_scroll_wheel']");

    By changeBannerSel = MobileBy.xpath("//*[contains(@content-desc, 'Profile Banner')]");
    By changeBioSel = MobileBy.xpath("//*[contains(@content-desc, 'Bio')]");
    By addBioFieldSel = MobileBy.className("android.widget.EditText");
    By changeUsernameSel = MobileBy.xpath("//*[contains(@content-desc, 'Username')]");
    By changeUsernameBtnSel = MobileBy.AccessibilityId("Change Username");
    By changeUsernameTextFieldSel = MobileBy.className("android.widget.EditText");
    By saveChangeUsernameBtnSel = MobileBy.AccessibilityId("Save");
    By cancelChangeUsernameBtnSel = MobileBy.AccessibilityId("Cancel");
    By addSNSLinkSel = MobileBy.xpath("//*[contains(@content-desc, 'Manage Social Link')]");
    By addSNSFieldSel = MobileBy.className("android.widget.EditText");
    By addBtnSel = MobileBy.AccessibilityId("Add");
    By doneBtnSel = MobileBy.AccessibilityId("Done");
    By cancelBtnSel = MobileBy.AccessibilityId("Cancel");

    public EditProfileForm(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
        this.wait = new WebDriverWait(appiumDriver, 10);
    }

    public EditProfileForm accessProfile() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(profileNavSel));
        appiumDriver.findElement(profileNavSel).click();
        return this;
    }
    public EditProfileForm accessEditProfile() {
        accessProfile();
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameSel));
        appiumDriver.findElement(usernameSel).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editProfileBtnSel));
        appiumDriver.findElement(editProfileBtnSel).click();
        return this;
    }
    public void touchActionWithElement(MobileElement element) {
        // Thực hiện thao tác tap lên element tại tọa độ đã lấy (Hiện tại không thể tap trực tiếp do element đang bị đè, nên tạm thời tương tác bằng cách get XY của element)
        utils.getLocationElement.ElementUtils elementUtils = new getLocationElement.ElementUtils(element);
        int elementX = elementUtils.getElementXCoordinate();
        int elementY = elementUtils.getElementYCoordinate();
        System.out.println(elementX);
        System.out.println(elementY);
        TouchAction<?> touchAction = new TouchAction<>(appiumDriver);
        touchAction.tap(PointOption.point(elementX + 50, elementY + 50)).perform();
    }
    public EditProfileForm changeAvatar () {
        wait.until(ExpectedConditions.visibilityOfElementLocated(changeAvatarSel));
        appiumDriver.findElement(changeAvatarSel).click();
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
    public EditProfileForm changeBanner () {
        wait.until(ExpectedConditions.visibilityOfElementLocated(changeBannerSel));
        appiumDriver.findElement(changeBannerSel).click();
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
    public EditProfileForm editBio (String bioText) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(changeBioSel));
        appiumDriver.findElement(changeBioSel).click();
        appiumDriver.findElement(addBioFieldSel).sendKeys(bioText);
        // Thực hiện thao tác tap lên element tại tọa độ đã lấy (Hiện tại không thể tap trực tiếp do element đang bị đè, nên tạm thời tương tác bằng cách get XY của element)
        MobileElement doneBtn = appiumDriver.findElementByAccessibilityId("Done");
        touchActionWithElement(doneBtn);
        System.out.println("Tap on Done button");
        return this;
    }
    public EditProfileForm changeUsername (String newUsername) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(changeUsernameSel));
        appiumDriver.findElement(changeUsernameSel).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(changeUsernameBtnSel));
        appiumDriver.findElement(changeUsernameBtnSel).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(changeUsernameTextFieldSel));
        appiumDriver.findElement(changeUsernameTextFieldSel).clear();
        appiumDriver.findElement(changeUsernameTextFieldSel).sendKeys(newUsername);
        MobileElement saveBtn = appiumDriver.findElement(saveChangeUsernameBtnSel);
        touchActionWithElement(saveBtn);
        return this;
    }
    public EditProfileForm addSNS (String SNSLink) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(addSNSLinkSel));
        appiumDriver.findElement(addSNSLinkSel).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(addSNSFieldSel));
        appiumDriver.findElement(addSNSFieldSel).clear();
        appiumDriver.findElement(changeUsernameTextFieldSel).sendKeys(SNSLink);
        MobileElement addBtn = appiumDriver.findElement(addBtnSel);
        touchActionWithElement(addBtn);
        return this;
    }
    public EditProfileForm cancelEditProfile() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cancelBtnSel));
        appiumDriver.findElement(cancelBtnSel).click();
        return this;
    }

    // Verify the bio-inputted display is correct.
    public EditProfileForm verifyBioInputtedDisplayCorrect(String inputtedBioText) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(changeBioSel));
        appiumDriver.findElement(changeBioSel).click();
        String currentTextInBioField = appiumDriver.findElement(addBioFieldSel).getText();
        if (currentTextInBioField.equals(inputtedBioText)) {
            System.out.println("[PASS] Nội dung bio đã input đã lưu thành công!");
        } else {
            System.out.println("FAIL: Nội dung bio đã input hiển thị không đúng");
        }
        swipeBack = new SwipeBack(appiumDriver);
        swipeBack.swipeBack();
        return this;
    }
    // Verify the Username-inputted display is correct.
    public EditProfileForm verifyUsernameInputtedDisplayCorrect(String inputtedUsernameText) {
        System.out.println("[1] Inputted username: " + inputtedUsernameText);
        // Get username in [EditProfile] screen
        By newUsernameSel = MobileBy.xpath("//android.view.ViewGroup[contains(@content-desc, 'Username')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(newUsernameSel));
        String[] text =  appiumDriver.findElement(newUsernameSel).getAttribute("content-desc").split(",");
        String currentUsernameOnEditProfile = text[1].trim();
        System.out.println("[2] Current username (on Edit profile screen): " + currentUsernameOnEditProfile);

        // Back về Profile screen để check username hiển thị hiện tại sau khi input username mới
        swipeBack = new SwipeBack(appiumDriver);
        swipeBack.swipeBack();
        String usernameOnProfileScreen = appiumDriver.findElement(usernameOnProfileSel).getText().trim();
        System.out.println("[3] Username (on Profile screen): " + usernameOnProfileScreen);
        //
        if (currentUsernameOnEditProfile.equals(inputtedUsernameText) && inputtedUsernameText.equals(usernameOnProfileScreen)) {
            System.out.println("[PASS] Username input đã lưu thành công!");
        } else {
            System.out.println("FAIL: Username đã input hiển thị không đúng");
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(editProfileBtnSel));
        appiumDriver.findElement(editProfileBtnSel).click();

        return this;
    }

}
