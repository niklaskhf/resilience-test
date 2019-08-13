package com.example.PatternService;
 
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.HystrixCommandProperties;
 
@Service
public class PatternServiceDelegate {
 
    @Autowired
    RestTemplate restTemplate;
    

    @HystrixCommand(fallbackMethod = "callCircuitBreaker_Fallback")
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
    private String callCircuitBreaker_Fallback() {
 
        System.out.println("FaultService is not responding! Fallback");
        try{
            Thread.sleep(200);
        }catch(Exception e){

        }
        return "CIRCUIT BREAKER ENABLED!!! - " + new Date();
    }
 
    @HystrixCommand(fallbackMethod = "callCircuitBreaker_Fallback")
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