package tpa.trabalho_pratico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import tpa.trabalho_pratico.merge.KWayMerge;
import tpa.trabalho_pratico.merge.MergeSortExterno;
import tpa.trabalho_pratico.util.ArquivoUtil;

@Slf4j
public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        log.info("Iniciada a geracao");
        File gerado = ArquivoUtil.geraArquivoTeste(2000000L);
        log.info("Fim da geracao");
        final BufferedReader reader = new BufferedReader(new FileReader(gerado));
        final List<String> arquivos = new ArrayList<>();
        final long num_linhas = ArquivoUtil.countLines(gerado) / 10L;
        log.info("Iniciada a divisao");
        for (long i = 0; i < 10; i++) {
            arquivos.add(MergeSortExterno.divideEntrada(i, reader, num_linhas));
        }
        log.info("Fim da divisao");
        reader.close();
        //gerado.delete();
        log.info("Iniciado o k-way merge");
        while (arquivos.size() > 1) {
            KWayMerge.realizaKWayMerge(new File(arquivos.get(0)), new File(arquivos.get(1)), arquivos);
        }
        log.info("Fim do k-way merge");
    }
}
