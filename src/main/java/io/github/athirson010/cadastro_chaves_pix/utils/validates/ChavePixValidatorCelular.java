package io.github.athirson010.cadastro_chaves_pix.utils.validates;

import io.github.athirson010.cadastro_chaves_pix.domains.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.utils.ValidacaoRegex;

public class ChavePixValidatorCelular implements ChavePixValidator {

    @Override
    public void validarChave(CadastroChavePixRequest request) {
        String regex = "^\\+(?:[1-9][0-9])?(?:\\d{3})?\\d{9}$";
        ValidacaoRegex.validar(regex, request.getValorChave());
    }
}
