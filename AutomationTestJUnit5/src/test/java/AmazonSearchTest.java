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

import java.util.List;
import java.util.stream.Collectors;

//Here we will use JUnit5 hooks to make a proper Test Case structure.
public class AmazonSearchTest {
    private static WebDriver driver;
//The BeforeAll hook help ups set up all parameters that aren't test actions.
//In this case, we set up the web driver here, outside the test code.
    @BeforeAll
    public static void setUpDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

// The Test hook is where we are going to write all the code needed to run said test.
// All the actions the test needs to perform.
    @Test
    public void checkAmazonSearch() {
        driver.get("https://www.amazon.com/");

        WebElement searchInput = driver.findElement(By.cssSelector("#twotabsearchtextbox"));

        String searchPhrase = "iphone";

        searchInput.sendKeys(searchPhrase);
        searchInput.sendKeys(Keys.ENTER);


        List<String> actualItems = driver.findElements(By.cssSelector("[data-component-type='s-search-result'] h2 .a-link-normal"))
                .stream()
                .map(element -> element.getText().toLowerCase() + element.getAttribute("href").toLowerCase())
                .collect(Collectors.toList());

        List<String> expectedItems = actualItems.stream()
                .filter(item -> item.contains("invalid search phrase"))
                .collect(Collectors.toList());

        Assertions.assertEquals(expectedItems, actualItems);
    }

// The AfterAll hook define actions after the test that are going to execute despite the results of said test.
    @AfterAll
    public static void tearDownDriver() {
        driver.quit();
    }
}