package com.example.inventory;

// Importa a classe SpringApplication, que contém métodos para iniciar a aplicação Spring Boot
import org.springframework.boot.SpringApplication;

// Importa a anotação SpringBootApplication, que marca esta classe como a configuração principal de uma aplicação Spring Boot
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * A classe principal da aplicação "InventoryApplication".
 * Esta classe é o ponto de entrada para a aplicação Spring Boot.
 * O uso da anotação @SpringBootApplication combina várias funcionalidades do Spring Boot, como:
 * - @Configuration: Indica que esta classe contém definições de beans para o contexto da aplicação.
 * - @EnableAutoConfiguration: Permite ao Spring configurar automaticamente os componentes com base nas dependências do projeto.
 * - @ComponentScan: Habilita a detecção automática de componentes (como controladores e serviços) no pacote e seus subpacotes.
 */
@SpringBootApplication // Marca esta classe como a classe principal de configuração do Spring Boot
public class InventoryApplication {

    /**
     * O método principal (main), que serve como ponto de entrada da aplicação.
     * Este método é executado pela JVM quando a aplicação é iniciada.
     *
     * @param args Argumentos passados pela linha de comando ao executar a aplicação (opcionais).
     */
    public static void main(String[] args) {
        // O método run da classe SpringApplication inicializa o contexto do Spring.
        // Ele carrega os componentes da aplicação, configura beans e inicia o servidor embutido (ex.: Tomcat ou Jetty).
        SpringApplication.run(InventoryApplication.class, args);
    }
}

/*
Notas adicionais:
1. Estrutura do pacote:
   - A aplicação está contida no pacote "com.example.inventory".
   - Este é o pacote base da aplicação. O Spring Boot automaticamente procura por componentes dentro deste pacote e seus subpacotes.

2. Sobre o método "main":
   - Este é o método que será chamado ao executar o ficheiro JAR da aplicação.
   - "SpringApplication.run" executa várias tarefas:
     a) Configura o contexto de aplicação do Spring.
     b) Regista todos os beans definidos no projeto.
     c) Inicia um servidor web embutido (ex.: Tomcat) se a aplicação for web.

3. Sobre a anotação @SpringBootApplication:
   - Facilita a configuração inicial da aplicação, eliminando a necessidade de adicionar múltiplas anotações manualmente.
   - É um "atalho" para as anotações @Configuration, @EnableAutoConfiguration e @ComponentScan.

4. Considerações de boas práticas:
   - A classe principal da aplicação deve estar no nível mais alto do pacote para que @ComponentScan funcione corretamente sem configurações adicionais.
   - Use nomes de pacotes descritivos que reflitam o propósito da aplicação.

5. Passos para execução:
   - Compile a aplicação com Maven ou Gradle ("mvn clean install" ou "./gradlew build").
   - Execute o ficheiro JAR gerado (ex.: "java -jar inventory-application.jar").
   - Certifique-se de que o ficheiro "application.properties" ou "application.yml" contém as configurações necessárias.
*/
