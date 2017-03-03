FROM openjdk:8-jre

RUN mkdir /app
WORKDIR /app

ADD wait-for-it.sh /usr/local/bin/wait-for-it
RUN chmod +x /usr/local/bin/wait-for-it

ADD ./build/libs/edge-service-0.0.1-SNAPSHOT.jar /app
RUN ln -sf edge-service-0.0.1-SNAPSHOT.jar edge-service-latest.jar

CMD java -Xmx128m -jar /app/edge-service-latest.jar
