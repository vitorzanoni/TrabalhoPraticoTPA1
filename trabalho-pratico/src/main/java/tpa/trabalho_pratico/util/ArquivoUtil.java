package tpa.trabalho_pratico.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javafaker.Faker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ArquivoUtil {

    private ArquivoUtil() {

    }

    public static String divideArquivoCSV(Long numero, BufferedReader reader, long num_linhas, boolean ordena) throws IOException {
        final File arquivo = new File("lista" + numero.toString() + ".csv");
        final BufferedWriter out = new BufferedWriter(new FileWriter(arquivo));
        final List<String> linhas = new ArrayList<>();

        for (long i = numero * num_linhas; i < numero * num_linhas + num_linhas; i++) {
            linhas.add(reader.readLine() + '\n');
        }
        
        if (ordena) {
            linhas.sort((a, b) -> a.compareTo(b));
        }
        
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

    public static File geraArquivoTesteCSV(Long maxSize) {
        try {
            final File gerado = new File("gerado" + maxSize + ".csv");
            final BufferedWriter out = new BufferedWriter(new FileWriter(gerado));
            final Faker faker = new Faker();
            String nome = faker.name().fullName();
            String telefone = faker.phoneNumber().cellPhone();
            String cidade = faker.address().city();
            String pais = faker.address().country();
            for (long i = 0; i < maxSize; i++) {
                out.write(nome + ';'
                        + telefone + ';'
                        + cidade + ';'
                        + pais + ";\n");
                nome = faker.name().fullName();
                telefone = faker.phoneNumber().cellPhone();
                cidade = faker.address().city();
                pais = faker.address().country();
            }
            out.close();
            return gerado;
        } catch (IOException e) {
            log.info("Erro ao gerar o arquivo para teste.", e);
        }
        return null;
    }

    public static long countLines(File arquivo) throws IOException {
        final BufferedReader bf = new BufferedReader(new FileReader(arquivo));
        final long count = bf.lines().count();
        bf.close();
        return count;
    }
}
