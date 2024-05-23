package io.github.athirson010.cadastro_chaves_pix.utils.validacoes.impl;

import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.ChavePixValidacao;

import static io.github.athirson010.cadastro_chaves_pix.utils.validacoes.ValidacaoChave.validarRegex;

public class ChavePixValidacaoCelular implements ChavePixValidacao {
    @Override
    public ChavePixValidacao validarCaracteristicas(String valor) {
        String regex = "^\\+55\\d{12}$";
        validarRegex(regex, valor);
        return this;
    }
}

