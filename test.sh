CURR_TAG=${COMMIT_BUILD_TYPE}${COMMIT_BUILD_NUMBER}

for name in discovery-service fund-service trade-service invest-service inventory-service web-gateway sequence-service
do
    echo "docker tag docker-registry:5000/${name}:${CURR_TAG} docker-registry:5000/${name}:latest"
done

