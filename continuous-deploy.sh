CURR_TAG=${COMMIT_BUILD_TYPE}${COMMIT_BUILD_NUMBER}

for name in "discovery-service fund-service trade-service invest-service inventory-service web-gateway sequence-service"
do
    docker tag docker-registry:5000/${name}:${CURR_TAG} docker-registry:5000:latest
done

cd ${COMMIT_WORKSPACE}
docker-compose -f infrastructure-cd.yml up -d
sleep 10
./createdb.sh
./migratedb.sh
docker-compose -f application-cd.yml up -d
