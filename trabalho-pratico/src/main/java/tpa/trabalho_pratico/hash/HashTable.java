package tpa.trabalho_pratico.hash;

import lombok.Getter;

@Getter
public class HashTable {

    private Dados array[] = new Dados[500000];

    public Integer calculaHash(Elemento elemento) {
        Integer soma = 0;
        final byte[] bytes = elemento.getNome().getBytes();
        for (int i = 0; i < bytes.length; i++) {
            soma += bytes[i] * 10 + i;
        }
        return soma %= 2069;
    }

    public void salvaElemento(Elemento elemento) {
        Integer soma = calculaHash(elemento);
        if (array[soma] == null) {
            final Dados dados = new Dados();
            dados.getElementos().add(elemento);
            array[soma] = dados;
        } else {
            array[soma].getElementos().add(elemento);
        }
    }
}
