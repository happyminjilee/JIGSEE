FROM openjdk:21-jdk
ARG JAR_FILE=./build/libs/app-member.jar
COPY ${JAR_FILE} ./app-member.jar
CMD sh -c 'if [ -e /app-member.jar ]; then java -jar /app-member.jar; else echo "app-member.jar not found, skipping execution"; fi'