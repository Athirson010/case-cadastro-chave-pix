package io.github.athirson010.cadastro_chaves_pix.domains.mappers;

import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.ChavePixResponse;
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

    public static ChavePixResponse of(ChaveEntity chave) {
        return ChavePixResponse.builder()
                .id(chave.getId())
                .numeroConta(chave.getConta().getNumeroConta())
                .numeroAgencia(chave.getConta().getNumeroAgencia())
                .nomeCorrentista(chave.getConta().getNomeCorrentista())
                .sobrenomeCorrentista(chave.getConta().getSobrenomeCorrentista())
                .tipoConta(chave.getConta().getTipoConta())
                .tipoChave(chave.getTipoChave())
                .valorChave(chave.getValorChave())
                .dataInclusao(chave.getDataInclusao())
                .dataInativacao(chave.getDataInativacao())
                .build();
    }
}
