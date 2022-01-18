package tpa.trabalho_pratico;

import lombok.extern.slf4j.Slf4j;
import tpa.trabalho_pratico.util.ArquivoUtil;

@Slf4j
public class App {
    public static void main(String[] args) {
        log.info("Iniciada a geracao");
        ArquivoUtil.geraArquivoTeste(1L);
        log.info("Fim da geracao");
    }
}
