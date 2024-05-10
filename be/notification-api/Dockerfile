FROM openjdk:21-jdk
ARG JAR_FILE=./build/libs/app-notification-api.jar
COPY ${JAR_FILE} ./app-notification-api.jar
CMD sh -c 'if [ -e /app-notification-api.jar ]; then java -jar /app-notification-api.jar; else echo "app-notification-api.jar not found, skipping execution"; fi'