#!/usr/bin/env bash

MYSQL_IP_ADDR=$1
if [ ! $MYSQL_IP_ADDR ]; then
    MYSQL_IP_ADDR="127.0.0.1"
fi

export MYSQL_IP_ADDR

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
