package tpa.trabalho_pratico.hash;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import lombok.Getter;

@Getter
public class HashTable {

    private Dados[] array = new Dados[10000];
    private int colisoes = 0;

    public int calculaHash(Elemento elemento) {
        int soma = 0;
        final byte[] bytes = elemento.getNome().getBytes();
        for (int i = 0; i < bytes.length; i++) {
            soma += bytes[i] * 10 + i;
        }
        return soma %= 2069;
    }

    public void salvaElemento(Elemento elemento) {
        int soma = calculaHash(elemento);
        if (array[soma] == null) {
            final Dados dados = new Dados();
            dados.getElementos().add(elemento);
            array[soma] = dados;
        } else {
            colisoes += 1;
            array[soma].getElementos().add(elemento);
        }
    }

    public void consultar(String nome) {
        int soma = calculaHash(new Elemento(nome + "; ; ; "));
        if (array[soma] != null) {
            for (Elemento elemento : array[soma].getElementos()) {
                if (elemento.getNome().equalsIgnoreCase(nome)) {
                    System.out.println(String.format("Resultado da consulta:\n%s\n%s\n%s\n%s", elemento.getNome(),
                            elemento.getTelefone(),
                            elemento.getCidade(), elemento.getPais()));
                }
            }
        }
    }

    public void inserir(Elemento elemento) {
        salvaElemento(elemento);
    }

    public void excluir(String nome) {
        int soma = calculaHash(new Elemento(nome + "; ; ; "));
        if (array[soma] != null) {
            for (int i = 0; i < array[soma].getElementos().size(); i++) {
                if (array[soma].getElementos().get(i).getNome().equalsIgnoreCase(nome)) {
                    array[soma].getElementos().remove(i);
                }
            }
        }
    }

    public void atualizar(String nome, String telefone, String cidade, String pais) {
        int soma = calculaHash(new Elemento(nome + "; ; ; "));
        if (array[soma] != null) {
            for (Elemento elemento : array[soma].getElementos()) {
                if (elemento.getNome().equalsIgnoreCase(nome)) {
                    elemento.setTelefone(telefone);
                    elemento.setCidade(cidade);
                    elemento.setPais(pais);
                }
            }
        }
    }

    public void salvar() throws IOException {
        final BufferedWriter out = new BufferedWriter(new FileWriter("saida.csv"));
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                for (Elemento elemento : array[i].getElementos()) {
                    out.write(elemento.toString() + '\n');
                }
            }
        }
        out.close();
    }
}
