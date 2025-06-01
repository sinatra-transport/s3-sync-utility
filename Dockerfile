FROM eclipse-temurin:17-jdk

COPY ./ /build/
RUN cd /build && /build/gradlew :buildFatJar
RUN mv /build/build/libs/s3syncutil.jar /s3syncutil.jar
RUN rm -rf /build

ENTRYPOINT ["java", "-jar", "/s3syncutil.jar"]