#!/usr/bin/env bash

cd fund-service
./gradlew flywayMigrate
cd -

cd inventory-service
./gradlew flywayMigrate
cd -

cd invest-service
./gradlew flywayMigrate
cd -

cd trade-service
./gradlew flywayMigrate
cd -
