FROM java:8
WORKDIR /
ADD gameforce-hub-0.1-runner.jar gameforce-hub-0.1-runner.jar
ADD lib lib
EXPOSE 8080
CMD ["java", "-jar", "gameforce-hub-0.1-runner.jar"]