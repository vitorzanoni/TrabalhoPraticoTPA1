package tpa.trabalho_pratico.merge;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class MergeSortExterno {

    private MergeSortExterno(){}
    
    private static final int num_arq = 1000000;
    
    public static void divideEntrada(Long numero, BufferedReader reader) throws IOException, InterruptedException {
        log.info("numero -> {}", numero);
        BufferedWriter out = new BufferedWriter(new FileWriter(new File("lista"+ numero.toString() + ".txt")));
        for (long i = numero * num_arq; i < numero * num_arq + num_arq ; i++) {
            String linha = reader.readLine() + '\n';

            out.write(linha);
            
        }
        out.close();
    }
}