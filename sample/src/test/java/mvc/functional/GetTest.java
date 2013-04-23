package mvc.functional;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetTest extends FunctionalTest {

    @Test
    public void should_response_get_request() {
        driver.get(JETTY_SERVER_URL + "/pet/index");
        assertThat(getBody(), is("this is the pet index page, there are 5 pets"));
    }

    @Test
    public void should_response_to_get_request_with_params() {
        driver.get(JETTY_SERVER_URL + "/pet/show?id=1");
        assertThat(getBody(), is("this is the detail page for pet with name Doudou"));
    }
}

