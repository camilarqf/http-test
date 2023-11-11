#!/bin/bash

# Iniciar a aplicação Java
java -jar /http.response-0.0.1-SNAPSHOT.jar &

# Aguardar um tempo para a aplicação inicializar (ajuste conforme necessário)
sleep 60

# Iniciar o agente do New Relic
java -javaagent:/usr/local/tomcat/newrelic/newrelic.jar

# Mantém o contêiner rodando após a execução do script
tail -f /dev/null
