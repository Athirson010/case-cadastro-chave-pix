package io.github.athirson010.cadastro_chaves_pix.utils.validates;

import io.github.athirson010.cadastro_chaves_pix.domains.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.utils.ValidacaoRegex;

public class ChavePixValidatorCNPJ implements ChavePixValidator{
    @Override
    public void validarChave(CadastroChavePixRequest request) {
        String regex = "^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}-\\d{2}$|^\\d{14}$";
        ValidacaoRegex.validar(regex, request.getValorChave());
    }
}
