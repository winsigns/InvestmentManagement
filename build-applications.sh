#!/usr/bin/env bash

cd edge-service && ./gradlew dev clean build && cd -
cd fund-service && ./gradlew dev clean build && cd -
cd inventory-service && ./gradlew dev clean build && cd -
cd invest-service && ./gradlew dev clean build && cd -
cd trade-service && ./gradlew dev clean build && cd -
