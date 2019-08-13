package com.example.PatternService;
 
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
@Service
public class PatternServiceDelegate {
 
    @Autowired
    RestTemplate restTemplate;
     
    public PatternServiceDelegate() {
        System.out.println("Rules loaded in delegate");

        List<DegradeRule> rules = new ArrayList<DegradeRule>();
        DegradeRule rule = new DegradeRule();
        rule.setResource("callCircuitBreaker");
        // set threshold 
        rule.setCount(0.5);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO);
        rule.setTimeWindow(10);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
        

    }

    @SentinelResource(value = "callCircuitBreaker", fallback = "callCircuitBreaker_Fallback")
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
    public String callCircuitBreaker_Fallback() {
 
        System.out.println("FaultService is not responding! Fallback");
        try{
            Thread.sleep(200);
        }catch(Exception e){

        }
        return "CIRCUIT BREAKER ENABLED!!! - " + new Date();
    }
 
    @SentinelResource(fallback = "callCircuitBreaker_Fallback")
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