package com.apache.camel.example.camelexample.rest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.VerbDefinition;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.StringReader;

@Component
public class RestRoute extends RouteBuilder {

    @Value("${route.rest.path}")
    private String path;

    @Value("${route.rest.port}")
    private String port;

    private final Environment env;

    public RestRoute(Environment env) {
        this.env = env;
    }


    @Override
    public void configure() throws Exception {

        restConfiguration()
                .contextPath(env.getProperty("camel.component.servlet.mapping.contextPath", path))
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Spring Boot Camel Postgres Rest API.")
                .apiProperty("api.version", "1.0")
                .apiProperty("cors", "true")
                .apiContextRouteId("doc-api")
                .port(env.getProperty("server.port", port))
                .bindingMode(RestBindingMode.auto);

        rest("/send")
                .post()
                .produces("json")
                .to( "activemq:queue:{{queue.name}}?requestTimeout=9000")
                .responseMessage();
    }
}
