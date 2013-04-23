package mvc.functional;

import com.thoughtworks.main.JettyLauncher;
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

        JettyLauncher.start("src/main/webapp", "/sample");
        driver = new ChromeDriver();

    }

    @Test
    public void should_response_get_request() {
        driver.get(JETTY_SERVER_URL + "/pet/index");
        assertThat(getBody(), is("this is the pet index page, there are 5 pets"));
    }

    @Test
    public void should_response_to_get_request_with_params() {
        driver.get(JETTY_SERVER_URL + "/pet/show?id=1");
        assertThat(getBody(), is("this is the detail page for pet with name doudou"));
    }

    @After
    public void tearDown() {
        driver.quit();
        try {
            JettyLauncher.stop();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private String getBody() {
        return driver.findElement(By.tagName("body")).getText();
    }


}

