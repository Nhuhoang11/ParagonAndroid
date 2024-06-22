package C01_Register;

import C02_Login.LoginForm;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class RegisterForm {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private LoginForm loginForm;
    By signInSel = MobileBy.AccessibilityId("SIGN IN");
    By signUpLinkSel = MobileBy.AccessibilityId("Sign Up");
    By signUpFormSel = MobileBy.className("android.widget.EditText"); // 4 elements
    By signUpBtnSel = MobileBy.AccessibilityId("SIGN UP");
    By theFirstToggleSel = MobileBy.xpath("//android.view.ViewGroup[3]/android.view.ViewGroup/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.ImageView");
    By theSecondToggleSel = MobileBy.xpath("//android.view.ViewGroup[1]/android.view.ViewGroup[4]/android.view.ViewGroup/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.ImageView");
    By outsideSel = MobileBy.xpath("//android.widget.TextView[@text = 'Sign Up']");
    By OTPSel = MobileBy.className("android.widget.EditText");
    By submitOTPBtnSel = MobileBy.AccessibilityId("SUBMIT");
    By sendAgainSel = MobileBy.AccessibilityId("Send again");
    By homeSel = MobileBy.AccessibilityId("Home");
    By OKBtnSel = MobileBy.xpath("//android.widget.Button[@text = 'OK']");

    public RegisterForm(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
        this.wait = new WebDriverWait(appiumDriver, 10);
    }

    // Click outside
    public void tapOutSide() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(outsideSel));
        appiumDriver.findElement(outsideSel).click();
    }
    // Click on "Don't have an account? Sign up
    public void clickOnSignUpLink() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(signUpLinkSel));
        appiumDriver.findElement(signUpLinkSel).click();

        System.out.println("Click sign Up link");
    }
    // Click on Toggle
    public void clickOnToggle() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(theFirstToggleSel));
        appiumDriver.findElement(theFirstToggleSel).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(theSecondToggleSel));
        appiumDriver.findElement(theSecondToggleSel).click();

        System.out.println("Turn on toggle");
    }
    // Input Username
    public void inputUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(signUpFormSel));
        List<MobileElement> signUpFormElements = appiumDriver.findElements(signUpFormSel);
        signUpFormElements.get(0).sendKeys(username);
        tapOutSide();
        System.out.println("Input Username");
    }
    // Input Email
    public void inputEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(signUpFormSel));
        List<MobileElement> signUpFormElements = appiumDriver.findElements(signUpFormSel);
        signUpFormElements.get(1).sendKeys(email);
        tapOutSide();
        System.out.println("Input Email");
    }
    // Input Password
    public void inputPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(signUpFormSel));
        List<MobileElement> signUpFormElements = appiumDriver.findElements(signUpFormSel);
        signUpFormElements.get(2).sendKeys(password);
        tapOutSide();
        System.out.println("Input Password");
    }
    // Input Repeat password
    public void inputRepeatPassword(String repeatPassword) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(signUpFormSel));
        List<MobileElement> signUpFormElements = appiumDriver.findElements(signUpFormSel);
        signUpFormElements.get(3).sendKeys(repeatPassword);
        tapOutSide();
        System.out.println("Input Repeat password");
    }
    // Click on SIGN UP
    public void clickOnSignUpBtn() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(signUpBtnSel));
        appiumDriver.findElement(signUpBtnSel).click();
        try {
            Thread.sleep(3000);
            System.out.println("Click sign up button");
        } catch (Exception e) {
            System.out.println("Cannot click on sign up button");
        }
    }
    // Input OTP
    public void inputNewOTP(String newOTP) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(OTPSel));
        List<MobileElement> otpBox = appiumDriver.findElements(OTPSel);
        for (int i = 0; i < newOTP.length(); i++) {
            otpBox.get(i).sendKeys(Character.toString(newOTP.charAt(i)));
        }
        // Tap outside
        appiumDriver.findElement(submitOTPBtnSel).click();
    }
    public void clickOnSubmitOTPBtn() {
        MobileElement submitBtn = appiumDriver.findElement(submitOTPBtnSel);
        wait.until(ExpectedConditions.visibilityOf(submitBtn));
        submitBtn.click();
    }
    public void clickOnResendOTP() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(sendAgainSel));
        appiumDriver.findElement(sendAgainSel).click();
    }
    // Clear signup Form
    public void clear() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(signUpFormSel));
        List<MobileElement> signUpFormElements = appiumDriver.findElements(signUpFormSel);
        for (MobileElement elements: signUpFormElements) {
            elements.clear();
        }
        System.out.println("clear...");
    }
    // Clear OTP Box
    public void clearOTP() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(OTPSel));
        List<MobileElement> otpBox = appiumDriver.findElements(OTPSel);
        for (MobileElement otp: otpBox) {
            otp.clear();
        }
    }
    // Close popup when register fail
    public void closePopup() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(OKBtnSel));
        appiumDriver.findElement(OKBtnSel).click();
        System.out.println("Close pop up");
    }
    // Verify register failed
    public boolean isRegisterFailed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(signUpBtnSel));
            MobileElement signUpBtn = appiumDriver.findElement(signUpBtnSel);
            return signUpBtn.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        } catch (Exception e) {
            // Xử lý ngoại lệ khác, có thể log ra lỗi để debug
            e.printStackTrace();
            return false;
        }
    }
    // Verify successfully register
    public boolean isLoginScreenDisplayed() {
        try {
            // Nếu register thành công, app sẽ tự động login, home icon sẽ hiển thị
            wait.until(ExpectedConditions.visibilityOfElementLocated(signInSel));
            MobileElement loginScreen = appiumDriver.findElement(signInSel);
            return loginScreen.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
