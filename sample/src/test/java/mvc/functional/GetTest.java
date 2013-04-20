package mvc.functional;

import com.thoughtworks.main.JettyServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetTest {

    public static final String JETTY_SERVER_URL = "http://localhost:8080/sample";
    private WebDriver driver;

    @Before
    public void setUp() {

        JettyServer.start("sample/src/main/webapp", "/sample");
        driver = new ChromeDriver();

    }

    @Test
    public void should_response_get_by_id_request() {
        driver.get(JETTY_SERVER_URL + "/user/index");
        assertThat(getBody(), is("this is the user index page, there are 5 users"));
    }

    @After
    public void tearDown() {
        driver.quit();
        try {
            JettyServer.stop();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private String getBody() {
        return driver.findElement(By.tagName("body")).getText();
    }


}

