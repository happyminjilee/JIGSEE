FROM openjdk:21-jdk
ARG JAR_FILE=./build/libs/app-jig.jar
COPY ${JAR_FILE} ./app-jig.jar
CMD sh -c 'if [ -e /app-jig.jar ]; then java -jar /app-jig.jar; else echo "app-jig.jar not found, skipping execution"; fi'