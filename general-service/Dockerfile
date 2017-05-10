FROM openjdk:8-jre

RUN mkdir /app
WORKDIR /app

ADD wait-for-it.sh /usr/local/bin/wait-for-it
RUN chmod +x /usr/local/bin/wait-for-it

ADD build/libs/general-service-0.0.1-SNAPSHOT.jar /app
RUN ln -sf general-service-0.0.1-SNAPSHOT.jar general-service-latest.jar

ADD start.sh /app/start
RUN chmod +x /app/start

CMD /app/start /app/sequence-service-latest.jar
