FROM maven:3.5.2-jdk-8-alpine as MAVEN_DIR
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
ENV SPRING_PROFILES_ACTIVE=SPRING_PROFILES_ACTIVE
RUN mvn -Dmaven.test.skip=true -P $SPRING_PROFILES_ACTIVE package

# Copiar o agente do New Relic
COPY src/main/resources/newrelic.yml /tmp/target/dependency/newrelic.yml
RUN mvn org.apache.maven.plugins:maven-dependency-plugin:3.1.2:copy-dependencies \
    -DoutputDirectory=/tmp/target/dependency \
    -DincludeGroupIds=com.newrelic.agent.java \
    -DincludeArtifactIds=newrelic-agent \
    -DstripVersion=true

#Processo de deploy do jar
FROM openjdk:8-jdk-alpine
COPY --from=MAVEN_DIR tmp/target/http.response-0.0.1-SNAPSHOT.jar http.response-0.0.1-SNAPSHOT.jar

#Configurando New Relic
COPY --from=MAVEN_DIR /tmp/target/dependency/newrelic-agent.jar /app/newrelic/newrelic-agent.jar
COPY --from=MAVEN_DIR /tmp/target/dependency/newrelic.yml /app/newrelic/newrelic.yml

# Adicionando Healthcheck
RUN apk --no-cache add curl
HEALTHCHECK --interval=20s --timeout=30s --retries=3 \
  CMD curl -f http://localhost:8081/healthcheck || exit 1

ENTRYPOINT ["java","-javaagent:/app/newrelic/newrelic-agent.jar","-jar","http.response-0.0.1-SNAPSHOT.jar"]
#ENTRYPOINT ["java", "-jar","http.response-0.0.1-SNAPSHOT.jar"]

