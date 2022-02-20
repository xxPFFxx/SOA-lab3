package com.soa.util;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.NotRegisteredException;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Singleton
@Startup
public class ConsulUtil {
    private AgentClient agentClient = null;
    private String service_id = "1";
    private Integer port = 51510;
    private String name = null;
    private Long ttl = 3L;

    @PostConstruct
    public void register() {
        try {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("consul.properties");
        Properties properties = new Properties();
        properties.load(input);
        port = Integer.parseInt(properties.getProperty("consul.port"));
        name = properties.getProperty("consul.name");
        service_id = properties.getProperty("consul.service_id");
        ttl = Long.parseLong(properties.getProperty("consul.ttl"));

            Consul consul = Consul.builder().build();
            agentClient = consul.agentClient();
            agentClient.register(ImmutableRegistration.builder()
                    .id(service_id)
                    .name(name)
                    .port(port)
                    .check(Registration.RegCheck.ttl(ttl))
                    .build());
            System.out.println("Consul registered");
        }catch (Exception e){
            System.out.println("Error trying to connect to Consul");
        }
    }
    @Schedule(hour = "*", minute = "*", second = "*/20")
    public void checkIn() throws NotRegisteredException {
        agentClient.pass(service_id);
    }
}
