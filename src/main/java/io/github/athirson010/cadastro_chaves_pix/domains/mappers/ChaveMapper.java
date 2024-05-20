package io.github.athirson010.cadastro_chaves_pix.domains.mappers;

import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.entity.ChaveEntity;
import io.github.athirson010.cadastro_chaves_pix.domains.entity.ContaEntity;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;

import java.time.LocalDateTime;

public class ChaveMapper {
    public static ChaveEntity of(CadastroChavePixRequest request, ContaEntity conta) {
        return ChaveEntity.builder()
                .conta(conta)
                .valorChave(request.getValorChave())
                .status(StatusChaveEnum.ATIVA)
                .dataInclusao(LocalDateTime.now())
                .tipoChave(request.getTipoChave())
                .build();
    }
}
