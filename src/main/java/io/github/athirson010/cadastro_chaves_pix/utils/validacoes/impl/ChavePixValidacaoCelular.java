package io.github.athirson010.cadastro_chaves_pix.utils.validacoes.impl;

import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.ChavePixValidacao;

import static io.github.athirson010.cadastro_chaves_pix.utils.validacoes.ValidacaoChave.validarRegex;

public class ChavePixValidacaoCelular implements ChavePixValidacao {
    @Override
    public ChavePixValidacao validarCaracteristicasChave(String valor) {
        String regex = "^\\+(?:[1-9][0-9])?(?:\\d{3})?\\d{9}$";
        validarRegex(regex, valor);
        return this;
    }
}
