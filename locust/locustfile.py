from locust import HttpLocust, TaskSet, task



class MyTaskSet(TaskSet):
    @task
    def hystrix_cb(self):
        self.client.get("http://hystrix:8080/circuitbreaker", name="hystrix/circuitbreaker")

    @task
    def resilience4j_cb(self):
        self.client.get("http://resilience4j:8081/circuitbreaker", name="resilience4j/circuitbreaker")

    @task
    def semian_cb(self):
        self.client.get("http://semian:8082/circuitbreaker", name="semian/circuitbreaker")
        
    @task
    def polly_cb(self):
        self.client.get("http://polly:8084/api/circuitbreaker", name="polly/circuitbreaker")

    @task
    def sentinel_cb(self):
        self.client.get("http://sentinel:8083/circuitbreaker", name="sentinel/circuitbreaker")


class WebsiteUser(HttpLocust):
    host = "localhost"
    task_set = MyTaskSet
    min_wait = 100
    max_wait = 2000