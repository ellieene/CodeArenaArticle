# Dockerfile

FROM eclipse-temurin:21

ARG JAR_FILE=target/CodeArenaArticle-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} CodeArenaArticle-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/CodeArenaArticle-0.0.1-SNAPSHOT.jar"]