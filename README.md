## 🚀 Descrição
ByteBank API é uma aplicação Spring Boot que simula operações bancárias básicas, como criação de contas, depósitos, saques, transferências e o gerenciamento de transações.

## 📝 Documentação (Swagger)
http://localhost:8080/swagger-ui/index.html

## 🛠️ Funcionalidades
- Criação e gerenciamento de contas bancárias.
- Realização de depósitos, saques e transferências.
- Consulta de extratos bancários.
- Cancelamento de transações com mecanismos de segurança para evitar saldos negativos.

## 🛠️ Tecnologias e Versões Utilizadas
- **Maven para gerenciamento de dependências**
- **Jackson para manipulação de JSON**
- **Spring Boot Starter Parent**: `3.2.0`
- **Java Version**: `17`
- **Spring Boot Starter Validation**: (Versão gerenciada pelo Spring Boot Starter Parent)
- **Spring Boot Starter Web**: (Versão gerenciada pelo Spring Boot Starter Parent)
- **Spring Boot DevTools**: (Versão gerenciada pelo Spring Boot Starter Parent)
- **Lombok**: (Versão gerenciada pelo Spring Boot Starter Parent)
- **Spring Boot Starter Test**: (Versão gerenciada pelo Spring Boot Starter Parent, escopo de teste)
- **Spring Security Test**: (Versão gerenciada pelo Spring Boot Starter Parent, escopo de teste)
- **Jackson Databind**: `2.15.3`
- **Jackson Datatype JSR310**: `2.15.3`
- **Spring Boot Maven Plugin**: (Versão gerenciada pelo Spring Boot Starter Parent)

## 🐳 Pré-requisitos com Docker
Antes de começar, você precisa ter o Docker instalado em seu computador. Se você ainda não tem o Docker, você pode baixá-lo e instalá-lo [aqui](https://www.docker.com/products/docker-desktop/).

## Como Usar
Siga estes passos para baixar e executar a ByteBank API em seu ambiente local.

### Passo 1: Baixar a Imagem da ByteBank API
Abra seu terminal e execute o seguinte comando para baixar a imagem mais recente da ByteBank API do Docker Hub:

docker pull victorbertho/bytebank-api:1.0.0

### Passo 2: Executar um Contêiner com a Imagem

Após baixar a imagem, você pode executar um contêiner com a ByteBank API usando o seguinte comando:

docker run -p 8080:8080 victorbertho/bytebank-api:1.0.0

Este comando inicia um contêiner que expõe a ByteBank API na porta 8080 do seu computador.

## Acessando a API

Com o contêiner em execução, você pode acessar a API navegando para `http://localhost:8080` em seu navegador ou usando uma ferramenta como Postman para fazer requisições HTTP.

## 📝 Pré-requisitos
- Java JDK 17 ou superior.
- Maven.
- IDE de sua preferência (Recomendado: IntelliJ IDEA).

##  ⚙️️ Configuração e Instalação
1. Clone o repositório:
2. Abra o projeto na sua IDE.
3. Certifique-se de que o Maven baixou todas as dependências necessárias.

## Executando a Aplicação
Para iniciar a aplicação, execute o seguinte comando na raiz do projeto:
mvn spring-boot:run

A aplicação estará acessível em http://localhost:8080.

## 🌐 Estrutura do Projeto

O projeto segue o padrão de arquitetura MVC, organizado da seguinte forma:

### Model
- `Conta`: Classe que representa a conta bancária.
- `Transacao`: Classe que representa as transações realizadas.
- `StatusConta`: Enumeração que define os estados possíveis de uma conta.
- `TipoConta`: Enumeração que define os tipos de conta.
- `TipoTransacao`: Enumeração que define os tipos de transação.

### View
A camada de visualização é manipulada pelos templates do frontend (não inclusos neste repositório).

### Controller
- `ContaController`: Controlador para operações relacionadas a contas bancárias.
- `TransacaoController`: Controlador para operações relacionadas a transações financeiras.

### Repository
- `ContaRepository`: Interface para o repositório de contas.
- `JsonFileContaRepository`: Implementação de repositório que lida com a persistência de contas em um arquivo JSON.
- `TransacaoRepository`: Interface para o repositório de transações.
- `JsonFileTransacaoRepository`: Implementação de repositório que lida com a persistência de transações em um arquivo JSON.
- `JsonFileTransacoesCanceladasRepository`: Implementação de repositório que lida com a persistência de transações canceladas.

### Service
- `ContaService`: Serviço para lógica de negócios relacionada a contas bancárias.
- `TransacaoService`: Serviço para lógica de negócios relacionada a transações financeiras.

### Exception
- `GlobalExceptionHandler`: Classe para manipulação global de exceções.
- `ServiceException`: Classe para exceções específicas dos serviços.

### Utilities
- `BytebankApplication`: Classe principal que inicia a aplicação Spring Boot.


# Uso da API
Você pode testar a API usando ferramentas como Postman. Aqui estão alguns exemplos de requisições que você pode fazer:

### Criar Conta
- **URL**: `/contas`
- **Método**: `POST`
- **Body**:
  {
    "nomeTitular": "Nome do Titular",
    "cpfTitular": "CPF do Titular",
    "tipoConta": "CORRENTE" // Opções: CORRENTE, POUPANCA, SALARIO
  }

### Listar Todas as Contas
- **URL**: `/contas`
- **Método**: `GET`

### Obter Conta por ID
- **URL**: `/contas/{id}`
- **Método**: `GET`

### Atualizar Conta
- **URL**: `/contas/{id}`
- **Método**: `PUT`
- **Body**:
{
  "nomeTitular": "Nome Atualizado do Titular",
  "status": "ATIVA" // Opções: ATIVA, SUSPENSA, ENCERRADA
}

### Deletar Conta (Encerrar)
- **URL**: `/contas/{id}`
- **Método**: `DELETE`

### Realizar Depósito
- **URL**: `/contas/{idConta}/depositar`
- **Método**: `POST`
- **Body**:
{
  "valor": 100.00
}

### Realizar Saque
- **URL**: `/contas/{idConta}/sacar`
- **Método**: `POST`
- **Body**:
{
  "valor": 50.00
}

### Realizar Transferência
- **URL**: `/contas/{idContaOrigem}/transferir`
- **Método**: `POST`
- **Body**:
{
  "idContaDestino": "UUID-da-conta-destino",
  "valor": 150.00
}

### Obter Extrato da Conta
- **URL**: `/contas/{idConta}/extrato`
- **Método**: `GET`

### Cancelar Transação
- **URL**: `/transacoes/{idTransacao}/cancelar`
- **Método**: `DELETE`

## 🎁 Contribuição
### Contribuições são bem-vindas. Para contribuir:
1. Faça um fork do projeto.
2. Crie uma branch para sua funcionalidade: git checkout -b minha-nova-funcionalidade
3. Commit suas mudanças: git commit -am 'Adiciona alguma funcionalidade'
4. Faça push para a branch: git push origin minha-nova-funcionalidade
5. Envie um pull request.
