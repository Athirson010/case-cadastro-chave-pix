package io.github.athirson010.cadastro_chaves_pix.business;

import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.responses.CadastroChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.utils.validates.*;
import org.springframework.stereotype.Service;

@Service
public class ChavePixBusiness {
    public CadastroChavePixResponse criarChaveComConta(CadastroChavePixRequest body) {
        ChavePixValidator validador = resgatarTipoValidacao(body.getTipoChave());
        validador.validarChave(body);
        return null;
    }

    private ChavePixValidator resgatarTipoValidacao(TipoChaveEnum tipoChave) {
        return switch (tipoChave) {
            case CELULAR -> new ChavePixValidatorCelular();
            case EMAIL -> new ChavePixValidatorEmail();
            case CPF -> new ChavePixValidatorCPF();
            case CNPJ -> new ChavePixValidatorCNPJ();
            case ALEATORIA -> new ChavePixValidatorAleatoria();
        };
    }
}
