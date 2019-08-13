## Build 
```
./mvnw install dockerfile:build 
```
## Push 
```
docker login
docker push resiliencefs/faultservice 
```

## Run
```
docker run -p 8090:8090 -t resiliencefs/faultservice
```

## Commands for chaos monkey
[DOCS](https://codecentric.github.io/chaos-monkey-spring-boot/2.0.1/#_http_endpoint)
```
localhost:8090/actuator/chaosmonkey/enable # POST enable chaos monkey
localhost:8090/actuator/chaosmonkey/disable # POST disable chaos monkey 
localhost:8090/actuator/chaosmonkey/assaults # GET run assault config
localhost:8090/actuator/chaosmonkey/assaults # POST set new assault config
```
