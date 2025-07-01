# Cotacao-API 💰

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Java](https://img.shields.io/badge/Java-17-007396?style=for-the-badge&logo=java&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9.6-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Render](https://img.shields.io/badge/Render-2950F8?style=for-the-badge&logo=render&logoColor=white)

Este é o repositório do backend da aplicação de cotação de moedas. A **Cotacao-API** é responsável por buscar dados de cotação de moedas em tempo real, gerenciar transações de compra e venda fictícias, e persistir essas informações em um banco de dados PostgreSQL.

## 🌟 Funcionalidades

* **Cotação de Moedas em Tempo Real**: Integração com uma API externa para obter os valores atualizados das principais moedas.
* **Conversão de Moedas**: Realiza conversões com base nas cotações obtidas.
* **Registro de Transações**: Permite o registro de operações de compra e venda de moedas fictícias, armazenando o tipo da transação, moeda, valor e data.
* **Histórico de Transações**: Consulta de todas as transações realizadas e salvas no banco de dados.
* **API RESTful**: Endpoints bem definidos para facilitar a integração com o frontend ou outros serviços.
* **Persistência de Dados**: Utiliza PostgreSQL para armazenamento de dados, com Spring Data JPA para gerenciamento.

## 🚀 Tecnologias Utilizadas

* **Java 17**: Linguagem de programação.
* **Spring Boot 3.x**: Framework para construção de aplicações Java robustas e escaláveis.
    * **Spring WebFlux**: Para desenvolvimento reativo de APIs (usado com WebClient para chamadas externas).
    * **Spring Data JPA**: Para abstração e persistência de dados.
    * **Lombok**: Para reduzir boilerplate code.
* **Maven**: Ferramenta de gerenciamento de dependências e build.
* **PostgreSQL**: Sistema de gerenciamento de banco de dados relacional.
* **Docker**: Para conteinerização da aplicação e do banco de dados (ambiente de desenvolvimento).
* **Render**: Plataforma de deploy contínuo.

## 🛠️ Como Executar o Projeto Localmente

Siga os passos abaixo para configurar e rodar a aplicação em sua máquina local.

### Pré-requisitos

* **Java Development Kit (JDK) 17** ou superior instalado.
* **Maven** instalado.
* **Docker** e **Docker Compose** instalados (recomendado para rodar o PostgreSQL localmente).
* Um IDE como IntelliJ IDEA, VS Code ou Eclipse.

