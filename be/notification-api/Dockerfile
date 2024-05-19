FROM openjdk:21-jdk
ARG JAR_FILE=./build/libs/app-watching.jar
COPY ${JAR_FILE} ./app-watching.jar
CMD sh -c 'if [ -e /app-watching.jar ]; then java -jar /app-watching.jar; else echo "app-watching.jar not found, skipping execution"; fi'