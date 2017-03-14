FROM openjdk:8-jre

RUN mkdir /app
WORKDIR /app

ADD discovery-service-0.0.1-SNAPSHOT.jar /app
RUN ln -sf discovery-service-0.0.1-SNAPSHOT.jar discovery-service-latest.jar

ADD start.sh /app/start
RUN chmod +x /app/start

CMD /app/start /app/discovery-service-latest.jar
