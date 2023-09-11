FROM azul/zulu-openjdk:17-latest
MAINTAINER paradoxhorizondevs.com
COPY target/core-0.0.1-SNAPSHOT.jar core-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/core-0.0.1-SNAPSHOT.jar"]