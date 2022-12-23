# dao-jdbc
## JDBC
Java DataBase Connectivity é uma abstração do mundo java que possibilita os desenvolvedores se comunicarem com qualquer banco de dados, seja MySql, Sql Sever, PostgreeSQL, etc.
Em resumo, cada banco de dados possue seu driver com suas funcionalidades e para que não seja necessário entender todas as diferentes sintaxes, a JDBC faz a interpretação desses diferentes drivers e o disponibiliza com sua própria sintaxe.

Essa API é composta por interfaces e classes que realizam as operações de um banco de dados, como inserir, deletar, alterar e procurar.

Para utilizar o JBDC é necessário que a biblioteca da banco de dados escolhido esteja presente no projeto. Essa biblioteca é um arquivo .JAR dispobilizado pelos sites oficiais dos banco de dados.
Após instalação, basta adicionar como biblioteca do seu projeto e utilizar os recursos da JDBC.


## Descrição do projeto
![image](https://user-images.githubusercontent.com/84423626/209351882-33949068-96eb-421c-98a5-7111fb3da7f4.png)

Através do diagrama acima, abstraimos essas entidades para classes na linguagem Java e utilizando o JDBC, foi implementado as seguintes funcionalidades:

- `insert`
- `deleteById`
- `update`
- `findById`
- `findAll`

## Próximos passos
- [ ] Criar classe que executa operações CRUD para a entidade Department
