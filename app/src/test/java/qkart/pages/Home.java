package qkart.pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home {
    private static WebDriver driver;
    private static String url = "https://crio-qkart-frontend-qa.vercel.app";
    static WebDriverWait wait;

    public Home(WebDriver driver) {
        Home.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public static void navigateToHome() {
        if (!driver.getCurrentUrl().equals(url)) {
            driver.get(url);
        }
    }

    public static boolean PerformLogout() {
        try {
            // Find and click on the Logout Button
            WebElement logout_button = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.className("MuiButton-text")));
            logout_button.click();

            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("css-1urhf6j"),
                    "Logout"));

            return true;
        } catch (Exception e) {
            System.out.println("Error while performing logout: " + e.getMessage());
            return false;
        }
    }

    public static boolean searchForProduct(String product) {
        try {
            // Search for the product
            WebElement searchBox = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='search']")));
            searchBox.clear();
            searchBox.sendKeys(product);

            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(String.format(
                            "//div[@class='MuiCardContent-root css-1qw96cp']/p[contains(text(),'%s')]",
                            product))),
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//h4[normalize-space(text())='No products found']"))));

            return true;
        } catch (Exception e) {
            System.out.println("Error while searching for a product: " + e.getMessage());
            return false;
        }
    }

    /**
     * ADD product to cart
     * - Find the product by iterating through search results
     * - Click on the "ADD TO CART" button for the target product
     * - Return true if these operations succeeds
     */

    public static boolean addProductToCart(String product) {
        try {

            List<WebElement> searchResults = wait
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("css-sycj1h")));

            for (WebElement searchResult : searchResults) {
                if (product.contains(searchResult.findElement(By.className("css-yg30e6")).getText())) {
                    searchResult.findElement(By.xpath(".//button[contains(text(),'Add to cart')]")).click();

                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
                            "//*[@class='MuiBox-root css-1gjj37g']/div[text()='%s']",
                            product))));
                    return true;
                }
            }
            System.out.println("Unable to find the given product");
            return false;
        } catch (Exception e) {
            System.out.println("Exception while performing add to cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Clicking on checkout button
     */
    public static boolean clickCheckout() {
        Boolean status = false;
        try {
            // Find and click on the the Checkout button
            WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.className("checkout-btn")));
            checkoutBtn.click();

            status = true;
            return status;
        } catch (Exception e) {
            System.out.println("Exception while clicking on Checkout button: " + e.getMessage());
            return status;
        }
    }

}
