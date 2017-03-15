#!/usr/bin/env bash

cd discovery-service && ./gradlew clean build && cd -
cd sequence-service && ./gradlew clean build && cd -
