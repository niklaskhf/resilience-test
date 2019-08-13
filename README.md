# How To 

## Run the experiment
```
chmod -x experiment.sh
./experiment.sh
```

## docker-compose
- build services individually
- push docker images of services to registry
- Start: 
```
docker-compose pull
docker-compose build
docker-compose up (-d)
```
- End:
```
docker-compose down
```
## Locust:
```
localhost:8089
```

## Prometheus:
```
localhost:9090
```

## Grafana:
```
localhost:3000
```

Example query: ```http_server_request_duration_seconds_sum{path="/circuitbreaker"} or http_server_requests_seconds_sum{uri="/circuitbreaker"} or http_sever_requests_duration_seconds_sum{uri="/circuitbreaker"}```



# New Service
1. Write service
2. Add prometheus
3. Dockerize
4. Add to locustfile.py
5. Update locust docker
6. Add to prometheus.yml 
7. Update prometheus docker
8. Add to docker-compose