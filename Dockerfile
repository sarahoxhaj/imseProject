#WORKDIR /app
#COPY ./target/imse-0.0.1/SNAPSHOT.jar /app
#
#EXPOSE 8080
#
#CMD ["java", "-jar", "imse-0.0.1-SNAPSHOT.jar"]


FROM openjdk:17
VOLUME /tmp
LABEL maintainer ="howtodoinjava"
COPY target/imse-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

