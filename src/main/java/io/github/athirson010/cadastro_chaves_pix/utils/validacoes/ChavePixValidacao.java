package io.github.athirson010.cadastro_chaves_pix.utils.validacoes;

import io.github.athirson010.cadastro_chaves_pix.services.ChaveService;
import io.github.athirson010.cadastro_chaves_pix.services.v2.ChaveServiceV2;

public interface ChavePixValidacao {
    ChavePixValidacao validarCaracteristicas(String valor);

    default ChavePixValidacao validarExistenciaChave(String valor, ChaveService chaveService) {
        ValidacaoChave validacaoChave = new ValidacaoChave(chaveService);
        validacaoChave.validarExistencia(valor);
        return this;
    }

    default ChavePixValidacao validarExistenciaChave(String valor, ChaveServiceV2 chaveService) {
        ValidacaoChave validacaoChave = new ValidacaoChave(chaveService);
        validacaoChave.validarExistencia(valor);
        return this;
    }

    default void validarDadosConta(String numeroAgencia, String numeroConta) {
        ValidacaoChave.validarRegex("^\\d{1,4}$", numeroAgencia);
        ValidacaoChave.validarRegex("^\\d{1,8}$", numeroConta);
    }

}
