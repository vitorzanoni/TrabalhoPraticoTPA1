package tpa.trabalho_pratico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;
import tpa.trabalho_pratico.hash.Elemento;
import tpa.trabalho_pratico.hash.HashTable;
import tpa.trabalho_pratico.merge.KWayMerge;
import tpa.trabalho_pratico.merge.MergeSortExterno;
import tpa.trabalho_pratico.util.ArquivoUtil;

@Slf4j
public class App {

    private static HashTable hashTable;

    private static void hash(String nome) throws IOException {
        log.info("Iniciado o hash");
        hashTable = new HashTable();
        final BufferedReader arquivo = new BufferedReader(new FileReader(nome));
        for (String linha : arquivo.lines().toList()) {
            Elemento elemento = new Elemento(linha);
            hashTable.salvaElemento(elemento);
        }
        arquivo.close();
        log.info("Numero de colisoes -> {}", hashTable.getColisoes());
        log.info("Fim do hash");
    }

    private static String kWayMerge(BufferedReader reader, long numLinhas) throws IOException {
        final List<String> arquivos = new ArrayList<>();

        log.info("Iniciada a divisao");
        for (long i = 0; i < 10; i++) {
            arquivos.add(ArquivoUtil.divideArquivoCSV(i, reader, numLinhas / 10L, true));
        }
        log.info("Fim da divisao");

        log.info("Iniciado o k-way merge");
        while (arquivos.size() > 1) {
            KWayMerge.realizaKWayMergeCSV(new File(arquivos.get(0)), new File(arquivos.get(1)), arquivos);
        }
        log.info("Fim do k-way merge");

        return "lista4_lista5_lista6_lista7_lista8_lista9_lista0_lista1_lista2_lista3.csv";
    }

    private static boolean verificaVazio(File arquivo) throws IOException {
        FileReader reader = new FileReader(arquivo);
        if (reader.read() == -1) {
            reader.close();
            arquivo.delete();
            return true;
        }
        return false;
    }

    private static String mergeSortExterno(BufferedReader reader, long numLinhas)
            throws IOException {
        log.info("Iniciado o merge sort externo");
        MergeSortExterno.realizaMergeSortCSV(reader, numLinhas);
        log.info("Fim do merge sort externo");

        File lista0 = new File("lista0.csv");
        File lista1 = new File("lista1.csv");
        File lista2 = new File("lista2.csv");
        File lista3 = new File("lista3.csv");
        String nome = null;
        if (!verificaVazio(lista0)) {
            nome = lista0.getName();
        }
        if (!verificaVazio(lista1)) {
            nome = lista1.getName();
        }
        if (!verificaVazio(lista2)) {
            nome = lista2.getName();
        }
        if (!verificaVazio(lista3)) {
            nome = lista3.getName();
        }
        return nome;
    }

    private static File geraArquivo(long maxSize) {
        log.info("Iniciada a geracao");
        final File gerado = ArquivoUtil.geraArquivoTesteCSV(maxSize);
        log.info("Fim da geracao");
        return gerado;
    }

    public static void main(String[] args) throws IOException {
        // final File gerado = geraArquivo(10000000L);
        final File gerado = new File("gerado10.csv");
        final BufferedReader reader = new BufferedReader(new FileReader(gerado));
        final long numLinhas = ArquivoUtil.countLines(gerado);
        // final String nome = mergeSortExterno(reader, numLinhas);
        final String nome = kWayMerge(reader, numLinhas);

        System.out.println("1. Carregar Arquivo\n" +
                "2. Localizar Contato sendo como chave de busca o Nome Completo.\n" +
                "3. Inserir Contato Novo\n" +
                "4. Excluir Contato\n" +
                "5. Atualizar Contato (Atualizar dados de Telefone, Cidade e Pais)\n" +
                "6. Salvar Dados\n" +
                "7. Fim do Programa");

        int loop = 1;
        while (loop == 1) {
            System.out.print("Entrada: ");
            final Scanner scanner = new Scanner(System.in);
            final String entrada = scanner.nextLine();
            switch (entrada) {
                case "1":
                System.out.println("Carregar Arquivo");
                    hash(nome);
                    break;
                case "2":
                System.out.println("Localizar Contato sendo como chave de busca o Nome Completo");
                    System.out.print("Nome: ");
                    final String nomeBusca = scanner.nextLine();
                    hashTable.consultar(nomeBusca);
                    break;
                case "3":
                System.out.println("Inserir Contato Novo");
                    System.out.print("Nome: ");
                    final String nomeInserir = scanner.nextLine();
                    System.out.print("Telefone: ");
                    final String telefone = scanner.nextLine();
                    System.out.print("Cidade: ");
                    final String cidade = scanner.nextLine();
                    System.out.print("Pais: ");
                    final String pais = scanner.nextLine();
                    final Elemento elemento = new Elemento(nomeInserir + ';' + telefone + ';'
                            + cidade + ';' + pais);
                    hashTable.inserir(elemento);
                    break;
                case "4":
                System.out.println("Excluir Contato");
                    System.out.print("Nome: ");
                    final String nomeExcluir = scanner.nextLine();
                    hashTable.excluir(nomeExcluir);
                    break;
                case "5":
                System.out.println("Atualizar Contato (Atualizar dados de Telefone, Cidade e Pais)");
                    System.out.print("Nome: ");
                    final String nomeUpdate = scanner.nextLine();
                    System.out.print("Telefone: ");
                    final String telefoneUpdate = scanner.nextLine();
                    System.out.print("Cidade: ");
                    final String cidadeUpdate = scanner.nextLine();
                    System.out.print("Pais: ");
                    final String paisUpdate = scanner.nextLine();
                    hashTable.atualizar(nomeUpdate, telefoneUpdate,
                            cidadeUpdate, paisUpdate);
                    break;
                case "6":
                System.out.println("Salvar Dados");
                    hashTable.salvar();
                    break;
                case "7":
                System.out.println("Fim do Programa");
                    loop = 0;
                    scanner.close();
                    break;
                default:
                    System.out.println("Entrada invalida");
            }
        }
        reader.close();
    }

}
