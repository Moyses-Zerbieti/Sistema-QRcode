#build
FROM maven:3.8.8-amazoncorretto-21-al2023 as build

WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests

# run
FROM amazoncorretto:21.0.5
WORKDIR /app

COPY --from=build ./build/target/*.jar ./tagid.jar

EXPOSE 8080

ENV DATASOURCE_URL=''
ENV DATASOURCE_USERNAME=''
ENV DATASOURCE_PASSWORD=''

ENV SPRING_PROFILES_ACTIVE='production'
ENV TZ='America-/São_Paulo'

ENTRYPOINT java -jar tagid.jar