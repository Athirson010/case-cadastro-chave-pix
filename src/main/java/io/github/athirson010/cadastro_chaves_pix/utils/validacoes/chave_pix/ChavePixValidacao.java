package io.github.athirson010.cadastro_chaves_pix.utils.validacoes.chave_pix;

import io.github.athirson010.cadastro_chaves_pix.services.ChaveService;

public interface ChavePixValidacao {

    ChavePixValidacao validarCaracteristicasChave(String valor);

    default void validarExistenciaChave(String valor, ChaveService chaveService) {
        ValidacaoChaveExistente validacaoChaveExistente = new ValidacaoChaveExistente(chaveService);
        validacaoChaveExistente.validar(valor);
    }
}
