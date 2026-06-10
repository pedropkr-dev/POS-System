# 🛒 Sistema de Frente de Loja (PDV) em Java

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Java Swing](https://img.shields.io/badge/Java_Swing-007396?style=for-the-badge&logo=java&logoColor=white)
![Arquitetura MVC](https://img.shields.io/badge/Arquitetura-MVC-brightgreen?style=for-the-badge)
![Clean Code](https://img.shields.io/badge/Clean_Code-Solid-blueviolet?style=for-the-badge)

## 📖 Sobre o Projeto

Este projeto consiste na Atividade Final da disciplina de **Projeto de Programação**, ministrada pelo Prof. M.Sc. Yúri Faro Dantas de Sant'Anna. O objetivo é simular um ambiente real de Frente de Loja (Ponto de Venda - PDV) através de uma aplicação Desktop utilizando **Java Swing**.

O grande diferencial deste sistema é a **ausência de um Sistema Gerenciador de Banco de Dados (SGBD)**. Toda a persistência de dados (Catálogo de Produtos, Histórico de Vendas e Cadastro de Clientes) foi arquitetada "do zero" utilizando manipulação avançada de arquivos de texto transacionais (`.csv`) com as bibliotecas modernas de alta velocidade do pacote `java.nio.file` (NIO.2).

## ✨ Principais Funcionalidades

De acordo com as regras de negócio exigidas, o sistema possui os seguintes módulos:

*   📦 **Gestão de Produtos:** Permite listar, cadastrar e alterar produtos. Cada produto no catálogo possui um Código de Barras validador e uma imagem associada.
*   👥 **Gestão de Clientes (com Consumidor Final):** Cadastro completo de clientes utilizando validação de CPF. O sistema suporta compras rápidas gerando automaticamente o perfil dinâmico de "Consumidor Final" associando um ID numérico por baixo dos panos para manter a integridade.
*   🛒 **Frente de Caixa (Módulo de Vendas):** Leitura de código de barras para inserção no carrinho (`ProdutoVenda`), vinculação de cliente pelo CPF validado, cálculo dinâmico de subtotais e sumarização de formas de pagamento.
*   📊 **Relatórios e Histórico:** Mecanismo de *parseamento* reverso que lê o arquivo `vendas.csv`, cruza as chaves estrangeiras com `clientes.csv` e `produtos.csv`, e remonta o histórico perfeitamente detalhado na tela. Arquivos são atualizados dinamicamente a cada conclusão de venda.

## 🏗️ Arquitetura e Engenharia de Software

Este projeto foi construído fugindo do "código espaguete", adotando rigorosos padrões corporativos de desenvolvimento:

*   **Padrão de Camadas (Model, DAO, Service, View/Controller):** O sistema está perfeitamente modularizado. Telas (Views) não tocam em arquivos; DAOs não fazem cálculos; e os Models são agentes ativos.
*   **Engenharia Defensiva e "Fail Fast":** A camada de `Service` atua como um "bunker" de segurança. Regras de negócio são validadas imediatamente e, em caso de erro, exceções (`IllegalArgumentException`) são lançadas impedindo o processamento falho.
*   **Proteção de Memória (Try-with-resources):** DAOs implementados com `FileWriter` e *Try-with-resources* garantindo que nenhum vazamento de memória (Memory Leak) ocorra durante a gravação de arquivos.
*   **Princípio "Tell, Don't Ask" (Diga, não pergunte):** Forte uso de encapsulamento defensivo. Variáveis não possuem "Setters" indiscriminados. O cálculo do subtotal da venda é feito pela própria classe do carrinho acessando a classe embutida de Produto.

## 📂 Estrutura de Diretórios (Packages)

A estruturação segue a regra corporativa de empacotamento para segurança e controle de acesso:
```text
src/
 └── com/pointofsale/
      ├── model/       # Entidades de abstração real
      ├── dao/         # Data Access Objects
      ├── service/     # Validadores de regras de negócio
      ├── controller/  # Event Listeners
      ├── util/        # Utilitários globais
      └── view/        # Telas, Painéis e Interface Gráfica
dados/
 ├── produtos.csv      # Persistência do Catálogo
 ├── clientes.csv      # Persistência de Clientes
 └── vendas.csv        # Registro anonimizado/referenciado de Transações
🛠️ Como Executar o Projeto
Certifique-se de ter o Java JDK 8 ou superior instalado em sua máquina.
Clone este repositório:
Abra o projeto em sua IDE favorita (IntelliJ IDEA, Eclipse, NetBeans, etc).
Execute a classe Main (geralmente localizada na raiz do pacote view ou app).
(Nota): Os arquivos de banco de dados (.csv) na pasta dados/ serão criados automaticamente pelo sistema na primeira execução caso não existam.
👨‍💻 Autores
Anderson Hugo - Estudante de Ciência da Computação
Pedro Guilherme - Estudante de Ciência da Computação

***
