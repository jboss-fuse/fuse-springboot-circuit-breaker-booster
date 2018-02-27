package com.redhat.fuse.boosters.cb;

import org.apache.camel.component.hystrix.metrics.servlet.HystrixEventStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        return new ServletRegistrationBean(new CorsHystrixEventStreamServlet(), "/hystrix.stream");
    }

    private class CorsHystrixEventStreamServlet extends HystrixEventStreamServlet {

        public CorsHystrixEventStreamServlet() {
        }

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            //XXX: workaround waiting for https://github.com/Netflix/Hystrix/issues/1760
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
            response.addHeader("Access-Control-Expose-Headers", "*");

            super.doGet(request, response);
        }
    }
}