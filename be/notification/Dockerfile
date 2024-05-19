FROM openjdk:21-jdk
ARG JAR_FILE=./build/libs/app-notification.jar
COPY ${JAR_FILE} ./app-notification.jar
CMD sh -c 'if [ -e /app-notification.jar ]; then java -jar /app-notification.jar; else echo "app-notification.jar not found, skipping execution"; fi'