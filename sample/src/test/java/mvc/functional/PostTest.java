package mvc.functional;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PostTest extends FunctionalTest {


    @Test
    public void should_accept_post_request() {
        driver.get(JETTY_SERVER_URL + "/pet/new");

        WebElement element = driver.findElement(By.name("name"));

        // Enter something to search for
        element.sendKeys("Cheese!");

        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();
    }

}