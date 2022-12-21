import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/*
The Implicit Wait in Selenium is used to tell the web driver to wait for a certain amount of time
before it throws a “No Such Element Exception”.
The default setting is 0.
Once we set the time, the web driver will wait for the element for that time before throwing an exception.

The Explicit Wait in Selenium is used to tell the Web Driver to wait for certain conditions (Expected Conditions)
or maximum time exceeded before throwing “ElementNotVisibleException” exception.
It is an intelligent kind of wait, but it can be applied only for specified elements.
It gives better options than implicit wait as it waits for dynamically loaded Ajax elements.
 */

public class GitHubSearchTest {
    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setUpDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
//Here we set up the Implicit Wait.
//This line of code tells our web browser to wait a certain amount of time before running the test.
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
//Here we set up the Explicit Wait.
    @BeforeAll
    public static void setUpWait(){
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
//Due to errors occurring when both waits are used, we need to shut off the implicit wait.
    private static void switchOffImplicitWait(){
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }


    @Test
    public void checkGithubSearch() {
        driver.get("https://github.com/");

        WebElement searchInput = driver.findElement(By.cssSelector("[name='q']"));

        String searchPhrase = "selenium";

        searchInput.sendKeys(searchPhrase);
        searchInput.sendKeys(Keys.ENTER);

        List<String> actualItems = driver.findElements(By.cssSelector(".repo-list-item")).stream()
                .map(element -> element.getText().toLowerCase())
                .collect(Collectors.toList());

        List<String> expectedItems = actualItems.stream()
                .filter(item -> item.contains(searchPhrase))
                .collect(Collectors.toList());

        System.out.println(LocalDateTime.now());
//Here we switch off the Implicit Wait to let the Explicit Wait get the Expected Conditions.
        switchOffImplicitWait();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".repo-list-item")));
        Assertions.assertEquals(expectedItems, actualItems);
    }


    @AfterAll
    public static void tearDownDriver() {
        System.out.println(LocalDateTime.now());
        driver.quit();
    }
}