# Build 
```
docker build -t resiliencefs/patternservice_semian .
```

# Push 
```
docker login
docker push resiliencefs/patternservice_semian 
```

# Run
```
docker run -p 8082:8082 resiliencefs/patternservice_semian
```

