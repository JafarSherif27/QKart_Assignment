package qkart.pages;

import java.sql.Timestamp;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Register {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static String url = "https://crio-qkart-frontend-qa.vercel.app/register";
    public static String lastGeneratedUsername = "";

    public Register(WebDriver driver) {
        Register.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public static void navigateToRegisterPage() {
        if (!driver.getCurrentUrl().equals(url)) {
            driver.get(url);
        }
    }

    public static void enterText(WebElement textBox, String text) {
        new Actions(driver).click(textBox).keyDown(Keys.CONTROL).sendKeys("a")
                .keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).perform();
        textBox.sendKeys(text);
    }

    public static boolean registerUser(String Username, String Password, Boolean makeUsernameDynamic) {
        try {
            // Time stamp to generate unique username
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            String test_data_username;

            if (makeUsernameDynamic)
                // Concatenate the timestamp to string to form unique username
                test_data_username = Username + "_" + String.valueOf(timestamp.getTime());
            else
                test_data_username = Username;

            // Find the Username text box and enter username
            WebElement username_txt_box = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
            enterText(username_txt_box, test_data_username);

            // Find the password Text Box and enter password
            WebElement password_txt_box = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            enterText(password_txt_box, Password);

            // Find confirm password text box and enter password
            WebElement confirm_password_txt_box = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmPassword")));
            enterText(confirm_password_txt_box, Password);

            // Find the register now button and click on it
            WebElement register_now_button = wait
                    .until(ExpectedConditions.elementToBeClickable(By.className("button")));
            register_now_button.click();

            wait.until(ExpectedConditions.urlToBe("https://crio-qkart-frontend-qa.vercel.app/login"));

            lastGeneratedUsername = test_data_username;

            return driver.getCurrentUrl().endsWith("/login");

        } catch (Exception e) {
            System.out.println("Error occured while trying to register: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }
}
