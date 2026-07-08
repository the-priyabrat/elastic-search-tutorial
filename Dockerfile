FROM maven:4.0.0-rc-5-eclipse-temurin-17 AS build

WORKDIR ./app

ADD pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests

#---------------------------------------------

FROM eclipse-temurin:17-jre

WORKDIR /app

ADD . /app

COPY --from=build /app/target/*.jar elastic-search-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "elastic-search-service-0.0.1-SNAPSHOT.jar"]
