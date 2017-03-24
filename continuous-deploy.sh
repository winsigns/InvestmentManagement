CURR_TAG=${COMMIT_BUILD_TYPE}${COMMIT_BUILD_NUMBER}

for name in discovery-service fund-service trade-service invest-service inventory-service web-gateway sequence-service
do
    docker rmi docker-registry:5000/${name}:latest
    docker tag docker-registry:5000/${name}:${CURR_TAG} docker-registry:5000/${name}:latest
done

cd ${COMMIT_WORKSPACE}
docker-compose -f infrastructure-cd.yml down
docker-compose -f infrastructure-cd.yml up --build -d

HOST_IP=$(ip route|awk '/default/ { print $3 }')

# wait for infrastructures startup complete. 
bash ./wait-for-it.sh -t 0 ${HOST_IP}:3306
sleep 30s

./createdb.sh ${HOST_IP}
./migratedb.sh ${HOST_IP}

docker-compose -f application-cd.yml down
docker-compose -f application-cd.yml up -d
