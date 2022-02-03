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

    private static BufferedReader reader0;
    private static BufferedReader reader1;
    private static BufferedWriter writer0;
    private static BufferedWriter writer1;
    private static StringBuilder sb0 = new StringBuilder();
    private static StringBuilder sb1 = new StringBuilder();
    private static List<String> linhas0 = new ArrayList<>();
    private static List<String> linhas1 = new ArrayList<>();
    private static List<String> escrita0 = new ArrayList<>();
    private static List<String> escrita1 = new ArrayList<>();
    private static long run = 1L;
    private static boolean inverte = true;

    private static void leLinhas() throws IOException {
        linhas0.addAll(reader0.lines().toList());
        linhas1.addAll(reader1.lines().toList());
    }

    // private static void separaLinhas(long i) {
    // if (i % 2 == 0) {
    // if (sb0.toString().compareTo(sb1.toString()) <= 0) {
    // escrita0.add(sb0.toString());
    // escrita0.add(sb1.toString());
    // sb0.setLength(0);
    // sb1.setLength(0);
    // } else {
    // escrita0.add(sb1.toString());
    // escrita0.add(sb0.toString());
    // sb1.setLength(0);
    // sb0.setLength(0);
    // }
    // } else {
    // if (sb0.toString().compareTo(sb1.toString()) <= 0) {
    // escrita1.add(sb0.toString());
    // escrita1.add(sb1.toString());
    // sb0.setLength(0);
    // sb1.setLength(0);
    // } else {
    // escrita1.add(sb1.toString());
    // escrita1.add(sb0.toString());
    // sb1.setLength(0);
    // sb0.setLength(0);
    // }
    // }
    // }

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

    public static void realizaMergeSortCSV(File entrada, boolean teste) throws IOException {
        final BufferedReader arquivoInicial = new BufferedReader(new FileReader(entrada));
        final long metade = ArquivoUtil.countLines(entrada) / 2L;

        final File arquivo0 = new File(ArquivoUtil.divideArquivoCSV(0L, arquivoInicial, metade, false));
        final File arquivo1 = new File(ArquivoUtil.divideArquivoCSV(1L, arquivoInicial, metade, false));
        final File arquivo2 = new File("lista2.csv");
        final File arquivo3 = new File("lista3.csv");

        reader0 = new BufferedReader(new FileReader(arquivo0));
        reader1 = new BufferedReader(new FileReader(arquivo1));
        writer0 = new BufferedWriter(new FileWriter(arquivo2));
        writer1 = new BufferedWriter(new FileWriter(arquivo3));

        boolean troca = true;
        while (run <= metade) {
            leLinhas();
            while (linhas0.size() != 0) {
                if (troca) {
                    troca = false;
                    for (Long j = 0L; j < run; j++) {
                        if ((linhas0.size() > j) && (linhas1.size() > j)) {
                            if (linhas0.get(0).compareTo(linhas1.get(0)) <= 0) {
                                escrita0.add(linhas0.get(0));
                                escrita0.add(linhas1.get(0));
                                linhas0.remove(0);
                                linhas1.remove(0);
                            } else {
                                escrita0.add(linhas1.get(0));
                                escrita0.add(linhas0.get(0));
                                linhas0.remove(0);
                                linhas1.remove(0);
                            }
                        } else if (linhas0.size() > j) {
                            escrita0.add(linhas0.get(0));
                            linhas0.remove(0);
                        } else if (linhas1.size() > j) {
                            escrita0.add(linhas1.get(0));
                            linhas1.remove(0);
                        }
                    }
                } else {
                    troca = true;
                    for (Long j = 0L; j < run; j++) {
                        if ((linhas0.size() > j) && (linhas1.size() > j)) {
                            if (linhas0.get(0).compareTo(linhas1.get(0)) <= 0) {
                                escrita1.add(linhas0.get(0));
                                escrita1.add(linhas1.get(0));
                                linhas0.remove(0);
                                linhas1.remove(0);
                            } else {
                                escrita1.add(linhas1.get(0));
                                escrita1.add(linhas0.get(0));
                                linhas0.remove(0);
                                linhas1.remove(0);
                            }
                        } else if (linhas0.size() > j) {
                            escrita1.add(linhas0.get(0));
                            linhas0.remove(0);
                        } else if (linhas1.size() > j) {
                            escrita1.add(linhas1.get(0));
                            linhas1.remove(0);
                        }
                    }
                }
            }
            escrita0.sort((a, b) -> a.compareTo(b));
            escrita1.sort((a, b) -> a.compareTo(b));
            escreveLinhas(writer0, escrita0);
            escreveLinhas(writer1, escrita1);
            if (run == metade) {
                break;
            } else {
                proximaRun(arquivo0, arquivo1, arquivo2, arquivo3);
                if (run > metade) {
                    run = metade;
                }
            }
        }
    }

    // public static void realizaMergeSortCSV(File entrada) throws IOException {
    // final BufferedReader arquivoInicial = new BufferedReader(new
    // FileReader(entrada));
    // final long metade = ArquivoUtil.countLines(entrada) / 2L;

    // final File arquivo0 = new File(ArquivoUtil.divideArquivoCSV(0L,
    // arquivoInicial, metade, false));
    // final File arquivo1 = new File(ArquivoUtil.divideArquivoCSV(1L,
    // arquivoInicial, metade, false));
    // final File arquivo2 = new File("lista2.csv");
    // final File arquivo3 = new File("lista3.csv");

    // reader0 = new BufferedReader(new FileReader(arquivo0));
    // reader1 = new BufferedReader(new FileReader(arquivo1));
    // writer0 = new BufferedWriter(new FileWriter(arquivo2));
    // writer1 = new BufferedWriter(new FileWriter(arquivo3));

    // while (run <= metade) {
    // log.info("run -> {}", run);
    // long cont = metade / run % 2 == 0 ? metade / run : metade / run + 1;
    // for (long i = 1L; i < cont; i++) {
    // log.info("i -> {}", i);
    // leLinhas();
    // separaLinhas(i);
    // log.info(Arrays.toString(linhas0.toArray()));
    // log.info(Arrays.toString(linhas1.toArray()));
    // escreveLinhas(writer0, escrita0);
    // escreveLinhas(writer1, escrita1);
    // }
    // if (run == metade) {
    // break;
    // } else {
    // proximaRun(arquivo0, arquivo1, arquivo2, arquivo3);
    // if (run > metade) {
    // run = metade;
    // }
    // }
    // }
    // }
}
