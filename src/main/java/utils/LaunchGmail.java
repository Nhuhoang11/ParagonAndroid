package utils;

import Action.ScrollDown90Percent;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LaunchGmail {
    private AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private ScrollDown90Percent scrollDown90Percent;

    public LaunchGmail(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    public void openGmail() {
        appiumDriver.runAppInBackground(Duration.ofSeconds(3));
        // Open the Gmail
        appiumDriver.activateApp("com.google.android.gm");
    }

    // Get new OTP
    public String getnewOTP() {
        // Find email and open email received
        try {
            MobileElement newEmail = appiumDriver.findElementByXPath("//android.widget.TextView[@text = 'Your verification code']");
//            wait.until(ExpectedConditions.visibilityOf(newEmail));
            if (newEmail.isDisplayed()) {
                // Open email
                System.out.println("Click on new email!");
                Thread.sleep(3000);
                newEmail.click();
            } else {
                System.out.println("No new email found!");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Users not received email!");
        }

        // Có thể user nhận được nhiều email từ cùng một emailAdd trong cùng 1 ngày, nên email mới nhất là email ở cuối danh sách
        // Get tất cả email trong danh sách
        By emailListSel = MobileBy.xpath("//android.widget.TextView[contains(@text, 'no-reply@')]");
        List<MobileElement> emailList = appiumDriver.findElements(emailListSel);
        if (!emailList.isEmpty()) {
            MobileElement newEmailElment = emailList.get(emailList.size() - 1);
            System.out.println(newEmailElment.getText());
        } else {
            System.out.println("Email is empty!");
            return null;
        }
        scrollDown90Percent = new ScrollDown90Percent(appiumDriver);
        scrollDown90Percent.scrollDownTo();
        // Get OTP from email
        // Locate OTP
        MobileElement newOTPSel = appiumDriver.findElementByXPath("//android.widget.TextView[contains(@text, 'Your password reset code is')]");
        // Lấy giá trị số ngày từ thuộc tính textContent
        String text = newOTPSel.getText();
        System.out.println(text);
        // Trích xuất số ngày từ đoạn văn bản
        String newOtpLeft = text.replaceAll("[^\\d]", "");
        return newOtpLeft;
    }

    // Get old OTP
    public String getOldOTP() {
        // Find email and open email received
        try {
            MobileElement newEmail = appiumDriver.findElementByXPath("//android.widget.TextView[@text = 'Your verification code']");
//            wait.until(ExpectedConditions.visibilityOf(newEmail));
            if (newEmail.isDisplayed()) {
                // Open email
                System.out.println("Click on email!");
                Thread.sleep(3000);
                newEmail.click();
            } else {
                System.out.println("No email found!");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Users not received email!");
        }

        // Có thể user nhận được nhiều email từ cùng một emailAdd trong cùng 1 ngày, nên email mới nhất là email ở cuối danh sách
        // Get tất cả email trong danh sách
        // Expand email received (optional)

        By emailListSel = MobileBy.xpath("//android.widget.TextView[contains(@text, 'no-reply@')]");
        List<MobileElement> emailList = appiumDriver.findElements(emailListSel);
        int OLD_EMAIL = 0;
        if (!emailList.isEmpty()) {
            emailList.get(OLD_EMAIL).click();
            try {
                System.out.println("4s");
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // Get value of email opened
            By oldOTPSel = MobileBy.xpath("//android.widget.TextView[contains(@text, 'Your password reset code is')]");
            List<MobileElement> allEmail = appiumDriver.findElements(oldOTPSel);
            int CONTENT_OLD_EMAIL = 1;
            String text = allEmail.get(CONTENT_OLD_EMAIL).getText();
//            By oldOTPSel = MobileBy.xpath("//android.widget.TextView[contains(@text, 'Your password reset code is')]");
//            List<MobileElement> oldNewEmail = appiumDriver.findElements(oldOTPSel);
//            System.out.println(oldNewEmail.size());
//            MobileElement contentOfOldEmail = oldNewEmail.get(oldNewEmail.size() - 1);
//            System.out.println(contentOfOldEmail.getText());

            // Get OTP from email
            // Locate OTP
            // Lấy giá trị số ngày từ thuộc tính textContent
//            String text = contentOfOldEmail.getText();
            System.out.println(text);
            // Trích xuất số ngày từ đoạn văn bản
            String oldOtpLeft = text.replaceAll("[^\\d]", "");
            return oldOtpLeft;
        } else {
            System.out.println("Email is empty!");
            return null;
        }


    }
}
