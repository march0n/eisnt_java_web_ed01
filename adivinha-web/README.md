# Jogo: Adivinha a Idade

Este projeto é um jogo simples chamado **"Adivinha a Idade"**, criado com **Vaadin** e **Spring Boot**. O objetivo é adivinhar uma idade escolhida aleatoriamente pelo programa. Este é um excelente ponto de partida para quem está a começar com Java.

---

## Pré-requisitos

Certifique-se de que tem as seguintes ferramentas instaladas:

1. **Java**  
   - Qualquer versão compatível com **Java 17** ou superior.  
   - Verifique a instalação com:
     ```bash
     java -version
     ```

2. **Maven**  
   - Ferramenta para gerir dependências.  
   - Instale em [Apache Maven](https://maven.apache.org/download.cgi) e configure as variáveis de ambiente.  
   - Verifique a instalação com:
     ```bash
     mvn -version
     ```

3. **Node.js**  
   - Necessário para construir os recursos do Vaadin.  
   - Descarregue a versão **LTS** em [Node.js](https://nodejs.org/en/download/).

4. **Visual Studio Code**  
   - Editor de código recomendado.  
   - Instale a extensão **Java Extension Pack** (confirme na barra de extensões do VS Code).

---

## Como Executar o Projeto

1. **Clone ou descarregue o projeto**:  
   - Clone com Git:
     ```bash
     git clone <URL_DO_PROJETO>
     cd adivinha
     ```
   - Ou descarregue o ficheiro ZIP e extraia-o.

2. **Abra o projeto no Visual Studio Code**:  
   - Vá a **File > Open Folder** e escolha a pasta do projeto.

3. **Execute o projeto**:  
   - No terminal integrado, corra:
     ```bash
     mvn spring-boot:run
     ```

4. **Aceda no navegador**:  
   - Abra [http://localhost:8080](http://localhost:8080).

---

## Como Jogar

1. Introduza um número no campo de texto.  
2. Clique em **"Verificar"**.  
3. O programa indica se a tentativa é baixa, alta ou correta.  
4. Quando acertar, clique em **"Reiniciar"** para jogar novamente.

---

## Próximos Passos

- Personalize o design em `src/main/frontend/styles/shared-styles.css`.
- Adicione funcionalidades como:
  - Exibição do número de tentativas.
  - Escolha do intervalo de idades pelo utilizador.

---

## Recursos Úteis

- [Documentação do Vaadin](https://vaadin.com/docs)
- [Guia do Maven](https://maven.apache.org/guides/index.html)

---

Se tiver dúvidas, abra uma **issue** ou entre em contacto! 😊
