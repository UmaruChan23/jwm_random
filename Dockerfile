FROM gradle:7.2-jdk-alpine AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle $APP_HOME

COPY gradle $APP_HOME/gradle
COPY --chown=gradle:gradle . /home/gradle/src
USER root
RUN chown -R gradle /home/gradle/src

COPY . .
RUN gradle clean build

# actual container
FROM openjdk:17-oracle
ENV ARTIFACT_NAME=jwm_random_ctf-0.0.1-SNAPSHOT.war
ENV APP_HOME=/usr/app

WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .

RUN echo "bsc{Wh3Re_1S_mY_M0n3Y}" > /flag.txt
RUN chmod 444 /flag.txt

EXPOSE 8080
ENTRYPOINT exec java -jar ${ARTIFACT_NAME}