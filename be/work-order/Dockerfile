FROM openjdk:21-jdk
ARG JAR_FILE=./build/libs/app-wo.jar
COPY ${JAR_FILE} ./app-wo.jar
CMD sh -c 'if [ -e /app-wo.jar ]; then java -jar /app-wo.jar; else echo "app-wo.jar not found, skipping execution"; fi'