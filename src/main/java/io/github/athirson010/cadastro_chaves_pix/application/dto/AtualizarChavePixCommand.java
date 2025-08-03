package io.github.athirson010.cadastro_chaves_pix.application.dto;

import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoContaEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoPessoaEnum;

import java.util.Objects;

public record AtualizarChavePixCommand(
        String chavePixId,
        TipoContaEnum tipoConta,
        String numeroAgencia,
        String numeroConta,
        String nomeCorrentista,
        TipoPessoaEnum tipoPessoa
) {
    public AtualizarChavePixCommand {
        Objects.requireNonNull(chavePixId, "ID da chave PIX é obrigatório");
        Objects.requireNonNull(tipoConta, "Tipo de conta é obrigatório");
        Objects.requireNonNull(numeroAgencia, "Número da agência é obrigatório");
        Objects.requireNonNull(numeroConta, "Número da conta é obrigatório");
        Objects.requireNonNull(nomeCorrentista, "Nome do correntista é obrigatório");
        Objects.requireNonNull(tipoPessoa, "Tipo de pessoa é obrigatório");
    }
}