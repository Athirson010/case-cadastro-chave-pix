package io.github.athirson010.cadastro_chaves_pix.utils.validacoes.chave_pix;

import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import io.github.athirson010.cadastro_chaves_pix.services.ChaveService;

public class ValidacaoChaveExistente {
    private final ChaveService chaveService;

    public ValidacaoChaveExistente(ChaveService chaveService) {
        this.chaveService = chaveService;
    }

    public void validar(String valor) {
        if (chaveService.buscarChavePorValorChave(valor).isPresent()) {
            throw new ValidacaoException();
        }
    }
}
