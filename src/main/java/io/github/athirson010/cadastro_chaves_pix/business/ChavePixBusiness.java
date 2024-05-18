package io.github.athirson010.cadastro_chaves_pix.business;


import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.responses.CadastroChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.utils.validates.ChavePixValidator;
import io.github.athirson010.cadastro_chaves_pix.utils.validates.ChavePixValidatorCPF;
import io.github.athirson010.cadastro_chaves_pix.utils.validates.ChavePixValidatorCelular;
import io.github.athirson010.cadastro_chaves_pix.utils.validates.ChavePixValidatorEmail;
import org.springframework.stereotype.Service;

@Service
public class ChavePixBusiness {
    public CadastroChavePixResponse criarChaveComConta(CadastroChavePixRequest body) {
        ChavePixValidator validador = resgatarTipoValidacao(body.getTipoChave());
        validador.validarChave(body);
        return null;
    }

    private ChavePixValidator resgatarTipoValidacao(TipoChaveEnum tipoChave) {
        ChavePixValidator retorno = null;
        switch (tipoChave) {
            case CELULAR:
                retorno = new ChavePixValidatorCelular();
                break;
            case EMAIL:
                retorno = new ChavePixValidatorEmail();
                break;
            case CPF:
                retorno = new ChavePixValidatorCPF();
                break;
            case CNPJ:
                retorno = new ChavePixValidatorCPF();
                break;
            case ALEATORIA:
                retorno = new ChavePixValidatorCPF();
                break;
        }
        return retorno;
    }
}
