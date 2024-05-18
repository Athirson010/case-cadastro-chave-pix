package io.github.athirson010.cadastro_chaves_pix.utils.validates;

import io.github.athirson010.cadastro_chaves_pix.domains.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.utils.ValidacaoCPF;
import io.github.athirson010.cadastro_chaves_pix.utils.ValidacaoRegex;

public class ChavePixValidatorCPF implements ChavePixValidator {
    @Override
    public void validarChave(CadastroChavePixRequest request) {
        String regex = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$";
        ValidacaoRegex.validar(regex, request.getValorChave());
        if (!ValidacaoCPF.isCPF(request.getValorChave())) {
            throw new RuntimeException("CPF invalido");
        }
    }
}
