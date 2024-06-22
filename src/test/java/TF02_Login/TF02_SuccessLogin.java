package TF02_Login;

import C02_Login.LoginForm;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.AccEx;
import utils.LaunchParagon;

public class TF02_SuccessLogin {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private LoginForm loginForm;
    @BeforeClass
    public void setup() {
        appiumDriver = LaunchParagon.getAppiumDriver();
        wait = new WebDriverWait(appiumDriver, 10);
        loginForm = new LoginForm(appiumDriver);

    }
    @Test
    public void TC001_InputValidCreds() {

        loginForm.inputPassword(AccEx.validPassword);
        loginForm.inputEmail(AccEx.validEmail1);
        loginForm.clickOnSignInBtn();
        if (loginForm.isLoginSuccess()) {
            System.out.println("[PASS] Successfully login!");
        } else {
            System.out.println("[FAIL] Login failed!");
        }
    }
}
