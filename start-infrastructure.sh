#!/usr/bin/env bash

./build-infrastructure.sh

docker-compose -f ./infrastructure.yml up -d --build
