package io.github.athirson010.cadastro_chaves_pix.domains.mappers;

import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.entity.ContaEntity;

public class ContaMapper {
    public static ContaEntity of(CadastroChavePixRequest request) {
        return ContaEntity.builder()
                .numeroConta(request.getNumeroConta())
                .numeroAgencia(request.getNumeroAgencia())
                .nomeCorrentista(request.getNomeCorrentista())
                .sobrenomeCorrentista(request.getSobrenomeCorrentista())
                .tipoPessoa(request.getTipoPessoa())
                .tipoConta(request.getTipoConta())
                .build();
    }
}
