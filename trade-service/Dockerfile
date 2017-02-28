FROM java:8

RUN mkdir /app
WORKDIR /app

ADD wait-for-it.sh /usr/local/bin/wait-for-it
RUN chmod +x /usr/local/bin/wait-for-it

ADD build/libs/trade-service-0.0.1-SNAPSHOT.jar /app
RUN ln -sf trade-service-0.0.1-SNAPSHOT.jar trade-service-latest.jar
CMD java -jar /app/trade-service-latest.jar