package C05_Discover;

import Action.SwipeBack;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;

public class VerifyFollowingList {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;
    private SwipeBack swipeBack;
    By discoverSel = MobileBy.AccessibilityId("Discover");
    By nameAcc = MobileBy.xpath("//android.view.ViewGroup[3]/android.widget.TextView[1]");
    By followBtnSel = MobileBy.xpath("//android.widget.TextView[@text = 'Follow']");
    By unFollowBtnSel = MobileBy.xpath("//android.widget.TextView[@text = 'Unfollow']");
    By buttonSel = MobileBy.xpath("//android.view.ViewGroup[@content-desc]/android.widget.TextView"); // 2nd element
    By profileNavSel = MobileBy.AccessibilityId("Profile");
    By avatarSel = MobileBy.xpath("//android.view.ViewGroup[@content-desc]/android.view.ViewGroup/android.widget.ImageView[1]");
    By followingSel = MobileBy.xpath("//android.widget.TextView[@text= 'Following']");
    By accountNameListSel = MobileBy.xpath("//android.widget.TextView[@text]");
    By allChannelsSel = MobileBy.xpath("//android.view.ViewGroup[@content-desc]/android.view.ViewGroup[1]/android.widget.ImageView");
    By backBtnSel =MobileBy.xpath("//android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.ImageView");

    public VerifyFollowingList(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
        this.wait = new WebDriverWait(appiumDriver, 10);
    }
    public void swipeUntilVisible(By targetSel) {
        while (true) {
            try {
                swipeBack.swipeBack();
                wait.until(ExpectedConditions.visibilityOfElementLocated(targetSel));
                break;
            } catch (TimeoutException e) {
                // Continue swiping
            }
        }
    }

