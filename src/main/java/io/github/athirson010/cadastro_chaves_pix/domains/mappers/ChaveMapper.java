package io.github.athirson010.cadastro_chaves_pix.domains.mappers;

import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.AtualizarChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.FiltroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.ChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModel;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModelV2;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ContaModelV2;

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
                .status(StatusChaveEnum.A)
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
        ChaveModel chave = new ChaveModel();
        chave.setTipoChave(request.getTipoChave());
        chave.setId(request.getId());
        chave.setDataInclusao(request.getDataInclusao() != null ? request.getDataInclusao().atStartOfDay() : null);
        chave.setDataInativacao(request.getDataInativacao() != null ? request.getDataInativacao().atStartOfDay() : null);
        chave.setStatus(request.getDataInativacao() != null ? StatusChaveEnum.I : StatusChaveEnum.A);
        chave.setNomeCorrentista(request.getNomeCorrentista());
        chave.setNumeroConta(request.getConta());
        chave.setNumeroAgencia(request.getAgencia());
        return chave;
    }

    public static ChaveModel of(AtualizarChavePixRequest request) {
        return ChaveModel.builder()
                .tipoConta(request.getTipoConta())
                .numeroConta(request.getNumeroConta())
                .numeroAgencia(request.getNumeroAgencia())
                .nomeCorrentista(request.getNomeCorrentista().concat(" ").concat(request.getSobrenomeCorrentista()))
                .build();
    }

    public static ChavePixResponse of(ChaveModelV2 update, ContaModelV2 conta) {
        return ChavePixResponse.builder()
                .id(update.getId())
                .numeroConta(conta.getNumeroConta())
                .numeroAgencia(conta.getNumeroAgencia())
                .nomeCorrentista(conta.getNomeCorrentista())
                .tipoConta(conta.getTipoConta())
                .tipoChave(update.getTipoChave())
                .valorChave(update.getValorChave())
                .dataInclusao(update.getDataInclusao())
                .dataInativacao(update.getDataInativacao())
                .build();
    }
}
