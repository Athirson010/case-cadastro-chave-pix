package io.github.athirson010.cadastro_chaves_pix.domains.models;

import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoContaEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoPessoaEnum;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("conta")
@EqualsAndHashCode(callSuper = true)
@Builder
public class ContaModel extends AbstractModel {
    private String nomeCorrentista;
    private String numeroAgencia;
    private String numeroConta;
    private TipoContaEnum tipoConta;
    private TipoPessoaEnum tipoPessoa;
}

