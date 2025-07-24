package qkart;

import qkart.pages.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestCase {
    protected WebDriver driver;
    private boolean status;
    private static final String HOME_PAGE_URL = "https://crio-qkart-frontend-qa.vercel.app";
    private String product = "YONEX Smash Badminton Racquet";
    private String Address = "Addr line 1 addr Line 2 addr line 3";
    private String username = "testUser";
    private String password = "testUser@123";
    Home homepage;
    Login loginPage;
    Register registerPage;
    Checkout checkoutPage;

    // Setup
    @BeforeClass(enabled = true)
    public void setup() {
        try {
            driver = new ChromeDriver();
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();
            driver.get(HOME_PAGE_URL);
            System.out.println("Driver initialized: " + (driver != null));

            this.registerPage = new Register(driver);
            this.loginPage = new Login(driver);
            this.homepage = new Home(driver);
            this.checkoutPage = new Checkout(driver);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Verifying product checkout flow
     */

    // Test
    @Test(description = "Verify that a new user can add product to cart and Checkout")
    public void TestCase01() {
        try {
            System.out.println("Start TestCase01: Validate product checkout flow");

            // Go to the Register page and register a new user
            Register.navigateToRegisterPage();
            status = Register.registerUser(username, password, true);
            System.out.println("Step: User successfully Registered: " + status);
            Assert.assertTrue(status, "Registration failed");

            // Save the username of the newly registered user
            String lastGeneratedUserName = Register.lastGeneratedUsername;

            // Go to login page and Login with the newly registered user's credentials
            Login.navigateToLoginPage();
            status = Login.PerformLogin(lastGeneratedUserName, password);
            System.out.println("Step: User successfully Logged-in: " + status);
            Assert.assertTrue(status, "User Perform Login Failed");

            // Go to the home page
            Home.navigateToHome();

            // Add product to cart
            status = Home.searchForProduct(product);
            Home.addProductToCart(product);

            // Click on the checkout button
            Home.clickCheckout();

            // Add a new address on the Checkout page and select it
            Checkout.addNewAddress(Address);
            Checkout.selectAddress(Address);

            // Place the order and validate if order is placed successfully
            status = Checkout.placeOrder();
            System.out.println("Step: User successfully placed order: " + status);
            Assert.assertTrue(status, "After placing order not redirected to Thanks page");

            // log out - (Tear-down)
            Home.navigateToHome();
            status = Home.PerformLogout();
            System.out.println("Step: User successfully logged-out: " + status);

            System.out.println("End TestCase01: Validate product checkout flow");

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

    }

    // Tear-down
    @AfterClass(enabled = false)
    public void tearDown() {
        driver.quit();
    }

}
