package io.github.athirson010.cadastro_chaves_pix.utils.validacoes.chave_pix.impl;

import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.ValidacaoCPF;
import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.ValidacaoRegex;
import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.chave_pix.ChavePixValidacao;

public class ChavePixValidacaoCPF implements ChavePixValidacao {
    @Override
    public ChavePixValidacao validarCaracteristicasChave(String valor) {
        String regex = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$";
        ValidacaoRegex.validar(regex, valor);
        if (!ValidacaoCPF.isCPF(valor)) {
            throw new ValidacaoException("CPF invalido: ".concat(valor));
        }
        return this;
    }
}
