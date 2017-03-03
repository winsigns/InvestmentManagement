FROM openjdk:8-jre

RUN mkdir /app
WORKDIR /app

ADD wait-for-it.sh /usr/local/bin/wait-for-it
RUN chmod +x /usr/local/bin/wait-for-it

ADD build/libs/inventory-service-0.0.1-SNAPSHOT.jar /app
RUN ln -sf inventory-service-0.0.1-SNAPSHOT.jar inventory-service-latest.jar
CMD java -Xmx128m -jar /app/inventory-service-latest.jar
