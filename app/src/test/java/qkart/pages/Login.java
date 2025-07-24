package qkart.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {
    private static WebDriver driver;
    private static String url = "https://crio-qkart-frontend-qa.vercel.app/login";
    private static WebDriverWait wait;

    public Login(WebDriver driver) {
        Login.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public static void navigateToLoginPage() {
        if (!driver.getCurrentUrl().equals(url)) {
            driver.get(url);
        }
    }

    public static void enterText(WebElement textBox, String text) {
        new Actions(driver).click(textBox).keyDown(Keys.CONTROL).sendKeys("a")
                .keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).perform();
        textBox.sendKeys(text);
    }

    public static boolean PerformLogin(String Username, String Password) {
        try {
            // Find the Username Text Box and enter username
            WebElement username_txt_box = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
            enterText(username_txt_box, Username);

            // Find the password Text Box and enter password
            WebElement password_txt_box = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            enterText(password_txt_box, Password);

            // Find the Login Button and click on it
            WebElement login_button = wait.until(ExpectedConditions.elementToBeClickable(By.className("button")));
            login_button.click();

            wait.until(ExpectedConditions.invisibilityOf(login_button));

            return VerifyUserLoggedIn(Username);

        } catch (Exception e) {
            System.out.println("Error occured while performing Login: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean VerifyUserLoggedIn(String Username) {
        try {
            // Find the username label (present on the top right of the page)
            WebElement username_label = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.className("username-text")));
            return username_label.getText().equals(Username);

        } catch (Exception e) {
            System.out.println("Error occured while checking for Userlabel: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

}
