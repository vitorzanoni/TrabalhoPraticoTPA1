package tpa.trabalho_pratico.hash;

import lombok.Getter;

@Getter
public class HashTable {

    private Dados[] array;
    private int colisoes = 0;

    public HashTable(Long tamanho) {
        this.array = new Dados[tamanho.intValue()];
    }

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
        int soma = calculaHash(new Elemento(nome + "; ; ;"));
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
        int soma = calculaHash(new Elemento(nome + "; ; ;"));
        for (Elemento elemento : array[soma].getElementos()) {
            if (elemento.getNome().equalsIgnoreCase(nome)) {
                array[soma].getElementos().remove(elemento);
            }
        }

    }

    public void atualizar(String nome, String telefone, String cidade, String pais) {
        int soma = calculaHash(new Elemento(nome + "; ; ;"));
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
