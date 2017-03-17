#!/usr/bin/env bash

#兼容jenkins
if [ -z ${WORKSPACE} ];then
   WORKSPACE=.
fi

cd ${WORKSPACE}/discovery-service && chmod +x ./gradlew && ./gradlew clean build && cd -
cd ${WORKSPACE}/fund-service && chmod +x ./gradlew && ./gradlew clean build && cd -
cd ${WORKSPACE}/inventory-service && chmod +x ./gradlew && ./gradlew clean build && cd -
cd ${WORKSPACE}/invest-service && chmod +x ./gradlew && ./gradlew clean build && cd -
cd ${WORKSPACE}/trade-service && chmod +x ./gradlew && ./gradlew clean build && cd -
cd ${WORKSPACE}/web-gateway && chmod +x ./gradlew && ./gradlew clean build && cd -
