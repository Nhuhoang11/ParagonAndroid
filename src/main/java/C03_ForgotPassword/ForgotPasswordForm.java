package C03_ForgotPassword;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;

public class ForgotPasswordForm {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    By forgotPasswordLinkSel = MobileBy.xpath("//android.widget.TextView[@text = 'Forgot password?']");
    By emailFieldSel = MobileBy.xpath("//android.widget.EditText[@text = 'example@gmail.com']");
    By submitEmailBtnSel = MobileBy.AccessibilityId("RESET PASSWORD");
    By firstToggleSel = MobileBy.xpath("//android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[1]/android.view.ViewGroup/android.widget.ImageView");
    By secondToggleSel = MobileBy.xpath("//android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[2]/android.view.ViewGroup/android.widget.ImageView");
    By otpAndPasswordSel = MobileBy.className("android.widget.EditText");
    By checkYourEmailTextSel = MobileBy.xpath("//android.widget.TextView[contains(@text, 'Check your email')]");
    By sendAgainSel = MobileBy.AccessibilityId("Send again");
    By submitResetPasswordSel = MobileBy.AccessibilityId("SUBMIT");
    By errorPopupSel = MobileBy.xpath("//android.widget.Button[@text = 'OK']");
    By backIconSel = MobileBy.className("android.widget.ImageView"); // 2nd element

    public ForgotPasswordForm(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
        this.wait = new WebDriverWait(appiumDriver, 10);
    }

    public void clickOnToggle() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstToggleSel));
        appiumDriver.findElement(firstToggleSel).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(secondToggleSel));
        appiumDriver.findElement(secondToggleSel).click();
    }
    public void enter() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkYourEmailTextSel));
        appiumDriver.findElement(checkYourEmailTextSel).click();
    }
    public void clickOnForgotPassword() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(forgotPasswordLinkSel));
        appiumDriver.findElement(forgotPasswordLinkSel).click();
    }
    public void inputEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailFieldSel));
        appiumDriver.findElement(emailFieldSel).sendKeys(email);
    }
    public void clickOnSubmitEmail() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(submitEmailBtnSel));
        appiumDriver.findElement(submitEmailBtnSel).click();
    }
    public void inputPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(otpAndPasswordSel));
        List<MobileElement> passwordFields = appiumDriver.findElements(otpAndPasswordSel);
        passwordFields.get(6).sendKeys(password);
        enter();
    }
    public void inputRepeatPassword(String repeatPassword) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(otpAndPasswordSel));
        List<MobileElement> passwordFields = appiumDriver.findElements(otpAndPasswordSel);
        passwordFields.get(7).sendKeys(repeatPassword);
        enter();
    }
    public void inputNewOTP(String newOTP) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(otpAndPasswordSel));
        List<MobileElement> otpBox = appiumDriver.findElements(otpAndPasswordSel);
        for (int i = 0; i < newOTP.length(); i++) {
            otpBox.get(i).sendKeys(Character.toString(newOTP.charAt(i)));
        }
        enter();
    }
    public void clickOnResetPassword() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(submitResetPasswordSel));
        appiumDriver.findElement(submitResetPasswordSel).click();
    }
    public void clear() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(otpAndPasswordSel));
        List<MobileElement> fields = appiumDriver.findElements(otpAndPasswordSel);
        for (MobileElement elements: fields) {
            elements.clear();
        }
    }
    public void clickOnResendOTP() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(sendAgainSel));
        appiumDriver.findElement(sendAgainSel).click();
    }
    public void closePopup() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(errorPopupSel));
            // Nếu phần tử popup được tìm thấy, đóng popup
            appiumDriver.findElement(errorPopupSel).click();
        } catch (TimeoutException e) {
            // Popup không hiển thị, tiếp tục thực hiện các thao tác tiếp theo
            System.out.println("Popup is not displayed, continuing execution.");
        } catch (NoSuchElementException e) {
            // Trường hợp phần tử không tìm thấy, có thể thêm xử lý ở đây nếu cần thiết
            System.out.println("Popup element not found, continuing execution.");
        } catch (Exception e) {
            // Bắt tất cả các ngoại lệ khác để tránh lỗi không mong muốn
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    public void backinputEmailScreen() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(backIconSel));
        List<MobileElement> backElement = appiumDriver.findElements(backIconSel);
        backElement.get(1).click();
    }
    public boolean isResetPasswordFail() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(submitResetPasswordSel));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        } catch (Exception e) {
            // Xử lý ngoại lệ khác, có thể log ra lỗi để debug
            e.printStackTrace();
            return false;
        }
    }
}
