package TF05_Discover.TOPCATEGORIES;

import Action.ScrollDown50;
import C02_Login.LoginForm;
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

import java.util.List;

public class TopCategories {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private LoginForm loginForm;
    private ScrollDown50 scrollDown50;
    By discoverSel = MobileBy.AccessibilityId("Discover");
    By categoriesSel = MobileBy.xpath("//android.view.ViewGroup[@content-desc]/android.widget.TextView"); // 3rd -> 6th; View all -> 2nd
//    By categoriesSel = MobileBy.xpath("//android.view.ViewGroup[@content-desc]"); // 8th - 11th
    By backBtnSel =MobileBy.xpath("//android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.ImageView");
    By liveTabSel = MobileBy.AccessibilityId("Lives");
    By videoTabSel = MobileBy.AccessibilityId("Videos");
    By allCategoriesSel = MobileBy.xpath("//android.view.ViewGroup[@content-desc]/android.widget.TextView");
    @BeforeClass
    public void setup() {
        appiumDriver = LaunchParagon.getAppiumDriver();
        wait = new WebDriverWait(appiumDriver, 10);
        loginForm = new LoginForm(appiumDriver);
        loginForm.inputEmail(AccEx.validEmail1);
        loginForm.inputPassword(AccEx.validPassword);
        loginForm.clickOnSignInBtn();
        wait.until(ExpectedConditions.visibilityOfElementLocated(discoverSel));
        appiumDriver.findElement(discoverSel).click();
    }

    @Test
    public void TC001_clickOnHorizontalList() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(categoriesSel));
        List<MobileElement> categoriesElements = appiumDriver.findElements(categoriesSel);
        for (int i = 1; i <= 4; i++) { //for (int i = 1; i <= 4; i++) {
            String category = categoriesElements.get(i).getText();
            System.out.println(category);
            categoriesElements.get(i).click();
            // Verify
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(liveTabSel));
                wait.until(ExpectedConditions.visibilityOfElementLocated(videoTabSel));
                System.out.println(category + " is clickable!");
            } catch (Exception e) {
                e.printStackTrace();
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(backBtnSel));
            appiumDriver.findElement(backBtnSel).click();
            categoriesElements = appiumDriver.findElements(categoriesSel);
        }
    }
    @Test
    public void TC002_clickOnAllCategories() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(categoriesSel));
        List<MobileElement> categoriesElements = appiumDriver.findElements(categoriesSel);
        categoriesElements.get(0).click();

        scrollDown50 = new ScrollDown50(appiumDriver);
        scrollDown50.scrollDownTo();
        wait.until(ExpectedConditions.visibilityOfElementLocated(allCategoriesSel));
        List<MobileElement> allCategoriesElements = appiumDriver.findElements(allCategoriesSel);
        int length = allCategoriesElements.size();
        for (int i = 0; i < length; i++) {
            String category = allCategoriesElements.get(i).getText();
            System.out.println(category);
            allCategoriesElements.get(i).click();
            // Verify
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(liveTabSel));
                wait.until(ExpectedConditions.visibilityOfElementLocated(videoTabSel));
                System.out.println(category + " is clickable!");
            } catch (Exception e) {
                e.printStackTrace();
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(backBtnSel));
            appiumDriver.findElement(backBtnSel).click();
            allCategoriesElements = appiumDriver.findElements(categoriesSel);
        }
    }
}
