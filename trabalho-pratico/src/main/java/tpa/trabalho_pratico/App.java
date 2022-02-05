package tpa.trabalho_pratico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import tpa.trabalho_pratico.hash.Elemento;
import tpa.trabalho_pratico.hash.HashTable;
import tpa.trabalho_pratico.merge.KWayMerge;
import tpa.trabalho_pratico.merge.MergeSortExterno;
import tpa.trabalho_pratico.util.ArquivoUtil;

@Slf4j
public class App {

    private static void hash(final long numLinhas) throws FileNotFoundException, IOException {
        log.info("Iniciado o hash");
        final HashTable hashTable = new HashTable(numLinhas);
        final BufferedReader arquivo = new BufferedReader(new FileReader("lista0_lista1_lista2_lista3.csv"));
        for (String linha : arquivo.lines().toList()) {
            Elemento elemento = new Elemento(linha);
            hashTable.salvaElemento(elemento);
        }
        arquivo.close();
        log.info("Fim do hash");
    }

    private static void kWayMerge(final BufferedReader reader, final long numLinhas) throws IOException {
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
    }

    private static void verificaVazio(File arquivo) throws IOException {
        FileReader reader = new FileReader(arquivo);
        if (reader.read() == -1) {
            reader.close();
            arquivo.delete();
        }
    }

    private static void mergeSortExterno(final BufferedReader reader, final long numLinhas)
            throws IOException {
        log.info("Iniciado o merge sort externo");
        MergeSortExterno.realizaMergeSortCSV(reader, numLinhas);
        File lista0 = new File("lista0.csv");
        File lista1 = new File("lista1.csv");
        File lista2 = new File("lista2.csv");
        File lista3 = new File("lista3.csv");
        verificaVazio(lista0);
        verificaVazio(lista1);
        verificaVazio(lista2);
        verificaVazio(lista3);
        log.info("Fim do merge sort externo");
    }

    private static File geraArquivo(long maxSize) {
        log.info("Iniciada a geracao");
        final File gerado = ArquivoUtil.geraArquivoTesteCSV(maxSize);
        log.info("Fim da geracao");
        return gerado;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final File gerado = geraArquivo(10L);
        // final File gerado = new File("gerado10.csv");
        final BufferedReader reader = new BufferedReader(new FileReader(gerado));
        final long numLinhas = ArquivoUtil.countLines(gerado);

        mergeSortExterno(reader, numLinhas);

        kWayMerge(reader, numLinhas);

        hash(numLinhas);

        reader.close();
    }

}
