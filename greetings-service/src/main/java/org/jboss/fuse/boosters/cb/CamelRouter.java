package org.jboss.fuse.boosters.cb;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

/**
 * A simple Camel REST DSL route that implement the greetings service.
 * 
 */
@Component
public class CamelRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // @formatter:off
        restConfiguration()
            .component("servlet")
            .bindingMode(RestBindingMode.json);
        
        rest("/greetings").description("Greetings REST service")
            .consumes("application/json")
            .produces("application/json")

            .get().outType(Greetings.class)
                .responseMessage().code(200).endResponseMessage()
                .to("direct:greetingsImpl");

        from("direct:greetingsImpl")
            .streamCaching()
            .hystrix()
                // see application.properties how hystrix is configured
                .log(" Try to call name Service")
                .to("http://localhost:8081/camel/name?bridgeEndpoint=true")
                .log(" Successfully called name Service")
            .onFallback()
                // we use a fallback without network that provides a repsonse message immediately
                .log(" We are falling back!!!!")
                .transform().simple("{ name: \"default fallback\" }")
            .end()
            .to("bean:greetingsService?method=getGreetings");     
        // @formatter:on
    }

}