//In JUnit5, we use Assertions instead of Assert.
//We'll make a Test Class in the Test folder.
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.stream.Collectors;


public class GoogleSearchTest {

    public static void main(String[] args) {
//First, we create the Chrome Driver, who will perform all the automated actions.
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
//Then, we give it an url to perform the test.
        driver.get("https://www.amazon.com/");
//Next, we make it find the search box.
        WebElement searchInput = driver.findElement(By.cssSelector("#twotabsearchtextbox"));
//Now we give a phrase to use in the search box.
        String searchPhrase = "iphone";

        searchInput.sendKeys(searchPhrase);
        searchInput.sendKeys(Keys.ENTER);
//Here we analize the search results, and with the streams API, we convert the search results to lowercase and put them in a string list.
        List<String> actualItems = driver.findElements(By.cssSelector("[data-component-type='s-search-result'] h2 .a-link-normal"))
                .stream()
                .map(element -> element.getText().toLowerCase() + element.getAttribute("href").toLowerCase())
                .collect(Collectors.toList());
//Next, we filter the previous results that contains the phrase used to search.
        List<String> expectedItems = actualItems.stream()
                .filter(item -> item.contains(searchPhrase))
                .collect(Collectors.toList());
//Finally, we compare both lists, the first one with all results and the one with the filtered ones.
//If there's matching elements, the test pass, otherwise, the test fails.
        Assertions.assertEquals(expectedItems, actualItems);

        driver.quit();
    }
}