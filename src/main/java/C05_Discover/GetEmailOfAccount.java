package C05_Discover;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static DB.DBConfig.getConnection;

public class GetEmailOfAccount {
    AppiumDriver<MobileElement> appiumDriver;
    private WebDriverWait wait;

    public GetEmailOfAccount(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
        this.wait = new WebDriverWait(appiumDriver, 10);
    }
    public static String getEmailOfChannel (String channel) throws SQLException {
        String query = "SELECT u.email " +
                "FROM \"public\".\"channel\" c " +
                "JOIN \"public\".\"users\" u ON u.id = c.user_id " +
                "WHERE c.name = ?";

        String email = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Đặt giá trị cho tham số channel
            preparedStatement.setString(1, channel);
            System.out.println("Get email of account...");

            // Thực thi truy vấn
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Xử lý kết quả truy vấn
                if (resultSet.next()) {
                    email = resultSet.getString("email");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Ném lại ngoại lệ để có thể được xử lý ở cấp độ cao hơn
        }
        System.out.println("Email of " + channel + " is: " + email);
        return email;
    }
}
