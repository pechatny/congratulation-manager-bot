FROM openjdk:17-oracle AS build
ARG TOKEN
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN --mount=type=cache,target=/root/.m2 ./mvnw -f $HOME/pom.xml clean package -Dsecret.bot.token=${TOKEN}

FROM openjdk:17-oracle
ARG TOKEN
ENV BOT_TOKEN=$TOKEN
ARG JAR_FILE=/usr/app/target/*.jar
COPY --from=build $JAR_FILE /app/runner.jar

ENTRYPOINT ["java", "-D$BOT_TOKEN", "-jar", "/app/runner.jar"]