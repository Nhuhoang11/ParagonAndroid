package Action;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScrollUpUntilEnd {
    private AppiumDriver<MobileElement> appiumDriver;
    private ScrollUp90 scrollUp90;
    public ScrollUpUntilEnd(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    public void scrollUptoStartPoint(String xpath) {
        boolean canScrollMore = true;

        while (canScrollMore) {
            // Lấy phần tử cuối cùng hiển thị trên màn hình trước khi vuốt
            List<MobileElement> elementsBeforeScroll = appiumDriver.findElementsByXPath(xpath);
            MobileElement lastElementBeforeScroll = elementsBeforeScroll.get(elementsBeforeScroll.size() - 1);

            // Thực hiện thao tác vuốt
            scrollUp90 = new ScrollUp90(appiumDriver);
            scrollUp90.scrollUpTo();

            // Đợi một chút để nội dung cập nhật sau khi vuốt
            try {
                Thread.sleep(2000); // ngủ 2 giây
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Lấy phần tử cuối cùng hiển thị trên màn hình sau khi vuốt
            List<MobileElement> elementsAfterScroll = appiumDriver.findElementsByXPath(xpath);
            MobileElement lastElementAfterScroll = elementsAfterScroll.get(elementsAfterScroll.size() - 1);

            // So sánh phần tử cuối cùng trước và sau khi vuốt
            canScrollMore = !lastElementBeforeScroll.equals(lastElementAfterScroll);
        }
    }
    public int countElementsWhileScrollingUp(By by) {
        boolean canScrollMore = true;
        Set<MobileElement> uniqueElements = new HashSet<>();
        while (canScrollMore) {
            // Lấy các phần tử trên màn hình trước khi cuộn
            List<MobileElement> elementsBeforeScroll = appiumDriver.findElements(by);
            uniqueElements.addAll(elementsBeforeScroll);

            // Thực hiện thao tác cuộn
            scrollUp90 = new ScrollUp90(appiumDriver);
            scrollUp90.scrollUpTo();

            // Đợi một chút để nội dung cập nhật sau khi cuộn
            try {
                Thread.sleep(1000); // ngủ 1 giây
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Lấy các phần tử trên màn hình sau khi cuộn
            List<MobileElement> elementsAfterScrollUp = appiumDriver.findElements(by);
            uniqueElements.addAll(elementsAfterScrollUp);

            // Kiểm tra nếu không có phần tử mới nào xuất hiện sau khi cuộn
            canScrollMore = elementsBeforeScroll.size() != elementsAfterScrollUp.size();
        }

        // Trả về số lượng phần tử duy nhất tìm thấy
        return uniqueElements.size();

    }

}
