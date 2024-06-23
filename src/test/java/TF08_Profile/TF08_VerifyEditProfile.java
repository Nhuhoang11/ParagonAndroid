package TF08_Profile;

import Action.ScrollUp90;
import Action.SwipeBack;
import C02_Login.LoginForm;
import C08_Profile.EditProfileForm;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.AccEx;
import utils.LaunchParagon;

public class TF08_VerifyEditProfile {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private LoginForm loginForm;
    private SwipeBack swipeBack;
    private EditProfileForm editProfileForm;
    private ScrollUp90 scrollUp90;

    @BeforeClass
    public void setup() {
        appiumDriver = LaunchParagon.getAppiumDriver();
        wait = new WebDriverWait(appiumDriver, 10);
        swipeBack = new SwipeBack(appiumDriver);
        scrollUp90 = new ScrollUp90(appiumDriver);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        loginForm = new LoginForm(appiumDriver);
        loginForm.login(AccEx.validEmail1, AccEx.validPassword);
        editProfileForm = new EditProfileForm(appiumDriver);
        editProfileForm.accessEditProfile();
    }
//    @Test
//    public void TC001_EditProfile() {
//        editProfileForm
//                .changeAvatar()
//                .changeBanner()
//                .editBio()
//                .changeUsername()
//                .addSNS()
//    }
    @Test
    public void TC() {
        editProfileForm.verifyUsernameInputtedDisplayCorrect("Test.android 001");
    }
}
