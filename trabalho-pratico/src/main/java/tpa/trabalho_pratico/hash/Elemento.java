package tpa.trabalho_pratico.hash;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Elemento {
    private String nome;
    private String telefone;
    private String cidade;
    private String pais;

    public Elemento(String linha) {
        final String[] dados = linha.split(";");
        this.nome = dados[0];
        this.telefone = dados[1];
        this.cidade = dados[2];
        this.pais = dados[3];
    }

    @Override
    public String toString() {
        return nome + ';' + telefone + ';' + cidade + ';' + pais;
    }
}