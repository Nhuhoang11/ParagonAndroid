package TF08_Profile;

import Action.ScrollUp90;
import Action.SwipeBack;
import C02_Login.LoginForm;
import C08_Profile.EditProfileForm;
import DB.GetUsernameByEmail;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.AccEx;
import utils.LaunchParagon;

import java.sql.SQLException;
import java.util.List;

public class TF08_VerifyProfileScreen {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private LoginForm loginForm;
    private SwipeBack swipeBack;
    private EditProfileForm editProfileForm;
    private ScrollUp90 scrollUp90;
    By usernameSel = MobileBy.xpath("//android.view.ViewGroup[@content-desc]/android.widget.TextView");

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
        editProfileForm.accessProfile();
    }
    @Test
    // Verify that Profile Displays 7 element: username; Subscription; My channel; Settings; Terms of use; Privacy Policy; Logout
    public void TC001_VerifyProfileScreenDisplayCorrect() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameSel));
        List<MobileElement> elements = appiumDriver.findElements(usernameSel);
        if (elements.get(0).isDisplayed()) {
            String username = elements.get(0).getText();
            System.out.println("[PASS] Username is displaying: " + username);
        }

        String[] profileElements = {"Subscriptions", "My channel", "Settings", "Terms of use", "Privacy Policy", "Logout"};
        for (String profileElement: profileElements) {
            By elementsOfProfileScreen = MobileBy.AccessibilityId(profileElement);
            MobileElement element = appiumDriver.findElement(elementsOfProfileScreen);
            if (element.isDisplayed()) {
                System.out.println("[PASS] " + element.getAttribute("content-desc") + " is displaying");
            } else {
                System.out.println("FAIL: " + element.getAttribute("content-desc") + " is not found!");
            }
        }
    }
    @Test
    // Verify that username on app and username in DB match
    public void TC002_VerifyUsernameDisplayedMatchDB() throws SQLException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameSel));
        List<MobileElement> elements = appiumDriver.findElements(usernameSel);
            String usernameOnApp = elements.get(0).getText();
            System.out.println("Username is displaying: " + usernameOnApp);
        String usernameOnDB = GetUsernameByEmail.getUsernameOfChannelByEmail(AccEx.validEmail1);
        if (usernameOnDB.equals(usernameOnApp)) {
            System.out.println("[PASS] Username on app and Username on DB matched!");
        } else {
            System.out.println("FAIL: Username on app and Username on DB not match");
        }
    }
}
