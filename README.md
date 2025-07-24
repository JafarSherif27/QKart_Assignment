# QKart_Assignment
Web Automation of https://crio-qkart-frontend-qa.vercel.app/ with Selenium, TestNG, POM and Gradle for Appscrip.

## Project Objectives
- Automate end-to-end flow: Register -> Login -> Search Product -> Add to Cart -> Checkout
- Validate elements using dynamic XPath, explicit waits, and page synchronization
- Structure test code using Page Object Model (POM) for scalability and readability

## Application Under Test 
QKart - QKart is an e-commerce web application designed by Crio.Do to simulate real-world shopping scenarios for automation testing. 
Link - https://crio-qkart-frontend-qa.vercel.app/

## Automation Script Details
- Page Object Model (POM) is used to separate page structure and test logic.
    - Home.java – Contains functions related to homepage, product search, logout, etc.
    - Login.java – Handles user login flow
    - Register.java – Handles user registration
    - Checkout.java – Manages cart and checkout-related interactions
- TestCase.java – Contains the Selenium automation script to validate the complete checkout flow


## Cloning the repository
 git clone https://github.com/JafarSherif27/QKart_Assignment.git

