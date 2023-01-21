# App Java para Extracao de Dados em Tempo Real Utilizando o Spark Streaming

>> Este mini-projeto traz a construção, compilação eexecuçãode uma app Java que extraie processa dados gerados em tempo real em um streaming de dados. Extração e processamento são feitos com a API Java doApache Spark Streaming

O objetivo é extrair  dados  em  tempo  real  das  coordenadas  da  ISS,  Estação Espacial  Internacional.  São  dados  reais,  públicos  e  disponíveis  gratuitamente  no  link  abaixo  do  Open Notify, uma interface para dados disponibilizados pela NASA.http://open-notify.org/Usaremos a API fornecida gratuitamente para extração dos dados em tempo real.

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
