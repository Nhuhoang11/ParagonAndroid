package TF03_ForgotPassword;

import C03_ForgotPassword.ForgotPasswordForm;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.AccEx;
import utils.LaunchGmail;
import utils.LaunchInstAddr;
import utils.LaunchParagon;

import java.util.List;

public class TF03_ForgotPasswordFailed {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private ForgotPasswordForm forgotPasswordForm;
    private LaunchGmail launchGmail;
    private String newOTP;
    String passwordLessThan8Char = "@Test12";
    String passwordWithoutUpper = "@test123";
    String passwordWithoutLower = "@TEST123";
    String passwordWithoutSymbol = "Test1234";
    String passwordWithoutNumber = "TestTest";
    @BeforeClass
    public void setup() {
        appiumDriver = LaunchParagon.getAppiumDriver();
        wait = new WebDriverWait(appiumDriver, 10);
        launchGmail = new LaunchGmail(appiumDriver);
        forgotPasswordForm = new ForgotPasswordForm(appiumDriver);
        forgotPasswordForm.clickOnForgotPassword();
        // Input email & submit
        forgotPasswordForm.inputEmail(AccEx.validEmail1);
        forgotPasswordForm.clickOnSubmitEmail();
        forgotPasswordForm.clickOnToggle();
        // Get OTP from Gmail
        launchGmail = new LaunchGmail(appiumDriver);
        launchGmail.openGmail();
        newOTP = launchGmail.getnewOTP();
        System.out.println(newOTP);
        // Relaunch Paragon
        appiumDriver.activateApp(AccEx.paragon);
    }

