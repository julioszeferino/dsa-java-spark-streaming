package stream;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import org.apache.spark.api.java.*;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.*;
import org.apache.spark.streaming.*;
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.sql.functions;
import static org.apache.spark.sql.functions.*;

public class ETLStream {

    /**Metodo GET: obtem os dados da API
     * @param address - endereco http da API
     */
    public static String GET(String address) throws IOException {

        URL url = new URL(address);

        System.out.println("Abrindo a conexao HTTP...");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // obtendo algumas propriedades da conexao
        connection.setRequestProperty("accept", "application/json");

        System.out.println("Obtendo o Stream de dados...");
        InputStream responseStream = connection.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(responseStream));

        // Leitura de cada linha do stream
        String line = in.readLine();

        // Fecha o Stream
		in.close();

		// Retorna o resultado
		return line;
    }


    public static void main(String[] args) throws NumberFormatException, UnknownHostException, IOException, InterruptedException {
        
        // Recuperando os argumentos default
		if (args.length != 3)
            args = new String[] { "1", "10", "dados" };

        // Cria sessão Spark
		SparkSession spark = SparkSession.builder().appName("StreamingApp").config("spark.master", "local[*]").getOrCreate();
    	
    	// Java Spark Context
    	JavaSparkContext context = new JavaSparkContext(spark.sparkContext());

    	// Java Streaming Context
    	JavaStreamingContext streamingContext = new JavaStreamingContext(context, Durations.seconds(10));
        
		// Nível de erro no log
		context.setLogLevel("ERROR");

        
		System.out.println("\nIniciando a Capturaa do Stream de Dados em Tempo Real.");
		System.out.println("\nFormato: <timestamp>, <iss_position, <message>\n");
        // Frequência de requisição dos dados
		int requestFrequency = Integer.parseInt(args[0]);
		
		// Frequência de gravação dos dados
		int writeFrequency = Integer.parseInt(args[1]);
		
		// Pasta de gravação dos dados
		String writeDirectory = args[2];

        // Obtém os dados via API 
		String url = "http://api.open-notify.org/iss-now.json";
		
		// Contador de gravação dos dados
		int i = 0;

		// Contador do loop de catura do Stream
		int z = 0;
		
		// Lista de strings para as linhas no Streaming
		List<String> lines = new ArrayList<>();

        // Loop por 30x
		while (z < 30) {

			// Executa o método GET
			String response = GET(url);

			// Imprime a resposta do método GET
			System.out.println(response);

			// Append das linhas extraídas via API
			lines.add(response);

			// Incrementa o contador de gravação
			i++;

            // Streaming no formato JavaDStream
			JavaDStream<String> jdstream =  streamingContext.textFileStream(writeDirectory);
			System.out.println("Java DStream: " + jdstream);

            // Condicional para gravação em disco
			if (i % writeFrequency == 0) {
				String filename = writeDirectory +  "/" + System.currentTimeMillis() + ".txt";
				System.out.println("gravando em " +  filename);
				FileWriter writer = new FileWriter(filename); 
				for(String str: lines) {
				  writer.write(str + System.lineSeparator());
				}
				writer.close();
				lines.clear();
			}

            // Sleep
			Thread.sleep(requestFrequency * 1000);

			// Incrementa o contador de loop
			z++;
        }

        System.out.println("\nCaptura do Stream de Dados Concluída com Sucesso. Verifique a pasta dados.\n"); 
    }
    
}
