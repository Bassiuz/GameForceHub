FROM openjdk:11
WORKDIR /
ADD target/gameforce-hub-0.1-runner.jar gameforce-hub-0.1-runner.jar
ADD target/lib lib
ENV OTHER_BACKEND_URL="https://gameforce-hub.herokuapp.com/"
EXPOSE 8000
CMD ["java", "-jar", "gameforce-hub-0.1-runner.jar"]