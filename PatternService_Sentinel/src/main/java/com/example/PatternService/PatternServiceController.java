package com.example.PatternService;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
public class PatternServiceController {
    @Autowired
    PatternServiceDelegate patternServiceDelegate;
 
    @RequestMapping(value = "/circuitbreaker", method = RequestMethod.GET)
    public String getCircuitBreaker() {
        return patternServiceDelegate.callCircuitBreaker();
    }
 
    @RequestMapping(value = "/bulkhead", method = RequestMethod.GET)
    public String getBulkhead() {
        return patternServiceDelegate.callBulkhead();
    }
}