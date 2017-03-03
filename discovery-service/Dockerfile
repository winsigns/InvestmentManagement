FROM openjdk:8-jre

RUN mkdir /app
WORKDIR /app

ADD build/libs/discovery-service-0.0.1-SNAPSHOT.jar /app
RUN ln -sf discovery-service-0.0.1-SNAPSHOT.jar discovery-service-latest.jar

CMD java -Xmx128m -jar /app/discovery-service-latest.jar
