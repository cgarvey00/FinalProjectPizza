package selenium_tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleAutomationTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        String projectPath = System.getProperty("user.dir");
        String chromeDriverPath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "chromedriver-win64" + File.separator + "chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
    }
    @Test
    public void testWebsiteTitle() {
        driver.get("http://localhost:8080/FinalProjectCoffee_war_exploded/index.jsp");

        assertEquals("Home", driver.getTitle());

    }

    @AfterEach
    public void tearDown() {
        if(driver!=null){
            driver.quit();
        }


    }}
