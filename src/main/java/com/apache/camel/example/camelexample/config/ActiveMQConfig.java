package com.apache.camel.example.camelexample.config;

import org.apache.camel.CamelContext;
import org.apache.camel.component.activemq.ActiveMQComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ActiveMQConfig {

    @Autowired
    private CamelContext context;

    @PostConstruct
    public void init() throws Exception {
        context.addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://localhost:61616"));
    }
}
