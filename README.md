# Sistema Biblioteca

O projeto **Sistema_Biblioteca** é uma aplicação desenvolvida em Java que utiliza JavaFX para a interface gráfica e JDBC para a interação com o banco de dados PostgreSQL, além de estar utilizando a Arquitetura em camadas com padrão arquitetural MVC. O objetivo principal do sistema é gerenciar empréstimos e devoluções de livros em uma biblioteca, permitindo o cadastro de alunos, livros e o controle das transações de empréstimo e devolução.

## Funcionalidades Princípais

- **Cadastro de Alunos**: Permite o registro de novos alunos no sistema, incluindo informações como matrícula, nome e curso.
- **Cadastro de Livros**: Possibilita a adição de novos livros ao acervo da biblioteca, com detalhes como título, autor e disponibilidade.
- **Empréstimo de Livros**: Facilita a realização de empréstimos, verificando a disponibilidade do livro e registrando a transação associada ao aluno.
- **Devolução de Livros**: Gerencia a devolução de livros, calculando multas em caso de atraso e atualizando o status de disponibilidade do livro.

## Estrutura do Projeto

O projeto está organizado nos seguintes pacotes:

- **app**: Contém as classes principais responsáveis pela interface gráfica e controle de fluxo da aplicação, como `RealizaEmprestimoController` e `RealizaDevolucaoControl`.
- **dao**: Inclui as classes de acesso a dados (Data Access Objects) que interagem com o banco de dados PostgreSQL, como `AlunoDao`, `LivroDao`, `EmprestimoDao`, `DebitoDao` e `DevolucaoDao`.
- **util**: Abriga classes utilitárias, como `AlertMessage`, que facilita a exibição de alertas na interface gráfica.

### Organização do Código
O projeto é estruturado em camadas seguindo o padrão arquitetural MVC:

- **Model**: [Model](https://github.com/Melissa-Francielle/Sistema_Biblioteca/tree/main/src/main/java/modelo)
  - Contém as classes responsáveis pela lógica de negócio e representação dos dados.
- **View**: [View](https://github.com/Melissa-Francielle/Sistema_Biblioteca/tree/main/src/main/resources/app)
  - Define a interface gráfica e interação com o usuário.
- **Controller**: [Controller](https://github.com/Melissa-Francielle/Sistema_Biblioteca/tree/main/src/main/java/app)
  - Gerencia a comunicação entre a View e o Model, processando eventos e ações do usuário.
- **DAO**: [DAO](https://github.com/Melissa-Francielle/Sistema_Biblioteca/tree/main/src/main/java/dao)
  - Realiza o acesso ao banco de dados, incluindo as operações de CRUD (Create, Read, Update, Delete).
- **UTIL**: [UTIL](https://github.com/Melissa-Francielle/Sistema_Biblioteca/tree/main/src/main/java/util)
  - Contém funções utilitárias e de suporte ao sistema.

## Requisitos

- **Java**: Versão 8 ou superior.
- **JavaFX**: Biblioteca para construção da interface gráfica.
- **PostgreSQL**: Banco de dados utilizado para armazenar as informações da biblioteca.

## Configuração do Ambiente

1. **Clone o repositório**:

   ```bash
   git clone https://github.com/Melissa-Francielle/Sistema_Biblioteca.git
   ```

2. **Configure o banco de dados**:

   - Instale o PostgreSQL e crie um banco de dados para o sistema.
   - Execute os scripts SQL fornecidos no diretório `sql` para criar as tabelas necessárias.
   - Atualize as configurações de conexão no arquivo `database.properties` com as credenciais do seu banco de dados.

3. **Compile e execute a aplicação**:

   - Utilize uma IDE compatível com projetos Java, como Eclipse ou IntelliJ IDEA.
   - Importe o projeto e configure as dependências do JavaFX.
   - Execute a classe principal para iniciar a aplicação.

## Uso

- **Cadastro de Alunos e Livros**: Utilize as opções do menu para adicionar novos alunos e livros ao sistema.
- **Empréstimo de Livros**: Selecione um aluno, escolha os livros desejados (até 3 por vez) e confirme o empréstimo.
- **Devolução de Livros**: Informe a matrícula do aluno, selecione o empréstimo a ser devolvido e confirme a devolução.


## Contato

Para mais informações, entre em contato com a desenvolvedora:

- **GitHub**: [Melissa-Francielle](https://github.com/Melissa-Francielle)

