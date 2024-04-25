package selenium_tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterPageTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        String projectPath = System.getProperty("user.dir");
        String chromeDriverPath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "chromedriver-win64" + File.separator + "chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
    }

    @Test
    public void navigationViewRegister() {
        driver.get("http://localhost:8080/FinalProjectCoffee_war_exploded/controller?action=view-register");
        //Testing if the Action Is present
        assertTrue(driver.getCurrentUrl().contains("register.jsp"));

        //Testing for the username input value present
        WebElement usernameInput = driver.findElement(By.id("username"));
        assertTrue(usernameInput.isDisplayed(), "Username Input is displayed");

        //Testing for the phone input value present
        WebElement phoneInput = driver.findElement(By.id("number"));
        assertTrue(phoneInput.isDisplayed(), "PhoneNumber Input is displayed");

        //Testing for the Email input value present
        WebElement emailInput = driver.findElement(By.id("email"));
        assertTrue(emailInput.isDisplayed(), "Email Input is displayed");

        //Testing for the password input value present
        WebElement passwordInput = driver.findElement(By.id("password"));
        assertTrue(passwordInput.isDisplayed(), "Password Input is displayed");

        //Testing for the confirm-password input value present
        WebElement confirmPasswordInput = driver.findElement(By.id("confirm-password"));
        assertTrue(confirmPasswordInput.isDisplayed(), "Confirm PasswordInput Input is displayed");

        //Testing for the register button present
        WebElement registerButton = driver.findElement(By.cssSelector("button[name='register']"));
        assertTrue(registerButton.isDisplayed());

        assertEquals("Register", driver.getTitle());

    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
