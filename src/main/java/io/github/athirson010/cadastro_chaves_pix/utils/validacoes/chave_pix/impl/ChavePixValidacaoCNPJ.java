package io.github.athirson010.cadastro_chaves_pix.utils.validacoes.chave_pix.impl;

import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.ValidacaoRegex;
import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.chave_pix.ChavePixValidacao;

public class ChavePixValidacaoCNPJ implements ChavePixValidacao {
    @Override
    public ChavePixValidacao validarCaracteristicasChave(String valor) {
        String regex = "^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}-\\d{2}$|^\\d{14}$";
        ValidacaoRegex.validar(regex, valor);
        return this;
    }
}
