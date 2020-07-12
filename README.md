## Definições

Projeto feito em Java com PostgreSQL

Foi utilizado REGEX, Lombok, JpaRepository e Hibernate Validator para o desenvolvimento do projeto

Foram colocados como variaveis de ambiente para configurações do banco
-Duser.name=postgres 
-Duser.password=123456 
-Ddatabase.url=jdbc:postgresql://localhost:5432/

Está criado alguns inserts para iniciar junto com sistema, onde a tabela é apagado quando termina a sessão com a opção:
spring.jpa.hibernate.ddl-auto=create-drop


As mensagens dos Validadores esta no ValidationMessages.properties

Foi feito testes no Validator e na implementação da regra de negócio

O controller não tem contato com a Model do banco por questão de segurança e manipulação de dados.
O MovieDTO não possui Id para não poder alterar algum dado que já está salvo e a MovieEntity é para retornar os valores com o ID para visualização dele.
