package io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.input.web.dto.responses;

import io.github.athirson010.cadastro_chaves_pix.domain.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoChaveEnum;

import java.time.LocalDateTime;

public record ChavePixResponse(
        String id,
        String contaId,
        TipoChaveEnum tipoChave,
        String valorChave,
        LocalDateTime dataInclusao,
        LocalDateTime dataInativacao,
        StatusChaveEnum status
) {
}