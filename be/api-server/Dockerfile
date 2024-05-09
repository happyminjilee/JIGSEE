FROM openjdk:21-jdk
ARG JAR_FILE=./build/libs/app.jar
COPY ${JAR_FILE} ./app.jar
CMD sh -c 'if [ -e /app.jar ]; then java -jar /app.jar; else echo "app.jar not found, skipping execution"; fi'