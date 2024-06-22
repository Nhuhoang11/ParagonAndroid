package TF01_Register;

import C01_Register.RegisterForm;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.LaunchParagon;

public class TF01_RegisterWithInvalidCreds {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private RegisterForm registerForm;
    String username = "nhutest";
    String email = "testFail@gmail.com";
    String password = "@Test123";
    String passwordLessThan8Char = "@Test12";
    String passwordWithoutUpper = "@test123";
    String passwordWithoutLower = "@TEST123";
    String passwordWithoutSymbol = "Test1234";
    String passwordWithoutNumber = "TestTest";
    @BeforeClass
    public void setup() {
        appiumDriver = LaunchParagon.getAppiumDriver();
        wait = new WebDriverWait(appiumDriver, 10);
        registerForm = new RegisterForm(appiumDriver);
        registerForm.clickOnSignUpLink();
    }
    @Test
    public void TC001_RegisterWithoutCredsTest() {
        registerForm.clickOnSignUpBtn();
        // Verify
        if (registerForm.isRegisterFailed()) {
            System.out.println("[PASS] Cannot register without input credentials");
            System.out.println("________________________________________________");
        } else {
            System.out.println("FAIL: Users can successfully register without input credentials");
            System.out.println("________________________________________________");
        }
    }
    @Test
    public void TC002_RegisterWithoutInputUsername() {
        registerForm.inputEmail(email);
        registerForm.inputPassword(password);
        registerForm.inputRepeatPassword(password);
        registerForm.clickOnSignUpBtn();
        // Verify
        if (registerForm.isRegisterFailed()) {
            System.out.println("[PASS] Cannot register without input username");
            System.out.println("________________________________________________");
        } else {
            System.out.println("FAIL: Users can successfully register even if left username empty");
            System.out.println("________________________________________________");
        }
    }
    @Test
    public void TC003_RegisterWithoutInputEmail() {
        registerForm.clear();
        registerForm.inputUsername(username);
        registerForm.inputPassword(password);
        registerForm.inputRepeatPassword(password);
        registerForm.clickOnSignUpBtn();
        // Verify
        if (registerForm.isRegisterFailed()) {
            System.out.println("[PASS] Cannot register without input email");
            System.out.println("________________________________________________");
        } else {
            System.out.println("FAIL: Users can successfully register even if left email empty");
            System.out.println("________________________________________________");
        }
    }
    @Test
    public void TC004_RegisterWithoutInputPassword() {
        registerForm.clear();
        registerForm.inputUsername(username);
        registerForm.inputEmail(email);
        registerForm.inputRepeatPassword(password);
        registerForm.clickOnSignUpBtn();
        // Verify
        if (registerForm.isRegisterFailed()) {
            System.out.println("[PASS] Cannot register without input password");
            System.out.println("________________________________________________");
        } else {
            System.out.println("FAIL: Users can successfully register even if left password empty");
            System.out.println("________________________________________________");
        }
    }
    @Test
    public void TC005_RegisterWithoutInputRepeatPassword() {
        registerForm.clear();
        registerForm.inputUsername(username);
        registerForm.inputEmail(email);
        registerForm.inputPassword(password);
        registerForm.clickOnSignUpBtn();
        // Verify
        if (registerForm.isRegisterFailed()) {
            System.out.println("[PASS] Cannot register without input Repeat password");
            System.out.println("________________________________________________");
        } else {
            System.out.println("FAIL: Users can successfully register even if left Repeat password empty");
            System.out.println("________________________________________________");
        }
    }
    @Test
    public void TC006_RegisterWithPasswordNotMatch() {
        registerForm.clear();
        registerForm.inputUsername(username);
        registerForm.inputEmail(email);
        registerForm.inputPassword(password);
        registerForm.inputRepeatPassword(password + "@");
        registerForm.clickOnSignUpBtn();
        // Verify
        if (registerForm.isRegisterFailed()) {
            System.out.println("[PASS] Cannot register if Password and Repeat password not match");
            System.out.println("________________________________________________");
        } else {
            System.out.println("FAIL: Users can successfully register even if Password and Repeat password not match");
            System.out.println("________________________________________________");
        }
    }
    @Test
    public void TC007_RegisterWithPasswordLessthan8Char() {
        registerForm.clear();
        registerForm.inputUsername(username);
        registerForm.inputEmail(email);
        registerForm.inputPassword(passwordLessThan8Char);
        registerForm.inputRepeatPassword(passwordLessThan8Char);
        registerForm.clickOnSignUpBtn();
        // Verify
        if (registerForm.isRegisterFailed()) {
            System.out.println("[PASS] Cannot register if Password and Repeat password less than 8 characters");
            System.out.println("________________________________________________");
        } else {
            System.out.println("FAIL: Users can successfully register even if Password and Repeat password less than 8 characters");
            System.out.println("________________________________________________");
        }
    }
    @Test
    public void TC008_RegisterWithPasswordWithoutUpperCase() {
        registerForm.clear();
        registerForm.inputUsername(username);
        registerForm.inputEmail(email);
        registerForm.inputPassword(passwordWithoutUpper);
        registerForm.inputRepeatPassword(passwordWithoutUpper);
        registerForm.clickOnSignUpBtn();
        // Verify
        if (registerForm.isRegisterFailed()) {
            System.out.println("[PASS] Cannot register if Password and Repeat password without upper case");
            System.out.println("________________________________________________");
        } else {
            System.out.println("FAIL: Users can successfully register even if Password and Repeat password without upper case");
            System.out.println("________________________________________________");
        }
    }
    @Test
    public void TC009_RegisterWithPasswordWithoutLowerCase() {
        registerForm.clear();
        registerForm.inputUsername(username);
        registerForm.inputEmail(email);
        registerForm.inputPassword(passwordWithoutLower);
        registerForm.inputRepeatPassword(passwordWithoutLower);
        registerForm.clickOnSignUpBtn();
        // Verify
        if (registerForm.isRegisterFailed()) {
            System.out.println("[PASS] Cannot register if Password and Repeat password without lower case");
            System.out.println("________________________________________________");
        } else {
            System.out.println("FAIL: Users can successfully register even if Password and Repeat password without lower case");
            System.out.println("________________________________________________");
        }
    }
    @Test
    public void TC010_RegisterWithPasswordWithoutSymbol() {
        registerForm.clear();
        registerForm.inputUsername(username);
        registerForm.inputEmail(email);
        registerForm.inputPassword(passwordWithoutSymbol);
        registerForm.inputRepeatPassword(passwordWithoutSymbol);
        registerForm.clickOnSignUpBtn();
        // Verify
        if (registerForm.isRegisterFailed()) {
            System.out.println("[PASS] Cannot register if Password and Repeat password without symbol");
            System.out.println("________________________________________________");
        } else {
            System.out.println("FAIL: Users can successfully register even if Password and Repeat password without symbol");
            System.out.println("________________________________________________");
        }
    }
    @Test
    public void TC011_RegisterWithPasswordWithoutNumber() {
        registerForm.clear();
        registerForm.inputUsername(username);
        registerForm.inputEmail(email);
        registerForm.inputPassword(passwordWithoutNumber);
        registerForm.inputRepeatPassword(passwordWithoutNumber);
        registerForm.clickOnSignUpBtn();
        // Verify
        if (registerForm.isRegisterFailed()) {
            System.out.println("[PASS] Cannot register if Password and Repeat password without number");
            System.out.println("________________________________________________");
        } else {
            System.out.println("FAIL: Users can successfully register even if Password and Repeat password without number");
            System.out.println("________________________________________________");
        }
    }
}
