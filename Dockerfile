FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /usr/local/src/triforce-blitz
COPY pom.xml /usr/local/src/triforce-blitz/pom.xml
COPY ./src /usr/local/src/triforce-blitz/src

RUN mvn clean install
RUN java -Djarmode=layertools -jar target/*.jar extract

FROM debian:12 AS environment

# Set up Temurik APT repository
RUN apt-get update -y && apt-get install -y wget apt-transport-https gpg
RUN wget -qO - https://packages.adoptium.net/artifactory/api/gpg/key/public | gpg --dearmor | tee /etc/apt/trusted.gpg.d/adoptium.gpg > /dev/null
RUN echo "deb https://packages.adoptium.net/artifactory/deb $(awk -F= '/^VERSION_CODENAME/{print$2}' /etc/os-release) main" | tee /etc/apt/sources.list.d/adoptium.list

# Install dependencies
RUN apt-get update -y && apt-get install -y curl ca-certificates temurin-21-jre python3

FROM environment
RUN useradd --shell /bin/bash triforceblitz

WORKDIR /opt/triforce-blitz
USER triforceblitz:triforceblitz
COPY --from=builder /usr/local/src/triforce-blitz/dependencies/ ./
COPY --from=builder /usr/local/src/triforce-blitz/snapshot-dependencies/ ./
COPY --from=builder /usr/local/src/triforce-blitz/spring-boot-loader/ ./
COPY --from=builder /usr/local/src/triforce-blitz/application/ ./

EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
