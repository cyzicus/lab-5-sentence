package com.cyzicus.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Component
public class WordClient {

    private final RestTemplate restTemplate;
    private final LoadBalancerClient loadBalancerClient;

    @Autowired
    public WordClient(RestTemplate restTemplate, LoadBalancerClient loadBalancerClient) {
        this.restTemplate = restTemplate;
        this.loadBalancerClient = loadBalancerClient;
    }

    public String getWord(String serviceName) {
        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceName);
        if (serviceInstance != null) {
            URI uri = serviceInstance.getUri();
            if (uri != null) {
                return (restTemplate.getForObject(uri, String.class));
            }
        }
        return null;
    }
}
