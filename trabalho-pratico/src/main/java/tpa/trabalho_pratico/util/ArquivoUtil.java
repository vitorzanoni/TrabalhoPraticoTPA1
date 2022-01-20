package tpa.trabalho_pratico.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.github.javafaker.Faker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ArquivoUtil {

    private ArquivoUtil() {

    }

    public static File geraArquivoTeste(Long maxSize) {
        try {
            final File gerado = new File("gerado" + maxSize + ".txt");
            final BufferedWriter out = new BufferedWriter(new FileWriter(gerado));
            final Faker faker = new Faker();
            String nome = faker.name().fullName();
            String telefone = faker.phoneNumber().cellPhone();
            String cidade = faker.address().city();
            String pais = faker.address().country();
            for (long i = 0; i < maxSize; i++) {
                out.write(nome + '\n'
                        + telefone + '\n'
                        + cidade + '\n'
                        + pais + '\n'
                        + "-----------\n");
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
