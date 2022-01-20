package tpa.trabalho_pratico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import tpa.trabalho_pratico.merge.MergeSortExterno;
import tpa.trabalho_pratico.util.ArquivoUtil;

@Slf4j
public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        log.info("Iniciada a geracao");
        File gerado = ArquivoUtil.geraArquivoTeste(1000000L);
        log.info("Fim da geracao");
        final BufferedReader reader = new BufferedReader(new FileReader(gerado));
        for (long i = 0; i < ArquivoUtil.countLines(gerado) / 1000000; i++) {
            MergeSortExterno.divideEntrada(i, reader);
        }
        reader.close();
    }
}
