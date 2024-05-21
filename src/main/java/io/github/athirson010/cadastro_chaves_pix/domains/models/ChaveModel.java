package io.github.athirson010.cadastro_chaves_pix.domains.models;

import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoContaEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoPessoaEnum;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("chave")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChaveModel extends AbstractModel {
    private String nomeCorrentista;
    private String numeroAgencia;
    private String numeroConta;
    private TipoContaEnum tipoConta;
    private TipoPessoaEnum tipoPessoa;
    private TipoChaveEnum tipoChave;
    private String valorChave;
    private LocalDateTime dataInclusao;
    private LocalDateTime dataInativacao;
    private StatusChaveEnum status;
}

//TODO Sugestão Conta ter Lista de CHAVES

