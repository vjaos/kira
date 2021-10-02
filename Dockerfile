FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /app
COPY build/libs/*.jar /app/kira.jar
ENTRYPOINT ["java","-jar","/app/kira.jar"]
