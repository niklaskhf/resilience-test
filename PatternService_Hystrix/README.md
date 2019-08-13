## Build 
```
./mvnw install dockerfile:build 
```
## Push 
```
docker login
docker push resiliencefs/patternservice_hystrix 
```

## Run
```
docker run -p 8080:8080 -t resiliencefs/patternservice_hystrix
```


## Use
Request ```localhost:8080/circuitbreaker``` or ```localhost:8080/bulkhead``` in server.