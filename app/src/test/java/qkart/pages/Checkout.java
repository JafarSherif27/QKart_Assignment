package qkart.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Checkout {
    private static WebDriver driver;
    static WebDriverWait wait;

    public Checkout(WebDriver driver) {
        Checkout.driver = driver;
        wait  = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    /**
     * Adding new address
     * - Click on "Add new address" button and enter the address
     * - Click on "ADD" button to save the address
     */
    public static void addNewAddress(String address) {
        try {
            WebElement addNewAddressBtn = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("add-new-btn")));
            addNewAddressBtn.click();

            WebElement addressBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//textarea[contains(@placeholder,'Enter your complete address')]")));
            addressBox.clear();
            addressBox.sendKeys(address);

            WebElement addBtn = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Add')]")));
            addBtn.click();

            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(String.format(
                    "//*[contains(@class,'MuiTypography-root') and text()='%s']",
                    address))));

        } catch (Exception e) {
            System.out.println("Exception occurred while entering address: " + e.getMessage());
            e.printStackTrace();

        }
    }

    /**
     * Selecting saved address
     * - Iterate through all the addresses saved
     * - select the correct address
     */
    public static void selectAddress(String addressToSelect) {
        try {
            WebElement address = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(String.format("//*[contains(@class,'MuiTypography-root') and text()='%s']",
                            addressToSelect))));
            address.click();

        } catch (Exception e) {
            System.out.println("Exception Occurred while selecting the given address: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /*
     * Placing order
     */
    public static boolean placeOrder() {
        try {
            WebElement placeOrder = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='PLACE ORDER']")));
            placeOrder.click();

            wait.until(ExpectedConditions.urlToBe("https://crio-qkart-frontend-qa.vercel.app/thanks"));

            return driver.getCurrentUrl().endsWith("/thanks");

        } catch (Exception e) {
            System.out.println("Exception while clicking on PLACE ORDER: " + e.getMessage());
            return false;
        }
    }

}
