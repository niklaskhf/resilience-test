## Build 
```
./mvnw install dockerfile:build 
```
## Push 
```
docker login
docker push resiliencefs/patternservice_resilience4j
```

## Run
```
docker run -p 8080:8080 -t resiliencefs/patternservice_resilience4j
```


## Use
Request ```localhost:8081/circuitbreaker``` or ```localhost:8081/bulkhead``` in server.