package io.github.athirson010.cadastro_chaves_pix.utils.validacoes.chave_pix.impl;

import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.ValidacaoRegex;
import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.chave_pix.ChavePixValidacao;


public class ChavePixValidacaoEmail implements ChavePixValidacao {
    @Override
    public ChavePixValidacao validarCaracteristicasChave(String valor) {
        String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.[a-zA-Z]{2,}$";
        ValidacaoRegex.validar(regex, valor);
        return this;
    }
}
