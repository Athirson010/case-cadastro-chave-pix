package io.github.athirson010.cadastro_chaves_pix.utils.validacoes.impl;

import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.ChavePixValidacao;

import static io.github.athirson010.cadastro_chaves_pix.utils.validacoes.ValidacaoChave.validarRegex;

public class ChavePixValidacaoCNPJ implements ChavePixValidacao {
    @Override
    public ChavePixValidacao validarCaracteristicas(String valor) {
        String regex = "^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}-\\d{2}$|^\\d{14}$";
        validarRegex(regex, valor);
        return this;
    }
}
