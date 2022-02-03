package tpa.trabalho_pratico.merge;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import tpa.trabalho_pratico.util.ArquivoUtil;

@Slf4j
@Getter
@Setter
public final class MergeSortExterno {

    private MergeSortExterno() {

    }

    public static void realizaMergeSortCSV(File entrada) throws IOException {
        final BufferedReader arquivoInicial = new BufferedReader(new FileReader(entrada));
        final long metade = ArquivoUtil.countLines(entrada) / 2L;

        final File arquivo0 = new File(ArquivoUtil.divideArquivoCSV(0L, arquivoInicial, metade, false));
        final File arquivo1 = new File(ArquivoUtil.divideArquivoCSV(1L, arquivoInicial, metade, false));
        final File arquivo2 = new File("lista2.csv");
        final File arquivo3 = new File("lista3.csv");
        
        BufferedReader reader0 = new BufferedReader(new FileReader(arquivo0));
        BufferedReader reader1 = new BufferedReader(new FileReader(arquivo1));
        BufferedWriter writer0 = new BufferedWriter(new FileWriter(arquivo2));
        BufferedWriter writer1 = new BufferedWriter(new FileWriter(arquivo3));

        final StringBuilder sb0 = new StringBuilder();
        final StringBuilder sb1 = new StringBuilder();

        List<String> ordem = new ArrayList<>();
        ordem.add("lista0.csv");
        ordem.add("lista1.csv");
        ordem.add("lista2.csv");
        ordem.add("lista3.csv");
        
        log.info("ordem -> {}", Arrays.toString(ordem.toArray()));
        long run = 1L;
        while (run <= metade) {
            log.info("run -> {}", run);
            for (long i = run; i <= metade; i += i) {
                for (long j = 0L; j < run; j++) {
                    sb0.append(reader0.readLine() + '\n');
                    sb1.append(reader1.readLine() + '\n');
                }
                if (sb0.toString().compareTo(sb1.toString()) <= 0) {
                    writer0.write(sb0.toString());
                    writer1.write(sb1.toString());
                    sb0.setLength(0);
                    sb1.setLength(0);
                }
                else {
                    writer1.write(sb1.toString());
                    writer0.write(sb0.toString());
                    sb1.setLength(0);
                    sb0.setLength(0);
                }
            }
            run *= 2L;
            reader0.close();
            reader1.close();
            writer0.close();
            writer1.close();

            reader0 = new BufferedReader(new FileReader(ordem.get(2)));
            reader1 = new BufferedReader(new FileReader(ordem.get(3)));
            writer0 = new BufferedWriter(new FileWriter(ordem.get(0)));
            writer1 = new BufferedWriter(new FileWriter(ordem.get(1)));

            List<String> inverte = ordem.subList(2, 4);
            inverte.add(ordem.get(0));
            inverte.add(ordem.get(1));

            ordem = inverte;
            log.info("ordem -> {}", Arrays.toString(ordem.toArray()));
        }
    }

}
