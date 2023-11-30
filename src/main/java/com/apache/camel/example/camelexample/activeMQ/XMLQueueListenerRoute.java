package com.apache.camel.example.camelexample.activeMQ;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class XMLQueueListenerRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("activemq:queue:xml")
                .to("log:XMLQueueListenerRoute");
    }
}
