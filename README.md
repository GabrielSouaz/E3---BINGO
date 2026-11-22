# E3 - BINGO

Sistema de gestao de bingo desenvolvido em Java para o Trabalho A3 da disciplina Gestao e Qualidade de Software.

O sistema apoia a gestao de um jogo de bingo: clientes (cadastrar, consultar, alterar e excluir), venda de cartelas, rodadas do bingo e financeiro (venda de cartelas x gastos com premiacoes).

## Aviso sobre a conta do GitHub

Este repositorio foi criado em uma conta diferente da usada nas atividades anteriores, porque o limite de uso do Codespaces da conta anterior foi atingido.

## Tecnologias

- Java 17
- Maven
- JUnit 5 (testes unitarios)
- Git e GitHub (versionamento)

## Estrutura do projeto

```
E3---BINGO
|-- pom.xml
|-- README.md
|-- src
    |-- main/java/br/com/bingo
    |   |-- Main.java
    |   |-- model
    |   |-- service
    |-- test/java/br/com/bingo
```

## Como executar

Compilar e rodar os testes:

```
mvn test
```

Executar o sistema:

```
mvn compile exec:java -Dexec.mainClass="br.com.bingo.Main"
```

Ou compilando e executando direto:

```
mvn compile
java -cp target/classes br.com.bingo.Main
```

## Branches

- `main`: versao estavel, sem push direto (somente via Pull Request)
- `develop`: integracao das funcionalidades
- `feature/cadastro`: Pessoa 2
- `feature/consulta`: Pessoa 3
- `feature/alteracao`: Pessoa 4
- `feature/exclusao`: Pessoa 5

Fluxo: cada integrante trabalha na sua branch `feature/*`, faz commits proprios e abre Pull Request para `develop`.

## Divisao de tarefas

| Pessoa | Responsabilidade |
|--------|------------------|
| 1 | Organizacao e estrutura (repositorio, branches, estrutura Java, Main, README) |
| 2 | Cadastro |
| 3 | Consulta |
| 4 | Alteracao |
| 5 | Exclusao |
| 6 | Integracao e qualidade |

Todos os integrantes devem ter commits proprios e seguir o ciclo RED, GREEN, REFACTOR nos testes.
