package tpa.trabalho_pratico.merge;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import tpa.trabalho_pratico.util.ArquivoUtil;

@Getter
@Setter
public final class MergeSortExterno {

    private MergeSortExterno() {

    }

    private static BufferedReader reader0;
    private static BufferedReader reader1;
    private static BufferedWriter writer0;
    private static BufferedWriter writer1;
    private static long run = 1L;
    private static boolean inverte = true;

    private static void proximaRun(File arquivo0, File arquivo1, File arquivo2, File arquivo3) throws IOException {
        run *= 2L;
        if (inverte) {
            inverte = false;
            reader0.close();
            reader1.close();
            writer0.close();
            writer1.close();
            reader0 = new BufferedReader(new FileReader(arquivo2));
            reader1 = new BufferedReader(new FileReader(arquivo3));
            arquivo0.delete();
            arquivo1.delete();
            writer0 = new BufferedWriter(new FileWriter("lista0.csv"));
            writer1 = new BufferedWriter(new FileWriter("lista1.csv"));
        } else {
            inverte = true;
            reader0.close();
            reader1.close();
            writer0.close();
            writer1.close();
            reader0 = new BufferedReader(new FileReader(arquivo0));
            reader1 = new BufferedReader(new FileReader(arquivo1));
            arquivo2.delete();
            arquivo3.delete();
            writer0 = new BufferedWriter(new FileWriter("lista2.csv"));
            writer1 = new BufferedWriter(new FileWriter("lista3.csv"));
        }
    }

    private static void escreveLinhas(BufferedWriter writer, List<String> linhas) {
        final BufferedWriter temp = writer;
        linhas.forEach(arg0 -> {
            try {
                temp.write(arg0 + '\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        linhas.clear();
    }

    public static void realizaMergeSortCSV(BufferedReader arquivoInicial, long numLinhas) throws IOException {
        final File arquivo0 = new File(ArquivoUtil.divideArquivoCSV(0L, arquivoInicial, numLinhas / 2L, false));
        final File arquivo1 = new File(ArquivoUtil.divideArquivoCSV(1L, arquivoInicial, numLinhas / 2L, false));
        final File arquivo2 = new File("lista2.csv");
        final File arquivo3 = new File("lista3.csv");

        reader0 = new BufferedReader(new FileReader(arquivo0));
        reader1 = new BufferedReader(new FileReader(arquivo1));
        writer0 = new BufferedWriter(new FileWriter(arquivo2));
        writer1 = new BufferedWriter(new FileWriter(arquivo3));
        final List<String> escrita0 = new ArrayList<>();
        final List<String> escrita1 = new ArrayList<>();

        boolean troca = true;
        String linha0 = reader0.readLine();
        String linha1 = reader1.readLine();
        while (true) {
            while ((linha0 != null) || (linha1 != null)) {
                if (troca) {
                    troca = false;
                    for (long j = 0L; j < run; j++) {
                        if (linha0 != null) {
                            escrita0.add(linha0);
                            linha0 = reader0.readLine();
                        }
                        if (linha1 != null) {
                            escrita0.add(linha1);
                            linha1 = reader1.readLine();
                        }
                    }
                } else {
                    troca = true;
                    for (long j = 0L; j < run; j++) {
                        if (linha0 != null) {
                            escrita1.add(linha0);
                            linha0 = reader0.readLine();
                        }
                        if (linha1 != null) {
                            escrita1.add(linha1);
                            linha1 = reader1.readLine();
                        }
                    }
                }
            }
            escrita0.sort((a, b) -> a.compareTo(b));
            escrita1.sort((a, b) -> a.compareTo(b));
            escreveLinhas(writer0, escrita0);
            escreveLinhas(writer1, escrita1);
            proximaRun(arquivo0, arquivo1, arquivo2, arquivo3);
            linha0 = reader0.readLine();
            linha1 = reader1.readLine();
            if ((linha0 == null) || (linha1 == null)) {
                break;
            }
        }
    }

}
