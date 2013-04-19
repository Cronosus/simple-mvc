package functional.com.thoughtworks.mvc;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetTest {

    public static final String JETTY_SERVER_URL = "http://localhost:8080/sample";
    private WebDriver driver;
    private Server server;

    @Before
    public void setUp() {

        startServer();
        driver = new ChromeDriver();

    }

    @Test
    public void should_response_get_by_id_request() {
        driver.get(JETTY_SERVER_URL + "/user/index");
        assertThat(getBody(), is("this is the user index page"));
    }

    @After
    public void tearDown() {
        driver.quit();
        try {
            server.stop();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private String getBody() {
        return driver.findElement(By.tagName("body")).getText();
    }

    private void startServer() {
        try {
            server = new Server(8080);
            WebAppContext webAppContext = new WebAppContext(new File("src/test/webapp").getAbsolutePath(), "/sample");
            server.setHandler(webAppContext);

            server.start();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

}
