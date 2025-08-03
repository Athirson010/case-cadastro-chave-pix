package io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.input.web.dto.responses;

import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoContaEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoPessoaEnum;

import java.time.LocalDateTime;

public record ContaResponse(
        String id,
        TipoContaEnum tipoConta,
        String numeroAgencia,
        String numeroConta,
        String nomeCorrentista,
        TipoPessoaEnum tipoPessoa,
        LocalDateTime dataInclusao
) {
}