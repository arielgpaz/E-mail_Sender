FROM amazoncorretto:17.0.9

ENV APP_NAME=grades_email_sender
WORKDIR /app

COPY ./target/${APP_NAME}.jar  ${APP_NAME}.jar

CMD java -jar ${APP_NAME}.jar
EXPOSE 8080