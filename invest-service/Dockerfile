FROM openjdk:8-jre

RUN mkdir /app
WORKDIR /app

ADD wait-for-it.sh /usr/local/bin/wait-for-it
RUN chmod +x /usr/local/bin/wait-for-it

ADD build/libs/invest-service-0.0.1-SNAPSHOT.jar /app
RUN ln -sf invest-service-0.0.1-SNAPSHOT.jar invest-service-latest.jar

ADD start.sh /app/start
RUN chmod +x /app/start

CMD /app/start /app/invest-service-latest.jar
