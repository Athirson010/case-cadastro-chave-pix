package io.github.athirson010.cadastro_chaves_pix.domains.requests;

import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoContaEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoPessoaEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CadastroChavePixRequest{
    @NotBlank
    private TipoChaveEnum tipoChave;
    @Length(max = 77)
    private String valorChave;
    @NotBlank
    private TipoContaEnum tipoConta;
    @Length(max = 4)
    private String numeroAgencia;
    @Length(max = 8)
    private String numeroConta;
    @Length(max = 30)
    private String numeroCorrentista;
    @Length(max = 45)
    private String sobrenomeCorrentista;
    @NotBlank
    private TipoPessoaEnum tipoPessoa;
}
