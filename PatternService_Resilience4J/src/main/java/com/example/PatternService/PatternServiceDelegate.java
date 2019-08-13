package com.example.PatternService;
 
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import static io.github.resilience4j.bulkhead.annotation.Bulkhead.*;


@Service
public class PatternServiceDelegate {
 
    @Autowired
    RestTemplate restTemplate;
     
    @CircuitBreaker(name = "backendA", fallbackMethod = "callCircuitBreaker_Fallback")
    public String callCircuitBreaker() {
 
        System.out.println("callCircuitBreaker called!");
        String response = restTemplate
                .exchange("http://faultservice:8090/greeting"
                , HttpMethod.GET
                , null
                , new ParameterizedTypeReference<String>() {
            }).getBody();
 
        System.out.println("Response Received as " + response + " -  " + new Date());
        try{
            Thread.sleep(5000);
        }catch(Exception e){

        }
        return "HEALTHY:" + response + " -  " + new Date();
    }
     
    @SuppressWarnings("unused")
    public String callCircuitBreaker_Fallback(Exception e) {
 
        System.out.println("FaultService is not responding! Fallback");
        try{
            Thread.sleep(200);
        }catch(Exception ex){

        }
        return "CIRCUIT BREAKER ENABLED!!! - " + new Date();
    }
 

    @Bulkhead(name = "backendA")
    public String callBulkhead() {
 
 
        String response = restTemplate
                .exchange("http://faultservice:8090/greeting"
                , HttpMethod.GET
                , null
                , new ParameterizedTypeReference<String>() {
            }).getBody();
 
        System.out.println("Response Received as " + response + " -  " + new Date());
 
        return "HEALTHY:" + response + " -  " + new Date();
    }
    

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}