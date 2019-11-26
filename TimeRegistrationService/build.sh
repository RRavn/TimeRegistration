#!/bin/bash
#
DOCKER_IMAGE="timeregistration-service"

if [[ -n $1 ]]; then
    VERSION="$1"
elif [ -z "${VERSION}" ]; then
    VERSION="$(mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version | grep -v '[\[D]')"
fi

# Don't run if jenkins.
if [ -z "${JOB_NAME}" ]; then
   echo "-------------------------------------------------------------------------------------------------"
   echo "Building java application ..."
   mvn clean package -U
   if [ $? -gt 0 ]; then
       exit $?
   fi
fi

if [ "1" -eq "$(docker images ${DOCKER_IMAGE} | grep ${VERSION} | wc -l)" ]; then
    echo "-------------------------------------------------------------------------------------------------"
    echo "Deleting old image \"${DOCKER_IMAGE}:${VERSION}\" ..."
    docker rmi -f ${DOCKER_IMAGE}:${VERSION}
fi

echo "-------------------------------------------------------------------------------------------------"
echo "Building image \"${DOCKER_IMAGE}:${VERSION}\" ..."
docker build --pull --rm --no-cache -t ${DOCKER_IMAGE}:${VERSION} .
