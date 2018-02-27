package com.redhat.fuse.boosters.cb;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
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
    public void nameShouldReturnDefaultMessage() throws Exception {
       Assert.assertEquals( "Jacopo", this.restTemplate.getForObject("http://localhost:" + port + "/camel/name", Name.class).getName());
    }

    @Test
    public void healthShouldReturnOkMessage() throws Exception {
        Assert.assertEquals( "{\"status\":\"UP\"}", this.restTemplate.getForObject("http://localhost:" + port + "/health", String.class));
    }
}
