Aqui está o **README.md** atualizado, incluindo o nome e as configurações específicas do projeto:

---

### **README.md**

```markdown
# Calculator Vaadin Application

Este é um projeto simples de uma calculadora desenvolvida com **Vaadin Flow** e **Spring Boot**. A calculadora suporta operações básicas, como adição, subtração, multiplicação, divisão e raiz quadrada, com uma interface interativa baseada na web.

---

## **Pré-requisitos**

Antes de executar o projeto, certifique-se de que você possui:

1. **Java 21** ou superior instalado.
2. **Maven** instalado ou utilize o Maven Wrapper (`./mvnw`) incluído no projeto.
3. **Google Chrome** instalado (necessário para testes Selenium).
4. **ChromeDriver** configurado no `PATH` do sistema (para testes automatizados).

---

## **Como Executar**

### **Usando Maven Wrapper (Recomendado)**

1. Navegue até o diretório do projeto:
   ```bash
   cd calculator-vaadin
   ```

2. Execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```
   No Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```

3. Acesse o aplicativo no navegador:
   ```
   http://localhost:8080
   ```

---

## **Funcionalidades**

- **Operações Básicas**:
  - Adição (`+`)
  - Subtração (`-`)
  - Multiplicação (`*`)
  - Divisão (`/`)
- **Operações Especiais**:
  - Raiz quadrada (`√`)
  - Limpar (`C`)
  - Suporte a números decimais (`.`)
- **Interface**:
  - Layout de botões semelhante a uma calculadora física.

---

## **Estrutura do Projeto**

```
calculator-vaadin/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.example.calculator/
│   │   │       ├── Application.java          # Classe principal para iniciar o Spring Boot
│   │   │       ├── Calculator.java           # Lógica de cálculo
│   │   │       └── CalculatorView.java       # Interface do usuário (Vaadin)
│   │   └── resources/
│   │       └── application.properties        # Configuração da aplicação
│   ├── test/
│   │   ├── java/
│   │   │   └── com.example.calculator/
│   │   │       ├── CalculatorTest.java       # Testes unitários para a lógica de cálculo
│   │   │       └── CalculatorViewSeleniumIT.java # Testes de integração da interface com Selenium
```

---

## **Testes**

### **Testes Unitários**
Testes da lógica do cálculo são implementados em `CalculatorTest.java`. Para executá-los:

```bash
./mvnw test
```

### **Testes de Integração com Selenium**
Os testes de integração verificam a interação do usuário com a interface.

#### **Pré-requisitos**:
- **ChromeDriver** instalado e configurado no `PATH`.

#### **Executando os Testes**:
1. Certifique-se de que o aplicativo está em execução:
   ```bash
   ./mvnw spring-boot:run
   ```

2. Execute os testes:
   ```bash
   ./mvnw test
   ```

---

## **Tecnologias Utilizadas**

- **Java 21**: Linguagem principal.
- **Spring Boot 3.1.3**: Para configurar e executar o backend.
- **Vaadin Flow 24.4.13**: Para criar a interface de usuário.
- **Selenium WebDriver 4.14.0**: Para testes automatizados da interface.
- **Maven**: Para gerenciamento de dependências e build.

---

## **Personalização**

### **Alterar a Porta**
Se você quiser alterar a porta padrão (8080), edite o arquivo `src/main/resources/application.properties`:

```properties
server.port=8081
```

---

## **Contribuição**

1. Faça um fork do repositório.
2. Crie um branch para sua feature:
   ```bash
   git checkout -b minha-feature
   ```
3. Commit suas mudanças:
   ```bash
   git commit -m "Adiciona nova funcionalidade"
   ```
4. Faça o push para o branch:
   ```bash
   git push origin minha-feature
   ```
5. Abra um Pull Request.

---

## **Licença**

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

---

## **Autor**

Desenvolvido por **Luís Simões da Cunha**.
```

---

### **Principais Ajustes**

1. **Versão do Java**: Atualizado para **Java 21**.
2. **Dependências do `pom.xml`**: Incluídas informações sobre as versões utilizadas.
3. **Nome do Autor**: Adicionado o nome **Luís Simões da Cunha** como autor.

Se precisar de algo mais ou de algum ajuste no texto, é só pedir! 😊