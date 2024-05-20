package io.github.athirson010.cadastro_chaves_pix.utils.validacoes.chave_pix.impl;

import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.ValidacaoRegex;
import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.chave_pix.ChavePixValidacao;

public class ChavePixValidacaoAleatoria implements ChavePixValidacao {
    @Override
    public ChavePixValidacao validarCaracteristicasChave(String valor) {
        String regex = "^[a-zA-Z0-9]{36}$"; //TODO Exemplo de chave aleatoria: zYxWvUtSrQpOnMlKjIhGfEdCbA123456, os bancos adiciona alguns "-" para completar os 36 caracteres.
        ValidacaoRegex.validar(regex, valor);
        return this;
    }
}
