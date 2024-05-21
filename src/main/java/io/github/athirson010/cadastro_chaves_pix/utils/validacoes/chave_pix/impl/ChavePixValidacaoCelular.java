package io.github.athirson010.cadastro_chaves_pix.utils.validacoes.chave_pix.impl;

import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.ValidacaoRegex;
import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.chave_pix.ChavePixValidacao;

public class ChavePixValidacaoCelular implements ChavePixValidacao {
    @Override
    public ChavePixValidacao validarCaracteristicasChave(String valor) {
        String regex = "^\\+(?:[1-9][0-9])?(?:\\d{3})?\\d{9}$";
        ValidacaoRegex.validar(regex, valor);
        return this;
    }
}
