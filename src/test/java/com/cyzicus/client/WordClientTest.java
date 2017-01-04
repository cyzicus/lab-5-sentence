package com.cyzicus.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WordClientTest {

    @Mock
    RestTemplate restTemplate;

    @Mock
    LoadBalancerClient loadBalancerClient;

    @Mock
    ServiceInstance serviceInstance;

    URI uri = URI.create("url");

    WordClient wordClient;


    @Before
    public void setup() throws Exception {
        wordClient = new WordClient(restTemplate, loadBalancerClient);
    }

    @Test
    public void getWord_returnsValueFromRestTemplate() throws Exception {
        when(loadBalancerClient.choose(anyString())).thenReturn(serviceInstance);
        when(serviceInstance.getUri()).thenReturn(uri);
        when(restTemplate.getForObject(uri, String.class)).thenReturn("Fred");
        String result = wordClient.getWord("ServiceName");
        Assert.assertEquals("Fred", result);
        verify(loadBalancerClient).choose(eq("ServiceName"));
        verifyNoMoreInteractions(loadBalancerClient);
        verify(serviceInstance).getUri();
        verifyNoMoreInteractions(serviceInstance);
        verify(restTemplate).getForObject(uri, String.class);
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void getWord_returnsNull_whenNoInstances() throws Exception {
        when(loadBalancerClient.choose(anyString())).thenReturn(null);
        String result = wordClient.getWord("AnotherService");
        Assert.assertNull(result);
        verify(loadBalancerClient).choose(eq("AnotherService"));
        verifyNoMoreInteractions(loadBalancerClient);
        verifyZeroInteractions(serviceInstance);
        verifyZeroInteractions(restTemplate);
    }

    @Test
    public void getWord_returnsNull_whenUriIsNull() throws Exception {
        when(loadBalancerClient.choose(anyString())).thenReturn(serviceInstance);
        when(serviceInstance.getUri()).thenReturn(null);
        Assert.assertNull(wordClient.getWord("ServiceName"));
        verify(serviceInstance).getUri();
        verifyNoMoreInteractions(serviceInstance);
        verifyZeroInteractions(restTemplate);
    }

}