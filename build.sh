#!/usr/bin/env bash

cd edge-service && ./gradlew clean build && cd -
cd fund-service && ./gradlew clean build && cd -
cd inventory-service && ./gradlew clean build && cd -