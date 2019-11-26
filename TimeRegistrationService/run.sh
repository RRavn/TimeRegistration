#!/bin/bash
DOCKER_IMAGE="timeregistration-service"
DOCKER_CONTAINER_NAME="timeregistration-service-test"

ROOTDIR="$(pwd)"
POM="${ROOTDIR}/pom.xml"
VERSION="$(mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version -f ${POM} | grep -v '\[')"
DOCKER_IMAGE="${DOCKER_IMAGE}:${VERSION}"

clear
if [ "1" -eq "$(docker ps -a | grep ${DOCKER_CONTAINER_NAME} | wc -l)" ]; then
    echo "-------------------------------------------------------------------------------------------------"
    echo "Deleting old container \"${DOCKER_CONTAINER_NAME}\" ..."
    docker rm -f ${DOCKER_CONTAINER_NAME}
fi

echo "-------------------------------------------------------------------------------------------------"

echo "Starting Docker container \"${DOCKER_CONTAINER_NAME}\" with image \"${DOCKER_IMAGE}\" ..."
docker run -it --rm -e "SPRING_PROFILES_ACTIVE=local" --publish=8080:8080 --name=${DOCKER_CONTAINER_NAME} --hostname=${DOCKER_CONTAINER_NAME} ${DOCKER_IMAGE}
