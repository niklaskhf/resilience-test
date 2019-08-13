FROM python:3.7.3

RUN pip install locustio 
ADD locustfile.py locustfile.py

COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

EXPOSE 8089 5557 5558
ENTRYPOINT [ "/bin/bash", "-c" ]
CMD ["./wait-for-it.sh hystrix:8080 --strict --timeout=120 -- locust -f locustfile.py --no-web -c 20 -r 10"]
