package io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.input.web.mappers;

import io.github.athirson010.cadastro_chaves_pix.application.dto.AtualizarChavePixCommand;
import io.github.athirson010.cadastro_chaves_pix.application.dto.CadastrarChavePixCommand;
import io.github.athirson010.cadastro_chaves_pix.application.dto.FiltroChavePixQuery;
import io.github.athirson010.cadastro_chaves_pix.domain.entities.ChavePix;
import io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.input.web.dto.requests.AtualizarChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.input.web.dto.requests.CadastrarChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.input.web.dto.requests.FiltroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.input.web.dto.responses.ChavePixResponse;

public final class ChavePixWebMapper {

    private ChavePixWebMapper() {
    }

    public static CadastrarChavePixCommand toCommand(CadastrarChavePixRequest request) {
        return new CadastrarChavePixCommand(
                request.tipoChave(),
                request.valorChave(),
                request.tipoConta(),
                request.numeroAgencia(),
                request.numeroConta(),
                request.nomeCorrentista(),
                request.tipoPessoa()
        );
    }

    public static AtualizarChavePixCommand toCommand(String chavePixId, AtualizarChavePixRequest request) {
        return new AtualizarChavePixCommand(
                chavePixId,
                request.tipoConta(),
                request.numeroAgencia(),
                request.numeroConta(),
                request.nomeCorrentista(),
                request.tipoPessoa()
        );
    }

    public static FiltroChavePixQuery toQuery(FiltroChavePixRequest request) {
        return new FiltroChavePixQuery(
                request.chavePixId(),
                request.tipoChave(),
                request.numeroAgencia(),
                request.numeroConta(),
                request.nomeCorrentista(),
                request.tipoPessoa(),
                request.dataInclusaoInicio(),
                request.dataInclusaoFim(),
                request.dataInativacaoInicio(),
                request.dataInativacaoFim()
        );
    }

    public static ChavePixResponse toResponse(ChavePix chavePix) {
        return new ChavePixResponse(
                chavePix.getId().getValue(),
                chavePix.getContaId().getValue(),
                chavePix.getTipoChave(),
                chavePix.getValorChave().getValue(),
                chavePix.getDataInclusao(),
                chavePix.getDataInativacao(),
                chavePix.getStatus()
        );
    }
}