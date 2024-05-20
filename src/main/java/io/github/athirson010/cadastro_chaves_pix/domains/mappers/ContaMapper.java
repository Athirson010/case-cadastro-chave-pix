package io.github.athirson010.cadastro_chaves_pix.domains.mappers;

import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ContaModel;

public class ContaMapper {
    public static ContaModel of(CadastroChavePixRequest request) {
        return ContaModel.builder()
                .numeroConta(request.getNumeroConta())
                .numeroAgencia(request.getNumeroAgencia())
                .nomeCorrentista(request.getNomeCorrentista().concat(" ")
                        .concat(request.getSobrenomeCorrentista()))
                .tipoPessoa(request.getTipoPessoa())
                .tipoConta(request.getTipoConta())
                .build();
    }
}
