package com.soa.secondservice.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationUtil {
    @Value("${consul.main-app-name}")
    private String mainAppName;

    @Autowired
    private DiscoveryClient discoveryClient;

    private ServiceInstance serviceInstance = null;

    public ServiceInstance getServiceInstance(){
        return discoveryClient.getInstances(mainAppName).stream().findFirst().get();
    }

    public String urlApiService(){
        if (serviceInstance == null){
            serviceInstance = getServiceInstance();
        }
        return "https://"+ serviceInstance.getHost()+":"+serviceInstance.getPort();
    }
}
