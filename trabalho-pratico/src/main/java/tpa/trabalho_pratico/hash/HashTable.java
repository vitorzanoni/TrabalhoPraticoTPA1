package tpa.trabalho_pratico.hash;

import java.lang.constant.Constable;

import javax.crypto.spec.DESKeySpec;

import lombok.Getter;

@Getter
public class HashTable {

    private static final int TAMANHO = 10;
    private Dados array[] = new Dados[TAMANHO];

    public Integer calculaHash(Elemento elemento) {
        Integer soma = 0;
        final byte[] bytes = elemento.getNome().getBytes();
        for (int i = 0; i < bytes.length; i++) {
            soma += bytes[i] * 10 + i;
        }
        return (soma %= 2069)/TAMANHO;
    }
    
    int cont = 0;
    public void salvaElemento(Elemento elemento) {
        Integer soma = calculaHash(elemento);
        if (array[soma] == null) {
            final Dados dados = new Dados();
            dados.getElementos().add(elemento);
            array[soma] = dados;
        } else {
            cont += 1;
            array[soma].getElementos().add(elemento);
        }
    }

    public void consultar(String nome) {

        Integer soma = calculaHash(new Elemento(nome + "; ; ; ;"));
        for (Elemento elemento : array[soma].getElementos()) {
            if (elemento.getNome().equalsIgnoreCase(nome)) {
                System.out.println(String.format("%s\n%s\n%s\n%s", elemento.getNome(), elemento.getTelefone(),
                        elemento.getCidade(), elemento.getPais()));
            }
        }
    }

    public void inserir(Elemento elemento) {
        salvaElemento(elemento);
    }

    public void excluir(String nome) {
        Integer soma = calculaHash(new Elemento(nome + "; ; ; ;"));
        for (Elemento elemento : array[soma].getElementos()) {
            if (elemento.getNome().equalsIgnoreCase(nome)) {
                array[soma].getElementos().remove(elemento);
            }
        }

    }

    public void atualizar(String nome, String telefone, String cidade, String pais) {
        Integer soma = calculaHash(new Elemento(nome + "; ; ; ;"));
        for (Elemento elemento : array[soma].getElementos()) {
            if (elemento.getNome().equalsIgnoreCase(nome)) {
                elemento.setTelefone(telefone);
                elemento.setCidade(cidade);
                elemento.setPais(pais);
            }
        }
    }

    public void salvar() {
        

    }
}
