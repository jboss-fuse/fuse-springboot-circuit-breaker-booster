package org.jboss.fuse.boosters.cb;

import org.apache.camel.component.hystrix.metrics.servlet.HystrixEventStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    /**
     * Main method to start the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean(name="hystrixEventStreamServlet")
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new HystrixEventStreamServlet(), "/hystrix.stream");
    }
}