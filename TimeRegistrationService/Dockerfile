FROM openjdk:11

RUN mkdir /opt/rr && mkdir /opt/rr/timeregistration-service

# Copying the service application into the docker image
COPY application/target/timeregistration-service-application.jar /opt/rr/timeregistration-service/.

WORKDIR /opt/rr/timeregistration-service/

RUN useradd -ms /bin/bash rr
RUN chown -R rr:rr /opt/rr

USER rr

EXPOSE 8080

CMD java -jar timeregistration-service-application.jar
