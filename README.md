@@ -1 +1,88 @@
# vrbeneficio
# Documentação
### Introdução

### Projeto para execução na maquina local

#### <strong>Para executar o projeto é necessário que tenha o docker rodando na sua maquina local. </strong> </br>

<strong>Observação: Nesse projeto iremos utilizar o JAVA 23.</strong>


Configuração do banco MongoDB definida no application.yml:

```
spring:
  data:
    mongodb:
      uri: mongodb://user:password@localhost:27017/miniautorizador?authSource=admin
      database: miniautorizador
      username: user
      password: password
```
Com a aplicação iniciada e sem haver nenhum problema em seu funcionamento é possivel obter o mapeamento dos endpoints através do <b>Swagger</b>, acessando a url:
```
http://localhost:8080/swagger-ui/index.html

user: vrbeneficio
password: password
```

### Regras de Negócio

Para estrutura de pastas do projeto está sendo utilizado o MVC. Separados com os seguintes nomes

<ol>
    <li>Resource(interfaces)</li>
    <li>Controller</li>
    <li>Service</li>
    <li>Repository</li>
    <li>Exception</li>
    <li>Configuration</li>
</ol>

- <b>Resource</b>

  Interfaces com os métodos definidos com anotações spring.web (métodos HTTP). As interfaces nessa pasta são implementadas nas classes de controler.

- <b>Controller</b>

  Classes que implementam a resource (interface) e tem como anotação em sua assinatura definir seu objetivo de criação de métodos de entrada e saída de dados. (Endpoints).

- <b>Service</b>

  Classes de serviço que contemplam todas as regras de negócio do projeto. Essas classes injetam repositórios, mapeamentos e validações.

- <b>Repository</b>

  Interfaces que extendem a JPA e tem como objetivo construir uma ponte para camadas de persitências de dados utilizando a lib do Hibernate ou SpringData

- <b>Exception</b>

  Classes que agregam todas as exceções não tratadas por meio de um interceptor advance e convertida em um retorno padrão. Por meio dela é possivel controlar o tipo de exceção, mensagem e corpo do response nas requisições HTTP.

- <b>Configuration</b>

  Classes com objetivo de injetar uma biblioteca no scopo de inicialização de uma aplicação, parametrizar o funcionamento destas libs, configurar partes de segurança e replicar funcionalidades para diversos componentes.

#### Spring Security (Basic Authentication)

Todos os endpoints estão autenticados sendo necessário informar o basic credential:

```
authentication:
  basic:
    user: vrbeneficio
    password: password
```

#### CompletableFuture

No método de transação está a ser utilizado o CompletableFuture com newSingleThreadExecutor. A ideia é que o retorno do método seja assíncrono (Future). Pois a sua função é configurar com uma única thread. O que garante que apenas uma tarefa seja executada por vez. Isso é essencial para evitar concorrência entre as tarefas.

#### Entidades

Estudando conforme o desafio. Eu vi uma melhoria significativa para diminuir a verbosidade e as implementações de código ao criar apenas a entidade <b>Cartão</b>. Com a definição das colunas de forma simples foi possível atender o objetivo.

### NOTAS

<i>O histórico de evolução da criação do projeto pode ser visto nos commits. Comentei de forma clara e objetiva o que foi feito por etapas. </i>