    // Left all fields empty -> click submit
    @Test
    public void TC001_ResetPasswordWithoutInputCreds() {
        forgotPasswordForm.clickOnResetPassword();
        if (forgotPasswordForm.isResetPasswordFail()) {
            System.out.println("[PASS] Cannot reset password without input OTP, Password and Repeat password");
        } else {
            System.out.println("FAIL: Users can successfully reset password even if left OTP, Password and Repeat password empty");
        }
    }
    // Để trống password & repeat password
    @Test
    public void TC002_ResetPasswordWithoutInputPasswordAndRepeatPassword() {
        forgotPasswordForm.inputNewOTP(newOTP);
        forgotPasswordForm.clickOnResetPassword();
        if (forgotPasswordForm.isResetPasswordFail()) {
            System.out.println("[PASS] Cannot reset password without input Password and Repeat password");
        } else {
            System.out.println("FAIL: Users can successfully reset password even if left Password and Repeat password empty");
        }
    }
    // Để trống otp
    @Test
    public void TC003_ResetPasswordWithoutInputOTP() {
        forgotPasswordForm.clear();
        forgotPasswordForm.inputPassword(AccEx.validPassword);
        forgotPasswordForm.inputRepeatPassword(AccEx.validPassword);
        forgotPasswordForm.clickOnResetPassword();
        // CLose popup
        forgotPasswordForm.closePopup();
        if (forgotPasswordForm.isResetPasswordFail()) {
            System.out.println("[PASS] Cannot reset password without input OTP");
        } else {
            System.out.println("FAIL: Users can successfully reset password even if left OTP empty");
        }
    }
    // Nhập sai otp
    @Test
    public void TC004_ResetPasswordWithIncorrectOTP() {
        forgotPasswordForm.clear();
        forgotPasswordForm.inputNewOTP("123456");
        forgotPasswordForm.inputPassword(AccEx.validPassword);
        forgotPasswordForm.inputRepeatPassword(AccEx.validPassword);
        forgotPasswordForm.clickOnResetPassword();
        // CLose popup
        forgotPasswordForm.closePopup();
        // Verify
        if (forgotPasswordForm.isResetPasswordFail()) {
            System.out.println("[PASS] Cannot reset password with incorrect OTP");
        } else {
            System.out.println("FAIL: Users can successfully reset password even if input incorrect OTP");
        }
    }
    // Nhập otp không hoàn chỉnh
    @Test
    public void TC005_ResetPasswordWithIncompleteOTP() {
        forgotPasswordForm.clear();
        forgotPasswordForm.inputNewOTP(newOTP);
        // clear 1/2 OTP
        By otpAndPasswordSel = MobileBy.className("android.widget.EditText");
        wait.until(ExpectedConditions.visibilityOfElementLocated(otpAndPasswordSel));
        List<MobileElement> fields = appiumDriver.findElements(otpAndPasswordSel);
        fields.get(5).clear();
        // Input password and repeat password
        forgotPasswordForm.inputPassword(AccEx.validPassword);
        forgotPasswordForm.inputRepeatPassword(AccEx.validPassword);
        forgotPasswordForm.clickOnResetPassword();
        // CLose popup
        forgotPasswordForm.closePopup();
        // Verify
        if (forgotPasswordForm.isResetPasswordFail()) {
            System.out.println("[PASS] Cannot reset password with incomplete OTP");
        } else {
            System.out.println("FAIL: Users can successfully reset password even if input incomplete OTP");
        }
    }
    // Nhập password và repeat password not match
    @Test
    public void TC006_ResetPasswordWithPasswordNotMatch() {
        forgotPasswordForm.clear();
        forgotPasswordForm.inputNewOTP(newOTP);
        forgotPasswordForm.inputPassword(AccEx.validPassword);
        forgotPasswordForm.inputRepeatPassword(AccEx.validPassword + "@");
        forgotPasswordForm.clickOnResetPassword();
        if (forgotPasswordForm.isResetPasswordFail()) {
            System.out.println("[PASS] Cannot reset password with password not match");
        } else {
            System.out.println("FAIL: Users can successfully reset password even if password not match");
        }
    }
    // Nhập invalid password & invalid repeat password: < 8 ký tự
    @Test
    public void TC007_ResetPasswordWithPasswordLessThan8Chars() {
        forgotPasswordForm.clear();
        forgotPasswordForm.inputNewOTP(newOTP);
        forgotPasswordForm.inputPassword(passwordLessThan8Char);
        forgotPasswordForm.inputRepeatPassword(passwordLessThan8Char);
        forgotPasswordForm.clickOnResetPassword();
        if (forgotPasswordForm.isResetPasswordFail()) {
            System.out.println("[PASS] Cannot reset password with password less than 8 characters");
        } else {
            System.out.println("FAIL: Users can successfully reset password even if password less than 8 characters");
        }
    }
    // Nhập invalid password & invalid repeat password: không có char viết hoa
    @Test
    public void TC008_ResetPasswordWithPasswordWithoutUpperCase() {
        forgotPasswordForm.clear();
        forgotPasswordForm.inputNewOTP(newOTP);
        forgotPasswordForm.inputPassword(passwordWithoutUpper);
        forgotPasswordForm.inputRepeatPassword(passwordWithoutUpper);
        forgotPasswordForm.clickOnResetPassword();
        // CLose popup
        forgotPasswordForm.closePopup();
        // Verify
        if (forgotPasswordForm.isResetPasswordFail()) {
            System.out.println("[PASS] Cannot reset password with password without upper case");
        } else {
            System.out.println("FAIL: Users can successfully reset password even if password without upper case");
        }
    }
    // Nhập invalid password & invalid repeat password: không có char viết thường
    @Test
    public void TC009_ResetPasswordWithPasswordWithoutLowerCase() {
        forgotPasswordForm.clear();
        forgotPasswordForm.inputNewOTP(newOTP);
        forgotPasswordForm.inputPassword(passwordWithoutLower);
        forgotPasswordForm.inputRepeatPassword(passwordWithoutLower);
        forgotPasswordForm.clickOnResetPassword();
        // CLose popup
        forgotPasswordForm.closePopup();
        // Verify
        if (forgotPasswordForm.isResetPasswordFail()) {
            System.out.println("[PASS] Cannot reset password with password without lower case");
        } else {
            System.out.println("FAIL: Users can successfully reset password even if password without lower case");
        }
    }
    // Nhập invalid password & invalid repeat password: không có chữ số
    @Test
    public void TC010_ResetPasswordWithPasswordWithoutNumber() {
        forgotPasswordForm.clear();
        forgotPasswordForm.inputNewOTP(newOTP);
        forgotPasswordForm.inputPassword(passwordWithoutNumber);
        forgotPasswordForm.inputRepeatPassword(passwordWithoutNumber);
        forgotPasswordForm.clickOnResetPassword();
        if (forgotPasswordForm.isResetPasswordFail()) {
            System.out.println("[PASS] Cannot reset password with password without lower case");
        } else {
            System.out.println("FAIL: Users can successfully reset password even if password without lower case");
        }
    }
    // Nhập invalid password & invalid repeat password: không có symbol
    @Test
    public void TC011_ResetPasswordWithPasswordWithoutSymbol() {
        forgotPasswordForm.clear();
        forgotPasswordForm.inputNewOTP(newOTP);
        forgotPasswordForm.inputPassword(passwordWithoutSymbol);
        forgotPasswordForm.inputRepeatPassword(passwordWithoutSymbol);
        forgotPasswordForm.clickOnResetPassword();
        if (forgotPasswordForm.isResetPasswordFail()) {
            System.out.println("[PASS] Cannot reset password with password without symbol");
        } else {
            System.out.println("FAIL: Users can successfully reset password even if password without symbol");
        }
    }
    // Không input email -> click submit button
    @Test
    public void TC012_ResetPasswordWithoutInputEmailAcc() {
        forgotPasswordForm.backinputEmailScreen();
        forgotPasswordForm.clickOnSubmitEmail();

        try {
            By emailFieldSel = MobileBy.xpath("//android.widget.EditText[@text = 'example@gmail.com']");
            wait.until(ExpectedConditions.visibilityOfElementLocated(emailFieldSel));
            System.out.println("[PASS] Cannot reset password without input email");
        } catch (NoSuchElementException e) {
            System.out.println("FAIL: Users can successfully reset password even if left email field empty");
        }
    }
}
