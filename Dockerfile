FROM openjdk:11-jre-slim

ENV JAVA_ENV=PRODUCTION
ENV KUMULUZEE_ENV_NAME=prod
ENV KUMULUZEE_ENV_PROD=true

ENV CLIENT_KEYCLOAK_CONFIG-DIR=classes/webapp/config

RUN mkdir /app
RUN mkdir /app/data
# create symlink to access configuration for angular project
RUN ln -s /app/classes/webapp/assets/config /ng-config
RUN ln -s /app/classes/webapp /ng-root

WORKDIR /app

ADD ./api/target/ /app

EXPOSE 8080

CMD ["java", "-server", "-cp", "classes:dependency/*", "com.kumuluz.ee.EeApplication"]
