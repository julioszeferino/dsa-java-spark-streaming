# App Java para Extracao de Dados em Tempo Real Utilizando o Spark Streaming

>> Este mini-projeto traz a construção de uma app Java para automatizar o processo ETL. Um componente do Apache Commons é usado para processamento de texto e a app é executada em um cluster Spark.

O objetivo é construir uma app Java para automatizar o processo ETL em um conjunto de dados com informações sobre automóveis. O dataset está no formato CSV e a app será executada em um cluster Spark (pseudo cluster de uma única máquina, simulando um cluster). 

O Apache Commons fornecerá um componente para processamento de texto. O processo ETL deverá gerar o seguinte resultado:
1. Extrair o número total de veículos no dataset.
2. Extrair o número total de veículos fabricados nos EUA.
3. Extrair o maior ano de fabricação entre todos os anos (ou seja, ano mais recente).
4. Extrair a média de horsepower de veículos produzidos no Japão.
5. Extrair a média de horsepower por país de fabricação dos veículos.
6. Considerando que a primeira palavra da coluna de nome do veículo é a marca, extrair as 
marcas distintas, convertendo para maiúsculas e minúsculas.
7. Extrair a marca com o maior número de veículos.  
8. O resultado será salvo em formato txt. O dataset fonte foi criado a partir do dataset MTCARS:
https://stat.ethz.ch/R-manual/R-devel/library/datasets/html/mtcars.html

## Ambiente de Desenvolvimento
Java JDK 11
Apache Maven
Apache Spark

## Execucao da aplicacao
```bash
# compilacao da aplicacao
mvn package

# execucao
spark-submit --class stream.ETLStream --master local[*] target/stream-1.0.jar 2 1 dados
```

## Referencias
