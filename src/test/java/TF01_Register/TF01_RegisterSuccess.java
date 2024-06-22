package TF01_Register;

import Action.ScrollDown50;
import C01_Register.RegisterForm;
import C02_Login.LoginForm;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.LaunchInstAddr;
import utils.LaunchParagon;

import java.io.IOException;

public class TF01_RegisterSuccess {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private RegisterForm registerForm;
    private LaunchInstAddr launchInstAddr;
    private ScrollDown50 scrollDown50;
    private LoginForm loginForm;
    String instAddrApp = "air.kukulive.mailnow";
    String paragonApp = "com.metasolutions.paragon";
    String username = "nhutest";
    String password = "@Test123";

    @BeforeClass
    public void setup() {
        appiumDriver = LaunchParagon.getAppiumDriver();
        wait = new WebDriverWait(appiumDriver, 10);
        launchInstAddr = new LaunchInstAddr(appiumDriver);
        registerForm = new RegisterForm(appiumDriver);
        registerForm.clickOnSignUpLink();
    }
    @Test
    public void TC001_RegisterWithValidCredsTest() throws IOException {
        // Get email
        launchInstAddr.launchInstAddress();
        String email = launchInstAddr.getEmail();
        System.out.println(email);
        // Relaunch Paragon
        appiumDriver.activateApp(paragonApp);
        // Input credentials and submit
        registerForm.inputUsername(username);
        registerForm.inputEmail(email);
        registerForm.inputPassword(password);
        registerForm.inputRepeatPassword(password);
        registerForm.clickOnSignUpBtn();
        // Relaunch InstAddr
        appiumDriver.activateApp(instAddrApp);
        String newOTP = launchInstAddr.getNewOTP();
        // Relaunch Paragon
        appiumDriver.activateApp(paragonApp);
        // Input OTP
        registerForm.inputNewOTP(newOTP);
        registerForm.clickOnSubmitOTPBtn();
        // Verify loginscreen display
        if (registerForm.isLoginScreenDisplayed()) {
            System.out.println("Login screen displayed!");
        } else {
            System.out.println("FAIL: Register failed!");
        }
        // and User can login with new acc
        loginForm = new LoginForm(appiumDriver);
        loginForm.inputEmail(email);
        loginForm.inputPassword(password);
        loginForm.clickOnSignInBtn();
        if (loginForm.isLoginSuccess()) {
            System.out.println("[PASS] - Successfully login!");
        } else {
            System.out.println("FAIL: Login failed!");
        }
    }
}
