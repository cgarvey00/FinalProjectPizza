package selenium_tests;

import com.finalprojectcoffee.controllers.Controller;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginAutomationTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        //String persistenceUnit = System.getProperty("pizzashop.persistence.unit", "pizzashop");
        System.setProperty("pizzashop.persistence.unit", "testpizzadeliveryshop");
        String projectPath = System.getProperty("user.dir");
        String chromeDriverPath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "chromedriver-win64" + File.separator + "chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();

    }


    @Test
    public void testLogin() {
        driver.get("http://localhost:8080/FinalProjectCoffee_war_exploded/controller?action=view-login");
        //Testing if the Action Is present
        assertTrue(driver.getCurrentUrl().contains("login.jsp"));
//
//        //Testing for the login form if present
        WebElement loginForm = driver.findElement(By.id("login-form"));
        assertTrue(loginForm.isDisplayed(), "Login Form is displayed");

        //Testing for the username input value present
        WebElement usernameInput = driver.findElement(By.id("username"));
//        assertTrue(usernameInput.isDisplayed(), "Username Input is displayed");
        usernameInput.sendKeys("cgarvey00");


        //Testing for the password input value present
        WebElement passwordInput = driver.findElement(By.id("password"));
//        assertTrue(usernameInput.isDisplayed(), "Password Input is displayed");
        passwordInput.sendKeys("Pa22w0rd19!");

        //Testing for the login button present
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
//        assertTrue(loginButton.isDisplayed());

        loginButton.click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.getStackTrace();
            System.out.println(e.getMessage());
        }

        String url = driver.getCurrentUrl();

        assertTrue(url.contains("customer-home.jsp"), "The user is redirected to the customer page");


    }

    @AfterEach
    public void tearDown() {
        System.clearProperty("pizzashop.persistence.unit");
        if (driver != null) {
            driver.quit();
        }


    }
}
