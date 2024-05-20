package io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests;

import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoContaEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoPessoaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CadastroChavePixRequest {
    @NotNull
    private TipoChaveEnum tipoChave;
    @Length(max = 77)
    private String valorChave;
    @NotNull
    private TipoContaEnum tipoConta;
    @Length(max = 4)
    @Schema(example = "1234")
    private String numeroAgencia;
    @Length(max = 8)
    @Schema(example = "12345678")
    private String numeroConta;
    @Length(max = 30)
    @Schema(example = "Athirson")
    private String nomeCorrentista;
    @Length(max = 45)
    @Schema(example = "Candido")
    private String sobrenomeCorrentista;
    @NotNull
    private TipoPessoaEnum tipoPessoa;
}