    public void openFollowingList() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(profileNavSel));
        appiumDriver.findElement(profileNavSel).click();
        // Tap on Avatar -> Open profile
        wait.until(ExpectedConditions.visibilityOfElementLocated(avatarSel));
        appiumDriver.findElement(avatarSel).click();
        // Open following list
        wait.until(ExpectedConditions.visibilityOfElementLocated(followingSel));
        appiumDriver.findElement(followingSel).click();
    }
    public void verifyAccInFollowingList() {
        // Get username
        String nameAccount = appiumDriver.findElement(nameAcc).getText();

        try {
            // Kiểm tra trạng thái của button
            MobileElement button = null;
            String buttonLabel;
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(unFollowBtnSel));
                button = appiumDriver.findElement(unFollowBtnSel);
                buttonLabel = button.getText().trim();
            } catch (TimeoutException e) {
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(followBtnSel));
                    button = appiumDriver.findElement(followBtnSel);
                    buttonLabel = button.getText().trim();
                } catch (TimeoutException ex) {
                    System.out.println("FAIL: Neither 'Follow' nor 'Unfollow' button is found.");
                    return;
                }
            }

            System.out.println("Button label: " + buttonLabel); // In ra giá trị button label để kiểm tra

            swipeBack = new SwipeBack(appiumDriver);
            swipeBack.swipeBack();
            wait.until(ExpectedConditions.visibilityOfElementLocated(backBtnSel));
            appiumDriver.findElement(backBtnSel).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(discoverSel));

            boolean isFollowing = false;

            openFollowingList();
            // Get account in list
            wait.until(ExpectedConditions.visibilityOfElementLocated(accountNameListSel));
            List<MobileElement> accountNames = appiumDriver.findElements(accountNameListSel);

            // Kiểm tra xem tài khoản có nằm trong danh sách Following hay không
            for (MobileElement account : accountNames) {
                if (account.getText().equals(nameAccount)) {
                    isFollowing = true;
                    break;
                }
            }

            if (buttonLabel.equals("Unfollow")) {
                System.out.println("The account has been followed");
                if (isFollowing) {
                    System.out.println("[PASS]: Followed accounts are displayed in the following list.");
                    System.out.println("--------------------------------------------------------------");
                } else {
                    System.out.println("FAIL: The button indicates 'Unfollow' but the account is not displayed in the following list.");
                }
            } else if (buttonLabel.equals("Follow")) {
                System.out.println("Account is not followed yet");
                if (!isFollowing) {
                    System.out.println("[PASS]: The account is not being followed and is not in the following list.");
                    System.out.println("--------------------------------------------------------------");
                } else {
                    System.out.println("FAIL: The button indicates 'Follow' but the account is in the following list.");
                }
            } else {
                System.out.println("FAIL: Unexpected button label: " + buttonLabel);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void verifyAccInExpandList(String nameAccount, String buttonLabel2) {
        try {
            System.out.println("New state of button: " + buttonLabel2); // In ra giá trị button label để kiểm tra
            if (buttonLabel2.equals("Follow")) {
                System.out.println(nameAccount + " should be not displayed in list...");
            } else {
                System.out.println(nameAccount + " should be displayed in list...");
            }

            swipeBack = new SwipeBack(appiumDriver);
            swipeBack.swipeBack();
            wait.until(ExpectedConditions.visibilityOfElementLocated(backBtnSel));
            appiumDriver.findElement(backBtnSel).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(discoverSel));

            boolean isFollowing = false;

            openFollowingList();
            // Get account in list
            wait.until(ExpectedConditions.visibilityOfElementLocated(accountNameListSel));
            List<MobileElement> accountNames = appiumDriver.findElements(accountNameListSel);

            // Kiểm tra xem tài khoản có nằm trong danh sách Following hay không
            for (MobileElement account : accountNames) {
                if (account.getText().equals(nameAccount)) {
                    isFollowing = true;
                    break;
                }
            }

            if (buttonLabel2.equals("Unfollow")) {
                System.out.println("The account has been followed");
                if (isFollowing) {
                    System.out.println("[PASS]: " + nameAccount + " accounts are displayed in the following list.");
                    System.out.println("--------------------------------------------------------------");
                } else {
                    System.out.println("FAIL: The button indicates 'Unfollow' but the account " + nameAccount + " is not displayed in the following list.");
                }
            } else if (buttonLabel2.equals("Follow")) {
                System.out.println("Account is not followed yet");
                if (!isFollowing) {
                    System.out.println("[PASS]: " + nameAccount + " account is not being followed and is not in the following list.");
                    System.out.println("--------------------------------------------------------------");
                } else {
                    System.out.println("FAIL: The button indicates 'Follow' but the account " + nameAccount + " is in the following list.");
                }
            } else {
                System.out.println("FAIL: Unexpected button label: " + buttonLabel2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void verifyAccInFollowingList1(String nameAccount, String buttonLabel) {
        try {
            System.out.println("Button label: " + buttonLabel); // In ra giá trị button label để kiểm tra

            swipeBack = new SwipeBack(appiumDriver);
            swipeBack.swipeBack();
            wait.until(ExpectedConditions.visibilityOfElementLocated(backBtnSel));
            appiumDriver.findElement(backBtnSel).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(discoverSel));

            boolean isFollowing = false;

            openFollowingList();
            // Get account in list
            wait.until(ExpectedConditions.visibilityOfElementLocated(accountNameListSel));
            List<MobileElement> accountNames = appiumDriver.findElements(accountNameListSel);

            // Kiểm tra xem tài khoản có nằm trong danh sách Following hay không
            for (MobileElement account : accountNames) {
                if (account.getText().equals(nameAccount)) {
                    isFollowing = true;
                    break;
                }
            }

            if (buttonLabel.equals("Unfollow")) {
                System.out.println("The account has been followed");
                if (isFollowing) {
                    System.out.println("[PASS]: accounts" + nameAccount + " are displayed in the following list.");
                    System.out.println("--------------------------------------------------------------");
                } else {
                    System.out.println("FAIL: The button indicates 'Unfollow' but the account " + nameAccount + " is not displayed in the following list.");
                }
            } else if (buttonLabel.equals("Follow")) {
                System.out.println("Account is not followed yet");
                if (!isFollowing) {
                    System.out.println("[PASS]: account " + nameAccount + " is not being followed and is not in the following list.");
                    System.out.println("--------------------------------------------------------------");
                } else {
                    System.out.println("FAIL: The button indicates 'Follow' but the account " + nameAccount + " is in the following list.");
                }
            } else {
                System.out.println("FAIL: Unexpected button label: " + buttonLabel);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
