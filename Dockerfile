FROM amazoncorretto:21-alpine

WORKDIR /app

COPY rest/target/rest-0.0.1-SNAPSHOT.jar /app/comments.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/comments.jar"]


#docker file for image creation