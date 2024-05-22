package io.github.athirson010.cadastro_chaves_pix.utils.validacoes;

import io.github.athirson010.cadastro_chaves_pix.services.ChaveService;
import io.github.athirson010.cadastro_chaves_pix.services.v2.ChaveServiceV2;

public interface ChavePixValidacao {

    ChavePixValidacao validarCaracteristicasChave(String valor);

    default void validarExistenciaChave(String valor, ChaveService chaveService) {
        ValidacaoChave validacaoChave = new ValidacaoChave(chaveService);
        validacaoChave.validarExistencia(valor);
    }

    default void validarExistenciaChave(String valor, ChaveServiceV2 chaveService) {
        ValidacaoChave validacaoChave = new ValidacaoChave(chaveService);
        validacaoChave.validarExistencia(valor);
    }
}
