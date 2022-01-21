package tpa.trabalho_pratico.merge;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class MergeSortExterno {

    private MergeSortExterno() {

    }

    private static final int num_arq = 1000000;

    public static String divideEntrada(Long numero, BufferedReader reader) throws IOException {
        final File arquivo = new File("lista" + numero.toString() + ".txt");
        final BufferedWriter out = new BufferedWriter(new FileWriter(arquivo));
        final List<String> linhas = new ArrayList<>();

        for (long i = numero * num_arq; i < numero * num_arq + num_arq; i += 5) {
            linhas.add(reader.readLine() + '\n' + reader.readLine() + '\n' + reader.readLine() + '\n'
                    + reader.readLine() + '\n' + reader.readLine() + '\n');
        }
        linhas.sort((a, b) -> a.compareTo(b));
        linhas.forEach(arg0 -> {
            try {
                out.write(arg0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        out.close();
        return arquivo.getName();
    }
}
