FROM openjdk:11-jre-slim

ENV JAVA_ENV=PRODUCTION
ENV KUMULUZEE_ENV_NAME=prod
ENV KUMULUZEE_ENV_PROD=true

RUN mkdir /app
RUN mkdir /app/data
RUN mkdir /app/init

WORKDIR /app

ADD ./api/target/tasker.jar /app

EXPOSE 8080

CMD ["java", "-jar", "tasker.jar"]
