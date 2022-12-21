import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class GoogleSearchTest {

    private WebDriver driver;
//First, we set up the web driver configurations.
    @Before
    public void setUp() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.google.com");
    }
//Next, we build the test case.
    @Test
    public void testGooglePage() {
// First Step, find the search box and clear it.
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.clear();
//Second Step, write something to search in Google and submit it.
        searchBox.sendKeys("quality-stream Introducción a la Automatización de Pruebas de Software");
        searchBox.submit();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//Third Step, compare the search phrase with the window title.
//If they're the same, the test pass. Otherwise, it fails.
        assertEquals("quality-stream Introducción a la Automatización de Pruebas de Software - Buscar con Google",driver.getTitle());
    }
//Finally, close the web driver.
    @After
    public void tearDown() {
        driver.quit();
    }

}