## Build 
```
./mvnw install dockerfile:build 
```
## Push 
```
docker login
docker push resiliencefs/patternservice_sentinel
```

## Run
```
docker run -p 8080:8080 -t resiliencefs/patternservice_sentinel
```


## Use
Request ```localhost:8083/circuitbreaker``` or ```localhost:8083/bulkhead``` in server.