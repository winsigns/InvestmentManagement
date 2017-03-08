#!/usr/bin/env bash

./build-applications.sh

docker-compose -f ./application.yml up -d --build
