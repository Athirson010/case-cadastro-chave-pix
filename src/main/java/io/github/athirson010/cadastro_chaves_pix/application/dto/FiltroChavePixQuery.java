package io.github.athirson010.cadastro_chaves_pix.application.dto;

import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoPessoaEnum;

import java.time.LocalDateTime;

public record FiltroChavePixQuery(
        String chavePixId,
        TipoChaveEnum tipoChave,
        String numeroAgencia,
        String numeroConta,
        String nomeCorrentista,
        TipoPessoaEnum tipoPessoa,
        LocalDateTime dataInclusaoInicio,
        LocalDateTime dataInclusaoFim,
        LocalDateTime dataInativacaoInicio,
        LocalDateTime dataInativacaoFim
) {
}