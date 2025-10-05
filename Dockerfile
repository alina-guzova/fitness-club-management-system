FROM eclipse-temurin:17-jdk
WORKDIR /fitness-club-management-system

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY . .

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/fitness-club-management-system-0.0.1-SNAPSHOT.jar"]
