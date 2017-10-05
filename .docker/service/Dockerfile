FROM jdk:8u111
ARG VERSION
ARG ARTIFACT_NAME
COPY files/${ARTIFACT_NAME}-${VERSION}.jar  /app/app.jar
CMD [ "sh", "-c", "java -server -Xms256m -Xmx256m -XX:+UseG1GC -jar /app/app.jar"]
