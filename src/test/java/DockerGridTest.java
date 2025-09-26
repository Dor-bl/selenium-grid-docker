import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DockerGridTest {

    private static WebDriver driver;
    private static final String GRID_URL = "http://localhost:4444/wd/hub";

    @BeforeAll
    public static void setUp() {
        System.out.println("Starting tests on Selenium Grid at " + GRID_URL);
    }

    @Test
    public void testGoogleSearchOnChrome() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL(GRID_URL), options);

        System.out.println("Executing Chrome test...");

        driver.get("https://www.google.com");
        assertEquals("Google", driver.getTitle());

        driver.findElement(By.name("q")).sendKeys("Selenium Docker Java");
        driver.findElement(By.name("q")).submit();

        // Check if results are displayed
        assertTrue(driver.getTitle().contains("Selenium Docker Java"));
        System.out.println("Chrome test passed! ✔️");
    }

    @Test
    public void testGoogleSearchOnFirefox() throws MalformedURLException {
        FirefoxOptions options = new FirefoxOptions();
        driver = new RemoteWebDriver(new URL(GRID_URL), options);

        System.out.println("Executing Firefox test...");

        driver.get("https://www.google.com");
        assertEquals("Google", driver.getTitle());

        driver.findElement(By.name("q")).sendKeys("Selenium Docker Java");
        driver.findElement(By.name("q")).submit();

        // Check if results are displayed
        assertTrue(driver.getTitle().contains("Selenium Docker Java"));
        System.out.println("Firefox test passed! ✔️");
    }

    @AfterEach
    public void tearDownEachDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}