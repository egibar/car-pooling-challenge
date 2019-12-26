FROM adoptopenjdk/openjdk11:alpine-slim
RUN mkdir /opt/app
COPY build/libs/car-pooling-challenge-yair.kukielka-0.0.1-SNAPSHOT.jar /opt/app/app.jar
RUN ls /opt/app
EXPOSE 9091
ENTRYPOINT ["java","-jar","/opt/app/app.jar"]
