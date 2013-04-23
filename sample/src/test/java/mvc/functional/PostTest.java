package mvc.functional;

import com.thoughtworks.main.JettyLauncher;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class PostTest {

    public static final String JETTY_SERVER_URL = "http://localhost:8080/sample";
    private WebDriver driver;

    @Before
    public void setUp() {

        JettyLauncher.start("src/main/webapp", "/sample");
        driver = new ChromeDriver();
    }

    @Test
    public void should_accept_nested_form() {
        driver.get(JETTY_SERVER_URL + "/pet/new");

        WebElement element = driver.findElement(By.name("a[]"));

        // Enter something to search for
        element.sendKeys("Cheese!");

        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();
    }

}
