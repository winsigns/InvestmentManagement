FROM opoenjdk:8

RUN mkdir /app
WORKDIR /app

ADD build/libs/discovery-service-0.0.1-SNAPSHOT.jar /app
RUN ln -sf discovery-service-0.0.1-SNAPSHOT.jar discovery-service-latest.jar

CMD java -jar /app/discovery-service-latest.jar