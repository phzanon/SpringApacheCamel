package com.apache.camel.example.camelexample.activeMQ;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class JsonQueueListenerRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("activemq:queue:{{queue.json}}")
                .to("log:JsonQueueListenerRoute");
    }
}
