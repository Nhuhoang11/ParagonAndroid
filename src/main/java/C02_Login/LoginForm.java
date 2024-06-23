package C02_Login;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class LoginForm {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    By loginFormSel = MobileBy.className("android.widget.EditText");
    By toggleSel = MobileBy.xpath("//android.view.ViewGroup/android.view.ViewGroup[3]/android.view.ViewGroup/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.ImageView");

    By tapOutsideSel = MobileBy.xpath("//android.widget.TextView[@text = 'Sign in']");
    By signInSel = MobileBy.AccessibilityId("SIGN IN");
    By homeSel = MobileBy.AccessibilityId("Home");

    public LoginForm(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
        this.wait = new WebDriverWait(appiumDriver, 10);
    }
    public void tapOutside() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tapOutsideSel));
        appiumDriver.findElement(tapOutsideSel).click();
    }
    // Input email
    public void inputEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginFormSel));
        List<MobileElement> loginForm = appiumDriver.findElements(loginFormSel);
        loginForm.get(0).sendKeys(email);
        tapOutside();
        System.out.println("Input email");
    }
    // Click toggle
    public void clickOnToggle() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(toggleSel));
        appiumDriver.findElement(toggleSel).click();
    }
    // Input password
    public void inputPassword(String password) {
        clickOnToggle();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginFormSel));
        List<MobileElement> loginForm = appiumDriver.findElements(loginFormSel);
        loginForm.get(1).sendKeys(password);
        tapOutside();
        System.out.println("Input password");
    }
    // Click SIGN IN
    public void clickOnSignInBtn() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(signInSel));
        appiumDriver.findElement(signInSel).click();
    }
    // Clear
    public void clear() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginFormSel));
        List<MobileElement> loginForm = appiumDriver.findElements(loginFormSel);
        for (MobileElement elements: loginForm) {
            elements.clear();
        }
    }
    // Verify Login success
    public boolean isLoginSuccess() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(homeSel));
            MobileElement homeIcon = appiumDriver.findElement(homeSel);
            return homeIcon.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public void login (String email, String password) {
        inputEmail(email);
        inputPassword(password);
        clickOnSignInBtn();
    }
}
