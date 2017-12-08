FROM openjdk:8-jre-alpine
COPY ./build/libs/shop-demo-uberjar.jar /root/shop-demo-uberjar.jar
WORKDIR /root
ENV PORT=8080
CMD ["java", "-server", "-Xms4g", "-Xmx4g", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=100", "-XX:+UseStringDeduplication", "-jar", "shop-demo-uberjar.jar"]
