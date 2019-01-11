FROM openjdk:11-jre
COPY sports-betting-application-app/target/sports-betting-application-jar-with-dependencies.jar .
CMD ["java", "-jar", "sports-betting-application-jar-with-dependencies.jar"]
