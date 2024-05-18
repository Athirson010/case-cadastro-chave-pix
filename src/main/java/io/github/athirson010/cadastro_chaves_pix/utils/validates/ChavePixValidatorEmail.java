package io.github.athirson010.cadastro_chaves_pix.utils.validates;

import io.github.athirson010.cadastro_chaves_pix.domains.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.utils.ValidacaoRegex;


public class ChavePixValidatorEmail implements ChavePixValidator {
    @Override
    public void validarChave(CadastroChavePixRequest request) {
        String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.[a-zA-Z]{2,}$";
        ValidacaoRegex.validar(regex, request.getValorChave());
    }
}
