package io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.input.web.dto.requests;

import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoContaEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoPessoaEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CadastrarChavePixRequest(
        @NotNull(message = "Tipo de chave é obrigatório")
        TipoChaveEnum tipoChave,
        
        @NotBlank(message = "Valor da chave é obrigatório")
        String valorChave,
        
        @NotNull(message = "Tipo de conta é obrigatório")
        TipoContaEnum tipoConta,
        
        @NotBlank(message = "Número da agência é obrigatório")
        @Size(min = 4, max = 4, message = "Número da agência deve ter 4 dígitos")
        String numeroAgencia,
        
        @NotBlank(message = "Número da conta é obrigatório")
        @Size(min = 8, max = 8, message = "Número da conta deve ter 8 dígitos")
        String numeroConta,
        
        @NotBlank(message = "Nome do correntista é obrigatório")
        @Size(max = 30, message = "Nome do correntista deve ter no máximo 30 caracteres")
        String nomeCorrentista,
        
        @NotNull(message = "Tipo de pessoa é obrigatório")
        TipoPessoaEnum tipoPessoa
) {
}