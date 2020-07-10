package helpers;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static helpers.Environment.videoStorageUrl;
import static org.openqa.selenium.logging.LogType.BROWSER;


public class AttachmentsHelper {
    public static void attachBrowserConsoleLogs() {
        attachAsText("Browser console logs", getConsoleLogs());
    }

    @Attachment(value = "{attachName}", type = "text/plain")
    public static String attachAsText(String attachName, String message) {
        System.out.println("[Attachment] " + attachName + ": " + message + "\n");
        return message;
    }

    @Attachment(value = "{attachName}", type = "image/png")
    public static byte[] attachScreenshot(String attachName) {
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page source", type = "text/plain")
    public static byte[] attachPageSource() {
        return getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8);
    }


    @Attachment(value = "Video", type = "text/html", fileExtension = ".html")
    public static String attachVideo() {
        if(videoStorageUrl != null) {
            return "<html><body><video width='100%' height='100%' controls autoplay><source src='"
                    + getVideoUrl()
                    + "' type='video/mp4'></video></body></html>";
        }
        return null;
    }

    public static String getVideoUrl() {
        try {
                return new URL(videoStorageUrl + "/" + getSessionId() + ".mp4") + "";
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSessionId(){
        return ((RemoteWebDriver) getWebDriver()).getSessionId().toString().replace("selenoid","");
    }

    public static String getConsoleLogs() {
        return String.join("\n", Selenide.getWebDriverLogs(BROWSER));
    }

}
