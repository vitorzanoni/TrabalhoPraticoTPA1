package tpa.trabalho_pratico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import tpa.trabalho_pratico.hash.Elemento;
import tpa.trabalho_pratico.hash.HashTable;
import tpa.trabalho_pratico.merge.KWayMerge;
import tpa.trabalho_pratico.merge.MergeSortExterno;
import tpa.trabalho_pratico.util.ArquivoUtil;

@Slf4j
public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        // log.info("Iniciada a geracao");
        // final File gerado = ArquivoUtil.geraArquivoTesteCSV(1000000L);
        // log.info("Fim da geracao");

        final File gerado = new File("gerado10.csv");
        final BufferedReader reader = new BufferedReader(new FileReader(gerado));
        final long numLinhas = ArquivoUtil.countLines(gerado);

        // log.info("Iniciado o merge sort externo");
        // MergeSortExterno.realizaMergeSortCSV(reader, numLinhas);
        // log.info("Fim do merge sort externo");

        final List<String> arquivos = new ArrayList<>();

        log.info("Iniciada a divisao");
        for (long i = 0; i < 4; i++) {
            arquivos.add(ArquivoUtil.divideArquivoCSV(i, reader, numLinhas / 4L, true));
        }
        log.info("Fim da divisao");

        log.info("Iniciado o k-way merge");
        while (arquivos.size() > 1) {
            KWayMerge.realizaKWayMergeCSV(new File(arquivos.get(0)), new File(arquivos.get(1)), arquivos);
        }
        log.info("Fim do k-way merge");
        
        log.info("Iniciado o hash");
        final HashTable hashTable = new HashTable();
        final BufferedReader arquivo = new BufferedReader(new FileReader("lista0_lista1_lista2_lista3.csv"));
        for (String linha: arquivo.lines().toList()) {
            Elemento elemento = new Elemento(linha);
            hashTable.salvaElemento(elemento);
        }
        arquivo.close();
        log.info("Fim do hash");

        hashTable.consultar("Therese Boyle IV");

        reader.close();
    }
}
