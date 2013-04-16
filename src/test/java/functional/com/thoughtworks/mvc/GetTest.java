package functional.com.thoughtworks.mvc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetTest {

    public static final String JETTY_SERVER_URL = "http://localhost:8080/sample/index.html";
    private WebDriver driver;

    @Before
    public void setUp() {

        driver = new ChromeDriver();
    }

    @Test
    public void should_response_get_by_id_request() {
        driver.get(JETTY_SERVER_URL);
        assertThat(getBody(), is("this is user with id 1"));
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    private String getBody() {
        return driver.findElement(By.tagName("body")).getText();
    }
}
