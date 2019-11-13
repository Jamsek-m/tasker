FROM openjdk:11-jre-slim

ENV JAVA_ENV=PRODUCTION
ENV KUMULUZEE_ENV_NAME=prod
ENV KUMULUZEE_ENV_PROD=true

ENV CLIENT_KEYCLOAK_CONFIG-DIR=classes/webapp/config

ENV KC_REALM=not_set
ENV KC_CLIENT-ID=not_set
ENV KC_AUTH-SERVER-URL=not_set
ENV CLIENT_KEYCLOAK_REALM=not_set
ENV CLIENT_KEYCLOAK_CLIENT-ID=not_set
ENV CLIENT_KEYCLOAK_AUTH-SERVER-URL=not_set

RUN mkdir /app
RUN mkdir /app/data

WORKDIR /app

ADD ./api/target/ /app

EXPOSE 8080

CMD ["java", "-server", "-cp", "classes:dependency/*", "com.kumuluz.ee.EeApplication"]
