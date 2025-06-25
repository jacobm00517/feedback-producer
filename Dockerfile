# ---------- build ----------
FROM eclipse-temurin:17-jdk        AS builder
WORKDIR /workspace
COPY mvnw .mvn pom.xml ./
RUN ./mvnw -q --batch-mode dependency:go-offline

COPY src ./src
RUN ./mvnw -q --batch-mode package -DskipTests

# ---------- runtime ----------
FROM eclipse-temurin:17-jre

# Create an unprivileged user in Debian/Ubuntu style
RUN groupadd -r spring && useradd -r -g spring spring

USER spring:spring
WORKDIR /app
COPY --from=builder /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh","-c","exec java -Xms128m -Xmx512m -jar /app/app.jar"]