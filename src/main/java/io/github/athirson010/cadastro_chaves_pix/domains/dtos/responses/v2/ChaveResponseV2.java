package io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.v2;

import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoChaveEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
public class ChaveResponseV2 {
    private String id;
    private TipoChaveEnum tipoChave;
    private String valorChave;
    private LocalDateTime dataInclusao;
    private LocalDateTime dataInativacao;
}
