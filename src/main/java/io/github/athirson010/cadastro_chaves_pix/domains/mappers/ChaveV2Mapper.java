package io.github.athirson010.cadastro_chaves_pix.domains.mappers;

import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModelV2;
import io.github.athirson010.cadastro_chaves_pix.utils.AbstractModel;

import java.time.LocalDateTime;

public class ChaveV2Mapper {
    public static ChaveModelV2 of(CadastroChavePixRequest body, AbstractModel save) {
        return ChaveModelV2.builder()
                .contaId(save.getId())
                .valorChave(body.getValorChave())
                .dataInclusao(LocalDateTime.now())
                .tipoChave(body.getTipoChave())
                .status(StatusChaveEnum.ATIVA)
                .build();
    }
//    public static ChaveModelV2 of(FiltroChavePixRequest request) {
//        return ChaveModelV2.builder()
//                .contaId(save.getId())
//                .valorChave(body.getValorChave())
//                .dataInclusao(LocalDateTime.now())
//                .tipoChave(body.getTipoChave())
//                .status(StatusChaveEnum.ATIVA)
//                .build();
//    }
}
