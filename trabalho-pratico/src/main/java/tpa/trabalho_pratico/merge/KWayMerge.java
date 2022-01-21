package tpa.trabalho_pratico.merge;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tpa.trabalho_pratico.util.ArquivoUtil;

public final class KWayMerge {

    private KWayMerge() {

    }

    public static void realizaKWayMerge(File a, File b, List<String> arquivos) throws IOException {
        long contA = 0;
        long contB = 0;
        final long sizeA = ArquivoUtil.countLines(a);
        final long sizeB = ArquivoUtil.countLines(b);
        final BufferedReader brA = new BufferedReader(new FileReader(a));
        final BufferedReader brB = new BufferedReader(new FileReader(b));
        final BufferedWriter out = new BufferedWriter(
                new FileWriter("merge_" + a.getName().substring(0, 6) + '_' + b.getName().substring(0, 6) + ".txt"));

        while ((contA < sizeA) && (contB < sizeB)) {
            String linhaA = brA.readLine() + '\n' + brA.readLine() + '\n' + brA.readLine() + '\n'
                    + brA.readLine() + '\n' + brA.readLine() + '\n';
            String linhaB = brB.readLine() + '\n' + brB.readLine() + '\n' + brB.readLine() + '\n'
                    + brB.readLine() + '\n' + brB.readLine() + '\n';
            if (linhaA.equals(linhaB)) {
                out.write(linhaA);
                contA += 5;
                contB += 5;
            } else if (linhaA.compareTo(linhaB) < 0) {
                out.write(linhaA);
                contA += 5;
            } else {
                out.write(linhaB);
                contB += 5;
            }
        }
        while (contA < sizeA) {
            String linhaA = brA.readLine() + '\n' + brA.readLine() + '\n' + brA.readLine() + '\n'
                    + brA.readLine() + '\n' + brA.readLine() + '\n';
            out.write(linhaA);
            contA += 5;
        }
        while (contB < sizeB) {
            String linhaB = brB.readLine() + '\n' + brB.readLine() + '\n' + brB.readLine() + '\n'
                    + brB.readLine() + '\n' + brB.readLine() + '\n';
            out.write(linhaB);
            contB += 5;
        }
        brA.close();
        brB.close();
        out.close();
        a.delete();
        b.delete();
        arquivos.remove(0);
        arquivos.remove(0);
    }

}