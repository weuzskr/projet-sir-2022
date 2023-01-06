FROM openjdk:17-jdk-alpine
ADD target/*.jar appprojet.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/appprojet.jar"]
VOLUME /main-app
LABEL key="groupeprojet2022"

# java -jar app.jar
