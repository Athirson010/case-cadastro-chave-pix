package io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses;

import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoContaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
public class DelecaoChavePixResponse {
    private String id;
    private TipoChaveEnum tipoChave;
    private String valorChave;
    private TipoContaEnum tipoConta;
    private String numeroAgencia;
    private String numeroConta;
    private String nomeCorrentista;
    private String sobrenomeCorrentista;
    private LocalDateTime dataInclusao;
    private LocalDateTime dataInativacao;
}
