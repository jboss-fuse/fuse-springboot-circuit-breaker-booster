package com.redhat.fuse.boosters.cb;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingsShouldReturnFallbackMessage() throws Exception {
	String message = this.restTemplate.getForObject("http://localhost:" + port + "/camel/greetings", Greetings.class).getGreetings();
	String defaultResult = "Hello, default fallback";
	String nameServiceUpResult = "Hello, Jacopo";

	// If the name service is running we get a different value.  Either "default fallback" or "Jacopo" are acceptable results here
	Assert.assertThat(message, CoreMatchers.anyOf(CoreMatchers.is(defaultResult), CoreMatchers.is(nameServiceUpResult)));
    }

    @Test
    public void healthShouldReturnOkMessage() throws Exception {
        Assert.assertEquals( "{\"status\":\"UP\"}", this.restTemplate.getForObject("http://localhost:" + port + "/actuator/health", String.class));
    }
}
