# case-cadastro-chave-pix

1. Código-base
   Uma base de código rastreada em controle de versão, muitos deploys
   Use um sistema de controle de versão como Git. O código da sua aplicação Java deve residir em um único repositório.

2. Dependências
   Declare e isole explicitamente as dependências
   Use uma ferramenta de build como Maven ou Gradle para gerenciar dependências.

xml
Copiar código
<!-- pom.xml para Maven -->
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.exemplo</groupId>
    <artifactId>twelve-factor-app</artifactId>
    <version>1.0-SNAPSHOT</version>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
</project>
3. Configurações
Armazene configurações no ambiente
Use variáveis de ambiente para configurar sua aplicação. Você pode usar @Value ou @ConfigurationProperties no Spring Boot para injetar essas variáveis.

java
Copiar código
// Aplicacao.java
@SpringBootApplication
public class Aplicacao {
public static void main(String[] args) {
SpringApplication.run(Aplicacao.class, args);
}
}

// application.properties
app.message=${APP_MESSAGE:Olá, Mundo!}

// MensagemController.java
@RestController
public class MensagemController {

    @Value("${app.message}")
    private String message;

    @GetMapping("/mensagem")
    public String getMessage() {
        return message;
    }

}

4. Serviços de apoio
   Trate serviços de apoio como recursos anexados
   Configure serviços como bancos de dados usando variáveis de ambiente.

yaml
Copiar código

# application.properties

spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}

5. Build, release, run
   Separe estritamente os estágios de build e execução
   Use ferramentas como Jenkins ou GitHub Actions para automatizar o processo de build e release.

bash
Copiar código

# Exemplo de script de build

./mvnw clean package
docker build -t meuapp:latest .

6. Processos
   Execute a aplicação como um ou mais processos sem estado
   Sua aplicação deve ser sem estado. Armazene o estado da sessão em um datastore como Redis.

java
Copiar código
// Exemplo de serviço sem estado
@RestController
public class StatelessController {
@GetMapping("/stateless")
public String stateless() {
return "Resposta sem estado em " + LocalDateTime.now();
}
}

7. Vinculação de portas
   Exporte serviços via vinculação de portas
   Use servidores embutidos como Tomcat ou Jetty.

java
Copiar código
// Spring Boot usa um servidor Tomcat embutido

8. Concurrency
   Escale via o modelo de processos
   Execute múltiplas instâncias de sua aplicação.

bash
Copiar código

# Exemplo de configuração Docker Compose para escalonamento

version: '3'
services:
app:
image: meuapp:latest
ports:

- "8080:8080"
  deploy:
  replicas: 3

9. Descartabilidade
   Maximize a robustez com startup rápido e desligamento gracioso
   Garanta que sua aplicação possa iniciar e desligar rapidamente.

java
Copiar código
// Aplicacao.java
public class Aplicacao {
public static void main(String[] args) {
SpringApplication app = new SpringApplication(Aplicacao.class);
app.setRegisterShutdownHook(true);
app.run(args);
}
}

10. Paridade Dev/Prod
    Mantenha desenvolvimento, staging e produção o mais semelhantes possível
    Use Docker para garantir consistência.

dockerfile
Copiar código

# Dockerfile

FROM openjdk:11-jre-slim
COPY target/twelve-factor-app.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

11. Logs
    Trate logs como fluxos de eventos
    Use um framework de logging como Logback e envie logs para stdout/stderr.

xml
Copiar código
<!-- logback.xml -->
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} - %msg%n</pattern>
        </encoder>
    </appender>
    <root level="info">
        <appender-ref ref="STDOUT"/>
    </root>
</configuration>
12. Processos administrativos
Execute tarefas administrativas/gerenciais como processos pontuais
Use o suporte integrado do Spring Boot para tarefas administrativas.

bash
Copiar código

# Executando uma tarefa de migração de banco de dados

./mvnw flyway:migrate