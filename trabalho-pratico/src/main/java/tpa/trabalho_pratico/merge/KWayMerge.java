package tpa.trabalho_pratico.merge;

import java.util.ArrayList;
import java.util.List;

public final class KWayMerge {

    private KWayMerge() {

    }

    public static List<String> realizaKWayMerge(List<String> a, List<String> b) {
        int contA = 0;
        int contB = 0;
        List<String> saida = new ArrayList<>();
        while ((contA < a.size()) && (contB < b.size())) {
            if (a.get(contA).equals(b.get(contB))) {
                saida.add(a.get(contA));
                contA++;
                contB++;
            } else if (a.get(contA).compareTo(b.get(contB)) < 0) {
                saida.add(a.get(contA));
                contA++;
            } else {
                saida.add(b.get(contB));
                contB++;
            }
        }
        while (contA < a.size()) {
            saida.add(a.get(contA));
            contA++;
        }
        while (contB < b.size()) {
            saida.add(b.get(contB));
            contB++;
        }
        return saida;
    }

}