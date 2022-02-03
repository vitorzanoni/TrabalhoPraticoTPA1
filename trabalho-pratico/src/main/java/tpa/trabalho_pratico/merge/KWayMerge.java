package tpa.trabalho_pratico.merge;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import tpa.trabalho_pratico.util.ArquivoUtil;

public final class KWayMerge {

    private KWayMerge() {

    }

    public static void realizaKWayMergeCSV(File a, File b, List<String> arquivos) throws IOException {
        long contA = 0;
        long contB = 0;
        final long sizeA = ArquivoUtil.countLines(a);
        final long sizeB = ArquivoUtil.countLines(b);
        final BufferedReader brA = new BufferedReader(new FileReader(a));
        final BufferedReader brB = new BufferedReader(new FileReader(b));
        final String nomeOut = "merge_" + a.getName().substring(0, a.getName().length() - 4) + '_' + b.getName().substring(0, b.getName().length() - 4) + ".csv";
        final BufferedWriter out = new BufferedWriter(new FileWriter(nomeOut));

        String linhaA = brA.readLine();
        String linhaB = brB.readLine();
        while ((contA < sizeA) && (contB < sizeB)) {
            if (linhaA.equals(linhaB)) {
                out.write(linhaA + '\n');
                out.write(linhaB + '\n');
                linhaA = brA.readLine();
                linhaB = brB.readLine();
                contA += 1;
                contB += 1;
            } else if (linhaA.compareTo(linhaB) < 0) {
                out.write(linhaA + '\n');
                linhaA = brA.readLine();
                contA += 1;
            } else {
                out.write(linhaB + '\n');
                linhaB = brB.readLine();
                contB += 1;
            }
        }
        while (contA < sizeA) {
            out.write(linhaA + '\n');
            linhaA = brA.readLine();
            contA += 1;
        }
        while (contB < sizeB) {
            out.write(linhaB + '\n');
            linhaB = brB.readLine();
            contB += 1;
        }
        brA.close();
        brB.close();
        out.close();
        a.delete();
        b.delete();
        arquivos.remove(0);
        arquivos.remove(0);
        arquivos.add(nomeOut);
    }

}