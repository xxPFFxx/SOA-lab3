package com.soa.util;

import com.soa.controllers.HumanBeingWebServiceImpl;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.xml.ws.Endpoint;

@Singleton
@Startup
public class MainMethodBean {

    @PostConstruct
    public void main() {
        Endpoint.publish("http://localhost:8080/humanbeingservice",
                new HumanBeingWebServiceImpl());
    }

}