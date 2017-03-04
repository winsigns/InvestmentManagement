FROM openjdk:8-jre

RUN mkdir /app
WORKDIR /app

ADD wait-for-it.sh /usr/local/bin/wait-for-it
RUN chmod +x /usr/local/bin/wait-for-it

ADD build/libs/fund-service-0.0.1-SNAPSHOT.jar /app
RUN ln -sf fund-service-0.0.1-SNAPSHOT.jar fund-service-latest.jar

ADD start.sh /app/start
RUN chmod +x /app/start

