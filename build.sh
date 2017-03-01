#!/usr/bin/env bash

cd edge-service && chmod +x ./gradlew && ./gradlew clean build && cd -
cd discovery-service && chmod +x ./gradlew && ./gradlew clean build && cd -
cd fund-service && chmod +x ./gradlew && ./gradlew clean build && cd -
cd inventory-service && chmod +x ./gradlew && ./gradlew clean build && cd -
cd invest-service && chmod +x ./gradlew && ./gradlew clean build && cd -
cd trade-service && chmod +x ./gradlew && ./gradlew clean build && cd -
