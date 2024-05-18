package io.github.athirson010.cadastro_chaves_pix.utils.validates;

import io.github.athirson010.cadastro_chaves_pix.domains.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.utils.ValidacaoRegex;

public class ChavePixValidatorAleatoria implements ChavePixValidator {
    @Override
    public void validarChave(CadastroChavePixRequest request) {
        String regex = "^[a-zA-Z0-9]{36}$"; //TODO Exemplo de chave aleatoria: zYxWvUtSrQpOnMlKjIhGfEdCbA123456, os bancos adiciona alguns "-" para completar os 36 caracteres.
        ValidacaoRegex.validar(regex, request.getValorChave());
    }
}
