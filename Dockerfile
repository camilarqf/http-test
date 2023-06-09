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
ADD src/main/resources/newrelic.jar /usr/local/tomcat/newrelic/newrelic.jar
ENV JAVA_OPTS="$JAVA_OPTS -javaagent:/usr/local/tomcat/newrelic/newrelic.jar"
ADD src/main/resources/newrelic.yml /usr/local/tomcat/newrelic/newrelic.yml
ENV NEW_RELIC_APP_NAME=NEW_RELIC_APP_NAME
ENV JAVA_OPTS="$JAVA_OPTS -Dnewrelic.config.app_name=$NEW_RELIC_APP_NAME"
ENV NEW_RELIC_LICENSE_KEY=NEW_RELIC_LICENSE_KEY
ENV JAVA_OPTS="$JAVA_OPTS -Dnewrelic.config.license_key=$NEW_RELIC_LICENSE_KEY"
CMD java -Dnewrelic.environment=$ENV -jar http.response-0.0.1-SNAPSHOT.jar
ENV NEW_RELIC_LOG_FILE_NAME="STDOUT"

ENTRYPOINT ["java","-javaagent:/usr/local/tomcat/newrelic/newrelic.jar","-jar","http.response-0.0.1-SNAPSHOT.jar"]
