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

    public static CadastrarChavePixCommand toCommand(CadastrarChavePixRequest requisicao) {
        return new CadastrarChavePixCommand(
                requisicao.tipoChave(),
                requisicao.valorChave(),
                requisicao.tipoConta(),
                requisicao.numeroAgencia(),
                requisicao.numeroConta(),
                requisicao.nomeCorrentista(),
                requisicao.tipoPessoa()
        );
    }

    public static AtualizarChavePixCommand toCommand(String chavePixId, AtualizarChavePixRequest requisicao) {
        return new AtualizarChavePixCommand(
                chavePixId,
                requisicao.tipoConta(),
                requisicao.numeroAgencia(),
                requisicao.numeroConta(),
                requisicao.nomeCorrentista(),
                requisicao.tipoPessoa()
        );
    }

    public static FiltroChavePixQuery toQuery(FiltroChavePixRequest requisicao) {
        return new FiltroChavePixQuery(
                requisicao.chavePixId(),
                requisicao.tipoChave(),
                requisicao.numeroAgencia(),
                requisicao.numeroConta(),
                requisicao.nomeCorrentista(),
                requisicao.tipoPessoa(),
                requisicao.dataInclusaoInicio(),
                requisicao.dataInclusaoFim(),
                requisicao.dataInativacaoInicio(),
                requisicao.dataInativacaoFim()
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