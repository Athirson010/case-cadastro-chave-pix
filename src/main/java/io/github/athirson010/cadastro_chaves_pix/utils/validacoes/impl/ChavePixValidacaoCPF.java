package io.github.athirson010.cadastro_chaves_pix.utils.validacoes.impl;

import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.ChavePixValidacao;

import static io.github.athirson010.cadastro_chaves_pix.utils.validacoes.ValidacaoChave.isCPF;
import static io.github.athirson010.cadastro_chaves_pix.utils.validacoes.ValidacaoChave.validarRegex;

public class ChavePixValidacaoCPF implements ChavePixValidacao {
    @Override
    public ChavePixValidacao validarCaracteristicas(String valor) {
        String regex = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$";
        if (!valor.matches(regex)) {
            valor = valor.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        }
        validarRegex(regex, valor);
        if (!isCPF(valor)) {
            throw new ValidacaoException("CPF inválido: ".concat(valor));
        }
        return this;
    }
}
