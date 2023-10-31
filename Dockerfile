FROM openjdk:11
COPY target/dvirh-chatBot*.jar /usr/src/dvirh-chatBot.jar
COPY src/main/resources/application.properties /opt/conf/application.properties
CMD ["java", "-jar", "/usr/src/dvirh-chatBot.jar", "--spring.config.location=file:/opt/conf/application.properties"]

