import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.impl.HomePage;
import pages.impl.SearchResultsPage;

import java.util.List;

/*
Page Object Model is a design pattern which is commonly used for Automating the Test Cases.
This design pattern can be used with any kind of framework like keyword-driven,
Data-driven, hybrid framework, etc.
The Page object is an object-oriented class which acts as an interface for the page of your Application
under test. Page class contains web elements and methods to interact with web elements.
While Automating the test cases, we create the object of these Page Classes and interact with web
elements by calling the methods of these classes.
 */

public class AmazonSearchTest {

    private static final String SEARCH_PHRASE = "iphone";

    private static ChromeDriver driver;

    @BeforeAll
    public static void setUpDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void checkAmazonSearch() {
        driver.get("https://www.amazon.com/");

        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);

        homePage.performSearch(SEARCH_PHRASE);

        List<String> actualItems = searchResultsPage.searchResultsItemsText();
        List<String> expectedItems = searchResultsPage.searchResultsItemsWithText(SEARCH_PHRASE);

        Assertions.assertEquals(expectedItems, actualItems);
    }

    @AfterAll
    public static void tearDownDriver() {
        driver.quit();
    }
}