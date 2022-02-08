package tpa.trabalho_pratico.hash;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Dados {
    private List<Elemento> elementos = new ArrayList<>();
}