#this is configuration how to get java version
#image and how to run the image in the container
#this image has linux lightweight os on which java is installed!

FROM openjdk:17-jdk-alpine

RUN mkdir /config

COPY target/*.jar /app.jar

CMD ["java", "-jar", "app.jar"]