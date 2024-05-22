package io.github.athirson010.cadastro_chaves_pix.domains.mappers;

import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.v2.ChaveResponseV2;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.v2.ContaResponseV2;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModelV2;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ContaModelV2;

import java.util.List;

public class ContaV2Mapper {
    public static ContaModelV2 of(CadastroChavePixRequest body) {
        return ContaModelV2.builder()
                .numeroAgencia(body.getNumeroAgencia())
                .numeroConta(body.getNumeroConta())
                .tipoConta(body.getTipoConta())
                .tipoPessoa(body.getTipoPessoa())
                .nomeCorrentista(body.getNomeCorrentista().concat(" ").concat(body.getSobrenomeCorrentista()))
                .build();
    }

    public static ContaResponseV2 of(ContaModelV2 conta, List<ChaveModelV2> chaves) {
        List<ChaveResponseV2> chaveResponseV2s = chaves.stream().map(ChaveV2Mapper::of).toList();
        return ContaResponseV2
                .builder()
                .numeroConta(conta.getNumeroConta())
                .numeroAgencia(conta.getNumeroAgencia())
                .nomeCorrentista(conta.getNomeCorrentista())
                .tipoConta(conta.getTipoConta())
                .chaves(chaveResponseV2s)
                .build();
    }
}
