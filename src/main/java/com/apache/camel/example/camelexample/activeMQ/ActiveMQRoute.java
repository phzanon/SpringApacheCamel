package com.apache.camel.example.camelexample.activeMQ;

import com.apache.camel.example.camelexample.model.CamelModelRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jacksonxml.JacksonXMLDataFormat;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ActiveMQRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        JacksonXMLDataFormat jacksonDataFormat = new JacksonXMLDataFormat();
        jacksonDataFormat.setPrettyPrint(true);
        jacksonDataFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

        from("activemq:queue:teste")
                .log("Received Message from queue: ${body}")
                .choice()
                    .when().ognl("request.body.code == 'xml' && request.body.messageFilter == 'xyz'")
                        .log("Sending to XML Queue")
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
                        })
                        .marshal(jacksonDataFormat)
                        .to("activemq:queue:xml")
                        .unmarshal(jacksonDataFormat)
                    .when().ognl("request.body.code == 'json' && request.body.messageFilter == 'xyz'")
                        .log("Sending to JSON Queue")
                        .to("activemq:queue:json");
    }
}
