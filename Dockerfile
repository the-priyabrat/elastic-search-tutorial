FROM amazoncorretto:19.0.2-alpine3.16
RUN apk add openssl=1.1.1w-r1
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} payzo-oci-service.jar
ENTRYPOINT ["java","-jar","/payzo-oci-service.jar"]