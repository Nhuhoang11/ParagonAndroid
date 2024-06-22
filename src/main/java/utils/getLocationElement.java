package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.WebDriverWait;

public class getLocationElement {
    public static class ElementUtils {
        private MobileElement element;

        public ElementUtils(MobileElement element) {
            this.element = element;
        }

        public Point getElementLocation() {
            return element.getLocation();
        }

        public int getElementXCoordinate() {
            return element.getLocation().getX();
        }

        public int getElementYCoordinate() {
            return element.getLocation().getY();
        }
    }

}
