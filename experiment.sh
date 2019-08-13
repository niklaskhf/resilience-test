#!/bin/bash

# start services
docker-compose -f docker-compose.yml build 
docker-compose -f docker-compose.yml pull 
docker-compose -f docker-compose.yml up -d 

date
echo "Waiting for services to start"
sleep 120s
echo "Services should be ready"
date
echo "Capturing steady state"
sleep 60s



date
echo "Injecting a fault"
curl -X POST http://localhost:8090/actuator/chaosmonkey/enable 
sleep 60s
echo "Removing the fault"
curl -X POST http://localhost:8090/actuator/chaosmonkey/disable 
date
echo "Returning to steady state"
sleep 120s
docker-compose -f docker-compose.yml rm -f -s -v locust
date
echo "Capture metrics now!"
read -p "Press any key to continue ..."




docker-compose -f docker-compose.yml down

echo "Experiment over!"