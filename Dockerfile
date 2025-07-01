FROM openjdk:17-jdk-slim AS build

# Definir JAVA_HOME explicitamente (geralmente não necessário com imagens openjdk, mas útil para depurar)
ENV JAVA_HOME /usr/local/openjdk-17
ENV PATH $JAVA_HOME/bin:$PATH

# Instalar Maven e limpar o cache do apt
RUN apt-get update && apt-get install -y --no-install-recommends \
    maven \
    && rm -rf /var/lib/apt/lists/*

# Verificar a versão do Java ANTES de rodar o Maven para depuração
RUN java -version
RUN mvn --version

COPY . /app
WORKDIR /app

# Executa o build do Maven
RUN mvn clean install

# --- Segunda Stage (Para uma imagem final menor) ---
# Usar 17-slim para o runtime (contém um JDK mínimo)
FROM openjdk:17-slim

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]