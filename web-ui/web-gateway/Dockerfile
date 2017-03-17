FROM openjdk:8-jre

RUN mkdir /app
WORKDIR /app

ADD wait-for-it.sh /usr/local/bin/wait-for-it
RUN chmod +x /usr/local/bin/wait-for-it

ADD ./build/libs/web-gateway-0.0.1-SNAPSHOT.jar /app
RUN ln -sf web-gateway-0.0.1-SNAPSHOT.jar web-gateway-latest.jar

ADD start.sh /app/start
RUN chmod +x /app/start

CMD /app/start /app/web-gateway-latest.jar
