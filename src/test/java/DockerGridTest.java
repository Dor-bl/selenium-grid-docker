import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class DockerGridTest {

    private static WebDriver driver;
    private static final String GRID_URL = "http://localhost:4444/wd/hub";

    @BeforeAll
    public static void setUp() {
        System.out.println("Starting tests on Selenium Grid at " + GRID_URL);
    }


    @Test
    public void testDuckDuckGoSearchOnChrome() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--no-sandbox");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);


        driver = new RemoteWebDriver(new URL(GRID_URL), options);

        System.out.println("Executing Chrome DuckDuckGo test...");

        driver.get("https://www.duckduckgo.com");

        assertTrue(driver.getTitle().contains("DuckDuckGo"), "DuckDuckGo title not found");

        WebElement q = driver.findElement(By.name("q"));
        q.sendKeys("Selenium Docker Java");
        q.submit();

        assertDuckDuckGoSearchResultsShown(driver);

        System.out.println("Chrome DuckDuckGo test passed! ✔️");
    }

    @Test
    public void testDuckDuckGoSearchOnFirefox() throws MalformedURLException {

        FirefoxProfile profile = new FirefoxProfile();
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
        options.addArguments("--width=1280", "--height=800");

        driver = new RemoteWebDriver(new URL(GRID_URL), options);

        System.out.println("Executing Firefox DuckDuckGo test...");

        driver.get("https://www.duckduckgo.com");

        assertTrue(driver.getTitle().contains("DuckDuckGo"), "DuckDuckGo title not found");

        WebElement q = driver.findElement(By.name("q"));
        q.sendKeys("Selenium Docker Java");
        q.submit();

        assertDuckDuckGoSearchResultsShown(driver);

        System.out.println("Firefox DuckDuckGo test passed! ✔️");
    }

    private void assertDuckDuckGoHomeLoaded(WebDriver driver) {
        WebElement box = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
        assertTrue(box.isDisplayed() && box.isEnabled(), "Search box not ready");
        assertTrue(driver.getCurrentUrl().contains("duckduckgo.com"), "Not on DuckDuckGo domain");
    }

    private void assertDuckDuckGoSearchResultsShown(WebDriver driver) {
        // Wait for at least one result title element (updated selector for DuckDuckGo)
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(
                        By.cssSelector(".react-results--main h2 a[data-testid='result-title-a']"), 0));
        assertFalse(driver.findElements(By.cssSelector(".react-results--main h2 a[data-testid='result-title-a']")).isEmpty(), "No DuckDuckGo result titles found");
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