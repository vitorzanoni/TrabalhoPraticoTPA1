package tpa.trabalho_pratico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import tpa.trabalho_pratico.merge.MergeSortExterno;
import tpa.trabalho_pratico.util.ArquivoUtil;

@Slf4j
public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        log.info("Iniciada a geracao");
        ArquivoUtil.geraArquivoTeste(1L);
        log.info("Fim da geracao");
        BufferedReader reader = new BufferedReader(new FileReader("C:/Users/carol/Documents/GitHub/TrabalhoPraticoTPA1/teste1000000.txt"));
        for (long i=0; i<5; i++)
        {
            MergeSortExterno.divideEntrada(i, reader);

        }
        reader.close();
    }
}
