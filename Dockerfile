FROM adoptopenjdk/openjdk11:alpine-slim  AS build
RUN mkdir /opt/app
WORKDIR /opt/app
COPY . .
RUN ls
RUN ./gradlew clean bootJar --no-daemon
RUN ls ./build/libs

FROM adoptopenjdk/openjdk11:alpine-slim
COPY --from=build /opt/app/build/libs/car-pooling-challenge-yair.kukielka-0.0.1-SNAPSHOT.jar /opt/app/app.jar
RUN ls /opt/app
EXPOSE 9091
ENTRYPOINT ["java","-jar","/opt/app/app.jar"]

# FROM alpine:3.8
#
# # This Dockerfile is optimized for go binaries, change it as much as necessary
# # for your language of choice.
#
# RUN apk --no-cache add ca-certificates=20190108-r0 libc6-compat=1.1.19-r10
#
# EXPOSE 9091
#
# COPY car-pooling-challenge /
#
# ENTRYPOINT [ "/car-pooling-challenge" ]
