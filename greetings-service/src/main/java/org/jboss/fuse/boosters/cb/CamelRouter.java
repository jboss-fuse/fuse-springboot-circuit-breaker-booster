package org.jboss.fuse.boosters.cb;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

/**
 * A simple Camel REST DSL route that implement the name service.
 * 
 */
@Component
public class CamelRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // @formatter:off
        restConfiguration()
            .component("undertow")
            .bindingMode(RestBindingMode.json).host("0.0.0.0").port(8081);
        
        rest("/greetings").description("Greetings REST service")
            .consumes("application/json")
            .produces("application/json")

            .get().outType(Greetings.class)
                .responseMessage().code(200).endResponseMessage()
                .to("direct:greetingsImpl");

        from("direct:greetingsImpl")
            .streamCaching()
            .log(" Client request: ${body}")
            .hystrix()
                // see application.properties how hystrix is configured
                .to("undertow:http://localhost:8080/name")
            .onFallback()
                // we use a fallback without network that provides a repsonse message immediately
                .transform().simple("{ name: \"default fallback\" }")
            .to("bean:greetingsService?method=getGreetings");     
        // @formatter:on
    }

}