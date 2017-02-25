FROM java:8

RUN mkdir /app
WORKDIR /app

ADD wait-for-it.sh /usr/local/bin/wait-for-it
RUN chmod +x /usr/local/bin/wait-for-it

ADD ./build/libs/edge-service-0.0.1-SNAPSHOT.jar /app
RUN ln -sf edge-service-0.0.1-SNAPSHOT.jar edge-service-latest.jar

CMD java -jar /app/edge-service-latest.jar