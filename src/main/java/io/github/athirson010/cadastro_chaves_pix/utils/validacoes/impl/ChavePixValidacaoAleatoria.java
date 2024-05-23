package io.github.athirson010.cadastro_chaves_pix.utils.validacoes.impl;

import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.ChavePixValidacao;

import static io.github.athirson010.cadastro_chaves_pix.utils.validacoes.ValidacaoChave.validarRegex;

public class ChavePixValidacaoAleatoria implements ChavePixValidacao {
    @Override
    public ChavePixValidacao validarCaracteristicas(String valor) {
        String regex = "^[a-zA-Z0-9]{36}$"; //TODO Exemplo de chave aleatoria: zYxWvUtSrQpOnMlKjIhGfEdCbA123456, os bancos adiciona alguns "-" para completar os 36 caracteres.
        validarRegex(regex, valor);
        return this;
    }
}
