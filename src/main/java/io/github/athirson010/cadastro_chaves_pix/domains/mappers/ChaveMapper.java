package io.github.athirson010.cadastro_chaves_pix.domains.mappers;

import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.FiltroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.ChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModel;

import java.time.LocalDateTime;

public class ChaveMapper {
    public static ChaveModel of(CadastroChavePixRequest request) {
        return ChaveModel.builder()
                .nomeCorrentista(request.getNomeCorrentista().concat(" ").concat(request.getSobrenomeCorrentista()))
                .numeroConta(request.getNumeroConta())
                .numeroAgencia(request.getNumeroAgencia())
                .tipoPessoa(request.getTipoPessoa())
                .valorChave(request.getValorChave())
                .tipoConta(request.getTipoConta())
                .status(StatusChaveEnum.ATIVA)
                .dataInclusao(LocalDateTime.now())
                .tipoChave(request.getTipoChave())
                .build();
    }

    public static ChavePixResponse of(ChaveModel chave) {
        return ChavePixResponse.builder()
                .id(chave.getId())
                .numeroConta(chave.getNumeroConta())
                .numeroAgencia(chave.getNumeroAgencia())
                .nomeCorrentista(chave.getNomeCorrentista())
                .tipoConta(chave.getTipoConta())
                .tipoChave(chave.getTipoChave())
                .valorChave(chave.getValorChave())
                .dataInclusao(chave.getDataInclusao())
                .dataInativacao(chave.getDataInativacao())
                .build();
    }

    public static ChaveModel of(FiltroChavePixRequest request) {
        return ChaveModel
                .builder()
                .tipoChave(request.getTipoChave())
                .dataInclusao(request.getDataInclusao() != null ? request.getDataInclusao().atStartOfDay() : null)
                .dataInativacao(request.getDataInativacao() != null ? request.getDataInativacao().atStartOfDay() : null)
                .nomeCorrentista(request.getNomeCorrentista())
                .numeroAgencia(request.getAgencia())
                .numeroConta(request.getConta())
                .build();
    }
}
