package com.apache.camel.example.camelexample.activeMQ;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("activemq:queue:teste")
                .log("Received Message from queue: ${body}");
    }
}
