package com.apache.camel.example.camelexample.activeMQ;

import com.apache.camel.example.camelexample.model.CamelModelRequest;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ActiveMQRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("activemq:queue:teste")
                .log("Received Message from queue: ${body}")
                .filter().ognl("request.body.code == '1234'")
                .process(exchange -> {
                    var body = exchange.getIn().getBody();
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        log.info("Trying to convert body in model Request");
                        CamelModelRequest request = mapper.convertValue(body, CamelModelRequest.class);
                        log.info("Convert successfully: " + request);
                    } catch (Exception e) {
                        log.error("Error to convert: " + e.getMessage());
                    }
                });
    }
}
