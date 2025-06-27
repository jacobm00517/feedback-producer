#############################
#       build stage         #
#############################
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /workspace

# copy the POM and download dependencies first (better layer-caching)
COPY pom.xml .
RUN mvn -q --batch-mode dependency:go-offline

# copy the rest of the source and build
COPY src ./src
RUN mvn -q --batch-mode package -DskipTests


#############################
#      runtime stage        #
#############################
FROM eclipse-temurin:17-jre 

# non-root user for defence-in-depth
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

WORKDIR /app
COPY --from=builder /workspace/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["sh","-c","exec java -Xms128m -Xmx512m -jar /app/app.jar"]
