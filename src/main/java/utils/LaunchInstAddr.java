package utils;

import Action.ScrollDown50;
import Action.ScrollDown90Percent;
import Action.ScrollUp90;
import DB.Users_GetAllEmail;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class LaunchInstAddr {
    private AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private ScrollDown90Percent scrollDown;
    private ScrollDown50 scrollDown50;
    private ScrollUp90 scrollUp90;
    private Users_GetAllEmail usersGetAllEmail;
    By emailAddSel = MobileBy.xpath("//android.widget.Button[contains(@text, '@')]");
    By emailElementsSel = MobileBy.className("android.widget.ImageView"); // second class
    By reloadIconSel = MobileBy.AccessibilityId("Reload");

    public LaunchInstAddr(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
        this.wait = new WebDriverWait(appiumDriver, 15);
    }

    public void launchInstAddress() throws IOException {
        // Put BusCLient in background till call again
        appiumDriver.runAppInBackground(Duration.ofSeconds(3));
        // Open the App Email
        appiumDriver.activateApp("air.kukulive.mailnow");
    }

    // Get email
    public String getEmail() {
        // Tap chọn icon để mở danh sách email
        List<MobileElement> getEmailSel = appiumDriver.findElements(emailElementsSel);
        int EMAIL_ADD = 1;
        getEmailSel.get(EMAIL_ADD).click();
        getEmailSel.get(EMAIL_ADD).click();

        scrollDown50 = new ScrollDown50(appiumDriver);
        scrollDown50.scrollDownTo();

        // Find Email: get first email in emaillist
        List<MobileElement> emailList = appiumDriver.findElements(emailAddSel);
        // Check if any email in the list is not in the database
        for (MobileElement emailElement : emailList) {
            String email = emailElement.getText();
            System.out.println("Check email in DB...");
            if (Users_GetAllEmail.isEmailInDatabase(email)) {
                System.out.println(email + "is already existing!");
            } else {
                return email; // Return the first email that is not in the database
            }
        }

        // If all emails are in the database, create a new email
        scrollUp90 = new ScrollUp90(appiumDriver);
        scrollUp90.scrollUpTo();
        MobileElement createEmail = appiumDriver.findElementByXPath("//android.widget.Button[@text = 'Create an address automatically']");
        createEmail.click();
        try {
            MobileElement closePopup = appiumDriver.findElementByXPath("//android.widget.Button[@text = 'Close']");
//            wait.until(ExpectedConditions.visibilityOf(closePopup));
            if (closePopup.isDisplayed()) {
                closePopup.click();
            }
        } catch (Exception e) {
            MobileElement agreeCreateBtn = appiumDriver.findElementByXPath("//android.widget.Button[@text = 'Yes']");
            wait.until(ExpectedConditions.visibilityOf(agreeCreateBtn));
            agreeCreateBtn.click();
            MobileElement closePopup = appiumDriver.findElementByXPath("//android.widget.Button[@text = 'Close']");
            wait.until(ExpectedConditions.visibilityOf(closePopup));
            closePopup.click();
        }

        // Find the new email and return it
        scrollDown50.scrollDownTo();
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailAddSel));
        // Get updated email list after scroll
        emailList = appiumDriver.findElements(emailAddSel);
        if (!emailList.isEmpty()) {
            System.out.println(emailList.size());
            MobileElement lastEmailAddElement = emailList.get(emailList.size() - 1);
            return lastEmailAddElement.getText();
        } else {
            System.out.println("Email address list is empty!");
            return null;
        }

    }
    public void openBox() {
        By emailElementsSel = MobileBy.className("android.widget.ImageView"); // third class
        List<MobileElement> emailBoxSel = appiumDriver.findElements(emailElementsSel);
        int EMAIL_BOX = 2;
        emailBoxSel.get(EMAIL_BOX).click();
        try {
            Thread.sleep(5000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(reloadIconSel));
            emailBoxSel.get(EMAIL_BOX).click();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    // Get new OTP
    public String getNewOTP() {
        openBox();
        scrollUp90 = new ScrollUp90(appiumDriver);
        scrollUp90.scrollUpTo();
        // Open the new email

        try {
            Thread.sleep(3000);
            By openNewEmail = MobileBy.xpath("//android.widget.Button[contains(@text, 'Your verification code')]");
            List<MobileElement> emailList = appiumDriver.findElements(openNewEmail);
            if (!emailList.isEmpty()) {
                int NEW_EMAIL = 0;
                MobileElement newEmailElement = emailList.get(NEW_EMAIL);
                System.out.println("Click on new email!");
                Thread.sleep(3000);
                wait.until(ExpectedConditions.elementToBeClickable(newEmailElement)).click();
            } else {
                System.out.println("No new email found!");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Cannot get OTP!");
        }
        // Locate OTP
        // Trường hợp email không được mở vì app cùi, repeat open email again
        By newOTPSel = MobileBy.xpath("//android.widget.TextView[contains(@text, 'Your confirmation code is')]");
        try {
            MobileElement newOTP = appiumDriver.findElement(newOTPSel);
            if (!newOTP.isDisplayed()) {
                By openNewEmail = MobileBy.xpath("//android.widget.Button[contains(@text, 'Your verification code')]");
                List<MobileElement> emailList = appiumDriver.findElements(openNewEmail);

                int NEW_EMAIL = 0;
                MobileElement newEmailElement = emailList.get(NEW_EMAIL);
                System.out.println("Click on new email!");
                wait.until(ExpectedConditions.elementToBeClickable(newEmailElement)).click();
            } else {
                System.out.println("No new email found to open.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while trying to open the email.");
        }

        // Lấy giá trị số ngày từ thuộc tính textContent
        MobileElement newOTP = appiumDriver.findElement(newOTPSel);
        String text = newOTP.getText();
        System.out.println(text);
        // Trích xuất số ngày từ đoạn văn bản
        String newOtpLeft = text.replaceAll("[^\\d]", "");

        return newOtpLeft;
    }

    public void openOldEmail() {
        try {
            Thread.sleep(3000);
            By openOldEmail = MobileBy.xpath("//android.widget.Button[contains(@text, 'Your verification code')]");
            List<MobileElement> emailList = appiumDriver.findElements(openOldEmail);
            if (!emailList.isEmpty()) {
                int OLD_EMAIL = 1;
                MobileElement oldEmailElement = emailList.get(OLD_EMAIL);
                System.out.println("Click on old email!");Thread.sleep(3000);
                wait.until(ExpectedConditions.elementToBeClickable(oldEmailElement)).click();
            } else {
                System.out.println("No old email found!");
            }
        } catch (Exception e) {
            System.out.println("Cannot get old OTP!");
        }
    }
    // Get old OTP
    public String getOldOTP() {
        // Open email box
        openBox();

        // Open the old email
//        try {
//            Thread.sleep(3000);
//            By openOldEmail = MobileBy.xpath("//android.widget.Button[contains(@text, 'Your verification code')]");
//            List<MobileElement> emailList = appiumDriver.findElements(openOldEmail);
//            if (!emailList.isEmpty()) {
//                int OLD_EMAIL = 1;
//                MobileElement oldEmailElement = emailList.get(OLD_EMAIL);
//                System.out.println("Click on old email!");
//                Thread.sleep(3000);
//                wait.until(ExpectedConditions.elementToBeClickable(oldEmailElement)).click();
//            } else {
//                System.out.println("No old email found!");
//                return null;
//            }
//        } catch (Exception e) {
//            System.out.println("Cannot get old OTP!");
//        }

        // Locate OTP

        // Tap chọn icon để mở danh sách email
        List<MobileElement> getEmailSel = appiumDriver.findElements(emailElementsSel);
        int EMAIL_ADD = 1;
        getEmailSel.get(EMAIL_ADD).click();
        openBox();
        openOldEmail();

        MobileElement OTPSel = appiumDriver.findElementByXPath("//android.widget.TextView[contains(@text, 'Your confirmation code is')]");
        // Lấy giá trị số ngày từ thuộc tính textContent
        String text = OTPSel.getText();
        // Trích xuất số ngày từ đoạn văn bản
        String oldOTPLeft = text.replaceAll("[^\\d]", "");
        // Đổi giá trị từ chuỗi sang kiểu số nguyên
//        int OTP = Integer.parseInt(otpLeft);
        return oldOTPLeft;
    }
}
