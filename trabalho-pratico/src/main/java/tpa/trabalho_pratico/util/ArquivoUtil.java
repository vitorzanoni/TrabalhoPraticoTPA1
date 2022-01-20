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

    public static void geraArquivoTeste(Long maxSize) {
        try {
            final File saida = new File("teste" + maxSize + ".txt");
            final BufferedWriter out = new BufferedWriter(new FileWriter(saida));
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
        } catch (IOException e) {
            log.info("Erro ao gerar o arquivo para teste.", e);
        }
    }

    public static long countLines(String path) throws IOException {

        BufferedReader bf = new BufferedReader(new FileReader(path));
        long count = bf.lines().count();
        bf.close();
        return count;
    }
}
