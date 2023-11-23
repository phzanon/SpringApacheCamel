package com.apache.camel.example.camelexample.rest;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class RestRoute extends RouteBuilder {

    private final Environment env;

    public RestRoute(Environment env) {
        this.env = env;
    }


    @Override
    public void configure() throws Exception {

        restConfiguration()
                .contextPath(env.getProperty("camel.component.servlet.mapping.contextPath", "/rest/*"))
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Spring Boot Camel Postgres Rest API.")
                .apiProperty("api.version", "1.0")
                .apiProperty("cors", "true")
                .apiContextRouteId("doc-api")
                .port(env.getProperty("server.port", "8081"))
                .bindingMode(RestBindingMode.json);

        rest("/teste")
                .post().to( "activemq:queue:teste?requestTimeout=9000")
                .responseMessage();
    }
}
