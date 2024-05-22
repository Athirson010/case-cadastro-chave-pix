package io.github.athirson010.cadastro_chaves_pix.domains.mappers;

import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ContaModelV2;

public class ContaV2Mapper {
    public static ContaModelV2 of(CadastroChavePixRequest body) {
        return ContaModelV2.builder()
                .numeroAgencia(body.getNumeroAgencia())
                .numeroConta(body.getNumeroConta())
                .tipoConta(body.getTipoConta())
                .tipoPessoa(body.getTipoPessoa())
                .nomeCorrentista(body.getNomeCorrentista().concat(" ").concat(body.getSobrenomeCorrentista()))
                .build();
    }
}
