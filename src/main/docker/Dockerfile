
FROM openjdk:11 as build
WORKDIR /app
ADD . ./

FROM openjdk:11
WORKDIR /app
COPY maven/*.jar /app/
ENTRYPOINT ["java", "-cp", "*", "io.woolford.SnowplowStreams"]
