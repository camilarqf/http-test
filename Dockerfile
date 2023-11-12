FROM maven:3.5.2-jdk-8-alpine as MAVEN_DIR
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
ENV SPRING_PROFILES_ACTIVE=SPRING_PROFILES_ACTIVE
RUN mvn -Dmaven.test.skip=true -P $SPRING_PROFILES_ACTIVE package

#Processo de deploy do jar
FROM openjdk:8-jdk-alpine
COPY --from=MAVEN_DIR tmp/target/http.response-0.0.1-SNAPSHOT.jar http.response-0.0.1-SNAPSHOT.jar

#Configurando New Relic
RUN mkdir -p /usr/local/tomcat/newrelic
ADD .newrelic/newrelic.jar /usr/local/tomcat/newrelic/newrelic.jar
ENV JAVA_OPTS="$JAVA_OPTS -javaagent:/usr/local/tomcat/newrelic/newrelic.jar"
ADD .newrelic/newrelic.yml /usr/local/tomcat/newrelic/newrelic.yml
ENV NEW_RELIC_APP_NAME=NEW_RELIC_APP_NAME
ENV JAVA_OPTS="$JAVA_OPTS -Dnewrelic.config.app_name=$NEW_RELIC_APP_NAME"
ENV NEW_RELIC_LICENSE_KEY=NEW_RELIC_LICENSE_KEY
ENV JAVA_OPTS="$JAVA_OPTS -Dnewrelic.config.license_key=$NEW_RELIC_LICENSE_KEY"
CMD java -Dnewrelic.environment=$ENV -jar /novoGerenciador.jar
ENV NEW_RELIC_LOG_FILE_NAME="STDOUT"

# Adicionando Healthcheck
RUN apk add --no-cache curl
HEALTHCHECK --interval=30s --timeout=10s --retries=6 --start-period=1m \
  CMD curl -f http://localhost:8081/healthcheck || exit 1

ENTRYPOINT ["java","-javaagent:/usr/local/tomcat/newrelic/newrelic.jar","-jar","http.response-0.0.1-SNAPSHOT.jar"]
#ENTRYPOINT ["java", "-jar","http.response-0.0.1-SNAPSHOT.jar"]
#ENTRYPOINT ["sh", "-c", "java -jar /http.response-0.0.1-SNAPSHOT.jar & sleep 60 && java -javaagent:/usr/local/tomcat/newrelic/newrelic.jar -jar /http.response-0.0.1-SNAPSHOT.jar"]
