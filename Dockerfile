FROM amazoncorretto:17.0.9

ENV APP_NAME=grades_email_sender

COPY ./target/${APP_NAME}.jar  /app/${APP_NAME}.jar

WORKDIR /app

CMD java -jar ${APP_NAME}.jar

EXPOSE 8080