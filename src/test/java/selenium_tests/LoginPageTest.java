package selenium_tests;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginPageTest {
    private WebDriver driver;
    private EntityManagerFactory factory;
    private UserRepositories userRep;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\conor\\Documents\\college\\Year 3\\Project\\FinalProjectStage3\\src\\main\\resources\\chromedriver-win64\\chromedriver.exe");
        factory = Persistence.createEntityManagerFactory("testpizzadeliveryshop");
        userRep = new UserRepositories(factory);
        driver = new ChromeDriver();
    }

    @Test
    public void LoginInAutomationSystemTest() {
        driver.get("http://localhost:8080/FinalProjectCoffee_war_exploded/controller?action=view-login");
        //Testing if the Action Is present
        assertTrue(driver.getCurrentUrl().contains("login.jsp"));

        //Testing for the login form if present
        WebElement loginForm = driver.findElement(By.id("login-form"));
        assertTrue(loginForm.isDisplayed(), "Login Form is displayed");

        //Testing for the username input value present
        WebElement usernameInput = driver.findElement(By.id("username"));
        assertTrue(usernameInput.isDisplayed(), "Username Input is displayed");

        //Testing for the password input value present
        WebElement passwordInput = driver.findElement(By.id("password"));
        assertTrue(passwordInput.isDisplayed(), "Password Input is displayed");

        //Testing for the login button present
        WebElement loginButton = driver.findElement(By.cssSelector("button[name='login']"));
        assertTrue(loginButton.isDisplayed());

        assertEquals("Login", driver.getTitle());

    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }


    }
}
