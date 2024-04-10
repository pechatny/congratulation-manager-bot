FROM openjdk:17-oracle AS build
ARG VAULT_TOKEN
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN --mount=type=cache,target=/root/.m2 ./mvnw -f $HOME/pom.xml clean package -Dsecret.bot.token=${VAULT_TOKEN}

FROM openjdk:17-oracle
ARG VAULT_TOKEN
ENV VAULT_TOKEN=$VAULT_TOKEN
ARG JAR_FILE=/usr/app/target/*.jar
COPY --from=build $JAR_FILE /app/runner.jar

EXPOSE 8080
ENTRYPOINT ["java", "-D$VAULT_TOKEN", "-jar", "/app/runner.jar"]