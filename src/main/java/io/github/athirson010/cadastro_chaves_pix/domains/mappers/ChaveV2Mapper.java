package io.github.athirson010.cadastro_chaves_pix.domains.mappers;

import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.AtualizarChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.FiltroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.v2.ChaveResponseV2;
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
                .status(StatusChaveEnum.A)
                .build();
    }

    public static ChaveModelV2 of(FiltroChavePixRequest request) {
        ChaveModelV2 chaveModelV2 = ChaveModelV2.builder()
                .dataInclusao(request.getDataInclusao() != null ? request.getDataInclusao().atStartOfDay() : null)
                .dataInativacao(request.getDataInativacao() != null ? request.getDataInativacao().atStartOfDay() : null)
                .tipoChave(request.getTipoChave())
                .build();

        chaveModelV2.setId(request.getId());
        chaveModelV2.setStatus(chaveModelV2.getDataInativacao() != null ? StatusChaveEnum.I : StatusChaveEnum.A);
        return chaveModelV2;
    }

    public static ChaveResponseV2 of(ChaveModelV2 chaveModelV2) {
        return ChaveResponseV2.builder()
                .id(chaveModelV2.getId())
                .dataInativacao(chaveModelV2.getDataInativacao())
                .dataInclusao(chaveModelV2.getDataInclusao())
                .tipoChave(chaveModelV2.getTipoChave())
                .valorChave(chaveModelV2.getValorChave())
                .build();
    }

    public static ChaveModelV2 of(AtualizarChavePixRequest body) {
        return ChaveModelV2.builder()
                .tipoChave(body.getTipoChave() != null ? body.getTipoChave() : null)
                .valorChave(body.getValorChave() != null ? body.getValorChave() : null)
                .tipoChave(body.getTipoChave() != null ? body.getTipoChave() : null)
                .dataInclusao(LocalDateTime.now())
                .build();
    }
}
