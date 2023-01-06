FROM openjdk:17
ADD target/*.jar projet4.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/projet4.jar"]
VOLUME /main-app
LABEL key="groupeprojet-4-2022"