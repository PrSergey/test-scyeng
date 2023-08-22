FROM amazoncorretto:17-alpine-jdk
COPY ./target/skyeng-test-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]