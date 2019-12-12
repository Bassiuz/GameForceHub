# GameForceHub
The digital hub for everything GameForce!

#How to run

Run a local database to test

```
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 --name quarkus_test -e POSTGRES_USER=quarkus_test -e POSTGRES_PASSWORD=quarkus_test -e POSTGRES_DB=quarkus_test -p 5432:5432 postgres:11.5
```

How to deploy localy
```
mvn compile quarkus:dev 
```
